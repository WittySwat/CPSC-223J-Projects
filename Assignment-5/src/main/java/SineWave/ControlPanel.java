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

package SineWave;

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

    private final SinePanel sinePanel;
    private JFormattedTextField catPixelSpeedInput;
    private JFormattedTextField mousePixelSpeedInput;
    private JFormattedTextField directionInput;
    private JFormattedTextField mouseXBallLocationInput;
    private JFormattedTextField mouseYBallLocationInput;
    private JFormattedTextField catXBallLocationInput;
    private JFormattedTextField catYBallLocationInput;
    private JLabel distanceBetweenMouseCatLabel;
    private final NumberFormat integerInstance = NumberFormat.getIntegerInstance();
    private JButton startPauseButton;
    private int CANVAS_WIDTH = 1000;
    private int CANVAS_HEIGHT = 1000;

    public ControlPanel(SinePanel sinePanel) {
        //Calls super() and sets size constraints, color, and border
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        this.sinePanel = sinePanel;
        this.setBackground(new Color(255, 105, 97));
        this.setPreferredSize(new Dimension(1000, 200));
        this.setBorder(BorderFactory.createEmptyBorder(25,10,25,10));

        createAndAddButtons();
    }




    private void createAndAddButtons() {
        startPauseButton = new JButton("Start");
        startPauseButton.addActionListener(startPauseButtonLisenter());
        startPauseButton.setPreferredSize(new Dimension(75, 35));

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(quitButtonListener());
        quitButton.setPreferredSize(new Dimension(75, 35));

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(clearButtonListener());
        clearButton.setPreferredSize(new Dimension(75, 35));

        JLabel mouseSpeedLabel = new JLabel("Mouse Speed (pix/sec)");
        mouseSpeedLabel.setFont(new Font(Font.DIALOG, Font.BOLD,13));
        mouseSpeedLabel.setPreferredSize(new Dimension(150, 35));

        JLabel catSpeedLabel = new JLabel("  Cat Speed (pix/sec)");
        catSpeedLabel.setFont(new Font(Font.DIALOG, Font.BOLD,13));
        catSpeedLabel.setPreferredSize(new Dimension(150, 35));

        JLabel directionLabel = new JLabel("       Direction");
        directionLabel.setFont(new Font(Font.DIALOG, Font.BOLD, 13));
        directionLabel.setPreferredSize(new Dimension(150, 35));


        catPixelSpeedInput = new JFormattedTextField(integerInstance);
        catPixelSpeedInput.setValue(50);
        catPixelSpeedInput.addMouseListener(clearFieldListener(catPixelSpeedInput));
        catPixelSpeedInput.setPreferredSize(new Dimension(65, 35));

        mousePixelSpeedInput = new JFormattedTextField(integerInstance);
        mousePixelSpeedInput.setValue(100);
        mousePixelSpeedInput.addMouseListener(clearFieldListener(mousePixelSpeedInput));
        mousePixelSpeedInput.setPreferredSize(new Dimension(65, 35));

        directionInput = new JFormattedTextField(integerInstance);
        directionInput.setValue(0);
        directionInput.addMouseListener(clearFieldListener(directionInput));
        directionInput.setPreferredSize(new Dimension(65, 35));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        topPanel.add(startPauseButton);
        topPanel.add(mouseSpeedLabel);
        topPanel.add(mousePixelSpeedInput);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new FlowLayout());

        middlePanel.add(clearButton);
        middlePanel.add(catSpeedLabel);
        middlePanel.add(catPixelSpeedInput);

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

        this.add(Box.createRigidArea(new Dimension(15,0)));

        JPanel distanceBetweenPanel = new JPanel();
        distanceBetweenPanel.setLayout(new BoxLayout(distanceBetweenPanel, BoxLayout.Y_AXIS));

        JLabel distanceUntilLabel = new JLabel("Distance Until Collision:");
        distanceUntilLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));

        distanceBetweenPanel.add(distanceUntilLabel);
        distanceBetweenMouseCatLabel = new JLabel(String.format("%.2f", 2.0));
        distanceBetweenMouseCatLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        distanceBetweenPanel.add(distanceBetweenMouseCatLabel);

        distanceBetweenPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JButton randomizeMouseDirectionSpeedButton = new JButton("Randomize Mouse");
        randomizeMouseDirectionSpeedButton.addActionListener(randomizeButtonListener());
        randomizeMouseDirectionSpeedButton.setPreferredSize(new Dimension(75, 35));

        distanceBetweenPanel.add(randomizeMouseDirectionSpeedButton);

        this.add(distanceBetweenPanel);

        this.add(Box.createRigidArea(new Dimension(15,0)));


//----------------------------------------------------------------------------//

        JPanel mouseBallLocationPanel = new JPanel();
        mouseBallLocationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        mouseBallLocationPanel.setLayout(new BoxLayout(mouseBallLocationPanel, BoxLayout.Y_AXIS));

        JLabel mouseBallLocationLabel = new JLabel("Mouse Location");
        mouseBallLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        mouseBallLocationLabel.setPreferredSize(new Dimension(150, 35));

        mouseBallLocationPanel.add(mouseBallLocationLabel);

        JPanel mouseXPanel = new JPanel();
        mouseXPanel.setLayout(new BoxLayout(mouseXPanel, BoxLayout.X_AXIS));

        JLabel mouseXLocationLabel = new JLabel("X =");
        mouseXLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        mouseXLocationLabel.setPreferredSize(new Dimension(65, 35));

        mouseXBallLocationInput = new JFormattedTextField(integerInstance);
        mouseXBallLocationInput.setValue(0);
        mouseXBallLocationInput.addMouseListener(clearFieldListener(mouseXBallLocationInput));
        mouseXBallLocationInput.setPreferredSize(new Dimension(65, 35));

        mouseXPanel.add(mouseXLocationLabel);
        mouseXPanel.add(mouseXBallLocationInput);

        JPanel mouseYPanel = new JPanel();
        mouseYPanel.setLayout(new BoxLayout(mouseYPanel, BoxLayout.X_AXIS));

        mouseYBallLocationInput = new JFormattedTextField(integerInstance);
        mouseYBallLocationInput.setValue(0);
        mouseYBallLocationInput.addMouseListener(clearFieldListener(mouseYBallLocationInput));
        mouseYBallLocationInput.setPreferredSize(new Dimension(65, 35));

        JLabel mouseYLocationLabel = new JLabel("Y =");
        mouseYLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        mouseYLocationLabel.setPreferredSize(new Dimension(65, 35));

        mouseYPanel.add(mouseYLocationLabel);
        mouseYPanel.add(mouseYBallLocationInput);

        mouseBallLocationPanel.add(mouseXPanel);
        mouseBallLocationPanel.add(mouseYPanel);

        this.add(mouseBallLocationPanel);

//-----------------------------------------------------------------------------//

        JPanel catBallLocationPanel = new JPanel();
        catBallLocationPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        catBallLocationPanel.setLayout(new BoxLayout(catBallLocationPanel, BoxLayout.Y_AXIS));

        JLabel catBallLocationLabel = new JLabel("Cat Location");
        catBallLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        catBallLocationLabel.setPreferredSize(new Dimension(150, 35));

        catBallLocationPanel.add(catBallLocationLabel);

        JPanel catXPanel = new JPanel();
        catXPanel.setLayout(new BoxLayout(catXPanel, BoxLayout.X_AXIS));

        JLabel catXLocationLabel = new JLabel("X =");
        catXLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        catXLocationLabel.setPreferredSize(new Dimension(65, 35));

        catXBallLocationInput = new JFormattedTextField(integerInstance);
        catXBallLocationInput.setValue(0);
        catXBallLocationInput.addMouseListener(clearFieldListener(mouseXBallLocationInput));
        catXBallLocationInput.setPreferredSize(new Dimension(65, 35));

        catXPanel.add(catXLocationLabel);
        catXPanel.add(catXBallLocationInput);

        JPanel catYPanel = new JPanel();
        catYPanel.setLayout(new BoxLayout(catYPanel, BoxLayout.X_AXIS));

        catYBallLocationInput = new JFormattedTextField(integerInstance);
        catYBallLocationInput.setValue(0);
        catYBallLocationInput.addMouseListener(clearFieldListener(mouseYBallLocationInput));
        catYBallLocationInput.setPreferredSize(new Dimension(65, 35));

        JLabel catYLocationLabel = new JLabel("Y =");
        catYLocationLabel.setFont(new Font(Font.DIALOG, Font.BOLD,15));
        catYLocationLabel.setPreferredSize(new Dimension(65, 35));

        catYPanel.add(catYLocationLabel);
        catYPanel.add(catYBallLocationInput);

        catBallLocationPanel.add(catXPanel);
        catBallLocationPanel.add(catYPanel);

        this.add(catBallLocationPanel);
    }

    /**
     * Returns a new ActionListener with an override of actionPerformed event that upon evoked will
     * start the player to move around the diamond. If this Listener gets evoked again the player will stop moving.
     * Also contains input checkers to make sure no wrong inputs are being inputted before calculations are made.
     *
     * @return ActionListener
     */
    private ActionListener startPauseButtonLisenter() {
        return actionEvent -> {
//            AnimationPanel.GameField gameField = animationPanel.getGameField();
//            if (((Number) mousePixelSpeedInput.getValue()).intValue() <= 0) {
//                JOptionPane.showMessageDialog(animationPanel, "Refresh Rate input cannot be negative or zero");
//                return;
//            }
//
//            if (((Number) catPixelSpeedInput.getValue()).intValue() <= 0) {
//                JOptionPane.showMessageDialog(animationPanel, "Pixel Speed input cannot be negative or zero");
//                return;
//            }
//
//            if (((Number) mouseXBallLocationInput.getValue()).intValue() < 0 || ((Number) mouseXBallLocationInput.getValue()).intValue() > gameField.getSize().width) {
//                JOptionPane.showMessageDialog(animationPanel, "X location cannot be placed outside game board, between numbers 1 and " + gameField.getSize().width);
//                return;
//            }
//
//            if (((Number) mouseYBallLocationInput.getValue()).intValue() < 0 || ((Number) mouseYBallLocationInput.getValue()).intValue() > gameField.getSize().height) {
//                JOptionPane.showMessageDialog(animationPanel, "Y location cannot be placed outside game board, between numbers 1 and " + gameField.getSize().height);
//                return;
//            }
//
//            animationPanel.moveMouseCat(
//                    ((Number)directionInput.getValue()).intValue(),
//                    startPauseButton,
//                    mousePixelSpeedInput,
//                    catPixelSpeedInput,
//                    directionInput,
//                    distanceBetweenMouseCatLabel,
//                    this,
//                    false);
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
//            sinePanel.moveMouseCat(
//                    ((Number)directionInput.getValue()).intValue(),
//                    startPauseButton,
//                    mousePixelSpeedInput,
//                    catPixelSpeedInput,
//                    directionInput,
//                    distanceBetweenMouseCatLabel,
//                    this,
//                    true);
//
//
//
//            directionInput.setValue(0);


        };
    }

    private ActionListener randomizeButtonListener() {
        return actionEvent -> {
            sinePanel.repaint();
            System.out.println("refreshed");
            System.out.println(sinePanel.getSize());
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
