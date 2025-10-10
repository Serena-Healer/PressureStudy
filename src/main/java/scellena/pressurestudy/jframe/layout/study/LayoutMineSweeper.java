package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class LayoutMineSweeper extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimer(g, 880 - 224 - 8, 16);
        RenderUtils.renderWAIcon(g, 400, 226 - 48 - 40);
        RenderUtils.renderResult(g, 240, 153);

        int w = 160; int h = 146;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        RenderUtils.renderContestantBox(g, 400 - w * (list.size() - 11), 226 - 48, MainSystem.gameData.currentId);

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                int x = 80 + w * i;
                int y = 80 + h * j;
                int n = list.size() - 15 + i + j * 5;

                if(n < 0)continue;
                g.setColor(new Color(255, 255, 224));
                g.fillRect(x, y, w, h);
                g.setColor(new Color(128, 128, 0));
                g.drawRect(x, y, w, h);
                g.drawRect(x + 1, y + 1, w - 2, h - 2);

                g.setColor(Color.BLACK);

                if(MainSystem.gameData.quiz != null){
                    if(list.size() > n){
                        QuizInformationBase.SingleQuizInformation q = list.get(n);
                        if(q.getHint() != null) {
                            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), x + w / 2, y + h / 2, w - 4, 32);
                            RenderUtils.renderTextMiddleWidth(g, q.getHint(), x + w / 2, y + h / 2, w - 4, 16);
                        }else{
                            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), x + w / 2, y + h / 2, w - 4, 32);
                        }
                        if(MainSystem.gameData.answered[n] || MainSystem.gameData.failed[n]){
                            g.setColor(Color.RED);
                            RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), x + w / 2, y + h - 14, w - 4, 24);
                        }
                    }
                }

                if(MainSystem.gameData.answered[n]){
                    g.setColor(new Color(0, 0, 0, 128));
                    g.fillRect(x, y, w, h);
                }

                if(MainSystem.gameData.selectedNumber == n && !MainSystem.gameData.answered[n]){
                    g.setColor(new Color(255, 0, 0));
                    g.drawRect(x, y, w, h);
                    g.drawRect(x + 1, y + 1, w - 2, h - 2);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 552, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 552 / 2, 40, 22);
    }
}
