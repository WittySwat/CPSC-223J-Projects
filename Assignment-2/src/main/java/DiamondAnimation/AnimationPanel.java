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
    private final int[] slope = {1, -1, 1, -1};


    private int current = 0;


    private final GameField gameField; // the custom drawing canvas (an inner class extends JPanel)
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public GameField getGameField() {
        return gameField;
    }

    // Constructor to set up the GUI components and event handlers
    public AnimationPanel() {

        // Construct a player given x, y, width, height, color
        player = new Player(xPoints[0], yPoints[0],
                20);


        // Set up the custom drawing canvas (JPanel)
        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        // Add both panels to this JFrame
        this.setLayout(new GridLayout());
        this.add(gameField);
        //this.add(btnPanel, BorderLayout.SOUTH);

//        // "super" JFrame fires KeyEvent
//        addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent evt) {
//                switch(evt.getKeyCode()) {
//                    case KeyEvent.VK_LEFT:  moveLeft();  break;
//                    case KeyEvent.VK_RIGHT: moveRight(); break;
//                }
//            }
//        });
        // pack all the components in the JFrame
        setVisible(true);  // show it
        requestFocus();    // "super" JFrame requests focus to receive KeyEvent
    }

    // Helper method to move the player left
//    private void moveLeft() {
//        // Save the current dimensions for repaint to erase the player
//        int savedX = player.x;
//        // update player
//        player.x -= 10;
//        // Repaint only the affected areas, not the entire JFrame, for efficiency
//        gameField.repaint(savedX, player.y, player.width, player.height); // Clear old area to background
//        gameField.repaint(player.x, player.y, player.width, player.height); // Paint new location
//    }

    // Helper method to move the player right
//    public void moveRight() {
//        // Save the current dimensions for repaint to erase the player
//        int savedX = player.x;
//        // update player
//        player.x += 10;
//        // Repaint only the affected areas, not the entire JFrame, for efficiency
//        gameField.repaint(savedX, player.y, player.width, player.height); // Clear old area to background
//        gameField.repaint(player.x, player.y, player.width, player.height); // Paint at new location
//    }

    private void move200(int pos) throws InterruptedException {
        System.out.println(pos);
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
            Thread.sleep(100);
        }
    }

    public void animateToNextPoint() throws InterruptedException {
        int startX = (xPoints[current % 4]);
        int startY = (yPoints[current % 4]);
        int endX = (xPoints[(current + 1) % 4]);
        int endY = (yPoints[(current + 1) % 4]);


        move200(current % 4);
        current++;


        //System.out.println(current % 4);
        //bresenham(startX, startY, endX, endY);
//        switch (current % 4) {
//            case 0:
//                plotLineLow(startX, startY, endX, endY);
//                break;
//            case 1:
//                plotLineHigh(startX, startY, endX, endY);
//                break;
//            case 2:
//            //    plotLineLow(endX, endY, startX, startY);
////                plotLineLow(startX, startY, endX, endY);
////                plotLineHigh(endX, endY, startX, startY);
//                plotLineHigh(startX, startY, endX, endY);
//                break;
//            case 3:
//                plotLineLow(startX, startY, endX, endY);
//                break;
//        }
//        current++;
//       this.animateWithBresenham(startX, startY, endX, endY);
        // current++;

//        Line2D line = new Line2D.Double(startX, startY, endX, endY);
//
//        Point2D[] point2DS = MathHelper.pointsAlongLine(line, 10);
//        System.out.println(point2DS.length);
//        for (Point2D  point : point2DS) {
//            player.x = (int) point.getX();
//            player.y = (int) point.getY();
//            System.out.println(player.x + " " + player.y);
//            gameField.paintImmediately(0,0,500,500);
//        }
//        current++;
    }

        public static void bresenham ( int x1, int y1, int x2, int y2)
        {
            int m_new = 2 * (y2 - y1);
            int slope_error_new = m_new - (x2 - x1);

            for (int x = x1, y = y1; x <= x2; x++) {
                System.out.print("(" + x + "," + y + ")\n");

                // Add slope to increment angle formed
                slope_error_new += m_new;

                // Slope error reached limit, time to
                // increment y and update slope error.
                if (slope_error_new >= 0) {
                    y++;
                    slope_error_new -= 2 * (x2 - x1);
                }
            }
        }

        private void plotLineLow ( double x0, double y0, double x1, double y1){
            System.out.println("low");
            double dx = (x1 - x0);
            double dy = (y1 - y0);
            int yi = 1;
            if (dy < 0) {
                yi = -1;
                dy = -dy;
            }
            double delta = (2 * dy) - dx;
            double y = y0;

            for (double i = x0; i < x1; i++) {
                //player.plot(MathHelper.rotateX(i, y, 90), MathHelper.rotateY(i, y, 90));
                player.plot(i, y);
                if (delta > 0) {
                    y += yi;
                    delta += (2 * (dy - dx));
                } else {
                    delta += (2 * dy);
                }
                this.paintImmediately(0, 0, 500, 500);
            }
        }

        private void plotLineHigh ( double x0, double y0, double x1, double y1){
            System.out.println("high");
            double dx = (x1 - x0);
            double dy = (y1 - y0);
            int xi = 1;
            if (dx < 0) {
                xi = -1;
                dx = -dx;
            }
            double delta = (2 * dx) - dy;
            double x = x0;

            for (double i = y0; i < y1; i++) {
                //player.plot(MathHelper.rotateX(x, i, 90), MathHelper.rotateY(x, i, 90));
                player.plot(x, i);
                if (delta > 0) {
                    x += xi;
                    delta += (2 * (dx - dy));
                } else {
                    delta += (2 * dx);
                }
                this.paintImmediately(0, 0, 500, 500);
            }
        }

        public void animateWithBresenham ( double x0, double y0, double x1, double y1) throws InterruptedException {
            if (Math.abs(y1 - y0) < Math.abs(x1 - x0)) {
                if (x0 > x1) {
                    this.plotLineLow(x1, y1, x0, y0);
                } else {
                    this.plotLineLow(x0, y0, x1, y1);
                }
            } else {
                if (y0 > y1) {
                    this.plotLineHigh(x1, y1, x0, y0);
                } else {
                    this.plotLineHigh(x0, y0, x1, y1);
                }
            }
            gameField.repaint();
        }

        // Define inner class GameField, which is a JPanel used for custom drawing
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

                g2d.drawOval(100, 100, 5, 5);
                //g2d.drawOval(400, 400, 5, 5);

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

    public void plot(double x, double y) {
        this.x = (int) (x);
        this.y = (int) (y);
        //System.out.println(this.x + " " + this.y);
    }

    public void plot(int x, int y) {
        this.x = x;
        this.y = y;
        //System.out.println(this.x + " " + this.y);
    }
}
