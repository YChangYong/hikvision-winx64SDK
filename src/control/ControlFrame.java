package control;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by wsx on 2017-06-29.
 */
public class ControlFrame extends JFrame{
    private JButton left_bt;
    private JButton right_bt;
    private JPanel MainPanel;
    private JPanel PlayerPanel;
    private JPanel ControlPanel;

    public ControlFrame()
    {
        this.setTitle("摄像头控制面板");
        this.setLocationRelativeTo(null);
        GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().setFullScreenWindow(this);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MainPanel = (JPanel)this.getContentPane();
        PlayerPanel = new JPanel();
        ControlPanel = new JPanel();
        MainPanel.setLayout(new GridLayout(1, 2));
        MainPanel.add(PlayerPanel);
        MainPanel.add(ControlPanel);

        PlayerPanel.setLayout(new GridLayout(2, 1));

        ControlPanel.setLayout(new GridLayout(2, 1));
        left_bt = new JButton("left");
        left_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("left"+"mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("left"+"mouseReleased");
            }
        });

        right_bt = new JButton("right");
        right_bt.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("right"+"mousePressed");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("right"+"mouseReleased");
            }
        });
        ControlPanel.add(left_bt);
        ControlPanel.add(right_bt);

        this.pack();
        this.setVisible(true);
    }

    public static void main(String args[])
    {
        new ControlFrame();
    }


}
