package scellena.pressurestudy.data;

import java.awt.*;

public class AnswererInformation {

    String name;
    String description;
    Color color;

    public void loadInformation(String s){
        String[] args = s.split(",");
        name = args[0];
        description = args[1];
        if(args.length > 2 && args[2].equals("red")){
            color = new Color(224, 0, 0);
        }else{
            color = new Color(0, 64, 224);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Color getColor() {
        return color;
    }
}
