/*
 * Program Name: "Diamond Animation".  This program shows how to add and subtract two numbers using a simple UI with three
 * active buttons.  Copyright (C) 2021 Jarrod Burges
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

package DiamondAnimation;


import javax.imageio.ImageIO;
import javax.management.openmbean.ArrayType;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public final class MathHelper {
    public static ArrayList<Point2D> bresenham(int x1, int y1, int x2, int y2) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx; // slope scaling factors to
        int dy2 = 2 * dy; // avoid floating point

        int ix = x1 < x2 ? 1 : -1; // increment direction
        int iy = y1 < y2 ? 1 : -1;

        int x = x1;
        int y = y1;

        if (dx >= dy) {
            while (true) {
                points.add(new Point2D.Double(x, y));
                if (x == x2)
                    break;
                x += ix;
                d += dy2;
                if (d > dx) {
                    y += iy;
                    d -= dx2;
                }
            }
        } else {
            while (true) {
                points.add(new Point2D.Double(x, y));
                if (y == y2)
                    break;
                y += iy;
                d += dx2;
                if (d > dy) {
                    x += ix;
                    d -= dy2;
                }
            }
        }
        return points;
    }

    public static Polygon generateRandomRhombus() {
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        for (int i = 0; i < 4; i++) {
            xPoints[i] = (int) (Math.random() * 450) + 25;
            yPoints[i] = (int) (Math.random() * 450) + 25;
        }

        Arrays.sort(xPoints);
        Arrays.sort(yPoints);

        xPoints = swap(xPoints, 0, 1);
        xPoints = swap(xPoints, 1, 3);

        yPoints = swap(yPoints, 2, 3);
        System.out.println(Arrays.toString(xPoints));
        System.out.println(Arrays.toString(yPoints));
        return new Polygon(xPoints, yPoints, 4);
    }

    public static int[] swap(int[] arr, int pos1, int pos2) {
        int temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return arr;
    }
}
