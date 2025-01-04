package software.ulpgc.kata4.io;

import software.ulpgc.kata4.model.Title;

public interface TitleDeserializer {
    Title deserialize(String line);
}
