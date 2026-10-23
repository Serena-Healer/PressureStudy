package scellena.pressurestudy.jframe.layout.special;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutSingleImage extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimer(g, 880 - 224 - 8, 16);
        RenderUtils.renderWAIcon(g, 480, 120);

        g.setColor(Color.WHITE);
        g.fillRect(80, 80, 800, 440);
        g.setColor(Color.BLACK);
        g.drawRect(80, 80, 800, 440);

        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();
        QuizInformationBase.SingleQuizInformation q = list.get(0);
        String path = q.getImagePath();

        if(MainSystem.gameData.timeMax > MainSystem.gameData.timeLeft) {
            g.setColor(Color.BLACK);
            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), 480, 280, 720, 64);
            if(q.getHint() != null && !MainSystem.gameData.answered[0]){
                RenderUtils.renderTextMiddleWidth(g, q.getHint(), 480, 464, 720, 48);
            }
            if (path == null || path.length() == 0) {
            } else {
                RenderUtils.renderImageMiddle(g, path, 480, 300, -1, 440);
            }
        }

        if(MainSystem.gameData.answered[0]) {
            g.setColor(Color.RED);
            RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), 480, 464, 720, 48);
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 552, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 552 / 2, 40, 22);
    }

}
