package scellena.pressurestudy.jframe.clips;

import scellena.pressurestudy.system.MainSystem;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class ClipPlayerHandler {

    public static Clip createClip(File path) {
        //指定されたURLのオーディオ入力ストリームを取得
        try (AudioInputStream ais = AudioSystem.getAudioInputStream(path)){

            //ファイルの形式取得
            AudioFormat af = ais.getFormat();

            //単一のオーディオ形式を含む指定した情報からデータラインの情報オブジェクトを構築
            DataLine.Info dataLine = new DataLine.Info(Clip.class,af);

            //指定された Line.Info オブジェクトの記述に一致するラインを取得
            Clip c = (Clip)AudioSystem.getLine(dataLine);

            //再生準備完了
            c.open(ais);

            return c;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void play(String fileName){
        try {
            File file;
            file = new File(fileName);
            if(!file.exists()){
                fileName = MainSystem.gameData.assetPath + "BGM素材\\" + fileName;
                file = new File(fileName);
            }
            Clip c = createClip(file);
            c.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playBGM(String fileName) {
        playBGM(fileName, true);
    }

    public static void playBGM(String fileName, boolean loop){
        try {
            if(new File(fileName).exists()){
            }else if(!new File(MainSystem.gameData.cd + fileName).exists()){
                fileName = MainSystem.gameData.assetPath + "BGM素材\\" + fileName;
            }else{
                fileName = MainSystem.gameData.cd + fileName;
            }
            stopBGM();
            MainSystem.gameData.bgmClip = createClip(new File(fileName));
            if(loop)MainSystem.gameData.bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            MainSystem.gameData.bgmClip.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void playChargeBGM(String fileName, boolean loop){
        try {
            fileName = MainSystem.gameData.assetPath + "BGM素材\\" + fileName;
            MainSystem.gameData.bgmClipCharge = createClip(new File(fileName));
            if(loop)MainSystem.gameData.bgmClipCharge.loop(Clip.LOOP_CONTINUOUSLY);
            MainSystem.gameData.bgmClipCharge.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void stopBGM(){
        try {
            if (MainSystem.gameData.bgmClip != null) MainSystem.gameData.bgmClip.stop();
            if (MainSystem.gameData.bgmClipCharge != null) MainSystem.gameData.bgmClipCharge.stop();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
