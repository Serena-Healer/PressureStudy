package scellena.pressurestudy.system;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TextFileLoader {

    public static List<String> load(String path){
        try {
            return Files.readAllLines(Paths.get(MainSystem.gameData.cd + path));
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
