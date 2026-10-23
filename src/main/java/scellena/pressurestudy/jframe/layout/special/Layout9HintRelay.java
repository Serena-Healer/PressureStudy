package scellena.pressurestudy.jframe.layout.special;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class Layout9HintRelay extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimerSpeedRelay(g, 80, 304);
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
        g.fillRect(80, 64, 480, 240);
        g.setColor(Color.BLACK);
        g.drawRect(80, 64, 480, 240);

        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();
        QuizInformationBase.SingleQuizInformation q = list.get(0);
        String path = q.getImagePath();

        String hintStr = MainSystem.gameData.relayHint;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                int n = i + j * 3;
                int h = 80;
                int w = 80;
                int x = 320 + w * i;
                int y = 64 + h * j;
                g.setColor(Color.BLUE);
                g.fillRect(x, y, w, h);
                g.setColor(Color.WHITE);
                g.drawRect(x, y, w, h);
                if(hintStr.length() > n && MainSystem.gameData.timeLeft < MainSystem.gameData.timeMax) {
                    RenderUtils.renderTextMiddle(g, Character.toString(hintStr.charAt(n)), x + w / 2, y + w / 2, 64);
                }
            }
        }

        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddle(g, "お題", 200, 144, 24);
        if(MainSystem.gameData.answered[0]){
            g.setColor(Color.RED);
            RenderUtils.renderTextMiddle(g, q.getAnswer(), 200, 196, 48);
        }else{
            RenderUtils.renderTextMiddle(g, "？", 200, 196, 64);
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 480, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 468 / 2, 40, 22);

        g.setColor(new Color(64, 128, 255));
        g.fillRect(80, 64, 240, 32);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, "脳内イメージ漢字", 200, 64 + 16, 24);
    }

}
