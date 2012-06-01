/*
 * LoginFrame.java 1.1 2012/10/18
 *
 * Copyright (c) 2012 Northeastern University Software Engineering College
 * Software International 1001 Group Three
 *
 * All rights reserved.
 *
 */

package view;

/**
 * LoginFrame - The login frame of the learnEnglish system.
 * <p>
 * Generate the login frame.
 * 
 * @author Luo Yaoshen
 * @version 1.2
 * @see view.AbstractFrame
 */
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class LoginFrame extends AbstractFrame {
	private JLabel titleLabel, idLabel, summaryLabel;
	private JPanel summaryPanel;
	private JComboBox userComboBox;
	private JButton loginButton, deleteButton;

	/**
	 * Initialize the summary panel of the login frame
	 */
	private void initSummaryPanel() {
		summaryPanel = new JPanel();
		summaryPanel.setOpaque(false);
	}

	/**
	 * Initialize the summary label of the login frame
	 */
	private void initSummaryLabel() {
		summaryLabel = new JLabel();
		summaryLabel.setBounds(100, 100, 200, 100);
	}

	/**
	 * Initialize the title label of the login frame
	 */
	private void initTitleLabel() {
		titleLabel = new JLabel("English Tutor");
		titleLabel.setBounds(610, 71, 275, 204);
		getContentPane().add(titleLabel);
	}

	/**
	 * Initialize the delete button of the login frame
	 */
	private void initDeleteButton() {
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(850, 330, 70, 40);
		deleteButton.setToolTipText("Delete the selected account.");
		getContentPane().add(deleteButton);
	}

	/**
	 * Initialize the user comboBox of the login frame
	 */
	private void initUserComboBox() {
		idLabel = new JLabel("ID");
		Font newButtonFont = new Font("Arial", Font.BOLD, 20);
		idLabel.setFont(newButtonFont);
		idLabel.setForeground(Color.WHITE);
		idLabel.setBounds(580, 330, 100, 50);
		getContentPane().add(idLabel);

		userComboBox = new JComboBox();
		userComboBox.setEditable(true);
		userComboBox.setBounds(609, 330, 197, 40);
		getContentPane().add(userComboBox);
	}

	/**
	 * Initialize the login button of the login frame
	 */
	private void initLoginButton() {

		loginButton = new JButton("LOG IN");
		loginButton.setBorderPainted(false);
		Font newButtonFont = new Font("Arial", Font.BOLD, 30);
		loginButton.setFont(newButtonFont);
		loginButton.setOpaque(false);
		loginButton.setBackground(new Color(0, 0, 0, 0));
		loginButton.setForeground(Color.yellow);
		loginButton.setBounds(730, 438, 200, 46);
		loginButton.setToolTipText("Login the English Tutor");
		getContentPane().add(loginButton);
	}

	/**
	 * To add an account into the comboBox
	 * 
	 * @param userName
	 *            the name of the account
	 */
	public void addAccountName(String userName) {
		userComboBox.addItem(userName);
	}

	/**
	 * To generate an instance of the login frame
	 */
	public LoginFrame() {
		super(FilenameUtils.separatorsToSystem("resource/LoginFrame.png"));
		initTitleLabel();
		initDeleteButton();
		initUserComboBox();
		initLoginButton();
		initSummaryPanel();
		this.setVisible(true);
		createBufferStrategy(2);
	}

	/**
	 * @return the deleteButton
	 */
	public JButton getDeleteButton() {
		return deleteButton;
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

	public static void main(String args[]) {
		LoginFrame test = new LoginFrame();
		test.addAccountName("Harry");
		test.addAccountName("Eric");
		test.setVisible(true);
	}
}
