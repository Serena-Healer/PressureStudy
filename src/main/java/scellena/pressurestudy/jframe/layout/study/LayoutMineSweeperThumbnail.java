package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutMineSweeperThumbnail extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderImageMiddle(g, MainSystem.gameData.assetPath + "minesweeper.png", 480, 270, 960, 540);

        int w = 229; int h = 141;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        for(int i=0; i<4; i++){
            for(int j=0; j<3; j++){
                int x = 25 + w * i;
                int y = 100 + h * j;
                int n = i + j * 4;

                if(list.size() <= n)continue;
                g.setColor(Color.BLACK);
                RenderUtils.renderTextMiddleWidth(g, list.get(n).getTitle(), x + w / 2, y + h / 2, w - 16, 32);
            }
        }

        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 480, 56, 32);
    }
}
