/*
 * Program Name: "Ricochet Ball".  This program shows how to add and subtract two numbers using a simple UI with three
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

package RicochetBall;


import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Math class that contains every complex math function used in the program
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public final class MathHelper {
    /**
     * Using bresenham's algorithm to find every integer point between two given points in a cartesian grid.
     * Every integer point is converted to a Point2D object and is added to an array of Point2Ds
     *
     * @param x1 x coordiate of point 1
     * @param y1 y coordiate of point 1
     * @param x2 x coordiate of point 2
     * @param y2 y coordiate of point 2
     * @return an ArrayList of Point2Ds of every integer point between x1, y1 and x2, y2
     */
    public static ArrayList<Point2D> bresenham(int x1, int y1, int x2, int y2) {
        ArrayList<Point2D> points = new ArrayList<Point2D>();
        int d = 0;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int dx2 = 2 * dx;
        int dy2 = 2 * dy;

        int ix = x1 < x2 ? 1 : -1;
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

    /**
     * Generates a random rhombus with four points in the order of bottom, right, top, left.
     *
     * @param min minimum number for the polygon's coordinates to have
     * @param max maximum number for the polygon's coordinates to have
     * @return A random polygon with four sides
     */
    public static Polygon generateRandomRhombus(int min, int max) {
        int[] xPoints = new int[4];
        int[] yPoints = new int[4];
        //randomly creates xPoints and yPoints arrays with a range of [25, 450]
        for (int i = 0; i < 4; i++) {
            xPoints[i] = (int) (Math.random() * max + min);
            yPoints[i] = (int) (Math.random() * max) + min;
        }

        //sorts the arrays from smallest to largest
        Arrays.sort(xPoints);
        Arrays.sort(yPoints);

        //swaps the elements in the array such that the elements are ordered in a bottom, right, top, left configuration
        //where those elements are:
        //bottom is a middle x, lowest y
        //right is largest x, a middle y
        //top is a middle x, largest y
        //left is lowest x, a middle
        //        top
        //   left      right
        //       bottom
        //this allows for the rhombus to represent a baseball diamond more easily
        xPoints = swap(xPoints, 0, 1);
        xPoints = swap(xPoints, 1, 3);

        yPoints = swap(yPoints, 2, 3);
        return new Polygon(xPoints, yPoints, 4);
    }

    /**
     * Swaps two int elements at given positions in a given int array.
     *
     * @param arr array for the elements to be swapped in
     * @param pos1 first position of element
     * @param pos2 second position of element
     * @return new array such that the elements at pos1 and pos2 are now pos2 and pos1
     */
    public static int[] swap(int[] arr, int pos1, int pos2) {
        int temp = arr[pos1];
        arr[pos1] = arr[pos2];
        arr[pos2] = temp;
        return arr;
    }
}
