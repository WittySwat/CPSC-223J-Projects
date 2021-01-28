/*
 *  Program Name: "Payroll System".  This program shows how to add and subtract two numbers using a simple UI with three
 *  active buttons.  Copyright (C) 2021 Jarrod Burges
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 *  version 3 as published by the Free Software Foundation.
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *  A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

package jburges;

import javax.swing.*;
import java.awt.*;

/**
 * Represents an Output Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class OutputPanel extends JPanel {
    private JLabel employeeNameLabel;
    private JLabel regularPayLabel;
    private JLabel overtimePayLabel;
    private JLabel grossPayLabel;

    /**
     * Creates an OutputPanel with set size and colors. Contains four output labels to display computed information.
     */
    public OutputPanel() {
        //Calls super() and sets size constraints and color
        super(new GridLayout(4, 2));
        this.setBackground(new Color(200, 200, 150));
        this.setPreferredSize(new Dimension(100, 150));

        createAndAddLabels();
    }

    /**
     * Creates and adds four labels to display computed data which are:
     * {@link #employeeNameLabel}, {@link #regularPayLabel}, {@link #overtimePayLabel}, {@link #grossPayLabel}
     * </p>
     */
    private void createAndAddLabels() {
        //Employee Name Label and Text Field
        this.add(new JLabel("Name of Employee: "), BorderLayout.CENTER);
        employeeNameLabel = new JLabel();
        this.add(employeeNameLabel, BorderLayout.CENTER);

        //Hours Worked Label and Text Field
        this.add(new JLabel("Regular Pay "), BorderLayout.CENTER);
        regularPayLabel = new JLabel();
        this.add(regularPayLabel, BorderLayout.CENTER);

        //Hourly Pay Rate Label and Text Field
        this.add(new JLabel("Overtime Pay "), BorderLayout.CENTER);
        overtimePayLabel = new JLabel();
        this.add(overtimePayLabel, BorderLayout.CENTER);

        //Gross Pay Labels
        this.add(new JLabel("Gross Pay "), BorderLayout.CENTER);
        grossPayLabel = new JLabel();
        this.add(grossPayLabel, BorderLayout.CENTER);
    }

    /**
     * @return JLabel {@link #employeeNameLabel}
     */
    public JLabel getEmployeeNameLabel() {
        return employeeNameLabel;
    }

    /**
     * @return JLabel {@link #regularPayLabel}
     */
    public JLabel getRegularPayLabel() {
        return regularPayLabel;
    }

    /**
     * @return JLabel {@link #overtimePayLabel}
     */
    public JLabel getOvertimePayLabel() {
        return overtimePayLabel;
    }

    /**
     * @return JLabel {@link #grossPayLabel}
     */
    public JLabel getGrossPayLabel() {
        return grossPayLabel;
    }

}
