package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Title;

import java.io.IOException;

public interface TitleReader extends AutoCloseable {
    Title read() throws IOException;
}
