package code;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class TestFrame extends AbstractFrame{
    private final String romanNumberals[] = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    private final int totalCountOfProgress = 10;
    private final Font font = new Font("Adobe Caslon Pro Bold", Font.BOLD, 20);
    
    private JLabel titleLabel, questionTitleLabel, questionLabel, answerLabel, processLabel[], processFeather;
    private JTextField answerTextField, questionTextField;
    private JButton nextButton, soundButton;
    private int now, progressTotalLength;


    public TestFrame(){
	super("resource/ResultFrame.png", 20, 20, 30, 12);
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
	progressTotalLength = romanNumberals[0].length();
	
	exitButton.setBounds(getWidth()-35, 0, 30, 30);
	shrinkButton.setBounds(getWidth()-75, 5, 50, 20);
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
	titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
	titleLabel.setBounds(220, 80, 100, 100);
	getContentPane().add(titleLabel);
    }

    private void initSoundButton(){
	soundButton = new JButton(new ImageIcon("resource/VoiceButton.png"));
	soundButton.setBackground(new Color(0, 0, 0, 0));
	soundButton.setBorderPainted(false);
	soundButton.setOpaque(false);
	soundButton.setBounds(424, 263, 50, 50);
	getContentPane().add(soundButton);
    }

    private void initQuestionLabel(){
	questionLabel = new JLabel();
	questionLabel.setBounds(152, 215, 300, 40);
	questionLabel.setFont(new Font("Scanserif",Font.PLAIN,10));
	questionLabel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK));
	questionLabel.setHorizontalAlignment(SwingConstants.CENTER);
	getContentPane().add(questionLabel);	
    }

    private void initQuestionTitleLabel(){
	questionTitleLabel = new JLabel("Question:");
	questionTitleLabel.setBounds(42, 210, 300, 50);
	questionTitleLabel.setFont(font);
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
	answerLabel.setFont(font);
	answerLabel.setBounds(42, 319, 300, 50);
	getContentPane().add(answerLabel);
    }

    private void initAnswerTextField(){
	answerTextField = new JTextField();
	answerTextField.setBounds(152, 323, 300, 40);
	answerTextField.setEditable(true);
	getContentPane().add(answerTextField);
    }

    private void initNextButton(){
	nextButton = new JButton(new ImageIcon("resource/NextButton.png"));
	nextButton.setBackground(new Color(0, 0, 0, 0));
	nextButton.setOpaque(false);
	nextButton.setBorderPainted(false);
	nextButton.setBounds(400, 478, 100, 100);
	nextButton.addMouseListener(new NextButtonMouseAdapter());
	getContentPane().add(nextButton);
    }

    private void initProcessLabel(){
	processLabel = new JLabel[11];
	//int distances[] = {20, 40, 60, 40, 
	int totalLength = 0;
	Font font = new Font("Chalkduster", Font.BOLD, 25);
	for(int i=0; i<10; i++){
	    processLabel[i] = new JLabel(romanNumberals[i]);
	    if(i-1 >= 0)
		totalLength += romanNumberals[i-1].length()*21;
	    processLabel[i].setBounds(15 + totalLength, 450, 100, 20);
	    processLabel[i].setVisible(false);
	    processLabel[i].setFont(font);
	    getContentPane().add(processLabel[i]);
	}
	processLabel[0].setVisible(true);
    }

    private void initProcessFeather(){
	processFeather = new JLabel(new ImageIcon("resource/Feather.png"));
	processFeather.setBounds(25, 390, 100, 90);
	getContentPane().add(processFeather);
    }

    private void nextQuestionMotion(){
	if (now < totalCountOfProgress - 1) {
	    progressTotalLength += romanNumberals[now+1].length()*20; 
	    processFeather.setBounds(20+progressTotalLength, 390, 100, 90);
	    processLabel[++now].setVisible(true);
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
