package scellena.pressurestudy.translator;

import org.bukkit.entity.Player;
import scellena.pressurestudy.enums.EnumLanguage;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TranslatorsHandler {

    public static final List<TranslatorBase> TRANSLATORS = new ArrayList<>();

    public static void init(){
        TRANSLATORS.add(new TranslatorMain());
    }

    public static String translate(String key, Player player, Object ... values){
        return translate(key, MainSystem.getPlayerData(player).language, values);
    }

    public static String translate(String key, Object ... values){
        return translate(key, MainSystem.gameData.serverLanguage, values);
    }

    public static String translate(String key, EnumLanguage player, Object ... values){
        AtomicReference<String> string = new AtomicReference<>(key);
        TRANSLATORS.forEach(t -> {
            String ret = t.translate(key, player, values);
            if(!ret.equals(key)){
                string.set(ret);
            }
        });
        String val = string.get();
        StringBuilder ans = new StringBuilder();
        int j = 0;
        for(int i=0; i<val.length(); i++){
            int tMode = -1;
            if(val.substring(i).startsWith("%s")) tMode = 0;
            if(val.substring(i).startsWith("%t")) tMode = 1;
            if(val.substring(i).startsWith("%p")) tMode = 2;
            if(tMode >= 0){
                String v = values[j].toString();
                if(values[j] instanceof Integer && tMode == 1){
                    v = Utilities.formatTimeString((Integer) values[j]);
                }else if(values[j] instanceof Double && tMode == 1){
                    v = Utilities.formatTimeString(((Double) values[j]).intValue());
                }else if(values[j] instanceof Double && tMode == 2){
                    v = (int)Math.abs((Double) values[j] * 100.0 - 100.0) + "%";
                }else if(values[j] instanceof Double){
                    v = String.format("%.1s", values[j]);
                }
                ans.append(translate(v, player, values));
                i++;
                j++;
            }else{
                ans.append(val.charAt(i));
            }
        }
        return ans.toString();
    }

}
