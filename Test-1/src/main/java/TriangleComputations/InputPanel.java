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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

/**
 * Represents an Input Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class InputPanel extends JPanel {

    /**
     * Represents the input field for side 1 of the triangle.
     */
    private JFormattedTextField inputSide1;

    /**
     * Represents the input field for side 2 of the triangle.
     */
    private JFormattedTextField inputSide2;

    /**
     * Represents the format the JFormattedTextFields will use.
     */
    private final NumberFormat doubleFormat = NumberFormat.getNumberInstance();

    public InputPanel() {
        //Calls super() and sets size constraints and color
        super(new GridLayout(2, 2));
        this.setBackground(new Color(119, 221, 119));
        this.setPreferredSize(new Dimension(100, 150));

        createAndAddInputFields();
    }

    private void createAndAddInputFields() {
        JLabel side1Label = new JLabel();
        side1Label.setHorizontalAlignment(SwingConstants.CENTER);
        side1Label.setText("Input Side 1: ");
        this.add(side1Label, BorderLayout.CENTER);

        inputSide1 = new JFormattedTextField(doubleFormat);
        inputSide1.setValue((double) 0);
        inputSide1.addMouseListener(clearFieldListener(inputSide1));
        this.add(inputSide1, BorderLayout.CENTER);


        JLabel side2Label = new JLabel();
        side2Label.setHorizontalAlignment(SwingConstants.CENTER);
        side2Label.setText("Input Side 2: ");
        this.add(side2Label, BorderLayout.CENTER);

        inputSide2 = new JFormattedTextField(doubleFormat);
        inputSide2.setValue((double) 0);
        inputSide2.addMouseListener(clearFieldListener(inputSide2));
        this.add(inputSide2, BorderLayout.CENTER);
    }

    private MouseAdapter clearFieldListener(JFormattedTextField field) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                field.setText("");
            }
        };
    }

    public JFormattedTextField getInputSide1() {
        return inputSide1;
    }

    public JFormattedTextField getInputSide2() {
        return inputSide2;
    }

}
