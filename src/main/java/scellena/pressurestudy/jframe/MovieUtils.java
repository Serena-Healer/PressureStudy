package scellena.pressurestudy.jframe;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MovieUtils {

    static FFmpegFrameGrabber grabber;
    static FFmpegFrameGrabber grabber2;
    static String moviePath;

    static int cacheClearCnt = 200;

    public static void load(String path, boolean ignoreIfAlreadyLoaded){
        try {
            if(!(path.startsWith("http")) && !(path.startsWith("C:"))){
                path = MainSystem.gameData.cd + path;
            }
            if(ignoreIfAlreadyLoaded && path.equals(moviePath)){
                if(grabber2 == null){
                    grabber2 = new FFmpegFrameGrabber(path);
                    grabber2.start();
                }
                return;
            }
            if(grabber != null && !(moviePath.equals(path)))grabber.stop();
            if(grabber2 != null && !(moviePath.equals(path)))grabber2.stop();
            moviePath = path;

            grabber = new FFmpegFrameGrabber(path);
            grabber.start();
            grabber2 = new FFmpegFrameGrabber(path);
            grabber2.start();
        }catch(Exception e){
            Utilities.error(e);
        }
    }

    public static void setFrame(int frame){
        try {
            int current = grabber.getFrameNumber();
            frame = Math.min(frame, grabber.getLengthInFrames());
            if(current > frame){
                grabber = grabber2;
                grabber2 = null;
                grabber.setFrameNumber(frame - 1);
            }else{
                for(int i=current; i<frame - 1; i++){
                    grabber.grabImage();
                }
            }
        }catch(Exception e){
            Utilities.error(e);
        }
    }

    public static void renderNext(Graphics2D g, int x, int y, int w, int h){
        try {
            Java2DFrameConverter converter = new Java2DFrameConverter();
            Frame frame = grabber.grabImage(); // It is important to use grabImage() to get a frame that can be turned into a BufferedImage
            BufferedImage bi = converter.convert(frame);
            RenderUtils.renderImageMiddle(g, moviePath + "_" + grabber.getFrameNumber(), bi, x, y, w, h, false);

            //定期的にキャッシュクリアする
            if(cacheClearCnt-- <= 0) {
                RenderUtils.clearCacheLight();
                cacheClearCnt = 50;
            }
        }catch(Exception e){
            Utilities.error(e);
        }
    }

    public static int getFrameRate(){
        try {
            return (int) grabber.getFrameRate();
        }catch(Exception e){
            Utilities.error(e);
            return 60;
        }
    }

}
