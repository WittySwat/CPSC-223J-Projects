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

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

public class AnimationPanel extends JPanel {
    // Define constants for the various dimensions
    public static final int CANVAS_WIDTH = 500;
    public static final int CANVAS_HEIGHT = 500;
    public static final Color CANVAS_BG_COLOR = Color.CYAN;

    private final int[] xPoints = {250, 450, 250, 50};
    private final int[] yPoints = {50, 250, 450, 250};

    private int current = 0;


    private final GameField gameField;
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public GameField getGameField() {
        return gameField;
    }

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

    private void move200(int pos) throws InterruptedException {
        for (int i = 0; i < 200; i++) {
            switch (pos) {
                case 0:
                player.plot(player.x + 1, player.y + 1);
                this.paintImmediately(0, 0, 500, 500);
            break;
            case 1:
                player.plot(player.x - 1, player.y + 1);
                this.paintImmediately(0, 0, 500, 500);
                break;
            case 2:
                player.plot(player.x - 1, player.y - 1);
                this.paintImmediately(0, 0, 500, 500);
                break;
            case 3:
                player.plot(player.x + 1, player.y - 1);
                this.paintImmediately(0, 0, 500, 500);
                break;
            default:
                break;
            }
            Thread.sleep(10);
        }
    }

    public void animateToNextPoint() throws InterruptedException {
        move200(current % 4);
        current++;
    }

    class GameField extends JPanel {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(new Color(255, 0, 0));
            g2d.scale(1, -1);
            g2d.translate(0, -getHeight());

            g2d.drawLine(0, 0, 500, 500);
            g2d.drawLine(0, 500, 500, 0);

            int numPoints = 4;
            g2d.drawPolygon(xPoints, yPoints, numPoints);

            setBackground(CANVAS_BG_COLOR);

            player.paint(g);
        }
    }
}

class Player {
    int x;
    int y;
    double radius;

    public Player(int x, int y, int radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void paint(Graphics g) {
        g.fillOval(x, y, (int) radius, (int) radius);
    }

    public void plot(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
