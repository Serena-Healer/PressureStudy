package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class LayoutJapanese extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderContestantBox(g, 880 - 320, 360 - 48, MainSystem.gameData.currentId);
        RenderUtils.renderTimer(g, 880 - 224 - 8, 16);
        RenderUtils.renderWAIcon(g, 560, 272);
        RenderUtils.renderFinePlayIcon(g, 560, 232);
        RenderUtils.renderResult(g, 720, 216);

        g.setColor(new Color(192, 255, 255));
        g.fillRect(0, 360, 960, 160);

        int w = 160; int h = 80;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        for(int i=0; i<5; i++){
            for(int j=0; j<2; j++){
                int x = 80 + w * i;
                int y = 360 + h * j;
                int n = i + j * 5;
                if(MainSystem.gameData.selectedNumber == n && !MainSystem.gameData.answered[n]){
                    g.setColor(Color.WHITE);
                    g.fillRect(x, y, w, h);
                    g.setColor(new Color(255, 128, 128));
                    g.fillRect(x, y, 24, h);
                    g.setColor(new Color(128, 128, 0));
                    g.drawRect(x, y, w, h);
                    g.drawRect(x, y, 24, h);
                    g.setColor(Color.WHITE);
                }else {
                    g.setColor(new Color(128, 128, 0));
                    g.drawRect(x, y, w, h);
                    g.drawRect(x, y, 24, h);
                    g.setColor(Color.BLACK);
                }
                RenderUtils.renderTextMiddle(g, Utilities.getNumberString(n), x + 12, y + h / 2, 24);

                g.setColor(Color.BLACK);

                if(MainSystem.gameData.quiz != null){
                    if(list.size() > i + j * 5){
                        QuizInformationBase.SingleQuizInformation q = list.get(n);
                        if(q.getHint() != null) {
                            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), x + 92, y + 32, w - 40, 32);
                            RenderUtils.renderTextMiddleWidth(g, q.getHint(), x + 92, y + 52, w - 40, 16);
                        }else{
                            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), x + 92, y + 40, w - 40, 32);
                        }
                        if(MainSystem.gameData.answered[i + j * 5]){
                            g.setColor(Color.RED);
                            RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), x + 92, y + 40 + 28, w - 40, 16);
                        }
                    }
                }

                if(MainSystem.gameData.answered[i + j * 5]){
                    g.setColor(new Color(0, 0, 0, 128));
                    g.fillRect(x, y, w, h);
                }
            }
        }

        h = 280;

        g.setColor(Color.WHITE);
        g.fillRect(80, 80, w * 3, h);
        g.setColor(new Color(128, 128, 0));
        g.drawRect(80, 80, w * 3, h);
        int n = MainSystem.gameData.selectedNumber;
        if(n >= 0){
            QuizInformationBase.SingleQuizInformation q = list.get(n);
            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, Utilities.getNumberString(n), 80 + 20, 80 + h / 2, 32);

            if(q.getHint() != null) {
                RenderUtils.renderTextMiddleWidth(g, q.getTitle(), (int) (80 + w * 1.5 + 20), 80 + h / 2 - 16, (int) (w * 2.4), 64);
                RenderUtils.renderTextMiddleWidth(g, q.getHint(), (int) (80 + w * 1.5 + 20), 80 + h / 2 + 32, (int) (w * 2.4), 32);
            }else{
                RenderUtils.renderTextMiddleWidth(g, q.getTitle(), (int) (80 + w * 1.5 + 20), 80 + h / 2, (int) (w * 2.4), 64);
            }
            if(MainSystem.gameData.answered[n]){
                g.setColor(Color.RED);
                RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), (int) (80 + w * 1.5), 80 + h - 24, w * 3, 32);
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 552, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 552 / 2, 40, 22);
    }
}
