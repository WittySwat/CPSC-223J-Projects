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
 * Represents an Info Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class InfoPanel extends JPanel {

    public InfoPanel(String companyName, String authorName, String line3) {
        //Calls super() and sets size constraints and color
        super(new GridLayout(3, 1));
        this.setPreferredSize(new Dimension(100, 100));
        this.setBackground(new Color(255,140,5));

        //Creates and adds Company Name Label
        JLabel company = new JLabel(companyName);
        company.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(company);

        //Creates and adds Author Name Label
        JLabel name = new JLabel(authorName);
        name.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(name);

        JLabel line3label = new JLabel(line3);
        line3label.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(line3label);
    }
}
