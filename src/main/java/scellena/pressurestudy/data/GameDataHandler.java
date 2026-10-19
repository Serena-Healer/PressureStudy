package scellena.pressurestudy.data;

import scellena.pressurestudy.enums.EnumLanguage;
import scellena.pressurestudy.enums.EnumScene;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.jframe.layout.study.LayoutTitle;

import javax.sound.sampled.Clip;
import java.util.*;

public class GameDataHandler {

    public EnumLanguage serverLanguage = EnumLanguage.JAPANESE;
    public int frames = 0;

    public EnumScene scene = EnumScene.LOBBY;

    public int timeLeft = 0;
    public int timeMax = 20 * 180;
    public int repeat = 0;
    public boolean charge = false;
    public int currentId = 0;
    public int demoTick = 0;

    public LayoutBase currentLayout = new LayoutTitle();
    public Clip bgmClip;
    public Clip bgmClipCharge;
    public String cd = "";
    public String assetPath = "C:\\Users\\ScellenaHealer\\Documents\\クイズ\\素材\\";
    public int waDisplayTick = 0;
    public int finePlayDisplayTick = 0;
    public int lastACNumber = 0;
    public boolean isResultWin = false;
    public int resultDisplayTick = 0;
    public String lastTelop = "";
    public int telopTick = 0;

    public List<AnswererInformation> answerBox = new ArrayList<>();
    public String teamName = "";
    public boolean[] answered = new boolean[256];
    public boolean[] failed = new boolean[256];
    public int[] sizeElapsed = new int[256];
    public boolean paused = false;
    public boolean sizePaused = false;
    public QuizInformationBase quiz;
    public int selectedNumber = -1;
    public int waCount = 0;

    public String relayHint = "";
    public int bgmId = 0;

}
