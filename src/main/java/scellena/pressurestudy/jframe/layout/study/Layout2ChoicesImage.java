package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class Layout2ChoicesImage extends LayoutBase {
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
                g.setColor(new Color(128, 224, 128));
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
        g.setColor(Color.WHITE);
        g.fillRect(80, 80, w * 6, h);
        g.setColor(new Color(128, 128, 0));
        g.drawRect(80, 80, w * 6, h);
        int n = MainSystem.gameData.selectedNumber;

        g.setColor(Color.RED);
        g.fillRect(80, 80 + h - 48, w * 3, 48);
        g.setColor(Color.WHITE);
        g.fillRect(96, 80 + h - 48 + 1, w * 3 - 17, 48 - 2);
        RenderUtils.renderTextMiddle(g, "A", 88, 80 + h - 24, 16);

        g.setColor(Color.BLUE);
        g.fillRect(80 + w * 3, 80 + h - 48, w * 3, 48);
        g.setColor(Color.WHITE);
        g.fillRect(96 + w * 3, 80 + h - 48 + 1, w * 3 - 17, 48 - 2);
        RenderUtils.renderTextMiddle(g, "B", 88 + w * 3, 80 + h - 24, 16);

        if(n >= 0){
            QuizInformationBase.SingleQuizInformation q = list.get(n);
            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, Utilities.getNumberString(n), 80 + 20, 80 + h / 2, 32);

            RenderUtils.renderImageMiddle(g, q.getImagePath(), (int) (80 + w * 3), 80 + (h - 48) / 2, w * 6 - 64, h - 64 - 48);
            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), (int) (80 + w * 3), 80 + h - 48 - 24, w * 5, 32);
            if(q.getHint() != null) {
                RenderUtils.renderTextMiddleWidth(g, q.getHint(), (int) (80 + w * 3), 80 + 20, w * 5, 32);
            }

            RenderUtils.renderTextMiddleWidth(g, q.getChoice().get(0), (int) (80 + w * 1.5 + 8), 80 + h - 24, w * 3 - 24, 32);
            RenderUtils.renderTextMiddleWidth(g, q.getChoice().get(1), (int) (80 + w * 4.5 + 8), 80 + h - 24, w * 3 - 24, 32);

            if(MainSystem.gameData.answered[n] || MainSystem.gameData.failed[n]){
                g.setColor(new Color(0, 0, 0, 128));
                if(!q.getAnswer().equals("A")){
                    g.fillRect(80, 80 + h - 48, w * 3, 48);
                }
                if(!q.getAnswer().equals("B")){
                    g.fillRect(80 + w * 3, 80 + h - 48, w * 3, 48);
                }
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 552, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 552 / 2, 40, 22);
    }
}
