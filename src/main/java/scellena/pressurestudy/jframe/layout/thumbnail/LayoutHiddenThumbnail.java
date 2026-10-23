package scellena.pressurestudy.jframe.layout.thumbnail;

import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;

import java.awt.*;

public class LayoutHiddenThumbnail extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        RenderUtils.renderImageMiddle(g, MainSystem.gameData.assetPath + "hidden_thumbnail.jpg", 480, 270, 960, 540);
        g.setColor(Color.BLACK);
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getTitle(), 480, 56, 32);
    }
}
