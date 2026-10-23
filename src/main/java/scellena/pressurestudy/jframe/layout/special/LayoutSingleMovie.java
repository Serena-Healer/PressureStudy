package scellena.pressurestudy.jframe.layout.special;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.MovieUtils;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutSingleMovie extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimer(g, 880 - 224 - 8, 16);

        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();
        QuizInformationBase.SingleQuizInformation q = list.get(0);
        String path = q.getImagePath();
        MovieUtils.load(path, true);
        if(MainSystem.gameData.charge){
            MovieUtils.setFrame(1);
        }else{
            int elapsed = MainSystem.gameData.timeMax - MainSystem.gameData.timeLeft;
            if(elapsed < 0)elapsed = 0;
            MovieUtils.setFrame(MovieUtils.getFrameRate() * elapsed / 20);
            MovieUtils.renderNext(g, 480, 80 + (800 * 1080 / 1920) / 2, 800, -1);
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 552, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 552 / 2, 40, 22);
    }

}
