package scellena.pressurestudy.jframe;

import scellena.pressurestudy.system.MainSystem;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CustomCanvas extends Canvas {

    BufferedImage image;
    Graphics2D g2d;

    public CustomCanvas(){
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        image = new BufferedImage(960, 540, BufferedImage.TYPE_INT_ARGB);
        g2d = (Graphics2D) image.getGraphics();

        RenderUtils.renderBackground(g2d);
        MainSystem.gameData.currentLayout.draw(g2d);

        g.drawImage(image, 0, 0, null);
    }

    //updateメソッドを自分で記述
    public void update(Graphics g){
        paint(g);
    }
}
