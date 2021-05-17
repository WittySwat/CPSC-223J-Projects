/*
 * Program Name: "<insert name>".  This program shows how to add and subtract two numbers using a simple UI with three
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

package SineWave;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Represents the main visual display of the {@link GameField} and it's objects {@link Mouse} {@link Cat}
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class AnimationPanel extends JPanel {

    /**
     * Represents the main panel GameField that the mouse can be see in
     */
    private final GameField gameField;

    /**
     * Represents the main mouse object within a {@link GameField}
     */
    private final Mouse mouse;

    /**
     * Represents the main cat object within a {@link GameField}
     */
    private final Cat cat;

    /**
     * Timer that controls the mouse's movement inside
     */
    private Timer mouseMovementTimer = null;

    /**
     * Timer that controls the cat's movement inside
     */
    private Timer catMovementTimer = null;

    /**
     * Timer that controls when the {@link GameField} is repainted. Controlled by {@link #REFRESH_RATE} in the unit of Hertz
     */
    private Timer refreshRateTimer = null;

    private final int CANVAS_WIDTH = 1000;
    private final int CANVAS_HEIGHT = 750;

    private final int X_CENTER = CANVAS_WIDTH/2;
    private final int Y_CENTER = CANVAS_HEIGHT/2;

    /**
     * Number of times per second the {@link GameField} gets refreshed.
     */
    private final int REFRESH_RATE = 120;

    /**
     * The distance when a cat has sucessfully caught the mouse.
     */
    private final int collisionDistance = 50;

    /**
     * Creates an DiamondAnimation.AnimationPanel with preset width and height equal to {@link #CANVAS_WIDTH} and {@link #CANVAS_HEIGHT}
     */
    public AnimationPanel() {
        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        mouse = new Mouse(X_CENTER, Y_CENTER, 0, gameField);
        cat = new Cat(60, ((int) gameField.getSize().getHeight()) - 25, mouse, gameField);

        this.setLayout(new GridLayout());
        this.add(gameField);

        setVisible(true);
    }

    public GameField getGameField() {
        return gameField;
    }

    /**
     * Main game logic method to handle all movement and refresh clocks for the {@link Mouse} and {@link Cat}.
     * Repetitive method calls toggles the game field state between currently running(clocks active) and not running(clocks inactive)
     *
     *
     * @param theta angle in degrees to move the mouse in
     * @param button JButton to change text on between "Start" and "Pause"
     * @param mousePixelSpeedInput JFormattedTextField representing the speed in pixel/sec to move the mouse
     * @param catPixelSpeedInput JFormattedTextField representing the speed in pixel/sec to move the cat
     * @param directionInput JFormattedTextField representing the direction(theta) input
     * @param distanceBetweenMouseCatLabel JLabel to display the current distance between mouse and cat
     * @param controlPanel {@link ControlPanel} object to update the location inputs with current positions
     * @param stopImmediately boolean to stop the timer clock immediately when true
     */
    public void moveMouseCat(double theta,
                             JButton button,
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
            mouse.setTheta(theta);
            //ignore method call a timer isn't running and stop immediately is true
            if (mouseMovementTimer == null && stopImmediately) {
                return;
            }
            //resets mouse, cat, and location labels and recalculates cat's distance to mouse
            //this is used when a cat has caught a mouse and the user then presses start again
            //to automatically reset the gamefield
            if (cat.getDistanceToMouse() <= collisionDistance) {
               //controlPanel.resetAndRefreshCatMouseLocation();
                cat.calculateDistanceToMouse(mouse);
            }
            //main start/pause if statement to toggle between the two states of the game
            if (mouseMovementTimer != null || stopImmediately) {
                //stops all clocks and sets them to null
                button.setText("Start");
                mouseMovementTimer.stop();
                catMovementTimer.stop();
                refreshRateTimer.stop();

                mouseMovementTimer = null;
                catMovementTimer = null;
                refreshRateTimer = null;
            } else {
                //assigns all clocks and starts them
                button.setText("Pause");

                mouseMovementTimer = new Timer(mousePixSpeed, e -> {
                    mouse.moveOneUnitUpdate();
                    directionInput.setValue(mouse.getTheta());
                });

                catMovementTimer = new Timer(catPixSpeed, e -> {
                    cat.moveOneUnitTowardPoint(mouse.getX(), mouse.getY());
                    distanceBetweenMouseCatLabel.setText(String.format("%.2f", cat.calculateDistanceToMouse(mouse.getX(), mouse.getY())));

                    //stopping logic to stop the game once the cat has caught the mouse
                    if (cat.getDistanceToMouse() <= collisionDistance) {
                        button.setText("Start");
                        mouseMovementTimer.stop();
                        catMovementTimer.stop();
                        refreshRateTimer.stop();

                        mouseMovementTimer = null;
                        catMovementTimer = null;
                        refreshRateTimer = null;
                    }
                });

                //refreshes only the gamefield and location inputs
                refreshRateTimer = new Timer((int) (1000.0/REFRESH_RATE), e -> {
                    gameField.paintImmediately(0, 0, (int) gameField.getSize().getWidth(), (int) gameField.getSize().getHeight());
                //    controlPanel.refreshLocationInputs();
                });

                //finally starts all 3 clocks
                mouseMovementTimer.start();
                catMovementTimer.start();
                refreshRateTimer.start();
            }
        }
    }

    /**
     * Main game logic method to handle all movement and refresh clocks for the {@link Mouse} and {@link Cat}.
     * Repetitive method calls toggles the game field state between currently running(clocks active) and not running(clocks inactive)
     * With the addition of adding some very basic movement AI to the mouse. The mouse will now try to run away from the cat.
     * The mouse will only pick random directions to move in only if that direction it is not within the quadrant the cat is within.
     * This very basic AI has the outcome where almost instantly the cat corners the mouse into a corner.
     *
     * @param button JButton to change text on between "Start" and "Pause"
     * @param mousePixelSpeedInput JFormattedTextField representing the speed in pixel/sec to move the mouse
     * @param catPixelSpeedInput JFormattedTextField representing the speed in pixel/sec to move the cat
     * @param directionInput JFormattedTextField representing the direction(theta) input
     * @param distanceBetweenMouseCatLabel JLabel to display the current distance between mouse and cat
     * @param controlPanel {@link ControlPanel} object to update the location inputs with current positions
     * @param stopImmediately boolean to stop the timer clock immediately when true
     */
    public void moveMouseCatRandom(JButton button,
                         JFormattedTextField mousePixelSpeedInput,
                         JFormattedTextField catPixelSpeedInput,
                         JFormattedTextField directionInput,
                         JLabel distanceBetweenMouseCatLabel,
                         ControlPanel controlPanel,
                         boolean stopImmediately) {

        int mousePixSpeed = (int) (1000/((Number) mousePixelSpeedInput.getValue()).doubleValue());
        int catPixSpeed = (int) (1000/((Number) catPixelSpeedInput.getValue()).doubleValue());
        final int[] lastChangeMouseDirection = {0};
        synchronized (this) {
            //ignore method call a timer isn't running and stop immediately is true
            if (mouseMovementTimer == null && stopImmediately) {
                return;
            }
            //resets mouse, cat, and location labels and recalculates cat's distance to mouse
            //this is used when a cat has caught a mouse and the user then presses start again
            //to automatically reset the gamefield
            if (cat.getDistanceToMouse() <= collisionDistance) {
       //         controlPanel.resetAndRefreshCatMouseLocation();
                cat.calculateDistanceToMouse(mouse);
            }
            //main start/pause if statement to toggle between the two states of the game
            if (mouseMovementTimer != null || stopImmediately) {
                //stops all clocks and sets them to null
                button.setText("Start");
                mouseMovementTimer.stop();
                catMovementTimer.stop();
                refreshRateTimer.stop();

                mouseMovementTimer = null;
                catMovementTimer = null;
                refreshRateTimer = null;
            } else {
                button.setText("Pause");
                Random random = new Random();
                mouse.setTheta(random.nextInt(360));
                directionInput.setValue(mouse.getTheta());
                mouseMovementTimer = new Timer(mousePixSpeed, e -> {
                    //only considers to change mouse direction if it has moved 100 pixels
                    if (lastChangeMouseDirection[0] > 25) {
                        //if mouse has moved 100 pixels then there's a 20% chance
                        // each clock tick to change the mouse's direction
                        if (Math.random() <= 0.2) {
                            //random theta between 0.0 and 360.0
                            if (mouse.getX() < cat.getX()) {
                                if (mouse.getY() < cat.getY()) {
                                    //case cat is in Quad 1(NE) of mouse
                                    //pick new theta from degrees [90, 360]
                                    mouse.setTheta(random.nextInt(360 + 1 - 90) + 90);
                                }
                                else {
                                    //case cat is in Quad 4(SE) of mouse
                                    //pick new theta from degrees [0, 270]
                                    mouse.setTheta(random.nextInt(270 + 1 - 0) + 0);
                                }
                            } else {
                                if (mouse.getY() < cat.getY()) {
                                    //case cat is in Quad 2(NW) of mouse
                                    //pick new theta from degrees [-180, 90]
                                    mouse.setTheta(random.nextInt(90 + 1 - (-180) + (-180)));
                                }
                                else {
                                    //case cat is in Quad 3(NW) of mouse
                                    //pick new theta from degrees [-90, 180]
                                    mouse.setTheta(random.nextInt(180 + 1 - (-90)) + (-90));
                                }
                            }
                            directionInput.setValue(mouse.getTheta());
                            lastChangeMouseDirection[0] = 0;
                        }
                    }
                    mouse.moveOneUnitUpdate();
                    lastChangeMouseDirection[0]++;
                });

                catMovementTimer = new Timer(catPixSpeed, e -> {
                    cat.moveOneUnitTowardPoint(mouse.getX(), mouse.getY());
                    distanceBetweenMouseCatLabel.setText(String.format("%.2f", cat.calculateDistanceToMouse(mouse.getX(), mouse.getY())));

                    if (cat.getDistanceToMouse() <= collisionDistance) {
                        button.setText("Start");
                        mouseMovementTimer.stop();
                        catMovementTimer.stop();
                        refreshRateTimer.stop();

                        mouseMovementTimer = null;
                        catMovementTimer = null;
                        refreshRateTimer = null;
                    }
                });

                refreshRateTimer = new Timer((int) (1000.0/REFRESH_RATE), e -> {
                    gameField.paintImmediately(0, 0, (int) gameField.getSize().getWidth(), (int) gameField.getSize().getHeight());
           //         controlPanel.refreshLocationInputs();
                });

                //finally starts all clocks
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

            //paints the mouse and cat objects
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
    private BufferedImage mouseImage;

    /**
     * Controls whether to flip the image to face right, true, or face left, false.
     */
    private boolean faceRight = true;
    private AnimationPanel.GameField gameField;

    /**
     *
     * @param x x coordinate on the {@link AnimationPanel.GameField}
     * @param y y coordinate on the {@link AnimationPanel.GameField}
     * @param theta angle the mouse moves at
     * @param gameField field for the mouse to move around in
     */
    public Mouse(double x, double y, double theta, AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.gameField = gameField;

        try {
            mouseImage = ImageIO.read(getClass().getResourceAsStream("/mouse.png"));
        } catch (IllegalArgumentException | IOException | NullPointerException e) {
            mouseImage = null;
        }
    }

    /**
     * Paints the mouse with either the fancy mouseImage if it was loaded or a simple circle if it wasn't.
     * Both the fancy image and the circle are centered on the current coordinates of the mouse.
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        double radius = 10;
        Math.round(x - radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        //whether or not to paint the mouse with the fancy image or plan circle
        if (mouseImage != null) {
            if (faceRight) {
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-mouseImage.getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

                g2d.drawImage(op.filter(mouseImage, null), paintX - 25, paintY - 5, null);
            } else {
                //paints the fancy player image centered on the current (x,y)
                g2d.drawImage(mouseImage, paintX - 25, paintY - 5, null);
            }
        }
        else {
            //paints an oval representing the player centered on the current (x,y)
            //only used if there was an issue loading the fancy player image
            g2d.fillOval(paintX, paintY, 20, 20);
        }
//        //offset to find center of image
//        g2d.fillOval(paintX, paintY, 10, 10);
    }

    /**
     * Moves the mouse one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x += (Math.cos(Math.toRadians(theta)));
        y += (Math.sin(Math.toRadians(theta)));

        //flips mouseImage if the x coordinate is decreasing
        //where x is decreasing means mouse faces left
        //where x is iscreasing means mouse faces right
        if ((Math.cos(Math.toRadians(theta))) <= 0) {
            faceRight = false;
        } else
            faceRight = true;

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
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;
    private BufferedImage catImage;
    private boolean hasReset = false;
    private double distanceToMouse;
    private boolean faceRight = true;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the mouse to move around in
     */

    /**
     *
     * @param x
     * @param y
     * @param mouse
     * @param gameField
     */
    public Cat(double x, double y, Mouse mouse, AnimationPanel.GameField gameField) {
        this.gameField = gameField;
        this.x = x;
        this.y = y;
        distanceToMouse = calculateDistanceToMouse(mouse);

        try {
            catImage = ImageIO.read(getClass().getResourceAsStream("/cat.png"));
        } catch (IllegalArgumentException | IOException | NullPointerException e ) {
            catImage = null;
        }
    }

    /**
     * Paints the mouse at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        //fixes weird error on application startup where the cat doesn't display in correct coordinates
        //so this runs once on the first ever method call to this function
        if (!hasReset) {
            this.resetCat();
            hasReset = true;
        }

        double radius = 20;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        if (catImage != null) {
            if (faceRight) {
                AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                tx.translate(-catImage.getWidth(null), 0);
                AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);

                g2d.drawImage(op.filter(catImage, null), paintX - 50, paintY - 20, null);
            } else {
                //paints the fancy player image centered on the current (x,y)
                g2d.drawImage(catImage, paintX - 50, paintY - 20, null);
            }
        }
        else {
            //paints an oval representing the player centered on the current (x,y)
            //only used if there was an issue loading the fancy player image
            g2d.fillOval(paintX, paintY, 20, 20);
        }
//        //offset to find center of image
//        g2d.fillOval(paintX, paintY, 10, 10);
    }

    /**
     * Moves the mouse one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitTowardPoint(double mouseX, double mouseY) {
        double length = Point2D.distance(x, y, mouseX, mouseY);

        x += (mouseX - x)/length;
        y += (mouseY - y)/length;

        //flips the cat if the difference between mouseX and catX is more than 15
        //has the effect of the cat always facing toward the mouse
        if (mouseX - x >= 15)
            faceRight = true;
        else
            faceRight = false;
    }

    /**
     * Calculates the distance between the mouse and cat
     *
     * @param mouseX mouse coordinates on the x axis
     * @param mouseY mouse coordinates on the y axis
     * @return distance between Cat and Mouse in double
     */
    public double calculateDistanceToMouse(double mouseX, double mouseY) {
        distanceToMouse = Point2D.distance(x, y, mouseX, mouseY);
        return distanceToMouse;
    }

    public double calculateDistanceToMouse(Mouse mouse) {
        distanceToMouse = Point2D.distance(x, y, mouse.getX(), mouse.getY());
        return distanceToMouse;
    }

    public void resetCat() {
        x = 60;
        y = gameField.getSize().getHeight() - 25;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDistanceToMouse() {
        return distanceToMouse;
    }

}
