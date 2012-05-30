
package code;


import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class LoginFrame extends AbstractFrame{
    private JLabel titleLabel, IDLabel, summaryLabel;
    private JPanel summaryPanel;
    private JComboBox userComboBox;
    private JButton loginButton;

    public LoginFrame(){
	super(FilenameUtils.separatorsToSystem("resource/LoginFrame.png"));
	initTitleLabel();
	initUserComboBox();
	initLoginButton();
	initSummaryPanel();
	this.setVisible(true);
	createBufferStrategy(2);
    }

    /**
     * @return the titleLabel
     */
    public JLabel getTitleLabel() {
        return titleLabel;
    }

    /**
     * @return the userComboBox
     */
    public JComboBox getUserComboBox() {
        return userComboBox;
    }

    /**
     * @return the summaryPanel
     */
    public JPanel getSummaryPanel() {
        return summaryPanel;
    }

    /**
     * @return the loginButton
     */
    public JButton getLoginButton() {
        return loginButton;
    }
    
    private void initSummaryPanel(){      
	summaryPanel = new JPanel();
	summaryPanel.setOpaque(false);
    }
    
    private void initSummaryLabel(){
	summaryLabel = new JLabel();
	summaryLabel.setBounds(100, 100, 200, 100);
    }
    
    private void initTitleLabel(){
    	titleLabel = new JLabel("English Tutor");
    	titleLabel.setBounds(570, 71, 275, 204);
    	getContentPane().add(titleLabel);
    }
    
    private void initUserComboBox(){
    	IDLabel = new JLabel("ID");
    	IDLabel.setForeground(Color.WHITE);
    	IDLabel.setBounds(580, 330, 50, 50);
    	getContentPane().add(IDLabel);
    	userComboBox = new JComboBox();
    	userComboBox.setEditable(true);
    	userComboBox.setBounds(609, 330, 197, 50);
    	getContentPane().add(userComboBox);
    }
    
    private void initLoginButton(){
    	loginButton = new JButton("LOG IN");
    	loginButton.setBorderPainted(false);
    	Font newButtonFont=new Font("Arial", Font.BOLD, 12);  
    	loginButton.setFont(newButtonFont);
    	loginButton.setForeground(Color.red);
        loginButton.setBounds(786, 438, 80, 46);
        getContentPane().add(loginButton);
    }

    public void addAccountName(String userName){
	userComboBox.addItem(userName);
    }

    public static void main(String args[]){
	LoginFrame test = new LoginFrame();
	test.addAccountName("Harry");
	test.addAccountName("Eric");
	test.setVisible(true);
    }
}
