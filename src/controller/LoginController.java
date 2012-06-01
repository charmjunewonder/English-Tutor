/*
 * LoginController.java 1.1 2012/6/1
 * 
 * Copyright (c) 2012 Northeastern University Software Engineering College
 * Software International 1001 Group Three
 * 
 * All rights reserved.
 */

package controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;

import org.apache.commons.io.FilenameUtils;

import model.Account;
import model.Database;
import program.InvalidFileNameException;
import view.EnsureFrame;
import view.LoginFrame;

/**
 * A class controls the view which lets the user type his/her name or delete a
 * existing user name, or select a name to log in.
 * 
 * @author Eric
 * @version 1.1
 * @see model.Account;
 * @see model.Database;
 * @see program.InvalidFileNameException;
 * @see view.EnsureFrame;
 * @see view.LoginFrame;
 */
public class LoginController {

    private Database database;
    private LoginFrame view;

    /**
     * Create a new instance of LoginController, show the existing user name,
     * add some ActionListener to the view. Initially, the view is visible.
     */
    public LoginController() {
	database = new Database();
	view = new LoginFrame();

	initAllExistingAccount();
	addListener();
	initLabels();

	view.setVisible(true);
    }

    /**
     * Set the visibility of the view
     * 
     * @param visibility of the view
     */
    public void setVisible(boolean visible) {
	view.setVisible(visible);
    }

    /**
     * Show all the user name.
     */
    private void initAllExistingAccount() {
	for (String name : database.getAllAccountNames()) {
	    view.addAccountName(name);
	}
    }

    /**
     * Initialize the title label and summary lable
     */
    private void initLabels() {
	try {

	    // TODO
	    // InputStream in =
	    // LoginController.class.getResourceAsStream("abaddon_0.TTF");
	    /*
	     * File file = new
	     * File(FilenameUtils.separatorsToSystem("resource/Jaggard Two.ttf"
	     * )); FileInputStream in = new FileInputStream(file);
	     * 
	     * Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in); Font
	     * dynamicFontPt = dynamicFont.deriveFont(30f);
	     * GraphicsEnvironment.getLocalGraphicsEnvironment
	     * ().registerFont(dynamicFontPt); in.close();
	     */
	    // Font font = Font.createFont(Font.TRUETYPE_FONT, );

	    // Font.deriveFont(font, 30);
	    JLabel titleLabel = view.getTitleLabel();
	    titleLabel
		    .setText("<html><Center> <p>English</p> <p>Tutor</p> </Center></html>");
	    titleLabel.setFont(new Font("Blackmoor LET", Font.BOLD, 72));
	    titleLabel.setForeground(Color.YELLOW);
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    /**
     * Add some listeners to the view
     */
    private void addListener() {

	// Login listener
	view.getLoginButton().addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		String name = null;
		Account account = null;
		try {
		    // TODO
		    // no selection
		    if (view.getUserComboBox().getSelectedIndex() == -1) {
			JTextComponent tc = (JTextComponent) view
				.getUserComboBox().getEditor()
				.getEditorComponent();
			name = tc.getText();
			account = database.createAccount(name);
			account.loadDefaultLessons();

			// user have selected
		    } else {
			name = (String) view.getUserComboBox()
				.getSelectedItem();

			account = new Account(name);
			account.loadLessonsFromDatabase();
		    }
		    // Account accountFinal = account;
		    MainController main = MainController.getMainController();
		    main.setAccount(account);
		    main.setVisible(true);
		    view.setVisible(false);
		    view.dispose();
		    /*
		     * MainController main = new MainController(account); //TODO
		     * 
		     * view.setVisible(false); view.dispose();
		     * main.setVisible(true);
		     * 
		     * EventQueue.invokeLater(new Runnable() { public void run()
		     * { try { MainController main = MainController
		     * .getMainController(); main.setAccount(accountFinal);
		     * 
		     * // view.createBufferStrategy(2); BufferStrategy
		     * myStrategy = view .getBufferStrategy(); Graphics g =
		     * myStrategy.getDrawGraphics(); // acquire // the //
		     * graphics
		     * 
		     * // TODO what is the magic here? // draw stuff here
		     * main.setVisible(true); main.view.paint(g);
		     * myStrategy.show(); view.setVisible(false);
		     * view.dispose(); g.dispose(); } catch (Exception e) {
		     * e.printStackTrace(); } } });
		     */

		} catch (InvalidFileNameException ifne) {
		    EnsureFrame
			    .showMessageDialog("<html><Center>"
				    + "<p>A filename cannot contain following characters:</p>"
				    + "<p> \\ / : * ? \" < > |</p>"
				    + "</Center></html>");
		} catch (SQLException sqle) {
		    // JOptionPane.showMessageDialog(null,
		    // "Please use another name.", name + " exists",
		    // JOptionPane.ERROR_MESSAGE);
		    EnsureFrame.showMessageDialog(name
			    + " has already existed. Please use another name.");
		} catch (Exception exception) {
		    exception.printStackTrace();
		}

	    }
	});

	// Exit listener
	view.getExitButton().addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		System.exit(0);
	    }
	});

	// Delete listener
	view.getDeleteButton().addActionListener(new ActionListener() {
	    public void actionPerformed(ActionEvent e) {
		// no selection
		if (view.getUserComboBox().getSelectedIndex() == -1) {
		    return;

		    // have selection
		} else {
		    String name = (String) view.getUserComboBox()
			    .getSelectedItem();
		    int value = JOptionPane.showConfirmDialog(null,
			    "Do you want to delete " + name + " ?", "Delete",
			    JOptionPane.YES_NO_OPTION);

		    // don't delete
		    if (value == JOptionPane.NO_OPTION) {
			return;
		    }
		    // deleting
		    int index = view.getUserComboBox().getSelectedIndex();
		    // delete the account name in database
		    database.deleteAccount(index);
		    // delete database file
		    File file = new File(FilenameUtils
			    .separatorsToSystem("data/" + name + ".db"));
		    file.delete();
		    // remove the item in comnobox
		    view.getUserComboBox().removeItemAt(index);
		}
	    }
	});

    }

}
