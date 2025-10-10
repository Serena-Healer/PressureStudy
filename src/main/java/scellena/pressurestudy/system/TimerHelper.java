package scellena.pressurestudy.system;

import scellena.pressurestudy.enums.EnumScene;
import scellena.pressurestudy.jframe.clips.ClipPlayerHandler;

import static scellena.pressurestudy.system.MainSystem.gameData;

public class TimerHelper {

    public static void onTick(){
        if(gameData.scene == EnumScene.IN_GAME) {
            if (gameData.charge) {
                gameData.timeLeft += (gameData.timeMax / 200);
                if (gameData.timeMax <= gameData.timeLeft) {
                    ClipPlayerHandler.stopBGM();
                    if(gameData.bgmId == 1){
                        ClipPlayerHandler.playBGM(MainSystem.gameData.quiz.getBgmAlternateName());
                    }else{
                        ClipPlayerHandler.playBGM(MainSystem.gameData.quiz.getBgmNormalName());
                    }
                    MainSystem.reset();
                    gameData.charge = false;
                    gameData.timeLeft = gameData.timeMax + 60;
                }
            } else {
                if (gameData.timeLeft >= 1) gameData.timeLeft--;
                if (gameData.timeLeft == 20 * 30 && gameData.timeMax > 20 * 30) {
                    ClipPlayerHandler.playBGM(MainSystem.gameData.quiz.getBgmLastName());
                }
                if (gameData.timeLeft == 0) {
                    if(gameData.repeat > 0){
                        gameData.repeat--;
                        gameData.timeLeft = gameData.quiz.getTimer();
                        MainSystem.gameData.currentId++;
                    }else {
                        MainSystem.gameData.bgmClip.stop();
                        ClipPlayerHandler.stopBGM();
                        ClipPlayerHandler.play(MainSystem.gameData.quiz.getBgmTimeUpName());
                        QuizSystem.lose();
                    }
                }
            }
        }
    }

    public static void chargeAndStart(int time){
        gameData.timeLeft = 0;
        gameData.timeMax = time;
        gameData.charge = true;
        ClipPlayerHandler.playChargeBGM("解答時間CHARGE中.wav", true);
    }

    public static void startWithoutCharge(int time){
        gameData.timeLeft = time;
        gameData.timeMax = time;
        gameData.charge = true;
        ClipPlayerHandler.stopBGM();
    }

    public static void extend(int duration){
        gameData.timeLeft += duration;
        Utilities.sendMessageToAll((duration / 20) + " 秒延長しました！");
    }

}
