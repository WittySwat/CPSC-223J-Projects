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

import javax.swing.*;
import java.awt.*;

/**
 * Represents the main visual display of the {@link GameField} and {@link Ball}
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class AnimationPanel extends JPanel {
    /**
     * Represents the main panel GameField that the ball can be see in
     */
    private GameField gameField;

    /**
     * Represents the only and main ball on the {@link GameField}
     */
    private Ball ball;

    /**
     * Timer that controls the ball's movement inside {@link AnimationPanel#moveBall(int, JButton)}
     */
    private Timer timer = null;

    private int CANVAS_WIDTH = 1000;
    private int CANVAS_HEIGHT = 800;

    private int X_CENTER = CANVAS_WIDTH/2;
    private int Y_CENTER = CANVAS_HEIGHT/2;

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset height and width equal to 1000.
     */
    public AnimationPanel() {
        gameField = new GameField();
        ball = new Ball(X_CENTER, Y_CENTER, 180.0, gameField);
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        this.setLayout(new GridLayout());
        this.add(gameField);

        setVisible(true);
    }


    /**
     * Moves {@link AnimationPanel#ball} across every point on the {@link GameField}.
     * In the order of bottom, right, top, left, then back to bottom. This movement only occurs if the ball
     * is stationary. If the ball is currently moving then the function call gets voided.
     *
     * @param delay time between each 1 unit increase of {@link Ball}
     * @param button JButton to change text on between "Start" and "Pause"
     */
    public void moveBall(int delay, JButton button) {
        synchronized (this) {
            if (timer != null) {
                timer.stop();
                timer = null;
                button.setText("Start");
            } else {
                button.setText("Pause");
                timer = new Timer(delay, e -> {
                    System.out.println("Timer running");
                    ball.moveOneUnitUpdate();
                    gameField.paintImmediately(0,0, 1000,800);
                });
                timer.start();
            }
        }
    }

    /**
     * Inner helper class for {@link AnimationPanel}. GameField represents the JPanel in which a {@link Ball}
     * exists in and moves around in.
     */
    class GameField extends JPanel {

        public GameField() {
            this.setBackground(Color.green);
        }


        /**
         * Main paint method to draw the GameField.
         * Modifies the GameField such that the orgin(0,0) occurs at the bottom left instead of top left.
         * Paints @{@link AnimationPanel#ball} to the GameField.
         *
         * @param g - Graphics object to paint to
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            //rescales and translates g2d to have (0,0) orgin at bottom left rather than top left
            //g2d.scale(1, -1);
           // g2d.translate(0, -getHeight());

            //only draws the fancy field image if there wasn't errors with loading the image from file
            ball.paint(g2d);
        }
    }
}

/**
 * Represents the ball that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Ball {
    private int x;
    private int y;
    private double theta;
    private AnimationPanel.GameField gameField;

    /**
     *
     * @param x x coordinate on the {@link AnimationPanel.GameField}
     * @param y y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the ball to move around in
     */
    public Ball(int x, int y, double theta, AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.gameField = gameField;
    }

    /**
     * Paints the ball at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        double radius = 20;
        g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), 20, 20);
    }

    /**
     * Moves the ball one unit in the direction it is heading.
     */
    public void moveOneUnitUpdate() {
        x += Math.cos(theta) * 1.5;
        y += Math.sin(theta) * 1.5;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setGameField(AnimationPanel.GameField gameField) {
        this.gameField = gameField;
    }
}