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

/**
 * Represents an Output Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class OutputPanel extends JPanel {

    /**
     * Represents the output field for the hypotenuse.
     */
    private JLabel hypotenuseLabel;

    /**
     * Represents the output field for the area.
     */
    private JLabel areaLabel;

    public OutputPanel() {
        //Calls super() and sets size constraints and color
        super(new GridLayout(2, 2));
        this.setBackground(new Color(200, 200, 150));
        this.setPreferredSize(new Dimension(100, 150));

        createAndAddLabels();
    }

    /**
     * Creates and adds four labels to display computed data which are:
     * {@link #hypotenuseLabel} and {@link #areaLabel}
     */
    private void createAndAddLabels() {
        JLabel hypotenuseLeftLabel = new JLabel();
        hypotenuseLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hypotenuseLeftLabel.setText("Hypotenuse is: ");
        this.add(hypotenuseLeftLabel, BorderLayout.CENTER);

        hypotenuseLabel = new JLabel();
        this.add(hypotenuseLabel, BorderLayout.CENTER);

        JLabel areaLeftLabel = new JLabel();
        areaLeftLabel.setHorizontalAlignment(SwingConstants.CENTER);
        areaLeftLabel.setText("Area is: ");
        this.add(areaLeftLabel, BorderLayout.CENTER);

        areaLabel = new JLabel();
        this.add(areaLabel, BorderLayout.CENTER);

    }

    public JLabel getHypotenuseLabel() {
        return hypotenuseLabel;
    }

    public JLabel getAreaLabel() {
        return areaLabel;
    }

}
