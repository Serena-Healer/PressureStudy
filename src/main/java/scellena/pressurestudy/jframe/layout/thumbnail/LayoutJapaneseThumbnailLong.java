package scellena.pressurestudy.jframe.layout.thumbnail;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutJapaneseThumbnailLong extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderImageMiddle(g, MainSystem.gameData.assetPath + "japanese_long.jpg", 480, 270, 960, 540);
        int w = 655; int h = 39;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        for(int i=0; i<1; i++){
            for(int j=0; j<10; j++){
                int x = 178;
                int y = 99 + h * j;
                int n = j;

                g.setColor(Color.BLACK);

                if(MainSystem.gameData.quiz != null){
                    if(list.size() > n){
                        QuizInformationBase.SingleQuizInformation q = list.get(n);
                        String s = q.getTitle().replaceAll("\\\\n", " ");
                        if(q.getHint() != null) {
                            s = s + q.getHint();
                        }
                        RenderUtils.renderText(g, s, x + 16, y + 8, 24);
                    }
                }

            }
        }

        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddleWidth(g, MainSystem.gameData.quiz.getTitle(), 172 + 681 / 2, 60, 681, 32);
    }
}
