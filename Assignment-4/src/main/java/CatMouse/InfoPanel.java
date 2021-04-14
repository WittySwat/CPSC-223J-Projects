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

/**
 * Represents an Info Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class InfoPanel extends JPanel {

    /**
     * Creates an InfoPanel with the given programName and authorName.
     *
     * @param programName Name to display on top of this Ricochet Mouse Application
     * @param authorName Name of the creator of this Ricochet Mouse Application
     */
    public InfoPanel(String programName, String authorName) {
        //Calls super() and sets size constraints and color
        super(new GridLayout(2, 1));
        this.setPreferredSize(new Dimension(1000, 50));
        this.setBackground(new Color(186,85,211));

        //Creates and adds Program Name Label
        JLabel programNameLabel = new JLabel(programName);
        programNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(programNameLabel);

        //Creates and adds Author Name Label
        JLabel authorNameLabel = new JLabel(authorName);
        authorNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(authorNameLabel);
    }
}
