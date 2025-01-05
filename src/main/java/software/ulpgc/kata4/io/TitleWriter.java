package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Title;

public interface TitleWriter extends AutoCloseable {
    void write(Title title);
}
