/**
 * 
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Eric
 *
 */
public class LearnController {

    private Lesson selectedLesson;
    private LearnFrame view;

    public LearnController(Lesson lesson){
	selectedLesson = lesson;
	view = new LearnFrame();

	initAllPhrases();
	addAllListener();

	view.setVisible(true);
    }

    private void initAllPhrases(){
	for(Phrase p : selectedLesson.getAllPhrases()){
	    view.addPhraseItem(p);
	}
    }

    private void addAllListener(){

	view.getFinishButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		MainController main = MainController.getMainController();
		main.getLessonController().initAllPhrases();
		main.setVisible(true);
		view.setVisible(false);
		view.dispose();
		selectedLesson = null;
	    }
	});

	view.addExitingReturnToMainController();
	

    }

    public static void main(String[] args) throws Exception {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/lesson_test.db");
	Lesson l = new Lesson(conn, 1);	

	new LearnController(l);
    }
}
