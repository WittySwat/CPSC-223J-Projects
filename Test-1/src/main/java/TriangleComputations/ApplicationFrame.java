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
 * Represents the Applications main GUI JFrame.
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class ApplicationFrame extends JFrame {

    public ApplicationFrame(String title) throws HeadlessException {
        //Calls super() and sets size constraints and close operation
        super(title);
        this.setSize(new Dimension(350, 400));
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.white);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        createAndAddPanels(mainPanel);

        this.add(mainPanel);
        this.setVisible(true);
    }

    private void createAndAddPanels(JPanel mainPanel) {
        InfoPanel infoPanel = new InfoPanel("Welcome to Triangle Computations",
                "Programmed by Jarrod Burges",
                "All Triangles are right triangles");
        InputPanel inputPanel = new InputPanel();
        OutputPanel outputPanel = new OutputPanel();
        ControlPanel controlPanel = new ControlPanel(inputPanel, outputPanel, this);

        mainPanel.add(infoPanel);
        mainPanel.add(inputPanel);
        mainPanel.add(outputPanel);
        mainPanel.add(controlPanel);
    }
}
