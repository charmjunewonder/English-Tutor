/**
 * 
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JTable;

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

    public void initAllPhrases(){
	view.clearPhraseTabelContent();
	for(Phrase p : selectedLesson.getAllPhrases()){
	    view.addPhrase(p);
	}

    }

    public void resetButtons(){
	boolean bool = selectedLesson.isEnabled();
	view.getTestButton().setEnabled(bool);
	view.getLearnButton().setEnabled(bool);
    }

    public void setSelectedLesson(Lesson l){
	selectedLesson = l;
	initAllPhrases();
	resetButtons();
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
		MainController.getMainController().setVisible(false);
		selectedLesson.setNeededToRestore(true);
	    }
	});

	view.getLearnButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		new LearnController(selectedLesson);
		MainController.getMainController().setVisible(false);
		selectedLesson.setNeededToRestore(true);
	    }
	});

	view.getPhraseTable().addMouseListener(new SoundAdapter());

    }

    private class SoundAdapter extends MouseAdapter{
	public void mousePressed(MouseEvent e){
	    JTable phraseTable = view.getPhraseTable();
	    int count = phraseTable.getRowCount();
	    for(int i=0;i<count;i++){
		if (phraseTable.isCellSelected(i, 2) && phraseTable.isColumnSelected(2) && phraseTable.getValueAt(i, 2).toString().charAt(0)!='j'){
		    SoundEngine.playSound(selectedLesson.getPhrase(i).getAudio());
		}
	    }
	} 
    }

    public static void main(String[] args) throws Exception {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/lesson_test.db");
	Lesson l = new Lesson(conn, 1);	

	new LessonController(l);
    }

}
