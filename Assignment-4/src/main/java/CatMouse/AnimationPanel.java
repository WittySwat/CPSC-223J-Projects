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

package CatMouse;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Represents the main visual display of the {@link GameField} and {@link Mouse}
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class AnimationPanel extends JPanel {
    /**
     * Represents the main panel GameField that the mouse can be see in
     */
    private GameField gameField;

    /**
     * Represents the only and main mouse on the {@link GameField}
     */
    private Mouse mouse;

    private Cat cat;

    /**
     * Timer that controls the mouse's movement inside
     */
    private Timer mouseMovementTimer = null;
    private Timer catMovementTimer = null;
    private Timer refreshRateTimer = null;

    private int CANVAS_WIDTH = 1000;
    private int CANVAS_HEIGHT = 750;

    private int X_CENTER = CANVAS_WIDTH/2;
    private int Y_CENTER = CANVAS_HEIGHT/2;

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset width and height equal to 1000 and 750.
     */
    public AnimationPanel() {
        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        mouse = new Mouse(X_CENTER, Y_CENTER, 0, gameField);
        cat = new Cat(10, ((int) gameField.getSize().getHeight()) - 10, gameField);

        this.setLayout(new GridLayout());
        this.add(gameField);


        setVisible(true);
    }

    public GameField getGameField() {
        return gameField;
    }

    /**
     * Moves {@link AnimationPanel#mouse} across every point on the {@link GameField}.
     * In the order of bottom, right, top, left, then back to bottom. This movement only occurs if the mouse
     * is stationary. If the mouse is currently moving then the function call gets voided.
     *
     * @param theta angle in degrees to move the mouse in
     * @param button JButton to change text on between "Start" and "Pause"
     * @param directionInput JFormattedTextField representing the direction(theta) input
     * @param stopImmediately boolean to stop the timer clock immediately when true
     */
    public void moveBall(double theta, JButton button,
                         JFormattedTextField mousePixelSpeedInput,
                         JFormattedTextField catPixelSpeedInput,
                         JFormattedTextField directionInput,
                         JLabel distanceBetweenMouseCatLabel,
                         ControlPanel controlPanel,
                         boolean stopImmediately) {

        int mousePixSpeed = (int) (1000/((Number) mousePixelSpeedInput.getValue()).doubleValue());
        int catPixSpeed = (int) (1000/((Number) catPixelSpeedInput.getValue()).doubleValue());
        mouse.setTheta(theta);
        synchronized (this) {
            //edge case for when you want to reset the field but the cat/mouse aren't moving
            if (mouseMovementTimer == null && stopImmediately) {
                return;
            }
            if (cat.getDistanceToMouse() <= 1) {
                controlPanel.resetAndRefreshCatMouseLocation();
            }

            if (mouseMovementTimer != null || stopImmediately) {
                mouseMovementTimer.stop();
                mouseMovementTimer = null;

                catMovementTimer.stop();
                catMovementTimer = null;

                refreshRateTimer.stop();
                refreshRateTimer = null;
                button.setText("Start");
            } else {
                button.setText("Pause");
                mouseMovementTimer = new Timer(mousePixSpeed, e -> {
                    mouse.moveOneUnitUpdate();
                    directionInput.setValue(mouse.getTheta());
                });

                catMovementTimer = new Timer(catPixSpeed, e -> {
                    cat.moveOneUnitTowardPoint(mouse.getX(), mouse.getY(), ((Number) catPixelSpeedInput.getValue()).doubleValue()/catPixSpeed);
                    distanceBetweenMouseCatLabel.setText(String.format("%.2f", cat.calculateDistanceToMouse(mouse.getX(), mouse.getY())));

                    if (cat.getDistanceToMouse() <= 1) {
                        button.setText("Start");
                        mouseMovementTimer.stop();
                        catMovementTimer.stop();
                        refreshRateTimer.stop();

                        mouseMovementTimer = null;
                        catMovementTimer = null;
                        refreshRateTimer = null;
                    }

                });

                refreshRateTimer = new Timer((int) (1000.0/120), e -> {
                    gameField.paintImmediately(0, 0, (int) gameField.getSize().getWidth(), (int) gameField.getSize().getHeight());
                    controlPanel.refreshLocationInputs();
                });


                mouseMovementTimer.start();
                catMovementTimer.start();
                refreshRateTimer.start();
            }
        }
    }

    public Mouse getMouse() {
        return this.mouse;
    }

    public Cat getCat() {
        return this.cat;
    }

    /**
     * Inner helper class for {@link AnimationPanel}. GameField represents the JPanel in which a {@link Mouse}
     * exists in and moves around in.
     */
    class GameField extends JPanel {
        public GameField() {
            this.setBackground(Color.green);
        }


        /**
         * Main paint method to draw the GameField.
         * Modifies the GameField such that the orgin(0,0) occurs at the bottom left instead of top left.
         * Paints @{@link AnimationPanel#mouse} to the GameField.
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
            mouse.paint(g2d);
            cat.paint(g2d);
        }
    }
}

/**
 * Represents the mouse that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Mouse {
    private double x;
    private double y;
    private double theta;
    private AnimationPanel.GameField gameField;

    /**
     *
     * @param x x coordinate on the {@link AnimationPanel.GameField}
     * @param y y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the mouse to move around in
     */
    public Mouse(double x, double y, double theta, AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.gameField = gameField;
    }

    /**
     * Paints the mouse at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        double radius = 20;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));
        g2d.fillOval(paintX, paintY, 20, 20);
    }

    /**
     * Moves the mouse one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x += (Math.cos(Math.toRadians(theta)));
        y += (Math.sin(Math.toRadians(theta)));

        if (x <= 0 || x >= gameField.getSize().getWidth())
            theta = 180 - theta;
        else if (y <= 0 || y >= gameField.getSize().getHeight())
            theta = 360 - theta;
    }

    public void resetMouse() {
        x = gameField.getSize().getWidth()/2;
        y = gameField.getSize().getHeight()/2;
        theta = 0;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public double getTheta() {
        return theta;
    }


    public double getX() {
        return x;
    }


    public double getY() {
        return y;
    }
}

/**
 * Represents the cat that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Cat {
    private int x;
    private int y;
    private AnimationPanel.GameField gameField;
    private boolean hasReset = false;
    private double distanceToMouse;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the mouse to move around in
     */
    public Cat(int x, int y, AnimationPanel.GameField gameField) {
        this.gameField = gameField;
        this.x = 0;
        this.y = 0;
        distanceToMouse = 0;
    }

    /**
     * Paints the mouse at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (!hasReset) {
            this.resetCat();
            hasReset = true;
        }
        double radius = 20;
        g2d.fillOval((int) (x - (radius / 2)), (int) (y - (radius / 2)), 20, 20);
    }

    /**
     * Moves the mouse one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitTowardPoint(double mouseX, double mouseY, double distance) {
        double length = Point2D.distance(x, y, mouseX, mouseY);

        x += distance * (mouseX - x)/length;
        y += distance * (mouseY - y)/length;
    }

    public double calculateDistanceToMouse(double mouseX, double mouseY) {
        distanceToMouse = Point2D.distance(x, y, mouseX, mouseY);
        return distanceToMouse;
    }

    public void resetCat() {
        x = 10;
        y = ((int) gameField.getSize().getHeight()) - 10;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getDistanceToMouse() {
        return distanceToMouse;
    }
}
