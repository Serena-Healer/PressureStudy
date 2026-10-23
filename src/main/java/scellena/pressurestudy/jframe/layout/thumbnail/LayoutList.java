package scellena.pressurestudy.jframe.layout.thumbnail;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class LayoutList extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {

        g.setColor(Color.WHITE);
        g.fillRect(80, 80, 800, 368);
        g.setColor(Color.BLACK);
        g.drawRect(80, 80, 800, 368);

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 800, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 400, 40, 22);

        g.setColor(new Color(192, 255, 255));
        g.fillRect(0, 456, 960, 64);
        g.setColor(new Color(255, 255, 224));
        g.fillRect(0, 460, 960, 56);
        g.setColor(Color.RED);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.programTelop, 480, 488, 40);

        int n = MainSystem.gameData.selectedNumber;
        if(n < 0) return;

        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();
        QuizInformationBase.SingleQuizInformation q = list.get(n);
        String path = q.getImagePath();

        if(MainSystem.gameData.timeMax > MainSystem.gameData.timeLeft) {
            g.setColor(Color.BLACK);
            if(q.getHint() != null && !MainSystem.gameData.answered[0]){
                RenderUtils.renderTextMiddleWidth(g, q.getHint(), 480, 44 + 368, 800 - 64, 48);
            }
            if (path == null || path.length() == 0) {
                RenderUtils.renderTextMiddleWidth(g, q.getTitle(), 480, 80 + 184, 800 - 64, 64);
            } else {
                RenderUtils.renderTextMiddleWidth(g, q.getTitle(), 480, 8 + 368, 800 - 64, 32);
                RenderUtils.renderImageMiddle(g, path, 480, 80 + 184 - 40, 720, 240);
            }
            RenderUtils.renderTextMiddleWidth(g, Utilities.getNumberString(n), 80 + 32, 80 + 184, 720, 48);
        }

        if(MainSystem.gameData.answered[0]) {
            g.setColor(Color.RED);
            RenderUtils.renderTextMiddleWidth(g, q.getAnswer(), 480, 44 + 368, 720, 48);
        }

    }

}
