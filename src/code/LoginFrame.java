
package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class LoginFrame extends AbstractFrame{
    private JLabel userComboBoxLabel;
    private JPanel summaryPanel,userPanel,userUpPanel,userMiddlePanel,userDownPanel,userComboBoxPanel,buttonPanel;
    private JComboBox userComboBox;
    private JButton loginButton;
    private BorderLayout userPanelLayout;

    public LoginFrame(){
	super(FilenameUtils.separatorsToSystem("resource/LoginFrame.png"));
	initMainPanelLayout();
	initSummaryPanel();
	initUserPanel();
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

    private void initMainPanelLayout(){
	getMainPanel().setLayout(new GridLayout(1,2));
    }

    private void initSummaryPanel(){      
	summaryPanel = new JPanel();
	summaryPanel.setOpaque(false);
	getMainPanel().add(summaryPanel);
    }

    private void initUserPanel(){

	userComboBoxPanel = new JPanel();
	userComboBoxPanel.setOpaque(false);
	userComboBoxLabel = new JLabel("ID");
	userComboBoxLabel.setSize(20,10);
	userComboBoxLabel.setForeground(Color.WHITE);

	userComboBox = new JComboBox();
	userComboBox.setEditable(true);
	userComboBox.setSize(50,10);
	userComboBox.setBounds(0, 0, 20, 100);

	userComboBoxPanel.add(userComboBoxLabel);
	userComboBoxPanel.add(userComboBox);

	loginButton = new JButton("LOG IN");
	loginButton.setSize(new Dimension(100,30));
	loginButton.setBorderPainted(false);
	//Set JButton font using new created font
	Font newButtonFont=new Font("Arial", Font.BOLD, 28);  
	loginButton.setFont(newButtonFont);
	loginButton.setForeground(Color.red);

	buttonPanel = new JPanel();
	buttonPanel.setOpaque(false);
	buttonPanel.setLayout(new GridLayout(1,5));
	buttonPanel.add(new JLabel());
	buttonPanel.add(new JLabel());
	buttonPanel.add(new JLabel());
	buttonPanel.add(new JLabel());
	buttonPanel.add(loginButton);

	userUpPanel = new JPanel();
	userUpPanel.setOpaque(false);
	userMiddlePanel = new JPanel();
	userMiddlePanel.setOpaque(false);
	userDownPanel = new JPanel();
	userDownPanel.setOpaque(false);

	userPanel = new JPanel();
	userPanelLayout = new BorderLayout();
	userPanel.setLayout(new GridLayout(3,1));
	userPanel.setOpaque(false);

	userPanel.add(userUpPanel);
	userPanel.add(userMiddlePanel);
	userPanel.add(userDownPanel);

	userMiddlePanel.setLayout(new GridLayout(4,1));
	userMiddlePanel.add(new JLabel());
	userMiddlePanel.add(userComboBoxPanel);
	userMiddlePanel.add(new JLabel());
	userMiddlePanel.add(buttonPanel); 

	getMainPanel().add(userPanel);

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
