package scellena.pressurestudy.jframe.layout.study;

import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.jframe.layout.LayoutBase;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;

public class LayoutTitle extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(0, 128, 0));
        if(MainSystem.gameData.quiz != null) {
            RenderUtils.renderTextMiddle(g, MainSystem.gameData.quiz.getCategory(), 480, 240, 96);
        }
    }
}
