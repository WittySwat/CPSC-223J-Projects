/*
 * Program Name: "Payroll System".  This program shows how to add and subtract two numbers using a simple UI with three
 * active buttons.  Copyright (C) 2021 Jarrod Burges
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * version 3 as published by the Free Software Foundation.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * A copy of the GNU General Public License v3 is available here:  <https://www.gnu.org/licenses/>.
 */

package PayrollSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Represents a Control Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class ControlPanel extends JPanel {

    /**
     * Represents an InputPanel to gather input variables to be used by
     * {@link ControlPanel#computeButtonListener()} math done by
     * {@link MathHelper}
     */
    private final InputPanel inputPanel;

    /**
     * Represents an OutputPanel to display output variables computed by {@link ControlPanel#computeButtonListener()}
     */
    private final OutputPanel outputPanel;

    /**
     * Represents an ApplicationFrame to display errors found within the {@link ControlPanel#computeButtonListener()}
     */
    private final ApplicationFrame applicationFrame;

    /**
     * Creates a jburges.ControlPanel with specified {@link InputPanel} and {@link OutputPanel}.
     * @param inputPanel {@link InputPanel} to be used to get input variables
     * @param outputPanel {@link OutputPanel} to be used to display output variables
     */
    public ControlPanel(InputPanel inputPanel, OutputPanel outputPanel, ApplicationFrame applicationFrame) {
        //Calls super() and sets size constraints, color, and border
        super(new GridLayout(1, 3));
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(100, 50));
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        //sets private class variables to constructor provided variables
        //to be used by computeButton
        this.inputPanel = inputPanel;
        this.outputPanel = outputPanel;
        this.applicationFrame = applicationFrame;

        createAndAddButtons();
    }

    /**
     * Creates and adds three buttons to this class: Clear, Compute, and Quit. Every button has its own ActionListener:
     * {@link ControlPanel#clearButtonListener()}
     * {@link ControlPanel#computeButtonListener()}
     * {@link ControlPanel#quitButtonListener()}
     */
    private void createAndAddButtons() {
        JButton clearButton = new JButton("Clear");
        JButton computeButton = new JButton("Compute");
        JButton quitButton = new JButton("Quit");

        clearButton.setPreferredSize(new Dimension(25, 25));
        computeButton.setPreferredSize(new Dimension(25, 25));
        quitButton.setPreferredSize(new Dimension(25, 25));

        clearButton.addActionListener(clearButtonListener());
        computeButton.addActionListener(computeButtonListener());
        quitButton.addActionListener(quitButtonListener());

        this.add(clearButton, BorderLayout.WEST);
        this.add(computeButton, BorderLayout.CENTER);
        this.add(quitButton, BorderLayout.EAST);
    }

    private void clearPanels() {
        //Resets every InputPanel JTextField or JFormattedTextField to either black text or 0.0d
        inputPanel.getEmployeeNameTextField().setText("");
        inputPanel.getHoursWorkedTextField().setValue(0.0d);
        inputPanel.getHourlyPayRateTextField().setValue(0.0d);

        //Resets every OutPanel JLabel to blank text
        outputPanel.getEmployeeNameLabel().setText("");
        outputPanel.getRegularPayLabel().setText("");
        outputPanel.getOvertimePayLabel().setText("");
        outputPanel.getGrossPayLabel().setText("");

        //Repaints both panels so the changes made above are now seen by the user
        inputPanel.repaint();
        outputPanel.repaint();
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     * clear all JTextFields and JFormattedTextFields within an jburges.InputPanel. As well as clearing all
     * JLabels found within an jburges.OutputPanel. jburges.InputPanel and OutPanel then are redrawn.
     *
     * @return ActionListener
     * @see InputPanel
     * @see OutputPanel
     */
    private ActionListener clearButtonListener() {
        return actionEvent -> clearPanels();
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     * gather the variables from an jburges.InputPanel's JFormattedTextField and JTextField and pass them to {@link MathHelper} to compute.
     * Those are then sent to an jburges.OutputPanel's JLabel and displayed. jburges.InputPanel and OutPanel then are redrawn.
     *
     * @return ActionListener
     * @see InputPanel
     * @see OutputPanel
     * @see MathHelper
     */
    private ActionListener computeButtonListener() {
        return actionEvent -> {
            //sets outputPanel's employee label to whatever is in inputPanel's employeeName field
            outputPanel.getEmployeeNameLabel()
                    .setText(inputPanel.getEmployeeNameTextField().getText());

            double hoursWorked = ((Number)inputPanel.getHoursWorkedTextField().getValue()).doubleValue();
            double hourlyPayRate = ((Number)inputPanel.getHourlyPayRateTextField().getValue()).doubleValue();

            if (hoursWorked < 0) {
                JOptionPane.showMessageDialog(applicationFrame, "Hours worked cannot be negative.");
                clearPanels();
                return;
            }

            if (hourlyPayRate < 0) {
                JOptionPane.showMessageDialog(applicationFrame, "Hourly rate cannot be negative.");
                clearPanels();
                return;
            }

            if (24 * 7 < hoursWorked) {
                JOptionPane.showMessageDialog(applicationFrame, "Hours worked cannot exceed hours in a week.");
                clearPanels();
                return;
            }

            //Computes and assigns regularPay and displayed to the outputPanel
            double regularPay = MathHelper.getRegularPay(hoursWorked, hourlyPayRate);
            outputPanel.getRegularPayLabel().setText(Double.toString(regularPay));

            //Computes and assigns overtimePay and displayed to the outputPanel
            double overtimePay = MathHelper.getOvertimePay(hoursWorked, hourlyPayRate);
            outputPanel.getOvertimePayLabel().setText(Double.toString(overtimePay));

            //Computes and assigns grossPay and displayed to the outputPanel
            double grossPay = MathHelper.getGrossPay(regularPay, overtimePay);
            outputPanel.getGrossPayLabel().setText(Double.toString(grossPay));

            inputPanel.repaint();
            outputPanel.repaint();
        };
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will quit the program.
     *
     * @return ActionListener
     */
    private ActionListener quitButtonListener() {
        return actionEvent -> System.exit(0);
    }
}
