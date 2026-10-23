package scellena.pressurestudy.jframe.layout.special;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutDescribe extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimer(g, 80, 80);
        RenderUtils.renderWAIcon(g, 480, 304);
        RenderUtils.renderResult(g, 320, 384);

        for(int i=0; i<10; i++){
            int x = 580; int y = 16 + i * 48;
            RenderUtils.renderContestantBox(g, x, y, i);
            if(MainSystem.gameData.currentId != i){
                g.setColor(new Color(0, 0, 0, 128));
                g.fillRect(x, y, 320, 48);
            }
        }

        g.setColor(Color.WHITE);
        g.fillRect(80, 136, 480, 240);
        g.setColor(Color.BLACK);
        g.drawRect(80, 136, 480, 240);

        int n = MainSystem.gameData.selectedNumber;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();
        if(list.size() > n && n >= 0) {
            QuizInformationBase.SingleQuizInformation q = list.get(n);

            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, "お題", 320, 144 + 56, 24);
            if (MainSystem.gameData.answered[n]) {
                g.setColor(Color.RED);
                RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), 320, 196 + 56, 440, 64);
            } else {
                RenderUtils.renderTextMiddle(g, "？", 320, 196 + 56, 64);
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 480, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 468 / 2, 40, 22);

        g.setColor(new Color(64, 128, 255));
        g.fillRect(80, 64 + 72, 480, 32);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, "プレッシャー説明力", 320, 64 + 72 + 16, 24);
    }

}
