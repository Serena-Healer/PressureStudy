package scellena.pressurestudy.jframe.layout;

import scellena.pressurestudy.jframe.RenderUtils;
import scellena.pressurestudy.system.MainSystem;
import scellena.pressurestudy.system.Utilities;

import java.awt.*;

public class LayoutPlayers extends LayoutBase {
    @Override
    public void draw(Graphics2D g) {

        for(int i=0; i<5; i++){
            for(int j=0; j<2; j++){
                int w = 320 + 16; int h = 64;
                int x = (480 - 328) + w * j;
                int y = (270 - 152) + h * i;
                RenderUtils.renderContestantBox(g, x, y, i + j * 5);
            }
        }

        g.setColor(new Color(255, 128, 0));
        RenderUtils.renderTextMiddle(g, MainSystem.gameData.teamName, 480, 59, 48);
    }
}
