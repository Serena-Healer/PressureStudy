package scellena.pressurestudy.jframe.layout.thumbnail;

import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;

public class LayoutTitleThumbnail extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderImageMiddle(g, MainSystem.gameData.assetPath + "title.jpg", 480, 270, 960, 540);

        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddleWidth(g, MainSystem.gameData.quiz.getTitle(), 480, 270, 880, 48);
        RenderUtils.renderTextMiddleWidth(g, MainSystem.gameData.quiz.getCategory(), 490, 136, 427, 32);
    }
}
