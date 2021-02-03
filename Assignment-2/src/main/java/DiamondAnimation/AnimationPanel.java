/*
 * Program Name: "Payroll System".  This program shows how to add and subtract two numbers using a simple UI with three
 *  active buttons.  Copyright (C) 2021 Jarrod Burges
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 *  General Public License
 *  version 3 as published by the Free Software Foundation.
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 *  even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
 *  License for more details.
 *  A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 *
 */

package DiamondAnimation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.io.IOException;

public class AnimationPanel extends JPanel {
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 500;
    public static final Color CANVAS_BG_COLOR = Color.CYAN;

    private final int[] xPoints = {250, 450, 250, 50};
    private final int[] yPoints = {50, 250, 450, 250};

    private int current = 0;

    private final GameField gameField;
    private final Player player;
    private Timer timer;

    public AnimationPanel() {
        player = new Player(xPoints[0], yPoints[0], 20);

        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.setLayout(new GridLayout());
        this.add(gameField);

        setVisible(true);
        requestFocus();
    }

    public void moveAcrossDiamond(int delay) {
        synchronized (this) {
            if (timer != null) {
                return;
            }
        }

        final int[] numTimes = {0, 0};
        timer = new Timer(delay, e -> {
            if (numTimes[1] > 3) {
                timer.stop();
                synchronized (AnimationPanel.this) {
                    timer = null;
                }
            }
            if (numTimes[0] == 200) {
                numTimes[1]++;
                numTimes[0] -= 200;
            }
            switch (numTimes[1]) {
                case 0:
                    player.plot(player.x + 1, player.y + 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                case 1:
                    player.plot(player.x - 1, player.y + 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                case 2:
                    player.plot(player.x - 1, player.y - 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                case 3:
                    player.plot(player.x + 1, player.y - 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                default:
                    break;
            }
            numTimes[0]++;
        });
        timer.start();
        current++;
    }


    class GameField extends JPanel {
        BufferedImage fieldImage;
        public GameField() {
            try {
                fieldImage = ImageIO.read(getClass().getResourceAsStream("/field.png"));
            } catch (IllegalArgumentException | IOException e) {
                fieldImage = null;
            }
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //rescales and translates g2d to have (0,0) orgin at bottom left rather than top left
            g2d.scale(1, -1);
            g2d.translate(0, -getHeight());

            if (fieldImage != null)
                g2d.drawImage(fieldImage, 0, 0, null);

            player.paint(g);
        }
    }
}

class Player {
    int x;
    int y;
    double radius;
    BufferedImage playerImage;

    public Player(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;

        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/player.png"));
        } catch (IllegalArgumentException | IOException e) {
            playerImage = null;
        }
    }

    public void paint(Graphics g) {
        if (playerImage != null)
            g.drawImage(playerImage, (int) (x - (radius)), (int) (y - (radius)), null);
        else
            g.fillOval((int) (x - (radius/2)), (int) (y - (radius/2)), 20, 20);
    }

    public void plot(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
