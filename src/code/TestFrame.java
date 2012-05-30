package code;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class TestFrame extends AbstractFrame{
    private JLabel titleLabel,questionTitleLabel, questionLabel, answerLabel,processLabel[],processFeather;
    private JTextField answerTextField,questionTextField;
    private JButton nextButton,soundButton;
    private int now,sum;

    public TestFrame(){
	super("resource/TestFrame.png");
	initTitleLabel();
	initSoundButton();
	initQuestionLabel();
	initQuestionTitleLabel();
	initAnswerLabel();
	initAnswerTextField();
	initNextButton();
	initProcessLabel();
	initProcessFeather();
	now=0;
	sum=9;
    }
    
    /**
     * @param answerLabel the answerLabel to set
     */
    public void setAnswerLabel(JLabel answerLabel) {
        this.answerLabel = answerLabel;
    }

    /**
     * @return the titleLabel
     */
    public JLabel getTitleLabel() {
	return titleLabel;
    }

    /**
     * @return the questionLabel
     */
    public JLabel getQuestionLabel() {
	return questionLabel;
    }

    /**
     * @return the answerLabel
     */
    public JLabel getAnswerLabel() {
	return answerLabel;
    }

    /**
     * @return the answerTextField
     */
    public JTextField getAnswerTextField() {
	return answerTextField;
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
	questionLabel = new JLabel();
	questionLabel.setBounds(130, 180, 300, 40);
	getContentPane().add(questionLabel);	
    }

    private void initQuestionTitleLabel(){
	questionTitleLabel = new JLabel("Question:");
	questionTitleLabel.setBounds(80, 180, 300, 50);
	getContentPane().add(questionTitleLabel);
    }

    private void initQuestionTextField(){
	questionTextField = new JTextField();
	questionTextField.setEditable(false);
	questionTextField.setBounds(130, 180, 300, 40);
	getContentPane().add(questionTextField);
    }

    private void initAnswerLabel(){
	answerLabel = new JLabel("Answer:");
	answerLabel.setBounds(80,235,300,50);
	getContentPane().add(answerLabel);
    }

    private void initAnswerTextField(){
	answerTextField = new JTextField();
	answerTextField.setBounds(130, 240, 300, 40);
	answerTextField.setEditable(true);
	getContentPane().add(answerTextField);
    }

    private void initNextButton(){
	nextButton = new JButton(new ImageIcon("resource/next.png"));
	nextButton.setBackground(new Color(0,0,0,0));
	nextButton.setOpaque(false);
	nextButton.setBorderPainted(false);
	nextButton.setBounds(400, 400, 100, 100);
	nextButton.addMouseListener(new NextButtonMouseAdapter());
	getContentPane().add(nextButton);
    }

    private void initProcessLabel(){
	processLabel = new JLabel[11];
	for(int i=0;i<10;i++){
	    processLabel[i] = new JLabel((i+1)+"");
	    processLabel[i].setBounds(50+i*30,500,15,15);
	    processLabel[i].setVisible(false);
	    getContentPane().add(processLabel[i]);
	}
	processLabel[0].setVisible(true);
    }

    private void initProcessFeather(){
	processFeather = new JLabel(new ImageIcon("resource/processFeather.png"));
	processFeather.setBounds(40, 410, 100, 90);
	getContentPane().add(processFeather);
    }

    private void nextQuestionMotion(){
	if (now<sum) {
	    processFeather.setBounds(45+(now+1)*30,410,100,90);
	    processLabel[now+1].setVisible(true);
	    now++;
	}
    }

    private class NextButtonMouseAdapter extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
	    nextQuestionMotion();
	}
    }

    public static void main(String args[]){
	TestFrame test = new TestFrame();
	test.setVisible(true);
    }
}
