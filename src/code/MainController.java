/**
 * 
 */
package code;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

/**
 * @author Eric
 *
 */
public class MainController {

    private Account account;
    //TODO
    public MainFrame view;
    private static MainController mainController;

    public static MainController getMainController(){
	if (mainController == null) {
	    synchronized (MainController.class) {
		if (mainController == null) {
		    mainController = new MainController();
		}
	    }
	}
	return mainController;
    }


    private MainController(){
	view = new MainFrame();

	addListener();

	//view.setVisible(true);
    }


    public void update(Graphics g){
	view.update(g);
    }


    /**
     * @param account the account to set
     */
    public void setAccount(Account account) {
        this.account = account;
	initAllLessons();
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
		    if(name == null || name.equals("")) throw new Exception("not valid lesson name");
		    account.createNewLesson(name);
		    view.addLesson(name);
		}catch(Exception e){
		    JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	    }
	});

	view.getDeleteLessonButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		int row = view.getLessonTable().getSelectedRow();
		if(row == -1) return;
		account.deleteLesson(row);
		view.deleteLesson(row);
	    }
	});

	view.getTestAllButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		new TestController(account);
		setVisible(false);
	    }
	});

    }

    public void setVisible(boolean visible){
	view.setVisible(visible);
    }

}
