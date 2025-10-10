package scellena.pressurestudy.translator;

import scellena.pressurestudy.enums.EnumLanguage;

public abstract class TranslatorBase {

    public String translate(String key, EnumLanguage player, Object ... args){
        return key;
    }

}
