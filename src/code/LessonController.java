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
    private LessonPanel view;

    public LessonController(Lesson lesson){
	selectedLesson = lesson;
	view = LessonPanel.getLessonPanel();

	initAllPhrases();
	addALlListeners();

	view.setVisible(true);
    }

    private void initAllPhrases(){
	view.clearPhraseTabelContent();
	for(Phrase p : selectedLesson.getAllPhrases()){
	    view.addPhrase(p);
	}
    }
    
    public void setSelectedLesson(Lesson l){
	selectedLesson = l;
	initAllPhrases();
    }

    private void addALlListeners(){
	view.getAddButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		String english = view.getEnglishTextField().getText();
		String chinese = view.getChineseTextField().getText();
		if(english.equals("") && chinese.equals("")) return;
		Phrase p = new Phrase(english, chinese, null);
		selectedLesson.addPhrase(p);
		view.addPhrase(p);
		view.clearEnglishChineseTextField();
	    }
	});

	view.getDeleteButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		int index = view.getPhraseTable().getSelectedRow();
		if(index == -1) return;
		Phrase p = selectedLesson.getPhrase(index);
		selectedLesson.deletePhrase(p);
		view.deletePhrase(index);
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
