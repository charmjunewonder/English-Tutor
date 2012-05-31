/**
 * 
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JPanel;

/**
 * @author Eric
 *
 */
public class LessonController {

    private Lesson selectedLesson;
    private JPanel view; //TODO

    public LessonController(Lesson lesson){
	selectedLesson = lesson;
	view = new JPanel(); //TODO

	initAllPhrases();
	addALlListeners();

	view.setVisible(true);
    }

    private void initAllPhrases(){
	for(Phrase p : selectedLesson.getAllPhrases()){
	    view.addPhraseItem(p);
	}
    }

    private void addALlListeners(){
	view.getAddButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		Phrase p = new Phrase(view.getEnglishTextField().getText(), 
					view.getChineseTextField().getText(), null);
		selectedLesson.addPhrase(p);
	    }
	});

	view.getDeleteButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		Phrase p = selectedLesson.getPhrase(view.getTabel().getSelectedRow());
		selectedLesson.deletePhrase(p);
	    }
	});

	view.getTestButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		new TestController(selectedLesson);
	    }
	});

	view.getLearnButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		new LearnController(selectedLesson);
	    }
	});

    }

    public static void main(String[] args) throws Exception {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/lesson_test.db");
	Lesson l = new Lesson(conn, 1);	

	new LessonController(l);
    }

}
