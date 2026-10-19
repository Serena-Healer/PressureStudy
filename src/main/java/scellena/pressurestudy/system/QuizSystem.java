package scellena.pressurestudy.system;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import scellena.pressurestudy.data.AnswererInformation;
import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.enums.EnumScene;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.clips.ClipPlayerHandler;
import scellena.pressurestudy.jframe.layout.study.LayoutMineSweeper;

import javax.sound.sampled.Clip;

public class QuizSystem {

    public static void ac(int number){
        if(!MainSystem.gameData.answered[number]) {
            ClipPlayerHandler.play("Qさま正解音.wav");

            if(MainSystem.gameData.quiz.getQuizList().size() > number && MainSystem.gameData.scene == EnumScene.IN_GAME){
                AnswererInformation info = MainSystem.gameData.answerBox.get(MainSystem.gameData.currentId);
                info.ac++;
                if(!(MainSystem.gameData.currentLayout instanceof LayoutMineSweeper)){
                    if(number >= 7) info.finePlay++;
                    if(number >= 9 && MainSystem.gameData.quiz.isDifficultyFlag()) info.finePlay++;
                }
            }

            if(MainSystem.gameData.quiz.getRepeat() <= 0 && !MainSystem.gameData.quiz.isNoAutoPass()) {
                MainSystem.gameData.currentId++;
            }
            MainSystem.gameData.answered[number] = true;
            MainSystem.gameData.finePlayDisplayTick = 20 * 5;
            MainSystem.gameData.lastACNumber = number;
            /*
            if (MainSystem.gameData.currentId >= Math.min(10, MainSystem.gameData.quiz.getQuizList().size()) && MainSystem.gameData.scene == EnumScene.IN_GAME) {
                win();
            }
             */
            if(MainSystem.gameData.quiz.getWaCountOption() == -1 && MainSystem.gameData.currentId == 9){
                RenderUtils.showTelop("リーダーは間違えたら即終了");
            }

            int ac = 0;
            for(int i=0; i<MainSystem.gameData.quiz.getQuizList().size(); i++){
                ac += (MainSystem.gameData.answered[i] ? 1 : 0);
            }
            if(ac >= Math.min(MainSystem.gameData.quiz.getAcCountRequired(), MainSystem.gameData.quiz.getQuizList().size())) win();
        }
    }

    public static void win(){
        if(MainSystem.gameData.scene == EnumScene.IN_GAME) {
            ClipPlayerHandler.stopBGM();
            ClipPlayerHandler.play("Qさまクリア.wav");
            MainSystem.gameData.isResultWin = true;
            MainSystem.gameData.resultDisplayTick = 20 * 10;
            MainSystem.onEnd();
        }
    }

    public static void lose(boolean playSound){
        if(MainSystem.gameData.scene == EnumScene.IN_GAME) {
            ClipPlayerHandler.stopBGM();
            if(playSound) ClipPlayerHandler.play("Qさま失敗.wav");
            MainSystem.gameData.isResultWin = false;
            MainSystem.gameData.resultDisplayTick = 20 * 10;
            MainSystem.onEnd();
        }
    }

    public static void wa(int number){
        ClipPlayerHandler.play("Qさま不正解音.wav");
        MainSystem.gameData.waCount++;
        MainSystem.gameData.waDisplayTick = 60;
        MainSystem.gameData.sizePaused = false;
        if(MainSystem.gameData.quiz.getWaCountAllowed() > 0 && MainSystem.gameData.quiz.getWaCountAllowed() <= MainSystem.gameData.waCount){
            MainSystem.gameData.failed[number] = true;
            lose(true);
        }else if(MainSystem.gameData.quiz.getWaCountAllowed() == MainSystem.gameData.waCount + 1){
            RenderUtils.showTelop("次 間違えたら即終了（問題は変更できない）");
        }
    }

    public static void select(int number){
        MainSystem.gameData.selectedNumber = number;
        MainSystem.gameData.waCount = 0;
        MainSystem.gameData.sizePaused = false;

        QuizInformationBase.SingleQuizInformation quiz = MainSystem.gameData.quiz.getQuizList().get(number);
        Utilities.sendMessageToAll("問題番号: " + ChatColor.GREEN + (number + 1));
        Utilities.sendMessageToAll(quiz.getTitle());
        if(quiz.getHint() != null) Utilities.sendMessageToAll(quiz.getHint());
        if(!quiz.getMusicPath().isEmpty()) ClipPlayerHandler.playBGM(quiz.getMusicPath());
        for(Player player : Bukkit.getOnlinePlayers()){
            if(player.getScoreboardTags().contains("admin")){
                player.sendMessage("正解: " + ChatColor.RED + quiz.getAnswer());
            }
        }
    }

}
