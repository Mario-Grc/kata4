package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Title;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static java.sql.Types.INTEGER;
import static java.sql.Types.NVARCHAR;

public class DatabaseTitleWriter implements TitleWriter {
    private final Connection connection;
    private final PreparedStatement insertTitlePreparedStatement;

    public DatabaseTitleWriter(String connection) throws SQLException {
        this(DriverManager.getConnection(connection));
    }

    public static DatabaseTitleWriter open(File file) throws SQLException {
        return new DatabaseTitleWriter("jdbc:sqlite:" + file.getAbsolutePath());
    }

    private final static String CreateTitlesTableStatement = """
        CREATE TABLE IF NOT EXISTS titles (
            id TEXT PRIMARY KEY,
            title TEXT NOT NULL,
            year INTEGER,
            duration INTEGER)
        """;
    public DatabaseTitleWriter(Connection connection) throws SQLException {
        this.connection = connection;
        this.connection.setAutoCommit(false);
        this.connection.createStatement().execute(CreateTitlesTableStatement);
        this.insertTitlePreparedStatement = this.createInsertStatement();
    }

    private final static String InsertTitleStatement = """
        INSERT INTO titles (id, title, year, duration)
        VALUES (?, ?, ?, ?)
        """;
    private PreparedStatement createInsertStatement() throws SQLException {
        return connection.prepareStatement(InsertTitleStatement);
    }

    @Override
    public void write(Title title) {
        try {
            insertTitleStatementFor(title).execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement insertTitleStatementFor(Title title) throws SQLException {
        insertTitlePreparedStatement.clearParameters();
        parametersOf(title).forEach(this::define);
        return insertTitlePreparedStatement;
    }

    private void define(Parameter parameter) {
        try {
            if (parameter.value == null){
                insertTitlePreparedStatement.setNull(parameter.index, parameter.type);
            } else{
                insertTitlePreparedStatement.setObject(parameter.index, parameter.value);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private List<Parameter> parametersOf(Title title) {
        return List.of(
                new Parameter(1, title.id(), NVARCHAR),
                new Parameter(2, title.name(), NVARCHAR),
                new Parameter(3, title.year() != -1 ? title.year() : null, INTEGER),
                new Parameter(4, title.duration() != -1 ? title.duration() : null, INTEGER)
        );
    }

    private record Parameter(int index, Object value, int type) {}
    @Override
    public void close() throws Exception {
        connection.commit();
        connection.close();
    }
}
