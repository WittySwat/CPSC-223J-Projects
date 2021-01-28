package PayrollSystem;/*
 *  Program Name: "Payroll System".  This program shows how to add and subtract two numbers using a simple UI with three
 *  active buttons.  Copyright (C) 2021 Jarrod Burges
 *  This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 *  version 3 as published by the Free Software Foundation.
 *  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 *  A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

/**
 * Represents an Input Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class InputPanel extends JPanel {
    /**
     * Represents the input field for the employee name.
     */
    private JTextField employeeNameTextField;

    /**
     * Represents the input field for the amount of hours worked by an employee.
     */
    private JFormattedTextField hoursWorkedTextField;

    /**
     * Represents the input field for the hourly pay rate for an employee.
     */
    private JFormattedTextField hourlyPayRateTextField;

    /**
     * Represents the format the JFormattedTextFields will use.
     */
    private final NumberFormat doubleFormat = NumberFormat.getNumberInstance();

    /**
     * Creates an jburges.InputPanel with set size and colors. Adds multiple fields to enter information into.
     */
    public InputPanel() {
        //Calls super() and sets size constraints and color
        super(new GridLayout(3, 2));
        this.setBackground(new Color(119, 221, 119));
        this.setPreferredSize(new Dimension(100, 150));

        createAndAddInputFields();
    }

    /**
     * Creates and adds an input JTextField {@link InputPanel#employeeNameTextField}
     *<p>Creates and adds the double only input JFormattedTextField
     * {@link InputPanel#hoursWorkedTextField},
     * {@link InputPanel#hourlyPayRateTextField}
     * </p>
     */
    private void createAndAddInputFields() {
        //Employee Name Label and Text Field
        this.add(new JLabel("Employee Name: "), BorderLayout.CENTER);
        employeeNameTextField = new JTextField(10);
        this.add(employeeNameTextField, BorderLayout.CENTER);

        //Hours Worked Label and Text Field
        this.add(new JLabel("Hours Worked: "), BorderLayout.CENTER);
        hoursWorkedTextField = new JFormattedTextField(doubleFormat);
        hoursWorkedTextField.setValue((double) 0);
        hoursWorkedTextField.addMouseListener(clearFieldListener(hoursWorkedTextField));
        this.add(hoursWorkedTextField, BorderLayout.CENTER);


        //Hourly Pay Rate Label and Text Field
        this.add(new JLabel("Hourly Pay Rate: "), BorderLayout.CENTER);
        hourlyPayRateTextField = new JFormattedTextField(doubleFormat);
        hourlyPayRateTextField.setValue((double) 0);
        hourlyPayRateTextField.addMouseListener(clearFieldListener(hourlyPayRateTextField));
        this.add(hourlyPayRateTextField, BorderLayout.CENTER);
    }

    /**
     * Creates a listener that when the mouse is clicked it will clear the text inside it.
     *
     * @param field a {@link JFormattedTextField} object to add this listener to
     * @return MouseAdapter mouseClicked listener to remove text inside text field
     */
    private MouseAdapter clearFieldListener(JFormattedTextField field) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e){
                field.setText("");
            }
        };
    }

    /**
     * @return {@link JTextField} {@link #employeeNameTextField}
     */
    public JTextField getEmployeeNameTextField() {
        return employeeNameTextField;
    }

    /**
     * @return JFormattedTextField {@link #hoursWorkedTextField}
     */
    public JFormattedTextField getHoursWorkedTextField() {
        return hoursWorkedTextField;
    }

    /**
     * @return JFormattedTextField {@link #hourlyPayRateTextField}
     */
    public JFormattedTextField getHourlyPayRateTextField() {
        return hourlyPayRateTextField;
    }

}
