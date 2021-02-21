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

/**
 * Represents mathematical computational methods to be used within the Application.
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public final class MathHelper {

    /**
     * Calculates the area of a triangle with given sides.
     *
     * @param side1 first side of a triangle
     * @param side2 second side of the triangle
     * @return area of triangle with side1 and side 2
     */
    public static double getArea(double side1, double side2) {
        return (side1*side2)/2.0;
    }

    /**
     * Calculates the hypotenuse of a triangle with given sides.
     *
     * @param side1 first side of a triangle
     * @param side2 second side of the triangle
     * @return hypotenuse of the triangle
     */
    public static double getHypotenuse(double side1, double side2) {
        return Math.sqrt(Math.pow(side1, 2) + Math.pow(side2, 2));
    }
}
