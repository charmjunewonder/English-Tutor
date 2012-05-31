package code;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.text.JTextComponent;

public class LoginController {

    private Database database;
    private LoginFrame view;

    public LoginController(){
	database = new Database();
	view = new LoginFrame();

	initAllExistingAccount();
	addListener();
	initLabels();

	view.setVisible(true);
    }

    public void setVisible(boolean visible){
	view.setVisible(visible);
    }

    private void initAllExistingAccount(){
	for(String name : database.getAllAccountNames()){
	    view.addAccountName(name); 
	}
    }

    private void initLabels(){
	try{
	    //InputStream in = LoginController.class.getResourceAsStream("abaddon_0.TTF");  
	    /*File file = new File(FilenameUtils.separatorsToSystem("resource/Jaggard Two.ttf"));
	    FileInputStream in = new FileInputStream(file);

	    Font dynamicFont = Font.createFont(Font.TRUETYPE_FONT, in);
	    Font dynamicFontPt = dynamicFont.deriveFont(30f);
	    GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(dynamicFontPt);
	    in.close();*/
	    //Font font = Font.createFont(Font.TRUETYPE_FONT, );

	    //Font.deriveFont(font, 30); 
	    JLabel titleLabel = view.getTitleLabel();
	    titleLabel.setText("<html><Center> <p>English</p> <p>Tutor</p> </Center></html>");
	    titleLabel.setFont(new Font("Blackmoor LET", Font.BOLD, 72));
	    titleLabel.setForeground(Color.YELLOW);
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    private void addListener(){
	view.getLoginButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		String name = null;
		Account account = null;
		try{
		    //TODO
		    if(view.getUserComboBox().getSelectedIndex() == -1){
			JTextComponent tc = (JTextComponent)view.getUserComboBox().getEditor().getEditorComponent();
			name = tc.getText();
			database.createAccount(name);

			account = new Account(name);
			account.loadDefaultLessons();
		    } else{
			name = (String) view.getUserComboBox().getSelectedItem();

			account = new Account(name);
			account.loadLessonsFromDatabase();
		    }
		    final Account accountFinal = account;
		    
		    //EnsureFrame ensureFrame = new EnsureFrame();
		    //EnsureFrame.showMessageDialog(name + " has already existed. Please use another name.");

		    /*MainController main = new MainController(account);
		    //TODO

		    view.setVisible(false);
		    view.dispose();
		    main.setVisible(true);*/
		    EventQueue.invokeLater(new Runnable() {
			public void run() {
			    try {
				MainController main = MainController.getMainController();
				main.setAccount(accountFinal);

				//view.createBufferStrategy(2);
				BufferStrategy myStrategy = view.getBufferStrategy();
				Graphics g = myStrategy.getDrawGraphics(); // acquire the graphics

				//TODO what is the magic here?
				// draw stuff here
				main.setVisible(true);
				main.view.paint(g);
				myStrategy.show();
				view.setVisible(false);
				view.dispose();
				g.dispose();
			    }
			    catch (Exception e) {
				e.printStackTrace();
			    }
			}
		    });

		} catch(SQLException sqle){
		    //JOptionPane.showMessageDialog(null, "Please use another name.", name + " exists", JOptionPane.ERROR_MESSAGE);
		    EnsureFrame.showMessageDialog(name + " has already existed. Please use another name.");
		} catch(Exception exception){
		    exception.printStackTrace();
		}


	    }
	});
    }

}
