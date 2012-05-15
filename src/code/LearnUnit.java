package code;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.sql.*;
import java.awt.event.*;

public class LearnUnit {
	private enum TestType{
		TestOneLesson, TestAllLesson
	}

	private Lesson selectedLesson;
	private int currentPhraseIndex;
	private LearnUnitView view;
	private TestType testType;
	private Account account;
	private ArrayList<Phrase> tenPhrases;
	private Phrase currentPhrase;

	public LearnUnit(Lesson l){
		view = new LearnUnitView();
		tenPhrases = new ArrayList<Phrase>();
		currentPhraseIndex = -1;
		testType = TestType.TestOneLesson;
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
		
		Phrase p = getNextPhrase();
		if(p == null) return;
		view.updatePhrase(p);
		currentPhrase = p;
	}

	public void setSelectedLesson(Lesson lesson){
		selectedLesson = lesson;
	}

	public void prepareTenPhrase(){
		switch(testType){
			case TestOneLesson:
				tenPhrases = selectedLesson.getRandomTenPhrase();
				break;
			case TestAllLesson:
				tenPhrases = account.getRandomTenPhrase();
				break;
		}
	}
	
	public Phrase getNextPhrase(){
		if(tenPhrases.size() == 0){
			this.prepareTenPhrase();
		}
		int index = currentPhraseIndex +1;
		if(index > tenPhrases.size()-1 || index < 0) return null;
		return tenPhrases.get(++currentPhraseIndex);
	}
	
	public Phrase getPreviousPhrase(){
		int index = currentPhraseIndex -1;
		if(index > tenPhrases.size()-1 || index < 0) return null;
		return tenPhrases.get(--currentPhraseIndex);
	}
	
	private void addActionListener(){
		view.getNextButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				// press 'Finish'
				if(view.getNextButton().getText().equals("Finish")){
					//TODO
					//System.exit(0);
				}
				Phrase p = getNextPhrase();
				if(p == null) return;
				view.updatePhrase(p);
				currentPhrase = p;
				// turn to 'Finish' button
				if(currentPhraseIndex == tenPhrases.size()-1){
					view.getNextButton().setText("Finish");
					view.getPreviousButton().setText("Previous");
				}
				else{
					view.getNextButton().setText("Next");
					view.getPreviousButton().setText("Previous");
				}
			}
		});
		
		view.getPreviousButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Phrase p = getPreviousPhrase();
				if(p == null) return;
				view.updatePhrase(p);
				currentPhrase = p;
				// turn to 'Nothing' button
				if(currentPhraseIndex == 0){
					view.getPreviousButton().setText("Nothing");
					view.getNextButton().setText("Next");
				}
				else{
					view.getPreviousButton().setText("Previous");
					view.getNextButton().setText("Next");
				}
			}
		});

		view.getSoundButton().addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				view.playSound(currentPhrase);	
			}			
		});
		
	}

	public static void main(String[] args) throws Exception {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		Lesson l = new Lesson("Eric", conn, "Lesson_1");
		l.addPharse("你好", "Hello", "New sounds/101.mp3");
		l.addPharse("好梦", "Nice Dream", "New sounds/102.mp3");
		
		new LearnUnit(l);
	}
}
