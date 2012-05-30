
package code;

import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JTable;

public class ResultFrame extends AbstractFrame{

    private JTable resultTable;
    private JLabel titleLabel,scoreLabel,suggestionOneLabel,suggestionTwoLabel;
    private JButton finishButton;

    public ResultFrame(Object[][] rowData){
	super("resource/ResultFrame.png");
	initTitleLabel();
	initScoreLabel();
	initResultTable(rowData);
	initSuggestionOneLabel();
	initSuggestionTwoLabel();
	initFinishButton();
    }
    private void initTitleLabel(){
    	titleLabel = new JLabel("RESULT");
    	titleLabel.setBounds(191, 69, 120, 56);
    	getContentPane().add(titleLabel);
    }
    
    private void initScoreLabel(){
    	scoreLabel = new JLabel("SCORE:");
        scoreLabel.setBounds(43,133,100,30);
        getContentPane().add(scoreLabel);
    }
    
    private void initResultTable(Object[][] rowData){
    	String[] name = new String[4];
    	name[0]="Lesson";
    	name[1]="Question";
    	name[2]="Your Answer";
    	name[3]="Correct Answer";
    	resultTable = new JTable(rowData,name);
    	resultTable.setBounds(53, 184, 392, 201);
    	getContentPane().add(resultTable);
    }

    private void initSuggestionOneLabel(){
    	suggestionOneLabel = new JLabel();
    	suggestionOneLabel.setOpaque(false);
    	suggestionOneLabel.setText("Suggestion:");
        suggestionOneLabel.setBounds(43, 434, 120, 25);
        getContentPane().add(suggestionOneLabel);
    }
    
    private void initSuggestionTwoLabel(){
    	suggestionTwoLabel = new JLabel();
    	suggestionTwoLabel.setOpaque(false);
    	suggestionTwoLabel.setText("Congraduation: XXX.Fuck you.");
        suggestionTwoLabel.setBounds(53, 481, 326, 45);
        getContentPane().add(suggestionTwoLabel);
    }
    
    private void initFinishButton(){
    	finishButton = new JButton("Finish");
    	finishButton.setBackground(new Color(0,0,0,0));
    	finishButton.setBounds(418,447,50,50);
        getContentPane().add(finishButton);
    }

    /**
     * @return the titleLabel
     */
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    /**
     * @return the suggestionLabel
     */
    public JLabel getSuggestionTwoLabel() {
        return suggestionTwoLabel;
    }

    /**
     * @return the finishButton
     */
    public JButton getFinishButton() {
        return finishButton;
    }

    public static void main(String args[]){
	String[][] x = new String[2][4];
	x[0][0]="Lesson";
	x[0][1]="Question";
	x[0][2]="Your Answer";
	x[0][3]="Correct Answer";

	x[1][0]="1";
	x[1][1]="MM is b";
	x[1][2]="Your sister";
	x[1][3]="Your mother";
	ResultFrame test = new ResultFrame(x);
	test.setVisible(true);
    }

    /**
     * @return the scoreLabel
     */
    public JLabel getScoreLabel() {
        return scoreLabel;
    }
}
