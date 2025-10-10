package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.data.QuizInformationBase;
import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.util.List;

public class LayoutImageThumbnail extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderImageMiddle(g, MainSystem.gameData.assetPath + "画像問題素材.jpg", 480, 270, 960, 540);

        int w = 182; int h = 221;
        List<QuizInformationBase.SingleQuizInformation> list = MainSystem.gameData.quiz.getQuizList();

        for(int i=0; i<5; i++){
            for(int j=0; j<2; j++){
                int x = 26 + w * i;
                int y = 135 + h * j;
                int n = i + j * 5;

                g.setColor(Color.BLACK);

                if(MainSystem.gameData.quiz != null){
                    if(list.size() > n){
                        QuizInformationBase.SingleQuizInformation q = list.get(n);
                        if(q.getChoice().size() > 0){
                            RenderUtils.renderTextMiddleWidth(g, q.getChoice().get(0), x + w / 2, y + 12, w - 8, 24);
                            RenderUtils.renderImageMiddle(g, q.getImagePath(), x + w / 2, y + 105 / 2 + 12, w - 8, 137 - 32 - 24);
                        }else {
                            RenderUtils.renderImageMiddle(g, q.getImagePath(), x + w / 2, y + 105 / 2, w - 8, 137 - 32);
                        }
                        RenderUtils.renderTextMiddleWidth(g, q.getHint(), x + w / 2, y + 157, w - 8, 32);
                        if(q.getHint() != null) {
                            RenderUtils.renderTextMiddleWidth(g, q.getTitle(), x + w / 2, y + 137 - 16, w - 8, 24);
                        }
                    }
                }

            }
        }

        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 480, 56, 32);
    }
}
