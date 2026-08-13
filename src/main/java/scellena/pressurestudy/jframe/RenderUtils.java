package scellena.pressurestudy.jframe;

import scellena.pressurestudy.data.AnswererInformation;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RenderUtils {

    public static void renderText(Graphics2D g, String text, int x, int y, int size){
        g.setFont(new Font("UD デジタル 教科書体 NP", Font.PLAIN, size));
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
        g.drawString(text, x, y + size);
    }

    public static void renderTextMiddleWidth(Graphics2D g, String text, int x, int y, int width, int maximum) {
        renderTextMiddle(g, text, x, y, adjustWidth(g, text, width, maximum));
    }

    public static void renderTextMiddle(Graphics2D g, String text, int x, int y, int size){
        Font font = new Font("UD デジタル 教科書体 NP", Font.PLAIN, size);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        int h = 0;
        for(int i=0; i<text.length(); i++){
            if(text.substring(i).startsWith("\\n")){
                i++;
                h++;
            }
        }

        StringBuilder builder = new StringBuilder();
        int hm = 0;
        for(int i=0; i<text.length() + 1; i++){
            if(i == text.length() || text.substring(i).startsWith("\\n")){
                i++;
                String str = builder.toString();
                g.drawString(str, x - g.getFontMetrics().stringWidth(str) / 2, y + g.getFontMetrics().getHeight() / 3 + (g.getFontMetrics().getHeight() * (hm * 2 - h) / 2));
                hm++;
                builder = new StringBuilder();
            }else{
                builder.append(text.charAt(i));
            }
        }
    }

    public static int adjustWidth(Graphics2D g, String text, int width, int minimum){
        Font font = new Font("UD デジタル 教科書体 NP", Font.PLAIN, 64);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);

        int ans = 0;

        StringBuilder builder = new StringBuilder();
        for(int i=0; i<text.length() + 1; i++){
            if(i == text.length() || text.substring(i).startsWith("\\n")){
                i++;
                String str = builder.toString();
                ans = (int)Math.max(ans, g.getFontMetrics().stringWidth(str));
                builder = new StringBuilder();
            }else{
                builder.append(text.charAt(i));
            }
        }

        if(ans == 0)return minimum;
        return (int)Math.min(width * 64 / ans, minimum);
    }

    public static int getEffectiveLength(String text){
        int t = 0;
        int ans = 0;
        for(int i=0; i<text.length() + 1; i++){
            if(i == text.length() || text.substring(i).startsWith("\\n")){
                if(ans < t)ans = t;
                t = 0;
                i++;
            }else{
                t++;
            }
        }
        return ans;
    }

    /**
     * 解答者表示描画 320*48
     */
    public static void renderContestantBox(Graphics2D g2d, int x, int y, int number){
        if(MainSystem.gameData.answerBox.size() <= number)return;
        AnswererInformation info = MainSystem.gameData.answerBox.get(number);
        g2d.setColor(info.getColor());
        g2d.fillRect(x, y, 320, 48);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x + 2, y + 2, 36, 44);

        g2d.setColor(Color.WHITE);
        renderText(g2d, info.getDescription(), x + 42, y, 16);
        renderText(g2d, info.getName(), x + 42, y + 17, 28);
        g2d.setColor(Color.BLACK);
        renderTextMiddle(g2d, Integer.toString(number + 1), x + 20, y + 24, 40);
    }

    /**
     * タイマー描画 224*48
     */
    public static void renderTimer(Graphics2D g2d, int x, int y){
        int timeLeft = MainSystem.gameData.timeLeft;
        int timeMax = MainSystem.gameData.timeMax;
        int separation;
        int seconds;

        if(timeMax > 20 * 30){
            if(timeMax < 20 * 120){
                if(timeLeft > timeMax)timeLeft = timeMax;
                timeMax = 20 * 120;
            }
            if(timeMax > (20 * 600)){
                separation = timeMax / (20 * 60);
            }else {
                separation = timeMax / (20 * 10);
            }
            if(timeLeft > 20 * 900){
                seconds = ((timeLeft + 1199) / 1200) * 10;
            } else if(timeLeft > 20 * 30){
                seconds = ((timeLeft + 199) / 200) * 10;
            } else{
                seconds = (timeLeft + 19) / 20;
            }
        }else{
            separation = timeMax / 20;
            if(timeLeft > 20 * 10){
                seconds = ((timeLeft + 199) / 200) * 10;
            }else{
                seconds = (timeLeft + 19) / 20;
            }
        }

        g2d.setColor(new Color(128, 128, 128));
        g2d.fillRect(x + 35, y + 4, 187, 40);
        g2d.setColor(new Color(64, 64, 64));
        g2d.fillRect(x + 37, y + 6, 183, 36);
        g2d.drawRect(x + 24, y, 202, 48);

        for(int i=0; i<183*timeLeft/timeMax; i++){
            if(i == 183)break;
            Color color;
            if(i < 90){
                color = new Color(192, 192 * i / 90, 0);
            }else if(i < 140){
                color = new Color(192 * (140 - i) / 50, 192, 0);
            }else{
                color = new Color(0, 192, 192 * (i - 140) / 43);
            }
            g2d.setColor(color);
            g2d.fillRect(x + 37 + i, y + 6, 1, 36);
        }

        g2d.setColor(new Color(255, 255, 255, 128));
        g2d.fillRect(x + 37, y + 10, 183, 2);
        g2d.fillRect(x + 37, y + 14, 183, 4);
        g2d.setColor(new Color(0, 0, 0, 64));
        g2d.fillRect(x + 37, y + 38, 183, 6);

        for(int i=0; i<separation-1; i++){
            int dx = (183) * (i + 1) / (separation);
            g2d.setColor(new Color(160, 160, 160));
            g2d.fillRect(x + 37 + dx, y + 6, 1, 36);
        }

        //30秒前点滅
        if(!MainSystem.gameData.charge && (timeMax > 20 * 30 && timeLeft < 20 * 30) || (timeMax <= 20 * 30 && timeLeft < 20 * 10)){
            int red = (10 - Math.abs(timeLeft % 20 - 10)) * 128 / 10;
            g2d.setColor(new Color(255, 0, 0, red));
            g2d.fillRect(x + 35, y + 4, 187, 40);
        }

        //g2d.setColor(new Color(128, 128, 128));
        //g2d.fillOval(x, y, 48, 48);
        renderImageMiddle(g2d, MainSystem.gameData.assetPath + "timerOval.png", x + 24, y + 24, 48, 48, true);

        //文字列
        if(MainSystem.gameData.charge){
            g2d.setColor(new Color(128, 128, 0));
            renderTextMiddle(g2d, "解答時間CHARGE中", x + 112, y + 48 + 8, 16);
        }else if(timeLeft > timeMax){
            g2d.setColor(Color.RED);
            renderTextMiddle(g2d, "解答時間CHARGE 完了", x + 112, y + 48 + 8, 16);
        }

        if(!MainSystem.gameData.charge && (timeMax > 20 * 30 && timeLeft < 20 * 30) || (timeMax <= 20 * 30 && timeLeft < 20 * 10)){
            int red = (10 - Math.abs(timeLeft % 20 - 10)) * 128 / 10;
            g2d.setColor(new Color(255, 0, 0, red));
            g2d.fillOval(x, y, 48, 48);
        }

        int yellow = 224;
        int white = 0;
        if(timeLeft > 20 * 30 || (timeLeft > 20 * 10 && timeMax <= 20 * 30)){
            int f = timeLeft % (20 * 10);
            if(timeLeft > 20 * 900){
                f = (timeLeft % (20 * 60)) - (20 * 50);
            }
            if(f > 20 * 8){
                int t = Math.abs(f - 20 * 9);
                yellow = t * 224 / 20;
            }
        }
        if(timeLeft > timeMax){
            int f = timeLeft - timeMax;
            if(f > 0 && f < 40){
                white = f * 224 / 40;
            }
            seconds = timeMax / 20;
        }

        if(!MainSystem.gameData.charge && !(MainSystem.gameData.demoTick > 0)) {
            g2d.setColor(Color.WHITE);
            renderText(g2d, "残", x + 128, y + 6, 16);
            renderText(g2d, "り", x + 128, y + 22, 16);
            g2d.setColor(new Color(224, yellow, white));
            if(seconds > 900){
                for (int i = 0; i < 3; i++) {
                    renderText(g2d, String.format("%3s", (seconds / 60)).substring(i, i + 1), x + 144 + 18 * i, y + 4, 32);
                }
                renderText(g2d, "分", x + 200, y + 20, 16);
            }else {
                for (int i = 0; i < 3; i++) {
                    renderText(g2d, String.format("%3s", seconds).substring(i, i + 1), x + 144 + 18 * i, y + 4, 32);
                }
                renderText(g2d, "秒", x + 200, y + 20, 16);
            }
        }
    }

    static Map<String, Image> imageMap = new HashMap<>();
    static ArrayList<String> keepImage = new ArrayList<>();

    public static void clearCache(){
        imageMap = new HashMap<>();
    }

    public static void clearCacheLight(){
        ArrayList<String> remove = new ArrayList<>();
        for(String key : imageMap.keySet()){
            if(!keepImage.contains(key)) remove.add(key);
        }
        for(String key : remove){
            imageMap.remove(key);
        }
    }

    public static void renderBackground(Graphics2D g){
        renderImageMiddle(g, MainSystem.gameData.assetPath + "background.jpg", 480, 270, -1, -1);
    }

    public static void renderImageMiddle(Graphics2D g, String path, int x, int y, int w, int h) {
        renderImageMiddle(g, path, x, y, w, h, false);
    }

    public static void renderImageMiddle(Graphics2D g, String path, int x, int y, int w, int h, boolean keep){
        if(!(path.startsWith("http")) && !(path.startsWith("C:"))){
            path = MainSystem.gameData.cd + path;
        }
        if(!imageMap.containsKey(path)){
            imageMap.put(path, Toolkit.getDefaultToolkit().createImage(path));
        }
        Image image = imageMap.get(path);
        if(image.getWidth(null) != -1){
            //System.out.println(w + " " + h + " " + image.getWidth(null) + " " + image.getHeight(null) + " " + ((double)w / image.getWidth(null) * image.getHeight(null)));
            if((double)w * image.getHeight(null) / image.getWidth(null) < h){
                renderImageMiddle(g, path, image, x, y, w, -1, keep);
            }else{
                renderImageMiddle(g, path, image, x, y, -1, h, keep);
            }
        }else{
            renderImageMiddle(g, path, image, -w, -h, w, h, keep);
        }
    }

    public static void renderImageMiddle(Graphics2D g, String path, Image image, int x, int y, int w, int h, boolean keep){
        if(!imageMap.containsKey(path)){
            imageMap.put(path, image);
            if(keep){
                keepImage.add(path);
            }
        }
        image = imageMap.get(path);
        if(image != null) {
            image = image.getScaledInstance(w, h, Image.SCALE_AREA_AVERAGING);
            path = path + "_" + w + "_" + h;

            if (!imageMap.containsKey(path)) {
                imageMap.put(path, image);
                if(keep){
                    keepImage.add(path);
                }
            }
            image = imageMap.get(path);
            g.drawImage(image, x - image.getWidth(null) / 2, y - image.getHeight(null) / 2, null);
        }
    }

    /**
     * 表示時間が残っている時のみ誤答アイコン表示 80 * 40
     */
    public static void renderWAIcon(Graphics2D g2d, int x, int y) {
        int w = 80;
        int h = 40;
        if(MainSystem.gameData.waDisplayTick > 0){
            g2d.setColor(Color.BLACK);
            g2d.fillRect(x, y, w, h);
            g2d.setColor(Color.RED);
            g2d.drawLine(x + 4, y + 4, x + w - 4, y + h - 4);
            g2d.drawLine(x + w - 4, y + 4, x + 4, y + h - 4);
        }
    }

    /**
     * 結果表示(中心起点) 160 * 160
     */
    public static void renderResult(Graphics2D g2d, int x, int y) {
        int w = 160;
        int h = 160;
        if(MainSystem.gameData.resultDisplayTick > 0){
            if(MainSystem.gameData.isResultWin){
                renderImageMiddle(g2d, MainSystem.gameData.assetPath + "win.jpg", x, y, w, h);
            }else{
                g2d.setColor(Color.BLUE);
                renderTextMiddle(g2d, "失敗", x, y, 80);
            }
        }
    }

    /**
     * 表示時間が残っている時のみFine Playアイコン表示
     */
    public static void renderFinePlayIcon(Graphics2D g2d, int x, int y) {
        int w = (int) (80 * 3.84);
        int h = 80;
        if(MainSystem.gameData.finePlayDisplayTick > 0){
            int k = MainSystem.gameData.lastACNumber;
            String fileName = null;
            if(k == 6){
                fileName = "NicePlay.png";
            }else if(k == 9 && MainSystem.gameData.quiz.isDifficultyFlag()){
                fileName = "VeryFinePlay.png";
            }else if(k >= 7){
                fileName = "FinePlay.png";
            }
            if(fileName != null){
                renderImageMiddle(g2d, MainSystem.gameData.assetPath + fileName, x + w / 2, y + h / 2, w, h);
            }
        }
    }

    /**
     * タイマー描画 リレー用 102*48
     */
    public static void renderTimerSpeedRelay(Graphics2D g2d, int x, int y) {
        int timeLeft = MainSystem.gameData.timeLeft;
        int timeMax = MainSystem.gameData.timeMax;
        int seconds = Math.min(timeMax, timeLeft) / 20;
        int w = 102;
        int h = 48;

        g2d.setColor(Color.BLACK);
        g2d.fillRect(x, y, w, h);
        g2d.setColor(Color.WHITE);
        g2d.drawRect(x, y, w, h);

        renderText(g2d, "残", x + 4, y + 6, 16);
        renderText(g2d, "り", x + 4, y + 22, 16);
        renderText(g2d, "秒", x + (w - 4 - 16), y + 22, 16);
        g2d.setColor(new Color(255, 128, 0));
        for (int i = 0; i < 3; i++) {
            renderText(g2d, String.format("%3s", seconds).substring(i, i + 1), x + 20 + 20 * i, y + 4, 32);
        }
    }
}
