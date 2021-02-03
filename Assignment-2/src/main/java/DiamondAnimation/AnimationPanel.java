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
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the main visual display of the {@link GameField} and {@link Player}
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class AnimationPanel extends JPanel {
    /**
     * Represents the main panel GameField that the player can be see in
     */
    private final GameField gameField;

    /**
     * Represents the only and main player on the {@link GameField}
     */
    private final Player player;

    /**
     * Timer that controls the player's movement inside {@link AnimationPanel#moveAcrossDiamond(int, JButton)}
     */
    private Timer timer = null;

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset height and width equal to 500.
     */
    public AnimationPanel() {
        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(500, 500));

        player = new Player(250, 50, gameField);

        this.setMaximumSize(new Dimension(500, 500));

        this.setLayout(new GridLayout());
        this.add(gameField);

        setVisible(true);
        requestFocus();
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
                        player.resetPos();

                        synchronized (AnimationPanel.this) {
                            timer = null;
                        }
                    }
                    player.moveOneUnit();
                    gameField.paintImmediately(0, 0, 500, 500);
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
        public GameField() {
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

            player.paint(g);
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
    private final int[] xPoints = {250, 450, 250, 50};
    private final int[] yPoints = {450, 250, 50, 250};
    private final AnimationPanel.GameField gameField;

    /**
     * @param x      x coordinate on the {@link AnimationPanel.GameField}
     * @param y      y coordinate on the {@link AnimationPanel.GameField}
     */
    public Player(int x, int y, AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.gameField = gameField;

        try {
            playerImage = ImageIO.read(getClass().getResourceAsStream("/player.png"));
        } catch (IllegalArgumentException | IOException e) {
            playerImage = null;
        }
    }

    /**
     * Paints the player at it's current coordinates centered on (x, y).
     *
     * @param g Graphics object to paint to
     */
    public void paint(Graphics g) {
        if (playerImage != null)
            g.drawImage(playerImage, (int) (x - (radius)), (int) (y - (radius)), null);
        else
            g.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), 20, 20);
    }

    /**
     * Sets the player to the specified coordinate on the {@link AnimationPanel.GameField}.
     * Requires a repaint on the GameField for changes to be seen in the UI.
     *
     * @param x x coordinate to change the player to on the {@link AnimationPanel.GameField}
     * @param y y coordinate to change the player to on the {@link AnimationPanel.GameField}
     */
    public void plot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void moveOneUnit() {
        int dx = 1;
        int dy = -1;
        if ((xPoints[nextPos%4] - xPoints[lastPos%4]) < 0)
            dx = -1;
        if ((yPoints[nextPos%4] - yPoints[lastPos%4]) < 0)
            dy = 1;
        x += dx;
        y += dy;

        if (x == 50 | x == 250 || x == 450) {
            if (y == 50 | y == 250 || y == 450) {
                lastPos++;
                nextPos++;
            }
        }
    }

    public void moveToNextPoint() {
        int numXUnits = Math.abs(xPoints[nextPos%4] - xPoints[lastPos%4]);
        int numYUnits = Math.abs(yPoints[nextPos%4] - yPoints[lastPos%4]);
        if (numXUnits != numYUnits)
            System.out.println("Error: not on y=x line");
        for (int i = 0; i < numXUnits; i++) {
            moveOneUnit();
            gameField.paintImmediately(0,0,500,500);
        }
        lastPos++;
        nextPos++;
    }

    public void resetPos() {
        nextPos = 1;
        lastPos = 0;
    }

    public int getLastPos() {
        return lastPos;
    }

}