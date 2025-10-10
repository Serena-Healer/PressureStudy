package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;
import java.util.List;

public class LayoutJapaneseThumbnail extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderImageMiddle(g, MainSystem.gameData.assetPath + "国語英語素材.jpg", 480, 270, 960, 540);
        int w = 464; int h = 84;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        for(int i=0; i<2; i++){
            for(int j=0; j<5; j++){
                int x = 117 + w * i;
                int y = 101 + h * j;
                int n = i * 5 + j;

                g.setColor(Color.BLACK);

                if(MainSystem.gameData.quiz != null){
                    if(list.size() > n){
                        QuizInformationBase.SingleQuizInformation q = list.get(n);
                        String s = q.getTitle();
                        if(q.getHint() != null) {
                            s = s + q.getHint();
                        }
                        RenderUtils.renderTextMiddleWidth(g, s, x + 172, y + h / 2, 344, 60);
                    }
                }

            }
        }

        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 480, 56, 32);
    }
}
