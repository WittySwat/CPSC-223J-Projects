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
     * Timer that controls the player's movement inside {@link AnimationPanel#moveAcrossDiamond(int delay)}
     */
    private Timer timer;

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset height and width equal to 500.
     */
    public AnimationPanel() {
        player = new Player(250, 50, 20);

        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(500, 500));
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
    public void moveAcrossDiamond(int delay) {
        //if timer isn't null, meaning a timer is currently running exit this function
        synchronized (this) {
            if (timer != null) {
                return;
            }
        }

        final int[] numTimes = {0, 0};
        timer = new Timer(delay, e -> {
            //if numTimes[1] is > 3 means that player has gone to every point on the diamond and is back at start pos
            //stop timer and set to null, meaning a new timer can not be run
            if (numTimes[1] > 3) {
                timer.stop();
                synchronized (AnimationPanel.this) {
                    timer = null;
                }
            }
            //if numTimes[0] == 200 means that the player is now at the next point on the diamond.
            if (numTimes[0] == 200) {
                //move current point to the next one
                numTimes[1]++;
                //reset point counter
                numTimes[0] -= 200;
            }
            //picks how to move the player based on which point the player is currently at
            switch (numTimes[1]) {
                //player at bottom
                case 0:
                    //moves the player from bottom to right by 1 unit on the diagonal
                    player.plot(player.x + 1, player.y + 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                //player at right
                case 1:
                    //moves the player from right to top by 1 unit on the diagonal
                    player.plot(player.x - 1, player.y + 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                //player at top
                case 2:
                    //moves the player from top to left by 1 unit on the diagonal
                    player.plot(player.x - 1, player.y - 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                //player at left
                case 3:
                    //moves the player from left to bottom by 1 unit on the diagonal, thus going back to start pos
                    player.plot(player.x + 1, player.y - 1);
                    gameField.paintImmediately(0, 0, 500, 500);
                    break;
                default:
                    break;
            }
            //increases point controller by 1 meaning the player moved 1 unit closer to next point
            //increases up to 200 where player is now at the next point, then reset back to 0
            numTimes[0]++;
        });
        //starts the timer for the player to move across the diamond at the specified delay above
        timer.start();
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
    int x;
    int y;
    double radius;
    BufferedImage playerImage;

    /**
     *
     * @param x x coordinate on the {@link AnimationPanel.GameField}
     * @param y y coordinate on the {@link AnimationPanel.GameField}
     * @param radius size of player only used if fancy player image can't load
     */
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

    /**
     * Paints the player at it's current coordinates centered on (x, y).
     *
     * @param g Graphics object to paint to
     */
    public void paint(Graphics g) {
        if (playerImage != null)
            g.drawImage(playerImage, (int) (x - (radius)), (int) (y - (radius)), null);
        else
            g.fillOval((int) (x - (radius/2)), (int) (y - (radius/2)), 20, 20);
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
}
