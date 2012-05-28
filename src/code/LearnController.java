/**
 * 
 */
package code;

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
	
	initAllPhrase();

	view.setVisible(true);
    }

    private void initAllPhrase(){
	for(Phrase p : selectedLesson.getAllPhrases()){
	    view.addPhraseItem(p);
	}
    }

    public static void main(String[] args) throws Exception {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/lesson_test.db");
	Lesson l = new Lesson(conn, 1);	

	new LearnController(l);
    }
}
