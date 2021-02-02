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
 */

package DiamondAnimation;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class AnimationPanel2 extends JPanel {
    private int[] xPoints = {50, 250, 450, 250};
    private int[] yPoints = {250, 50, 250, 450};
    private int current = 0;
    private JLabel label = new JLabel("player");

    public AnimationPanel2() {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(500, 500));
        this.setSize(new Dimension(500, 500));
        this.setMaximumSize(new Dimension(500, 500));
        this.setBounds(new Rectangle(500, 500));
        label.setBounds(xPoints[3], yPoints[3], 50, 50);

        this.add(label);
        label.setLocation(xPoints[3], yPoints[3]);
        this.repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(new Color(255, 0, 0));
        int numPoints = 4;
        g2d.drawPolygon(xPoints, yPoints, numPoints);
    }


}
