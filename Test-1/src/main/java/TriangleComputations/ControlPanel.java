/*
 * Program Name: "Triangle Computations".  This program shows how to add and subtract two numbers using a simple UI with three
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

package TriangleComputations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

/**
 * Represents a Control Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class ControlPanel extends JPanel {

    private final InputPanel inputPanel;
    private final OutputPanel outputPanel;
    private final ApplicationFrame applicationFrame;

    public ControlPanel(InputPanel inputPanel, OutputPanel outputPanel, ApplicationFrame applicationFrame) {
        //Calls super() and sets size constraints, color, and border
        super(new GridLayout(1, 3));
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(100, 50));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //sets private class variables to constructor provided variables
        //to be used by computeButton
        this.inputPanel = inputPanel;
        this.outputPanel = outputPanel;
        this.applicationFrame = applicationFrame;

        createAndAddButtons();
    }

    private void createAndAddButtons() {
        JButton clearButton = new JButton("Clear");
        JButton computeButton = new JButton("Compute");
        JButton quitButton = new JButton("Quit");

        clearButton.setPreferredSize(new Dimension(25, 25));
        computeButton.setPreferredSize(new Dimension(25, 25));
        quitButton.setPreferredSize(new Dimension(25, 25));

        clearButton.addActionListener(clearButtonListener());
        computeButton.addActionListener(computeButtonListener());
        quitButton.addActionListener(quitButtonListener());

        this.add(clearButton, BorderLayout.WEST);
        this.add(computeButton, BorderLayout.CENTER);
        this.add(quitButton, BorderLayout.EAST);
    }

    private void clearPanels() {
        //Resets every InputPanel JTextField or JFormattedTextField to either black text or 0.0d
        inputPanel.getInputSide1().setValue(0.0d);
        inputPanel.getInputSide2().setValue(0.0d);

        //Resets every OutPanel JLabel to blank text
        outputPanel.getHypotenuseLabel().setText("");
        outputPanel.getAreaLabel().setText("");

        //Repaints both panels so the changes made above are now seen by the user
        inputPanel.repaint();
        outputPanel.repaint();
    }

    private ActionListener clearButtonListener() {
        return actionEvent -> clearPanels();
    }

    private ActionListener computeButtonListener() {
        return actionEvent -> {

            double side1 = ((Number)inputPanel.getInputSide1().getValue()).doubleValue();
            double side2 = ((Number)inputPanel.getInputSide2().getValue()).doubleValue();

            if (side1 <= 0) {
                JOptionPane.showMessageDialog(applicationFrame, "Input side 1 worked cannot be negative or zero.");
                clearPanels();
                return;
            }

            if (side2 <= 0) {
                JOptionPane.showMessageDialog(applicationFrame, "Input side 2 cannot be negative or zero.");
                clearPanels();
                return;
            }
            DecimalFormat dec = new DecimalFormat("#0.00");

            String area = dec.format(MathHelper.getArea(side1, side2));
            outputPanel.getAreaLabel().setText(area);

            String hypotenuse = dec.format(MathHelper.getHypotenuse(side1, side2));
            outputPanel.getHypotenuseLabel().setText(hypotenuse);

            inputPanel.repaint();
            outputPanel.repaint();
        };
    }

    private ActionListener quitButtonListener() {
        return actionEvent -> System.exit(0);
    }
}
