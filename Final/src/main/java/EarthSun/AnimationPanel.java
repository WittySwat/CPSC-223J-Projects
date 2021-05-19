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

package EarthSun;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents the main visual display of the {@link GameField} and it's objects {@link Sun} {@link Mars}
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class AnimationPanel extends JPanel {

    /**
     * Represents the main panel GameField that the sun can be see in
     */
    private final GameField gameField;

    /**
     * Represents the main sun object within a {@link GameField}
     */
    private final Sun sun;

    /**
     * Represents the main earth object within a {@link GameField}
     */
    private final Earth earth;

    private final Mars mars;

    private final Moon moon;

    private final Mercury mercury;

    private final Venus venus;

    /**
     * Timer that controls the sun's movement inside
     */
    private Timer earthMovementTimer = null;

    private Timer marsMovementTimer = null;

    private Timer moonMovementTimer = null;

    private Timer mercuryMovementTimer = null;

    private Timer venusMovementTimer = null;

    /**
     * Timer that controls when the {@link GameField} is repainted. Controlled by {@link #REFRESH_RATE} in the unit of Hertz
     */
    private Timer refreshRateTimer = null;

    private final int CANVAS_WIDTH = 750;
    private final int CANVAS_HEIGHT = 750;

    private final int X_CENTER = CANVAS_WIDTH/2;
    private final int Y_CENTER = CANVAS_HEIGHT/2;

    /**
     * Number of times per second the {@link GameField} gets refreshed.
     */
    private final int REFRESH_RATE = 120;


    /**
     * Creates an DiamondAnimation.AnimationPanel with preset width and height equal to {@link #CANVAS_WIDTH} and {@link #CANVAS_HEIGHT}
     */
    public AnimationPanel() {
        gameField = new GameField();
        gameField.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.setMaximumSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        this.add(gameField);
        setVisible(true);

        sun = new Sun(X_CENTER, Y_CENTER, gameField);
        earth = new Earth(X_CENTER + 100, Y_CENTER, gameField, sun);
        mars = new Mars(X_CENTER + 250, Y_CENTER, gameField, sun);
        moon = new Moon(X_CENTER + 100, Y_CENTER, gameField, earth);
        mercury = new Mercury(X_CENTER + 250, Y_CENTER, gameField, sun);
        venus = new Venus(X_CENTER + 250, Y_CENTER, gameField, sun);

        this.setLayout(new GridLayout());
    }

    public GameField getGameField() {
        return gameField;
    }

    public void moveSolarSystemBodies(JButton button,
                          JFormattedTextField earthPixelSpeedInput,
                                      JLabel earthXLocationLabel,
                                      JLabel earthYLocationLabel,
                          boolean stopImmediately) {
        int earthPixelSpeed = (int) (1000/((Number) earthPixelSpeedInput.getValue()).doubleValue());
        System.out.println(((Number) earthPixelSpeedInput.getValue()).doubleValue());
        synchronized (this) {
            //ignore method call a timer isn't running and stop immediately is true
            if (earthMovementTimer == null && stopImmediately) {
                return;
            }

            //main start/pause if statement to toggle between the two states of the game
            if (earthMovementTimer != null || stopImmediately) {
                //stops all clocks and sets them to null
                button.setText("Start");
                earthMovementTimer.stop();
                marsMovementTimer.stop();
                moonMovementTimer.stop();
                mercuryMovementTimer.stop();
                venusMovementTimer.stop();
                refreshRateTimer.stop();

                earthMovementTimer = null;
                marsMovementTimer = null;
                moonMovementTimer = null;
                mercuryMovementTimer = null;
                venusMovementTimer = null;
                refreshRateTimer = null;
            } else {
                //assigns all clocks and starts them
                button.setText("Pause");

                earthMovementTimer = new Timer(earthPixelSpeed, e -> {
                    earth.moveOneUnitUpdate();
                    earthXLocationLabel.setText(String.format("%.2f", earth.getX()));
                    earthYLocationLabel.setText(String.format("%.2f", earth.getY()));
                });

                marsMovementTimer = new Timer(earthPixelSpeed, e -> {
                    mars.moveOneUnitUpdate();
                });

                moonMovementTimer = new Timer(earthPixelSpeed/2, e -> {
                    moon.moveOneUnitUpdate();
                });

                mercuryMovementTimer = new Timer(earthPixelSpeed, e -> {
                    mercury.moveOneUnitUpdate();
                });

                venusMovementTimer = new Timer(earthPixelSpeed, e -> {
                    venus.moveOneUnitUpdate();
                });

                //refreshes only the gamefield and location inputs
                refreshRateTimer = new Timer((int) (1000.0/REFRESH_RATE), e -> {
                    gameField.paintImmediately(0, 0, (int) gameField.getSize().getWidth(), (int) gameField.getSize().getHeight());
                });

                //finally starts all 3 clocks
                earthMovementTimer.start();
                marsMovementTimer.start();
                moonMovementTimer.start();
                mercuryMovementTimer.start();
                venusMovementTimer.start();
                refreshRateTimer.start();
            }
        }
    }

    public void resetSolarSystem() {
        sun.reset();
        earth.reset();
        mars.reset();
        moon.reset();
        mercury.reset();
        venus.reset();

        this.paintImmediately(0,0, this.getWidth(), this.getHeight());
    }

    /**
     * Inner helper class for {@link AnimationPanel}. GameField represents the JPanel in which a {@link Sun}
     * exists in and moves around in.
     */
    class GameField extends JPanel {
        public GameField() {
            this.setBackground(Color.DARK_GRAY);
        }


        /**
         * Main paint method to draw the GameField.
         * Modifies the GameField such that the orgin(0,0) occurs at the bottom left instead of top left.
         * Paints @{@link AnimationPanel#sun} to the GameField.
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

            //paints the sun and earth objects
            sun.paint(g2d);
            earth.paint(g2d);
            mars.paint(g2d);
            moon.paint(g2d);
            mercury.paint(g2d);
            venus.paint(g2d);
        }
    }
}

/**
 * Represents the sun that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Sun {
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;

    /**
     * @param x x coordinate on the {@link AnimationPanel.GameField}
     * @param y y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the sun to move around in
     */
    public Sun(double x, double y , AnimationPanel.GameField gameField) {
        this.x = x;
        this.y = y;
        this.gameField = gameField;
    }

    /**
     * Paints the sun with either the fancy mouseImage if it was loaded or a simple circle if it wasn't.
     * Both the fancy image and the circle are centered on the current coordinates of the sun.
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        double radius = 40;
        Math.round(x - radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        g2d.setColor(Color.YELLOW);
        g2d.fillOval(paintX, paintY, (int) radius, (int) radius);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {

    }

}

/**
 * Represents the earth that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Earth {
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;
    private boolean hasReset = false;
    private double theta = 0;
    private final double orbitRadius = 185;
    private final Sun sun;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the sun to move around in
     */

    /**
     *
     * @param x
     * @param y
     * @param gameField
     */
    public Earth(double x, double y, AnimationPanel.GameField gameField, Sun sun) {
        this.gameField = gameField;
        this.x = x;
        this.y = y;
        this.sun = sun;
    }

    /**
     * Paints the sun at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (!hasReset) {
            x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
            y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
            hasReset = true;
        }
        //fixes weird error on application startup where the earth doesn't display in correct coordinates
        //so this runs once on the first ever method call to this function

        double radius = 30;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        g2d.setColor(Color.GREEN);
        g2d.fillOval(paintX, paintY, (int) radius, (int) radius);
    }

    /**
     * Moves the sun one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
        theta += 0.58;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(0));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(0));
        theta = 0;
    }
}

/**
 * Represents the earth that occupies a {@link AnimationPanel.GameField} and that can move across the diamond.
 */
class Mars {
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;
    private boolean hasReset = false;
    private double theta = 0;
    private final double orbitRadius = 300;
    private final Sun sun;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the sun to move around in
     */

    /**
     *
     * @param x
     * @param y
     * @param gameField
     */
    public Mars(double x, double y, AnimationPanel.GameField gameField, Sun sun) {
        this.gameField = gameField;
        this.x = x;
        this.y = y;
        this.sun = sun;
    }

    /**
     * Paints the sun at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (!hasReset) {
            x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
            y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
            hasReset = true;
        }
        //fixes weird error on application startup where the earth doesn't display in correct coordinates
        //so this runs once on the first ever method call to this function

        double radius = 22;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        g2d.setColor(Color.RED);
        g2d.fillOval(paintX, paintY, (int) radius, (int) radius);

    }

    /**
     * Moves the sun one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
        theta += 0.5;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(0));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(0));
        theta = 0;
    }

}

class Moon {
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;
    private boolean hasReset = false;
    private double theta = 0;
    private double orbitRadius = 25;
    private final Earth earth;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the sun to move around in
     */

    /**
     *
     * @param x
     * @param y
     * @param gameField
     */
    public Moon(double x, double y, AnimationPanel.GameField gameField, Earth earth) {
        this.gameField = gameField;
        this.x = x;
        this.y = y;
        this.earth = earth;
    }

    /**
     * Paints the sun at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (!hasReset) {
            x = earth.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
            y = earth.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
            hasReset = true;
        }
        //fixes weird error on application startup where the earth doesn't display in correct coordinates
        //so this runs once on the first ever method call to this function

        double radius = 10;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        g2d.setColor(Color.GRAY);
        g2d.fillOval(paintX, paintY, (int) radius, (int) radius);
    }

    /**
     * Moves the sun one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x = earth.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
        y = earth.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
        theta += 1.0;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {
        x = earth.getX() + orbitRadius * Math.cos(Math.toRadians(0));
        y = earth.getY() + orbitRadius * Math.sin(Math.toRadians(0));
        theta = 0;
    }

}

class Mercury {
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;
    private boolean hasReset = false;
    private double theta = 0;
    private final double orbitRadius = 50;
    private final Sun sun;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the sun to move around in
     */

    /**
     *
     * @param x
     * @param y
     * @param gameField
     */
    public Mercury(double x, double y, AnimationPanel.GameField gameField, Sun sun) {
        this.gameField = gameField;
        this.x = x;
        this.y = y;
        this.sun = sun;
    }

    /**
     * Paints the sun at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (!hasReset) {
            x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
            y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
            hasReset = true;
        }
        //fixes weird error on application startup where the earth doesn't display in correct coordinates
        //so this runs once on the first ever method call to this function

        double radius = 15;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        g2d.setColor(Color.PINK);
        g2d.fillOval(paintX, paintY, (int) radius, (int) radius);

    }

    /**
     * Moves the sun one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
        theta += 1.5;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(0));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(0));
        theta = 0;
    }

}

class Venus {
    private double x;
    private double y;
    private AnimationPanel.GameField gameField;
    private boolean hasReset = false;
    private double theta = 0;
    private final double orbitRadius = 120;
    private final Sun sun;

    /**
     * @param x         x coordinate on the {@link AnimationPanel.GameField}
     * @param y         y coordinate on the {@link AnimationPanel.GameField}
     * @param gameField field for the sun to move around in
     */

    /**
     *
     * @param x
     * @param y
     * @param gameField
     */
    public Venus(double x, double y, AnimationPanel.GameField gameField, Sun sun) {
        this.gameField = gameField;
        this.x = x;
        this.y = y;
        this.sun = sun;
    }

    /**
     * Paints the sun at it's current coordinates centered on (x, y).
     *
     * @param g2d Graphics object to paint to
     */
    public void paint(Graphics2D g2d) {
        if (!hasReset) {
            x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
            y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
            hasReset = true;
        }
        //fixes weird error on application startup where the earth doesn't display in correct coordinates
        //so this runs once on the first ever method call to this function

        double radius = 20;
        Math.round(x- radius/2.0);
        int paintX = Math.toIntExact(Math.round(x - radius / 2.0));
        int paintY = Math.toIntExact(Math.round(y - radius / 2.0));

        g2d.setColor(Color.CYAN);
        g2d.fillOval(paintX, paintY, (int) radius, (int) radius);

    }

    /**
     * Moves the sun one unit in the direction calculated by it's current theta member.
     */
    public void moveOneUnitUpdate() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(theta));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(theta));
        theta += 0.75;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void reset() {
        x = sun.getX() + orbitRadius * Math.cos(Math.toRadians(0));
        y = sun.getY() + orbitRadius * Math.sin(Math.toRadians(0));
        theta = 0;
    }

}

