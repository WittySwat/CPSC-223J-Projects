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
    private JFormattedTextField pixelSpeedInput;
    private JFormattedTextField refreshRateInput;
    private JFormattedTextField directionInput;
    private final NumberFormat integerInstance = NumberFormat.getIntegerInstance();
    private JButton startPauseButton;

    public ControlPanel(AnimationPanel animationPanel) {
        //Calls super() and sets size constraints, color, and border
        super(new GridLayout(3, 1));
        this.animationPanel = animationPanel;
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(1000, 200));
        this.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        createAndAddButtons();
    }


    private void createAndAddButtons() {
        startPauseButton = new JButton("Start");
        startPauseButton.addActionListener(startPauseButtonLisenter());
        startPauseButton.setPreferredSize(new Dimension(65, 35));

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(quitButtonListener());
        quitButton.setPreferredSize(new Dimension(65, 35));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(clearButtonListener());
        clearButton.setPreferredSize(new Dimension(65, 35));

        JLabel refreshRateLabel = new JLabel("Refresh Rate (Hz)");
        refreshRateLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        refreshRateLabel.setPreferredSize(new Dimension(150, 35));

        JLabel speedLabel = new JLabel("Speed (pix/sec)");
        speedLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        speedLabel.setPreferredSize(new Dimension(150, 35));

        JLabel directionLabel = new JLabel("Direction");
        directionLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 15));
        directionLabel.setPreferredSize(new Dimension(150, 35));


        pixelSpeedInput = new JFormattedTextField(integerInstance);
        pixelSpeedInput.setValue(0);
        pixelSpeedInput.addMouseListener(clearFieldListener(pixelSpeedInput));
        pixelSpeedInput.setPreferredSize(new Dimension(65, 35));

        refreshRateInput = new JFormattedTextField(integerInstance);
        refreshRateInput.setValue(0);
        refreshRateInput.addMouseListener(clearFieldListener(refreshRateInput));
        refreshRateInput.setPreferredSize(new Dimension(65, 35));

        directionInput = new JFormattedTextField(integerInstance);
        directionInput.setValue(0);
        directionInput.addMouseListener(clearFieldListener(directionInput));
        directionInput.setPreferredSize(new Dimension(65, 35));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(startPauseButton, BorderLayout.WEST);
        topPanel.add(clearButton, BorderLayout.CENTER);
        topPanel.add(quitButton, BorderLayout.EAST);


        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        middlePanel.add(refreshRateLabel, BorderLayout.CENTER);
        middlePanel.add(speedLabel, BorderLayout.CENTER);
        middlePanel.add(directionLabel, BorderLayout.CENTER);


        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(pixelSpeedInput);
        bottomPanel.add(refreshRateInput);
        bottomPanel.add(directionInput);

        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     * start the player to move around the diamond. If this Listener gets evoked again the player will stop moving.
     *
     * @return ActionListener
     */
    private ActionListener startPauseButtonLisenter() {
        return actionEvent -> {
            animationPanel.moveAcrossDiamond(((Number)pixelSpeedInput.getValue()).intValue(), startPauseButton);
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
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will toggle the
     * gamefield between fancy field and a random field.
     *
     * @return ActionListener
     */
    private ActionListener clearButtonListener() {
        return actionEvent -> {

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
