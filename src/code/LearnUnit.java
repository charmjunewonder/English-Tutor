package code;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.sql.*;
import java.awt.event.*;

public class LearnUnit extends AbstractUnit{
    private enum LearnType{
	LEARN_ONE_LESSON, LEARN_ALL_LESSONS
    }

    private LearnUnitView view;
    private LearnType testType;

    public LearnUnit(Lesson l){
	super(l);
	view = new LearnUnitView(l.getPhraseCount());
	tenPhrases = new ArrayList<Phrase>();
	currentPhraseIndex = -1;
	testType = LearnType.LEARN_ONE_LESSON;
	selectedLesson = l;

	addActionListener();
	EventQueue.invokeLater(new Runnable() {
		public void run() {
		try {
		view.setVisible(true);
		}
		catch (Exception e) {
		e.printStackTrace();
		}
		}
		});	
    }

    private void addActionListener(){

    }

    public static void main(String[] args) throws Exception {
	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
	Lesson l = new Lesson(conn, "Lesson_1");
	l.addPharse("你好", "Hello", "sounds/101.mp3");
	l.addPharse("好梦", "Nice Dream", "sounds/102.mp3");

	new LearnUnit(l);
    }
}
