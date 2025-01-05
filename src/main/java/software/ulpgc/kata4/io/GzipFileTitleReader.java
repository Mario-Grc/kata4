package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Title;

import java.io.*;
import java.util.zip.GZIPInputStream;

public class GzipFileTitleReader implements TitleReader{
    private final BufferedReader reader;
    private final TitleDeserializer deserializer;

    public GzipFileTitleReader(File file, TitleDeserializer deserializer) throws IOException {
        this.reader = readerOf(file);
        this.deserializer = deserializer;
        this.skipHeader();
    }

    private void skipHeader() throws IOException {
        this.reader.readLine();
    }

    private BufferedReader readerOf(File file) throws IOException {
        return new BufferedReader(new InputStreamReader(gzipInputStreamOf(file)));
    }

    private GZIPInputStream gzipInputStreamOf(File file) throws IOException {
        return new GZIPInputStream(new FileInputStream(file));
    }

    @Override
    public Title read() throws IOException {
        return deserialize(reader.readLine());
    }

    private Title deserialize(String line) {
        return line != null ? deserializer.deserialize(line) : null;
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
