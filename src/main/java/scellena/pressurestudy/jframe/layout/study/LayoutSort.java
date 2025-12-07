package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class LayoutSort extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderContestantBox(g, 880 - 320, 360 - 48, MainSystem.gameData.currentId);
        RenderUtils.renderTimer(g, 880 - 224 - 8, 16);
        RenderUtils.renderWAIcon(g, 560, 272);
        RenderUtils.renderFinePlayIcon(g, 560, 232);
        RenderUtils.renderResult(g, 720, 216);

        g.setColor(Color.WHITE);
        g.fillRect(0, 360, 960, 40);
        g.setColor(new Color(192, 255, 255));
        g.fillRect(0, 400, 960, 120);

        int w = 80; int h = 120;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        for(int i=0; i<10; i++){
            int x = 80 + w * i;
            int y = 400;
            if(MainSystem.gameData.answered[i]) {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, w, h);
                g.setColor(Color.RED);
                RenderUtils.renderTextMiddle(g, "ク\\nリ\\nア", x + w / 2, y + h / 2, 32);
            }else if(MainSystem.gameData.failed[i]){
                g.setColor(Color.GRAY);
                g.fillRect(x, y, w, h);
                g.setColor(Color.BLUE);
                RenderUtils.renderTextMiddle(g, "失\\n敗", x + w / 2, y + h / 2, 32);
            }else if(MainSystem.gameData.selectedNumber == i){
                g.setColor(Color.WHITE);
                g.fillRect(x, y, w, h);
                g.setColor(Color.BLACK);
                RenderUtils.renderTextMiddle(g, Utilities.getNumberString(i), x + w / 2, y + h / 2, 40);
            }else if(MainSystem.gameData.quiz.getQuizList().size() <= i){
                g.setColor(Color.GRAY);
                g.fillRect(x, y, w, h);
            }else {
                g.setColor(new Color(255, 128, 128));
                g.fillRect(x, y, w, h);
                g.setColor(Color.BLACK);
                RenderUtils.renderTextMiddle(g, Utilities.getNumberString(i), x + w / 2, y + h / 2, 40);
            }
            g.setColor(new Color(128, 128, 0));
            g.drawRect(x, y, w, h);
        }

        g.setColor(Color.BLUE);
        RenderUtils.renderTextMiddle(g, "←易しい", 80 + 64, 380, 32);
        g.setColor(Color.RED);
        RenderUtils.renderTextMiddle(g, "難しい→", 880 - 64, 380, 32);

        h = 280;
        g.setColor(new Color(0, 64, 0));
        g.fillRect(80, 80, w * 6, h);
        g.setColor(new Color(128, 128, 0));
        g.drawRect(80, 80, w * 6, h);
        int n = MainSystem.gameData.selectedNumber;
        if(n >= 0){
            QuizInformationBase.SingleQuizInformation q = list.get(n);
            g.setColor(Color.WHITE);
            RenderUtils.renderTextMiddle(g, Utilities.getNumberString(n), 80 + 20, 80 + h / 2, 32);

            if(q.getHint() != null) {
                //RenderUtils.renderTextMiddleWidth(g, q.getTitle(), (int) (100 + w * 3), 80 + h / 2 - 16, w * 5, 64);
                RenderUtils.renderTextMiddleWidth(g, q.getHint(), (int) (100 + w * 3), 80 + h / 2 + 32, w * 5, 32);
            }else{
                //RenderUtils.renderTextMiddleWidth(g, q.getTitle(), (int) (100 + w * 3), 80 + h / 2, w * 5, 64);
            }

            String str = q.getTitle();
            int lines = (str.length() + 6) / 7;
            int blockSize = 48;
            for(int i=0; i<lines; i++){
                int lineSize = Math.min(str.length() - (i * 7), 7);
                for(int j=0; j<lineSize; j++){
                    int x = (100 + w * 3) + (blockSize * (j * 2 - lineSize) / 2);
                    int y = (80 + h / 2) + (blockSize * (i * 2 - (lines - 1)) / 2);

                    g.setColor(Color.WHITE);
                    g.fillRect(x - blockSize / 2, y - blockSize / 2, blockSize, blockSize);
                    g.setColor(Color.BLACK);
                    g.drawRect(x - blockSize / 2, y - blockSize / 2, blockSize, blockSize);
                    RenderUtils.renderTextMiddle(g, str.substring(i * 7 + j, i * 7 + j + 1), x, y, blockSize - 4);
                }
            }

            if(MainSystem.gameData.answered[n] || MainSystem.gameData.failed[n]){
                g.setColor(Color.YELLOW);
                RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), (int) (80 + w * 3), 80 + h - 24, w * 5, 32);
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 552, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 552 / 2, 40, 22);
    }
}
