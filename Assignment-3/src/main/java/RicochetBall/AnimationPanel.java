/*
 * Program Name: "Ricochet Ball".  This program shows how to add and subtract two numbers using a simple UI with three
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

package RicochetBall;

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

    private int CANVAS_WIDTH = 1000;
    private int CANVAS_HEIGHT = 800;

    private int[] defaultXPoints = {CANVAS_WIDTH/2, (int) (CANVAS_WIDTH*0.9), CANVAS_WIDTH/2, (int) (CANVAS_WIDTH*0.1)};
    private int[] defaultYPoints = {(int) (CANVAS_HEIGHT*0.1), CANVAS_HEIGHT/2, (int) (CANVAS_HEIGHT*0.9), CANVAS_HEIGHT/2};

    /**
     * The default polygon the player will run around on. Created using {@link #defaultXPoints} and {@link #defaultYPoints}
     */
    private Polygon defaultPolygon = new Polygon(defaultXPoints, defaultYPoints, 4);

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset height and width equal to 1000.
     */
    public AnimationPanel() {
        gameField = new GameField(defaultPolygon);
        player = new Player(defaultPolygon.xpoints[0], defaultPolygon.ypoints[0], defaultPolygon, gameField);
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.setLayout(new GridLayout());
        this.add(gameField);

        setVisible(true);
    }

    /**
     * Toggles the field between a fancy field and a random field.
     *
     * @param button JButton object to toggle text between fancy and default fields
     */
    public void swapFieldType(JButton button) {
        if (timer == null) {
            if (gameField.fancyField) {
                gameField.polygon = MathHelper.generateRandomRhombus(50, (int) (CANVAS_HEIGHT*0.9));
                player.setX(gameField.polygon.xpoints[0]);
                player.setY(gameField.polygon.ypoints[0]);
                player.setxPoints(gameField.polygon.xpoints);
                player.setyPoints(gameField.polygon.ypoints);
                player.refreshPointArray();
                player.resetPosCounters();
                player.setGameField(gameField);

                button.setText("Fancy Field");
                gameField.fancyField = false;
            } else {
                gameField.polygon = defaultPolygon;
                player.setX(defaultXPoints[0]);
                player.setY(defaultYPoints[0]);
                player.setxPoints(defaultXPoints);
                player.setyPoints(defaultYPoints);
                player.refreshPointArray();
                player.resetPosCounters();
                player.setGameField(gameField);

                button.setText("Random Field");
                gameField.fancyField = true;
            }
            this.paintImmediately(0, 0, this.getWidth(), this.getHeight());
        }
    }


    /**
     * Moves {@link AnimationPanel#player} across every point on the {@link GameField}.
     * In the order of bottom, right, top, left, then back to bottom. This movement only occurs if the player
     * is stationary. If the player is currently moving then the function call gets voided.
     *
     * @param delay time between each 1 unit increase of {@link Player}
     * @param button JButton to change text on between "Start" and "Pause"
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
                        player.resetPosCounters();

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
        private Image fieldImage;

        /**
         * Represents the shape of the diamond or rhombus for the player to move around on.
         */
        private Polygon polygon;

        private boolean fancyField = true;

        /**
         * Creates the GameField object with a set field image. If the field image cannot load properly
         * the background defaults to plain green.]
         *
         * @param polygon shape of what the player runs around on
         */
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
            if (fancyField)
                g2d.drawImage(fieldImage, 0, 0, null);
            else {
                g2d.setStroke(new BasicStroke(5));
                g2d.drawPolygon(polygon);
            }
            player.paint(g2d);
        }
    }
}

/**
 * Represents the player that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Player {
    private int x;
    private int y;
    private BufferedImage playerImage;
    private int lastPos = 0;
    private int[] xPoints;
    private int[] yPoints;
    private final ArrayList<ArrayList<Point2D>> everyPoint2D;
    private int pos = 0;
    private AnimationPanel.GameField gameField;

    /**
     *
     * @param x x coordinate on the {@link AnimationPanel.GameField}
     * @param y y coordinate on the {@link AnimationPanel.GameField}
     * @param polygon shape for the player to move around on
     * @param gameField field for the player to move around in
     */
    public Player(int x, int y, Polygon polygon, AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.gameField = gameField;
        this.xPoints = polygon.xpoints;
        this.yPoints = polygon.ypoints;

        everyPoint2D = new ArrayList<>(4);
        everyPoint2D.add(MathHelper.bresenham(xPoints[0], yPoints[0], xPoints[1], yPoints[1]));
        everyPoint2D.add(MathHelper.bresenham(xPoints[1], yPoints[1], xPoints[2], yPoints[2]));
        everyPoint2D.add(MathHelper.bresenham(xPoints[2], yPoints[2], xPoints[3], yPoints[3]));
        everyPoint2D.add(MathHelper.bresenham(xPoints[3], yPoints[3], xPoints[0], yPoints[0]));

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
        double radius = 20;
        if (playerImage != null) {
            //paints the fancy player image centered on the current (x,y)
            g2d.drawImage(playerImage, (int) (x - (radius)), (int) (y - (radius)), null);
        }
        else {
            //paints an oval representing the player centered on the current (x,y)
            //only used if there was an issue loading the fancy player image
            g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), 20, 20);
        }
    }

    /**
     * Resets the player to the initial/starting position of the player's polygon.
     */
    public void resetPosCounters() {
        pos = 0;
        lastPos = 0;
    }

    /**
     * Refreshes the point arrays for every line segment in the player's polygon.
     */
    public void refreshPointArray() {
        everyPoint2D.clear();
        everyPoint2D.add(MathHelper.bresenham(xPoints[0], yPoints[0], xPoints[1], yPoints[1]));
        everyPoint2D.add(MathHelper.bresenham(xPoints[1], yPoints[1], xPoints[2], yPoints[2]));
        everyPoint2D.add(MathHelper.bresenham(xPoints[2], yPoints[2], xPoints[3], yPoints[3]));
        everyPoint2D.add(MathHelper.bresenham(xPoints[3], yPoints[3], xPoints[0], yPoints[0]));
    }


    /**
     * Moves the player one unit toward the next point in the player's current polygon.
     */
    public void moveOneUnitUpdate() {
        //resets pos counter and increments lastPos if pos equals the size of the point array
        //means that the player has reached a new corner of the polygon
        if (pos == everyPoint2D.get(lastPos%4).size()) {
            pos = 0;
            lastPos++;
            System.out.println("X: " + x + "Y: " + y);
        }
        //moves the player to the next x,y coordinate in the point2D array
        this.x = (int) everyPoint2D.get(lastPos%4).get(pos).getX();
        this.y = (int) everyPoint2D.get(lastPos%4).get(pos).getY();

        //System.out.println("X: " + x + "Y: " + y);
        pos++;
        //only update the gamefield every 4th unit update
        //visually no impact but code speed increases greatly
        if (pos % 4 == 0)
            gameField.paintImmediately(0, 0, gameField.getWidth(), gameField.getHeight());
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