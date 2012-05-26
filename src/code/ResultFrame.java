
package code;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JTable;

public class ResultFrame extends AbstractFrame{
    private GridLayout resultFrameLayout;
    private JPanel firstPanel,secondPanel,thridPanel,forthPanel,buttonPanel;
    private JTable resultTable;
    private JLabel titleLabel,scoreLabel,suggestionLabel;
    private JButton retryButton,finishButton;

    public ResultFrame(Object[][] rowData){
	super("resource/ResultFrame.png");
	initResultFrameLayout();
	initFirstPanel();
	initSecondPanel(rowData);
	initThridPanel();
	initForthPanel();
    }

    private void initResultFrameLayout(){
	resultFrameLayout = new GridLayout(4,1);
	getMainPanel().setLayout(resultFrameLayout);
    }

    private void initFirstPanel(){
	firstPanel = new JPanel();
	firstPanel.setLayout(new GridLayout(2,3));
	firstPanel.setOpaque(false);

	titleLabel = new JLabel("                 RESULT");
	scoreLabel = new JLabel("   SCORE:");

	firstPanel.add(new JLabel());
	firstPanel.add(titleLabel);
	firstPanel.add(new JLabel());
	firstPanel.add(scoreLabel);
	firstPanel.add(new JLabel());
	firstPanel.add(new JLabel());
	getMainPanel().add(firstPanel);
    }

    private void initSecondPanel(Object[][] rowData){
	secondPanel = new JPanel();
	secondPanel.setOpaque(false);
	String[] name = new String[4];
	name[0]="Lesson";
	name[1]="Question";
	name[2]="Your Answer";
	name[3]="Correct Answer";
	resultTable = new JTable(rowData,name);
	secondPanel.add(resultTable);
	getMainPanel().add(secondPanel);
    }

    private void initThridPanel(){
	thridPanel = new JPanel();
	thridPanel.setOpaque(false);
	suggestionLabel = new JLabel();
	suggestionLabel.setOpaque(false);
	suggestionLabel.setText("Suggestion:\n  Congraduration,XXX.Fuck you");
	thridPanel.add(suggestionLabel);

	getMainPanel().add(thridPanel);
    }

    private void initForthPanel(){
	forthPanel = new JPanel();
	forthPanel.setOpaque(false);
	forthPanel.setLayout(new BorderLayout());

	buttonPanel = new JPanel();
	buttonPanel.setLayout(new GridLayout(1,5));
	buttonPanel.setOpaque(false);

	retryButton = new JButton(new ImageIcon("resource/RetryButton.png"));
	retryButton.setBackground(new Color(0,0,0,0));


	finishButton = new JButton("Finish");
	finishButton.setBackground(new Color(0,0,0,0));
	buttonPanel.add(new JLabel());
	buttonPanel.add(new JLabel());
	buttonPanel.add(new JLabel());
	buttonPanel.add(retryButton);
	buttonPanel.add(finishButton);


	forthPanel.add(buttonPanel,BorderLayout.NORTH);
	getMainPanel().add(forthPanel);
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
    public JLabel getSuggestionLabel() {
        return suggestionLabel;
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
