/**
 * 
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author Eric
 *
 */
public class MainController {

    private Account account;
    private MainFrame view;

    public MainController(Account a){
	account = a;
	view = new MainFrame();

	initAllLessons();
	addListener();

	view.setVisible(true);
    }

    private void initAllLessons(){
	for(Lesson l : account.getAllLesson()){
	    view.addLesson(l.getLessonName());
	}
    }

    private void addListener(){
	view.getLogoutButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		if(account.isModify()){
		    int value = JOptionPane.showConfirmDialog(null,
			    "Do you want to save you changes ?",
			    "Log out",
			    JOptionPane.YES_NO_CANCEL_OPTION);
		    if(value == JOptionPane.CANCEL_OPTION){
			return;
		    } else if(value == JOptionPane.YES_OPTION){
			account.writeToDatabase();
		    }
		}

		new LoginController();

		view.setVisible(false);
		view.dispose();
	    }
	});

	view.getAddLessonButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent ae){
		String name = JOptionPane.showInputDialog("Please input a name");
		try{
		    account.createNewLesson(name);
		    view.addLesson(name);
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	});

	view.getDeleteLessonButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		
	    }
	});
	
	view.getTestAllButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		
	    }
	});

    }

    public void setVisible(boolean visible){
	view.setVisible(visible);
    }

}
