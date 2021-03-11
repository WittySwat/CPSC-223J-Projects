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
        //super(new GridLayout(1, 2));
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.animationPanel = animationPanel;
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(1000, 200));
        this.setBorder(BorderFactory.createEmptyBorder(25,25,25,25));

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

        JLabel refreshRateLabel = new JLabel("    Refresh Rate (Hz)");
        refreshRateLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        refreshRateLabel.setPreferredSize(new Dimension(150, 35));

        JLabel speedLabel = new JLabel("     Speed (pix/sec)");
        speedLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        speedLabel.setPreferredSize(new Dimension(150, 35));

        JLabel directionLabel = new JLabel("       Direction");
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

        topPanel.add(startPauseButton);
        topPanel.add(refreshRateLabel);
        topPanel.add(refreshRateInput);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        middlePanel.add(clearButton);
        middlePanel.add(speedLabel);
        middlePanel.add(pixelSpeedInput);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        bottomPanel.add(quitButton);
        bottomPanel.add(directionLabel);
        bottomPanel.add(directionInput);

        JPanel inputControls = new JPanel();
        inputControls.setLayout(new BoxLayout(inputControls, BoxLayout.Y_AXIS));
        inputControls.add(topPanel);
        inputControls.add(middlePanel);
        inputControls.add(bottomPanel);

        this.add(inputControls);

        this.add(Box.createRigidArea(new Dimension(350,0)));

        JPanel ballLocationPanel = new JPanel();
        ballLocationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        ballLocationPanel.setLayout(new BoxLayout(ballLocationPanel, BoxLayout.Y_AXIS));

        JLabel ballLocationLabel = new JLabel("Ball Location");
        ballLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        ballLocationLabel.setPreferredSize(new Dimension(150, 35));

        ballLocationPanel.add(ballLocationLabel);

        JPanel xPanel = new JPanel();
        xPanel.setLayout(new BoxLayout(xPanel, BoxLayout.X_AXIS));

        JLabel xLocationLabel = new JLabel("X = ");
        xLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        xLocationLabel.setPreferredSize(new Dimension(65, 35));

        JFormattedTextField xBallLocationInput = new JFormattedTextField(integerInstance);
        xBallLocationInput.setValue(0);
        xBallLocationInput.addMouseListener(clearFieldListener(xBallLocationInput));
        xBallLocationInput.setPreferredSize(new Dimension(65, 35));

        xPanel.add(xLocationLabel);
        xPanel.add(xBallLocationInput);

        JPanel yPanel = new JPanel();
        yPanel.setLayout(new BoxLayout(yPanel, BoxLayout.X_AXIS));

        JFormattedTextField yBallLocationInput = new JFormattedTextField(integerInstance);
        yBallLocationInput.setValue(0);
        yBallLocationInput.addMouseListener(clearFieldListener(yBallLocationInput));
        yBallLocationInput.setPreferredSize(new Dimension(65, 35));

        JLabel yLocationLabel = new JLabel("Y = ");
        yLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        yLocationLabel.setPreferredSize(new Dimension(65, 35));

        yPanel.add(yLocationLabel);
        yPanel.add(yBallLocationInput);

        ballLocationPanel.add(xPanel);
        ballLocationPanel.add(yPanel);

        this.add(ballLocationPanel);
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     * start the player to move around the diamond. If this Listener gets evoked again the player will stop moving.
     *
     * @return ActionListener
     */
    private ActionListener startPauseButtonLisenter() {
        return actionEvent -> {
            animationPanel.moveBall(((Number)pixelSpeedInput.getValue()).intValue(), startPauseButton);
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
