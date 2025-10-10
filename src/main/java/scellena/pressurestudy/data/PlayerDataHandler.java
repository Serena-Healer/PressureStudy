package scellena.pressurestudy.data;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import scellena.pressurestudy.enums.EnumLanguage;
import scellena.pressurestudy.system.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerDataHandler {

    public Player owner;
    public ScoreboardHandler score;

    public EnumLanguage language = EnumLanguage.JAPANESE;

    int saveCount = 0;

    public PlayerDataHandler(Player player){
        if(player != null)score = new ScoreboardHandler(player);
    }

    public void read(JsonReader reader) throws IOException{
        String name = reader.nextName();
        if(name.equals("saveCount"))saveCount = reader.nextInt();
    }

    public final void readAll(JsonReader reader){
        try {
            while (reader.hasNext()) {
                read(reader);
            }
        }catch(Exception e){
            Utilities.error(e);
        }
    }

    public void write(JsonWriter writer) throws IOException{
        try {
            writer.name("saveCount").value(saveCount + 1);
        }catch(Exception e){
            Utilities.error(e);
        }
    }

    public final void writeAll(JsonWriter writer){
        try{
            write(writer);
        }catch(Exception e){
            Utilities.error(e);
        }
    }

}
