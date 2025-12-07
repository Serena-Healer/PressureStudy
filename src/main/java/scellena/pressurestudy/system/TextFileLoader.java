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

    public static void save(String path, List<String> text){
        try {
            if(!Files.exists(Paths.get(MainSystem.gameData.cd + path))) Files.createFile(Paths.get(MainSystem.gameData.cd + path));
            List<String> baseTxt = Files.readAllLines(Paths.get(MainSystem.gameData.cd + path));
            baseTxt.add("");
            baseTxt.addAll(text);
            Files.write(Paths.get(MainSystem.gameData.cd + path), baseTxt);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
