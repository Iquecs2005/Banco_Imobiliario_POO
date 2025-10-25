package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;

public class BoardPanel extends BasePanel {

    private BufferedImage boardImg;

    public BoardPanel(int width, int height) {
        super(width, height);
        try {
            boardImg = ImageIO.read(getClass().getResource("/resources/tabuleiro.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (boardImg != null) {
            g2d.drawImage(boardImg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
