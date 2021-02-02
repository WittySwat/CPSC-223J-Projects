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

package DiamondAnimation;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class MathHelper {
    public static Point2D[] pointsAlongLine(Line2D line, int n) {
        Point2D[] points = new Point2D[n];
        if(n == 1) {
            points[0] = line.getP1();
            return points;
        }

        double dy = line.getY2() - line.getY1();
        double dx = line.getX2() - line.getX1();

        double theta = dx > 0.001 ? Math.atan(dy / dx) :
                dy < 0 ? -Math.PI : Math.PI;

        double length = Math.abs(line.getP1().distance(line.getP2()));
        int numSegments = n - 1;
        double segmentLength = length / numSegments;
        double x = line.getX1();
        double y = line.getY1();
        double ddx = segmentLength * Math.cos(theta);
        double ddy = segmentLength * Math.sin(theta);

        for(int i = 0; i < n; i++) {
            points[i] = new Point2D.Double(x, y);
            x += ddx;
            y += ddy;
        }

        return points;
    }

    public static int rotateX(int x, int y, int theta) {
        return (int) ((x * Math.cos(theta)) - (y * Math.sin(theta)));
    }

    public static int rotateY(int x, int y, int theta) {
        return (int) ((x * Math.sin(theta)) + (y * Math.cos(theta)));
    }

    public static int rotateX(double x, double y, int theta) {
        return (int) ((x * Math.cos(theta)) - (y * Math.sin(theta)));
    }

    public static int rotateY(double x, double y, int theta) {
        return (int) ((x * Math.sin(theta)) + (y * Math.cos(theta)));
    }
}
