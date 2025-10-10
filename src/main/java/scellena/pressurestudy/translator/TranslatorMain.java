package scellena.pressurestudy.translator;

import org.bukkit.ChatColor;
import scellena.pressurestudy.enums.EnumLanguage;

public class TranslatorMain extends TranslatorBase{

    @Override
    public String translate(String key, EnumLanguage player, Object... args) {
        switch(key){
            case "translationkey": return "翻訳先テキスト";
        }
        return super.translate(key, player, args);
    }

}
