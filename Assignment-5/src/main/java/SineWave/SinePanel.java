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

import javax.swing.*;
import java.awt.*;

public class SinePanel extends JPanel {
    public SinePanel() {
        this.setPreferredSize(new Dimension(1000, 750));
        this.setMaximumSize(new Dimension(1000, 750));
        this.setLayout(new GridLayout());
        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        //rescales and translates g2d to have (0,0) orgin at bottom left rather than top left
        g2d.scale(1, -1);
        g2d.translate(0, -getHeight());


        for (double i = 0; i <= this.getWidth(); i++) {
            int y = (int) (200 * Math.sin(Math.toRadians(i*4.0))) + 250;
            g2d.drawOval((int) i, y, 2, 2);
        }
    }
}
