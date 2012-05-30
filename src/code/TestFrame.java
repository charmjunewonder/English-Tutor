package code;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;

import java.awt.Color;

public class TestFrame extends AbstractFrame{
    private JLabel titleLabel,questionLabel,answerLabel;
    private JTextField answerTextField,questionTextField;
	private JButton nextButton,soundButton;
	
	public TestFrame(){
		super("resource/TestFrame.png");
		initTitleLabel();
	    initSoundButton();
		initQuestionLabel();
		initQuestionTextField();
		initAnswerLabel();
		initAnswerTextField();
	}
	
	private void initTitleLabel(){
		titleLabel = new JLabel("Test");
		titleLabel.setBounds(250, 80, 50, 20);
		getContentPane().add(titleLabel);
	}
	
	private void initSoundButton(){
		soundButton = new JButton(new ImageIcon("resource/VoiceButton.png"));
	    soundButton.setBackground(new Color(0,0,0,0));
	    soundButton.setBorderPainted(false);
	    soundButton.setOpaque(false);
		soundButton.setBounds(20, 150, 50, 50);
		getContentPane().add(soundButton);
	}
	
	private void initQuestionLabel(){
		questionLabel = new JLabel("Question:");
		questionLabel.setBounds(80, 150, 300, 50);
		getContentPane().add(questionLabel);
	}
	
	private void initQuestionTextField(){
		questionTextField = new JTextField();
		questionTextField.setEditable(false);
		questionTextField.setBounds(130, 150, 300, 40);
		getContentPane().add(questionTextField);
	}
	
	private void initAnswerLabel(){
		answerLabel = new JLabel("Answer:");
		answerLabel.setBounds(80,200,300,50);
		getContentPane().add(answerLabel);
	}
	
	private void initAnswerTextField(){
		answerTextField = new JTextField();
		answerTextField.setBounds(130, 205, 300, 40);
		answerTextField.setEditable(true);
		getContentPane().add(answerTextField);
	}
	
	public static void main(String args[]){
    	TestFrame test = new TestFrame();
    	test.setVisible(true);
    }
}
