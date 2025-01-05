package software.ulpgc.kata4.control;

import software.ulpgc.kata4.io.*;
import software.ulpgc.kata4.model.Title;
import software.ulpgc.kata4.ui.ImportDialog;

import java.io.File;
import java.io.IOException;

public class ImportCommand implements Command {
    private final ImportDialog dialog;

    public ImportCommand(ImportDialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void execute() {
        try (TitleReader reader = new GzipFileTitleReader(dialog.get(), new TsvTitleDeserializer());
             TitleWriter writer = DatabaseTitleWriter.open(new File("movies.db"))){
            doExecute(reader, writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void doExecute(TitleReader reader, TitleWriter writer) throws IOException {
        while(true){
            Title title = reader.read();
            if (title == null) break;
            writer.write(title);
        }
    }
}
