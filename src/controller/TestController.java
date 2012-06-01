/**
 * 
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import program.SoundEngine;

import model.Account;
import model.Lesson;
import model.Phrase;
import view.TestFrame;

/**
 * @author charmjunewonder
 *
 */
public class TestController {
    public static final int ENGLISH_QUESTION_TYPE = 284;
    public static final int CHINESE_QUESTION_TYPE = 230;
    public static final int AUDIO_QUESTION_TYPE = 273;

    private enum TestType{
	TEST_ONE_LESSON, TEST_ALL_LESSONS
    }
    private enum TestState{
	NEXT_PHRASE, FINISH_TEST
    }

    private TestFrame view;
    private TestType testType;
    private TestState testState;
    private Lesson selectedLesson;
    private int currentPhraseIndex;
    private int totalCorrectPhraseNum;
    private Account account;
    private ArrayList<Phrase> tenPhrases;
    private Phrase currentPhrase;
    private SoundEngine soundEngine;

    private int currentQuestionType;
    private ArrayList<Integer> questionTypes;
    private ArrayList<Phrase> wrongPhrases;
    private ArrayList<String> wrongAnswers;

    public TestController(Lesson l){
	testType = TestType.TEST_ONE_LESSON;
	selectedLesson = l;
	initialize();
    }

    public TestController(Account a){
	testType = TestType.TEST_ALL_LESSONS;
	account = a;
	initialize();
    }

    private void initialize(){
	view = new TestFrame();
	tenPhrases = new ArrayList<Phrase>();
	questionTypes = new ArrayList<Integer>();
	wrongPhrases = new ArrayList<Phrase>();
	wrongAnswers = new ArrayList<String>();

	currentPhraseIndex = -1;
	testState = TestState.NEXT_PHRASE;
	soundEngine = new SoundEngine();
	addActionListener();

	view.setVisible(true);
	showNextPhrase();
    }

    public void prepareTenPhrase(){
	switch(testType){
	case TEST_ONE_LESSON:
	    tenPhrases = selectedLesson.getRandomTenPhrase();
	    break;
	case TEST_ALL_LESSONS:
	    tenPhrases = account.getRandomTenPhrase();
	    break;
	}
    }

    public void showNextPhrase(){
	if(tenPhrases.size() == 0){
	    this.prepareTenPhrase();
	}
	int index = currentPhraseIndex +1;
	if(index > tenPhrases.size()-1 || index < 0) return;

	Phrase p = tenPhrases.get(++currentPhraseIndex);
	if(p == null)	return;

	currentPhrase = p;

	Random ran = new Random();
	int rnum = ran.nextInt(3);
	switch(rnum){
	case 0: // show Chinese
	    currentQuestionType = TestController.CHINESE_QUESTION_TYPE;
	    view.getQuestionLabel().setText(p.getChinese());
	    break;
	case 1: // show English
	    currentQuestionType = TestController.ENGLISH_QUESTION_TYPE;
	    view.getQuestionLabel().setText(p.getEnglish());
	    break;
	case 2: // speak English
	    currentQuestionType = TestController.AUDIO_QUESTION_TYPE;
	    playSound(p);
	    break;
	}
    }

    private void clearComponent(){
	//view.getCorrectAnswerLabel().setText("");
	view.getQuestionLabel().setText("");
	view.getAnswerTextField().setText("");
    }

    private void verifyAnswer(){
	//testState = TestState.NEXT_PHRASE;
	if(currentPhrase.getEnglish().equals(view.getAnswerTextField().getText())){
	    // correct answer
	    totalCorrectPhraseNum++;
	    // TODO add some correct response
	    //pressNextButton();
	    currentPhrase.increaseAccuracy();
	}
	else{
	    // wrong answer
	    //TODO
	    questionTypes.add(currentQuestionType);
	    wrongPhrases.add(currentPhrase);
	    wrongAnswers.add(view.getAnswerTextField().getText());

	    currentPhrase.decreaseAccuracy();
	    //view.getCorrectAnswerLabel().setText(currentPhrase.getEnglish());
	}
	DateFormat dateFormat = new SimpleDateFormat("/MM/dd/yy");
	Date date = new Date();
	String dateString = dateFormat.format(date);
	currentPhrase.setLastReviewTime(dateString);

    }

    public void updateViewPhrase(Phrase p){
	view.getAnswerTextField().setText(p.getChinese());
    }	

    public void playSound(Phrase p){
	soundEngine.playSound(p.getAudio());
    }	

    /**
     * 
     */
    private void pressNextButton(){
	switch(testState){
	case NEXT_PHRASE:
	    verifyAnswer();
	    clearComponent();
	    showNextPhrase();
	    if(currentPhraseIndex == tenPhrases.size()-1){
		testState = TestState.FINISH_TEST;
	    }
	    break;
	case FINISH_TEST:
	    // TODO
	    verifyAnswer();
	    DateFormat dateFormat = new SimpleDateFormat("/MM/dd/yy");
	    Date date = new Date();
	    String dateString = dateFormat.format(date);
	    int score = (int) ((totalCorrectPhraseNum * 1.0 / tenPhrases.size()) * 100);
	    selectedLesson.addTestResult(dateString, score);
	    if(score >= 80){
		MainController.getMainController().increaseEnabeLessonIndex();
		this.selectedLesson.setEnabled(true);
	    }
	    //System.out.println(score);
	    new ResultController(wrongPhrases, questionTypes, wrongAnswers, score);
	    view.setVisible(false); //you can't see me!
	    view.dispose(); //Destroy the JFrame object
	    break;
	}
    }

    private void addActionListener(){
	view.getNextButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		pressNextButton();
	    }
	});


	view.getSoundButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		playSound(currentPhrase);	
	    }			
	});

	view.addExitingReturnToMainController();
    }

    public static void main(String[] args) throws Exception {

	Class.forName("org.sqlite.JDBC");
	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/test.db");
	Lesson l = new Lesson(conn, 1);

	new TestController(l);
    }
}
