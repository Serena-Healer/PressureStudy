package scellena.pressurestudy.jframe;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameHandler {

    public static JFrame jFrame;
    static CustomCanvas canvas;

    public static void create(){
        JFrame frame = new JFrame("プレッシャーSTUDY");

        // ウィンドウのサイズ・初期位置
        frame.setSize(960, 540 + 40);
        frame.setLocationRelativeTo(null);

        canvas = new CustomCanvas();

        // フレームに追加
        JPanel pane = new JPanel();
        frame.getContentPane().add(pane);

        canvas.setPreferredSize(new Dimension(960, 540));
        pane.add(canvas);

        // ウィンドウを表示
        frame.setVisible(true);

        jFrame = frame;
    }

    public static void onTick(){
        canvas.repaint();
    }

}
