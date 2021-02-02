/*
 * Program Name: "Payroll System".  This program shows how to add and subtract two numbers using a simple UI with three
 * active buttons.  Copyright (C) 2021 Jarrod Burges
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

package DiamondAnimation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.NumberFormat;

/**
 * Represents a Control Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class ControlPanel extends JPanel {

    private final AnimationPanel animationPanel;
    private JFormattedTextField pixelSpeedInput;
    private final NumberFormat doubleFormat = NumberFormat.getNumberInstance();

    public ControlPanel(AnimationPanel animationPanel) {
        //Calls super() and sets size constraints, color, and border
        super(new GridLayout(1, 4));
        this.animationPanel = animationPanel;
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(600, 50));
        this.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        //sets private class variables to constructor provided variables
        //to be used by computeButton

        createAndAddButtons();
    }


    private void createAndAddButtons() {
        JButton startButton = new JButton("Start");
        JButton quitButton = new JButton("Quit");
        JLabel speed = new JLabel("Speed");
        speed.setFont(new Font(Font.DIALOG, Font.BOLD,20));

        quitButton.setPreferredSize(new Dimension(25, 25));
        quitButton.addActionListener(quitButtonListener());
        startButton.addActionListener(startButtonListener());

        this.add(startButton, BorderLayout.WEST);
        this.add(speed, BorderLayout.CENTER);
        pixelSpeedInput = new JFormattedTextField(doubleFormat);
        pixelSpeedInput.setValue((double) 0);
        this.add(pixelSpeedInput, BorderLayout.CENTER);
        this.add(quitButton, BorderLayout.EAST);
    }

    private ActionListener startButtonListener() {
        return actionEvent -> {
            try {
                animationPanel.animateToNextPoint();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will quit the program.
     *
     * @return ActionListener
     */
    private ActionListener quitButtonListener() {
        return actionEvent -> System.exit(0);
    }
}
