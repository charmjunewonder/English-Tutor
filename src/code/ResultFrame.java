
package code;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.io.FilenameUtils;

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
	exitButton.setBounds(getWidth()-35, 0, 30, 30);
	shrinkButton.setBounds(getWidth()-85, 5, 50, 20);
	exitImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/x_black.png").getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	exitEnteredImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/x_red.png").getScaledInstance(20, 20, Image.SCALE_DEFAULT));
	shrinkImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/shrink_black.png").getScaledInstance(30, 12, Image.SCALE_DEFAULT));
	shrinkEnteredImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/shrink_green.png").getScaledInstance(30, 12, Image.SCALE_DEFAULT));
    }
    private void initTitleLabel(){
	titleLabel = new JLabel("Result");
	titleLabel.setBounds(200, 60, 200, 100);
	titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
	getContentPane().add(titleLabel);
    }

    private void initScoreLabel(){
	scoreLabel = new JLabel("SCORE:");
	scoreLabel.setBounds(43,145,100,30);
	scoreLabel.setFont(new Font("Adobe Caslon Pro Bold", Font.BOLD, 20));
	getContentPane().add(scoreLabel);
    }

    private void initResultTable(Object[][] rowData){
	String[] name = new String[4];
	name[0]="Lesson";
	name[1]="Question";
	name[2]="Your Answer";
	name[3]="Correct Answer";
	resultTable = new JTable(rowData,name){
	    //  Returning the Class of each column will allow different
	    //  renderers to be used based on Class

	    public Class getColumnClass(int column)
	    {
		return getValueAt(0, column).getClass();
	    }

	    public Component prepareRenderer(
		    TableCellRenderer renderer, int row, int column)
	    {
		Component c = super.prepareRenderer(renderer, row, column);
		//renderer.setBorder(new MatteBorder(1, 0, 1, 0, Color.RED));
		JComponent jc = (JComponent)c;
		jc.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Color.white));
		//  Color row based on a cell value
		//  Alternate row color

		if (isRowSelected(row)){
		    //c.setBackground(row % 2 == 0 ? getBackground() : Color.LIGHT_GRAY);
		    //jc.setBorder(new MatteBorder(1, 0, 1, 0, Color.RED) );
		}
		else{
		    //c.setBackground(getBackground());
		}


		//  Use bold font on selected row

		return c;
	    }
	    
	    @Override
	    public boolean isCellEditable(int row, int column) {
	       //all cells false
	       return false;
	    }
	};
	resultTable.setIntercellSpacing(new Dimension(0,0));
	resultTable.setShowVerticalLines(false);
	resultTable.setRowSelectionAllowed(false);
	resultTable.setRowHeight(25);
	//resultTable.setFont(new Font("Times New Roman", Font.BOLD, 15));
	//.setShowHorizontalLines(false);
	resultTable.setBounds(53, 184, 392, 300);
	resultTable.setOpaque(false);
	resultTable.setBackground(new Color(0, true));
	//resultTable.setBorder(BorderFactory.createCompoundBorder( BorderFactory.createMatteBorder(1,1,1,1,Color.BLACK), BorderFactory.createEmptyBorder(2,2,1,2)));
	//resultTable.setEnabled(false);
	//resultTable.removeEditor();
	resultTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

	//resultTable.setDefaultRenderer(Object.class, d);
	getContentPane().add(resultTable);
    }

    private void initSuggestionOneLabel(){
	suggestionOneLabel = new JLabel();
	suggestionOneLabel.setOpaque(false);
	suggestionOneLabel.setText("Suggestion:");
	suggestionOneLabel.setBounds(43, 434, 120, 25);
	suggestionOneLabel.setFont(new Font("Adobe Caslon Pro Bold", Font.BOLD, 20));
	getContentPane().add(suggestionOneLabel);
    }

    private void initSuggestionTwoLabel(){
	suggestionTwoLabel = new JLabel();
	suggestionTwoLabel.setOpaque(false);
	suggestionTwoLabel.setText("Congraduation: XXX.Fuck you.");
	suggestionTwoLabel.setBounds(53, 481, 326, 45);
	suggestionTwoLabel.setFont(new Font("Adobe Caslon Pro Bold", Font.BOLD, 15));
	getContentPane().add(suggestionTwoLabel);
    }

    private void initFinishButton(){
	finishButton = new JButton("Finish");
	finishButton.setBackground(new Color(0,0,0,0));
	finishButton.setBounds(394,478,50,50);
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
	String[][] x = new String[3][4];
	x[0][0]="Lesson";
	x[0][1]="Question";
	x[0][2]="Your Answer";
	x[0][3]="Correct Answer";

	x[1][0]="1";
	x[1][1]="MM is b";
	x[1][2]="Your sister";
	x[1][3]="Your mother";
	
	x[2][0]="1";
	x[2][1]="MM is b";
	x[2][2]="Your sister";
	x[2][3]="Your mother";
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
