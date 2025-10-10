package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.MovieUtils;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class LayoutSpeedMovie extends LayoutBase {

    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderTimer(g, 80, 80);
        RenderUtils.renderResult(g, 320, 246);

        for(int i=0; i<10; i++){
            int x = 580; int y = 16 + i * 48;
            RenderUtils.renderContestantBox(g, x, y, i);
            if(MainSystem.gameData.answered[i]){
                g.setColor(new Color(0, 0, 0, 128));
                g.fillRect(x, y, 320, 48);
            }
        }

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
            MovieUtils.renderNext(g, 320, 160 + (480 * 1080 / 1920) / 2, 480, -1);
        }

        g.setColor(Color.BLACK);
        g.fillRect(80, 16, 480, 48);
        g.setColor(Color.WHITE);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 80 + 468 / 2, 40, 22);
    }

}
