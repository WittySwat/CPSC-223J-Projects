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
package EarthSun;

/**
 * Represents the Applications main function
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class Main {

    /**
     * Creates an {@link ApplicationFrame} inside a Runnable() to be thread-safe.
     * @param args unused
     */
    public static void main(String[] args) {
        final ApplicationFrame[] applicationFrame = new ApplicationFrame[1];
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                applicationFrame[0] = new ApplicationFrame("Program 4");
            //    applicationFrame[0].getControlPanel().resetAndRefreshCatMouseLocation();
            }
        });
    }
}
