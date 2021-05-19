/*
 * Program Name: "Cat and Mouse".  This program shows how to add and subtract two numbers using a simple UI with three
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

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;

/**
 * Represents a Control Panel for a larger frame application
 * @author Jarrod Burges
 * @email jburges@csu.fullerton.edu
 */
public class ControlPanel extends JPanel {

    private final AnimationPanel animationPanel;
    private JFormattedTextField earthPixelSpeedInput;
    private JLabel earthXLocationLabel2;
    private JLabel earthYLocationLabel2;
    private final NumberFormat integerInstance = NumberFormat.getIntegerInstance();
    private JButton startPauseButton;

    public ControlPanel(AnimationPanel animationPanel) {
        //Calls super() and sets size constraints, color, and border
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.animationPanel = animationPanel;
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(750, 200));
        this.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));

        createAndAddButtons();
    }

    private void createAndAddButtons() {
        startPauseButton = new JButton("Start");
        startPauseButton.addActionListener(startPauseButtonListener());
        startPauseButton.setPreferredSize(new Dimension(75, 35));

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(quitButtonListener());
        quitButton.setPreferredSize(new Dimension(75, 35));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(clearButtonListener());
        clearButton.setPreferredSize(new Dimension(75, 35));

        JLabel earthSpeedLabel = new JLabel("Earth Speed (pix/sec)");
        earthSpeedLabel.setFont(new Font(Font.DIALOG, Font.BOLD,13));
        earthSpeedLabel.setPreferredSize(new Dimension(150, 35));

        earthPixelSpeedInput = new JFormattedTextField(integerInstance);
        earthPixelSpeedInput.setValue(25);
        earthPixelSpeedInput.addMouseListener(clearFieldListener(earthPixelSpeedInput));
        earthPixelSpeedInput.setPreferredSize(new Dimension(65, 35));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(startPauseButton);
        topPanel.add(clearButton);
        topPanel.add(quitButton);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        middlePanel.add(earthSpeedLabel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(earthPixelSpeedInput);

        JPanel inputControls = new JPanel();
        inputControls.setLayout(new BoxLayout(inputControls, BoxLayout.Y_AXIS));
        inputControls.add(topPanel);
        inputControls.add(middlePanel);
        inputControls.add(bottomPanel);

        this.add(inputControls);


//----------------------------------------------------------------------------//

        JPanel earthLocationPanel = new JPanel();
        earthLocationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        earthLocationPanel.setLayout(new BoxLayout(earthLocationPanel, BoxLayout.Y_AXIS));

        JLabel earthLocationLabel = new JLabel("Earth Location");
        earthLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        earthLocationLabel.setPreferredSize(new Dimension(150, 35));

        earthLocationPanel.add(earthLocationLabel);

        JPanel earthXPanel = new JPanel();
        earthXPanel.setLayout(new BoxLayout(earthXPanel, BoxLayout.X_AXIS));

        JLabel earthXLocationLabel = new JLabel("X = ");
        earthXLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        earthXLocationLabel.setPreferredSize(new Dimension(65, 35));

        earthXLocationLabel2 = new JLabel("");
        earthXLocationLabel2.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        earthXLocationLabel2.setPreferredSize(new Dimension(65, 35));

        earthXPanel.add(earthXLocationLabel);
        earthXPanel.add(earthXLocationLabel2);


        JPanel earthYPanel = new JPanel();
        earthYPanel.setLayout(new BoxLayout(earthYPanel, BoxLayout.X_AXIS));

        JLabel earthYLocationLabel = new JLabel("Y = ");
        earthYLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        earthYLocationLabel.setPreferredSize(new Dimension(65, 35));

        earthYLocationLabel2 = new JLabel("");
        earthYLocationLabel2.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        earthYLocationLabel2.setPreferredSize(new Dimension(65, 35));

        earthYPanel.add(earthYLocationLabel);
        earthYPanel.add(earthYLocationLabel2);

        earthLocationPanel.add(earthXPanel);
        earthLocationPanel.add(earthYPanel);

        this.add(earthLocationPanel);
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     * start the player to move around the diamond. If this Listener gets evoked again the player will stop moving.
     * Also contains input checkers to make sure no wrong inputs are being inputted before calculations are made.
     *
     * @return ActionListener
     */
    private ActionListener startPauseButtonListener() {
        return actionEvent -> {
            AnimationPanel.GameField gameField = animationPanel.getGameField();
            if (((Number) earthPixelSpeedInput.getValue()).intValue() <= 0) {
                JOptionPane.showMessageDialog(animationPanel, "Earth Pixel Speed input cannot be negative or zero");
                return;
            }
            animationPanel.moveSolarSystemBodies(
                    startPauseButton,
                    earthPixelSpeedInput,
                    earthXLocationLabel2,
                    earthYLocationLabel2,
                    false);
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

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     *
     * @return ActionListener
     */
    private ActionListener clearButtonListener() {
        return actionEvent -> {
            animationPanel.moveSolarSystemBodies(
                    startPauseButton,
                    earthPixelSpeedInput,
                    earthXLocationLabel2,
                    earthYLocationLabel2,
                    true);

            animationPanel.resetSolarSystem();
        };
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

}
