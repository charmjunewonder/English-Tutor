package code;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Eric
 * @version 0.1
 */

public class TestUnitView extends JFrame {

    private JPanel	contentPane;
    private JTextField answerTextField;
    private JProgressBar progressBar;
    private JButton nextButton;
    private JButton soundButton;
    private JLabel questionLabel;
    private JLabel correctAnswerLabel;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
		try {
		TestUnitView frame = new TestUnitView();
		frame.setVisible(true);
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		}
		});
    }

    /**
     * Create the frame.
     */
    public TestUnitView() {
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 300);
	contentPane = new JPanel();
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	SpringLayout sl_contentPane = new SpringLayout();
	contentPane.setLayout(sl_contentPane);

	questionLabel = new JLabel("");
	questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
	sl_contentPane.putConstraint(SpringLayout.NORTH, questionLabel, 51, SpringLayout.NORTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.WEST, questionLabel, 50, SpringLayout.WEST, contentPane);
	sl_contentPane.putConstraint(SpringLayout.EAST, questionLabel, -41, SpringLayout.EAST, contentPane);
	contentPane.add(questionLabel);

	soundButton = new JButton("Sound");
	sl_contentPane.putConstraint(SpringLayout.NORTH, soundButton, 10, SpringLayout.NORTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.EAST, soundButton, -10, SpringLayout.EAST, contentPane);
	contentPane.add(soundButton);

	answerTextField = new JTextField();
	sl_contentPane.putConstraint(SpringLayout.NORTH, answerTextField, 66, SpringLayout.SOUTH, questionLabel);
	sl_contentPane.putConstraint(SpringLayout.WEST, answerTextField, 137, SpringLayout.WEST, contentPane);
	sl_contentPane.putConstraint(SpringLayout.EAST, answerTextField, -169, SpringLayout.EAST, contentPane);
	contentPane.add(answerTextField);
	answerTextField.setColumns(10);

	nextButton = new JButton("Verify");
	sl_contentPane.putConstraint(SpringLayout.SOUTH, nextButton, -10, SpringLayout.SOUTH, contentPane);
	sl_contentPane.putConstraint(SpringLayout.EAST, nextButton, 0, SpringLayout.EAST, soundButton);
	contentPane.add(nextButton);

	progressBar = new JProgressBar();
	sl_contentPane.putConstraint(SpringLayout.SOUTH, progressBar, 0, SpringLayout.SOUTH, nextButton);
	sl_contentPane.putConstraint(SpringLayout.EAST, progressBar, -84, SpringLayout.WEST, nextButton);
	contentPane.add(progressBar);

	correctAnswerLabel = new JLabel("");
	correctAnswerLabel.setForeground(Color.RED);
	sl_contentPane.putConstraint(SpringLayout.WEST, correctAnswerLabel, 15, SpringLayout.WEST, contentPane);
	sl_contentPane.putConstraint(SpringLayout.SOUTH, correctAnswerLabel, -27, SpringLayout.NORTH, progressBar);
	sl_contentPane.putConstraint(SpringLayout.EAST, correctAnswerLabel, 161, SpringLayout.WEST, contentPane);
	contentPane.add(correctAnswerLabel);
	//contentPane.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{nextButton, answerTextField, progressBar, soundButton, questionLabel, correctAnswerLabel}));
    }

    /**
     * @return the contentPane
     */
    public JPanel getContentPane() {
	return contentPane;
    }

    /**
     * @return the answerTextField
     */
    public JTextField getAnswerTextField() {
	return answerTextField;
    }

    /**
     * @return the progressBar
     */
    public JProgressBar getProgressBar() {
	return progressBar;
    }

    /**
     * @return the nextButton
     */
    public JButton getNextButton() {
	return nextButton;
    }

    /**
     * @return the soundButton
     */
    public JButton getSoundButton() {
	return soundButton;
    }

    /**
     * @return the questionLabel
     */
    public JLabel getQuestionLabel() {
	return questionLabel;
    }

    /**
     * @return the correctAnswerLabel
     */
    public JLabel getCorrectAnswerLabel() {
	return correctAnswerLabel;
    }
}
