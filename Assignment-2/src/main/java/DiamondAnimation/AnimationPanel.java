/*
 * Program Name: "Diamond Animation".  This program shows how to add and subtract two numbers using a simple UI with three
 *  active buttons.  Copyright (C) 2021 Jarrod Burges
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU
 *  General Public License
 *  version 3 as published by the Free Software Foundation.
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 *  even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public
 *  License for more details.
 *  A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

package DiamondAnimation;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the main visual display of the {@link GameField} and {@link Player}
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class AnimationPanel extends JPanel {
    /**
     * Represents the main panel GameField that the player can be see in
     */
    private GameField gameField;

    /**
     * Represents the only and main player on the {@link GameField}
     */
    private Player player;

    /**
     * Timer that controls the player's movement inside {@link AnimationPanel#moveAcrossDiamond(int, JButton)}
     */
    private Timer timer = null;

    private int[] defaultXPoints = {250, 450, 250, 50};
    private int[] defaultYPoints = {50, 250, 450, 250};
    private Polygon defaultPolygon = new Polygon(defaultXPoints, defaultYPoints, 4);

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset height and width equal to 500.
     */
    public AnimationPanel() {
        gameField = new GameField(defaultPolygon);
        gameField.setPreferredSize(new Dimension(500, 500));
        player = new Player(defaultPolygon.xpoints[0], defaultPolygon.ypoints[0], defaultPolygon, gameField);

        this.setMaximumSize(new Dimension(500, 500));

        this.setLayout(new GridLayout());
        this.add(gameField);

        setVisible(true);
        requestFocus();
    }

    public void changeToDefaultRhombusField() {
        gameField = new GameField(defaultPolygon);
        this.repaint(0,0,this.getWidth(),this.getHeight());
    }

    public void changeToRandomRhombusField() {
        if (timer == null) {
            Polygon randomRhombus = MathHelper.generateRandomRhombus();
            gameField.polygon = randomRhombus;
            player.setX(randomRhombus.xpoints[0]);
            player.setY(randomRhombus.ypoints[0]);
            player.setxPoints(randomRhombus.xpoints);
            player.setyPoints(randomRhombus.ypoints);
            player.reloadPoint2DSArray();
            player.setGameField(gameField);

            this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        }
    }


    /**
     * Moves {@link AnimationPanel#player} across every point on the {@link GameField}.
     * In the order of bottom, right, top, left, then back to bottom. This movement only occurs if the player
     * is stationary. If the player is currently moving then the function call gets voided.
     *
     * @param delay time between each 1 unit increase of {@link Player}
     */
    public void moveAcrossDiamond(int delay, JButton button) {
        synchronized (this) {
            if (timer != null) {
                timer.stop();
                timer = null;
                button.setText("Start");
            } else {
                button.setText("Pause");
                timer = new Timer(delay, e -> {
                    if (player.getLastPos() > 3) {
                        timer.stop();
                        button.setText("Start");
                        player.reloadPoint2DSArray();

                        synchronized (AnimationPanel.this) {
                            timer = null;
                        }
                    }
                    player.moveOneUnitUpdate();
                });
                timer.start();
            }
        }
    }

    /**
     * Inner helper class for {@link AnimationPanel}. GameField represents the JPanel in which a {@link Player}
     * exists in and moves around in.
     */
    class GameField extends JPanel {

        /**
         * Represents the background image of this object.
         */
        BufferedImage fieldImage;

        /**
         * Creates the GameField object with a set field image. If the field image cannot load properly
         * the background defaults to plain green.
         */

        Polygon polygon;
        public GameField(Polygon polygon) {
            this.polygon = polygon;
            try {
                fieldImage = ImageIO.read(getClass().getResourceAsStream("/field.png"));
            } catch (IllegalArgumentException | IOException e) {
                fieldImage = null;
                this.setBackground(Color.GREEN);
            }
        }

        /**
         * Main paint method to draw the GameField.
         * Modifies the GameField such that the orgin(0,0) occurs at the bottom left instead of top left.
         * Paints @{@link AnimationPanel#player} to the GameField.
         *
         * @param g - Graphics object to paint to
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //rescales and translates g2d to have (0,0) orgin at bottom left rather than top left
            g2d.scale(1, -1);
            g2d.translate(0, -getHeight());

            //only draws the fancy field image if there wasn't errors with loading the image from file
            if (fieldImage != null)
                g2d.drawImage(fieldImage, 0, 0, null);
            g2d.drawPolygon(polygon);
            player.paint(g2d);
            g2d.dispose();
            g.dispose();
        }
    }
}

/**
 * Represents the player that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Player {
    private int x;
    private int y;
    private final double radius = 20;
    private BufferedImage playerImage;
    private int lastPos = 0;
    private int nextPos = 1;
    private int[] xPoints;
    private int[] yPoints;
    private ArrayList<ArrayList<Point2D>> everyPoint2DS;
    private int pos = 0;
    private AnimationPanel.GameField gameField;
    int test = 0;
    /**
     * @param x      x coordinate on the {@link AnimationPanel.GameField}
     * @param y      y coordinate on the {@link AnimationPanel.GameField}
     */
    public Player(int x, int y, Polygon polygon, AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.gameField = gameField;
        this.xPoints = polygon.xpoints;
        this.yPoints = polygon.ypoints;

        everyPoint2DS = new ArrayList<>(4);
        everyPoint2DS.add(MathHelper.bresenham(xPoints[0], yPoints[0], xPoints[1], yPoints[1]));
        everyPoint2DS.add(MathHelper.bresenham(xPoints[1], yPoints[1], xPoints[2], yPoints[2]));
        everyPoint2DS.add(MathHelper.bresenham(xPoints[2], yPoints[2], xPoints[3], yPoints[3]));
        everyPoint2DS.add(MathHelper.bresenham(xPoints[3], yPoints[3], xPoints[0], yPoints[0]));

        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/player.png"));
        } catch (IllegalArgumentException | IOException e) {
            playerImage = null;
        }
    }

    /**
     * Paints the player at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (playerImage != null)
            g2d.drawImage(playerImage, (int) (x - (radius)), (int) (y - (radius)), null);
        else
            g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), 20, 20);
    }

    public void reloadPoint2DSArray() {
        everyPoint2DS.clear();
        everyPoint2DS.add(MathHelper.bresenham(xPoints[0], yPoints[0], xPoints[1], yPoints[1]));
        everyPoint2DS.add(MathHelper.bresenham(xPoints[1], yPoints[1], xPoints[2], yPoints[2]));
        everyPoint2DS.add(MathHelper.bresenham(xPoints[2], yPoints[2], xPoints[3], yPoints[3]));
        everyPoint2DS.add(MathHelper.bresenham(xPoints[3], yPoints[3], xPoints[0], yPoints[0]));
        pos = 0;
        test = 0;
        lastPos = 0;
        nextPos = 1;
    }


    public void moveOneUnitUpdate() {
        if (pos == everyPoint2DS.get(test%4).size()) {
            System.out.println("moved point" + x);
            pos = 0;
            test++;

            lastPos++;
            nextPos++;
        }
        gameField.paintImmediately(this.x, this.y,100,100);
        this.x = (int) everyPoint2DS.get(test%4).get(pos).getX();
        this.y = (int) everyPoint2DS.get(test%4).get(pos).getY();
        pos++;
        gameField.paintImmediately(0, 0,500,500);
    }

    public int getLastPos() {
        return lastPos;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setxPoints(int[] xPoints) {
        this.xPoints = xPoints;
    }

    public void setyPoints(int[] yPoints) {
        this.yPoints = yPoints;
    }

    public void setGameField(AnimationPanel.GameField gameField) {
        this.gameField = gameField;
    }
}