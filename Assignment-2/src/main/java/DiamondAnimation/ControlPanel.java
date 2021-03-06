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
    private final NumberFormat integerInstance = NumberFormat.getIntegerInstance();
    private JButton startPauseButton;
    private JButton randomFieldButton;

    public ControlPanel(AnimationPanel animationPanel) {
        //Calls super() and sets size constraints, color, and border
        super(new FlowLayout());
        this.animationPanel = animationPanel;
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(550, 50));
        this.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        createAndAddButtons();
    }


    private void createAndAddButtons() {
        startPauseButton = new JButton("Start");
        startPauseButton.addActionListener(startPauseButtonLisenter());
        startPauseButton.setPreferredSize(new Dimension(65, 35));

        randomFieldButton = new JButton("Random Field");
        randomFieldButton.addActionListener(fancyRandomFieldListener());
        randomFieldButton.setPreferredSize(new Dimension(120, 35));

        JLabel speedLabel = new JLabel("Speed:");
        speedLabel.setFont(new Font(Font.DIALOG, Font.BOLD,20));
        speedLabel.setPreferredSize(new Dimension(70, 35));

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(quitButtonListener());
        quitButton.setPreferredSize(new Dimension(65, 35));

        pixelSpeedInput = new JFormattedTextField(integerInstance);
        pixelSpeedInput.setValue(0);
        pixelSpeedInput.addMouseListener(clearFieldListener(pixelSpeedInput));
        pixelSpeedInput.setPreferredSize(new Dimension(65, 35));

        this.add(startPauseButton, BorderLayout.WEST);
        this.add(randomFieldButton);
        this.add(speedLabel, BorderLayout.CENTER);
        this.add(pixelSpeedInput, BorderLayout.CENTER);
        this.add(quitButton, BorderLayout.EAST);
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
    private ActionListener fancyRandomFieldListener() {
        return actionEvent -> {
            animationPanel.swapFieldType(randomFieldButton);
            animationPanel.paintImmediately(0,0,500,500);
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
