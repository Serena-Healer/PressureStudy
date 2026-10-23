package scellena.pressurestudy.jframe.layout.special;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutElimination extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimer(g, 80, 80);
        RenderUtils.renderWAIcon(g, 480, 120);

        for(int i=0; i<10; i++){
            int x = 580; int y = 16 + i * 48;
            RenderUtils.renderContestantBox(g, x, y, i);
        }

        g.setColor(Color.WHITE);
        g.fillRect(80, 160, 480, 240);
        g.setColor(Color.BLACK);
        g.drawRect(80, 160, 480, 240);

        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();
        QuizInformationBase.SingleQuizInformation q = list.get(0);
        String path = q.getImagePath();

        //選択肢
        g.setColor(Color.RED);
        g.fillRect(80, 400, 240, 48);
        g.setColor(Color.WHITE);
        g.fillRect(104, 402, 214, 44);
        RenderUtils.renderTextMiddle(g, "A", 92, 424, 16);

        g.setColor(Color.BLUE);
        g.fillRect(320, 400, 240, 48);
        g.setColor(Color.WHITE);
        g.fillRect(344, 402, 214, 44);
        RenderUtils.renderTextMiddle(g, "B", 332, 424, 16);

        g.setColor(Color.GREEN);
        g.fillRect(80, 448, 240, 48);
        g.setColor(Color.WHITE);
        g.fillRect(104, 450, 214, 44);
        RenderUtils.renderTextMiddle(g, "C", 92, 472, 16);

        g.setColor(Color.ORANGE);
        g.fillRect(320, 448, 240, 48);
        g.setColor(Color.WHITE);
        g.fillRect(344, 450, 214, 44);
        RenderUtils.renderTextMiddle(g, "D", 332, 472, 16);

        if(MainSystem.gameData.timeMax > MainSystem.gameData.timeLeft) {
            g.setColor(Color.BLACK);
            if (path == null || path.length() == 0) {
                RenderUtils.renderTextMiddleWidth(g, q.getTitle(), 320, 280, 448, 64);
            } else {
                RenderUtils.renderImageMiddle(g, path, 320, 280, -1, 240);
            }

            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, q.getChoice().get(0), 211, 424, 24);
            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, q.getChoice().get(1), 451, 424, 24);
            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, q.getChoice().get(2), 211, 472, 24);
            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddle(g, q.getChoice().get(3), 451, 472, 24);
        }

        if(MainSystem.gameData.answered[0]){
            g.setColor(new Color(0, 0, 0, 128));
            if(!q.getAnswer().equals("A")){
                g.fillRect(80, 400, 240, 48);
            }
            if(!q.getAnswer().equals("B")){
                g.fillRect(320, 400, 240, 48);
            }
            if(!q.getAnswer().equals("C")){
                g.fillRect(80, 448, 240, 48);
            }
            if(!q.getAnswer().equals("D")){
                g.fillRect(320, 448, 240, 48);
            }
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 480, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 468 / 2, 40, 22);
    }

}
