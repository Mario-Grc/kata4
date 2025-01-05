package software.ulpgc.kata4;

import software.ulpgc.kata4.control.ImportCommand;
import software.ulpgc.kata4.ui.ImportDialog;

import java.io.File;

public class Main {
    public static void main(String[] args){
        new ImportCommand(importDialog()).execute();
    }

    private static ImportDialog importDialog() {
        return () -> new File("G:/Otros ordenadores/Mi portátil/Año_3/1ºSemestre/IS2/Práctica/title.basics.tsv/title.basics.tsv.gz");
    }
}
