/**
 * 
 */
package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Random;

import code.TestUnit.QuestionType;

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
	VERIFY_ANSWER, FINISH_TEST
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

    private QuestionType currentQuestionType;
    private ArrayList<QuestionType> questionTypes;
    private ArrayList<Phrase> wrongPhrases;
    private ArrayList<String> wrongAnswers;

    public TestController(Lesson l){
	view = new TestFrame();
	tenPhrases = new ArrayList<Phrase>();
	questionTypes = new ArrayList<QuestionType>();
	wrongPhrases = new ArrayList<Phrase>();
	wrongAnswers = new ArrayList<String>();

	currentPhraseIndex = -1;
	testType = TestType.TEST_ONE_LESSON;
	testState = TestState.VERIFY_ANSWER;
	selectedLesson = l;
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
	    currentQuestionType = QuestionType.CHINESE;
	    view.getQuestionLabel().setText(p.getChinese());
	    break;
	case 1: // show English
	    currentQuestionType = QuestionType.ENGLISH;
	    view.getQuestionLabel().setText(p.getEnglish());
	    break;
	case 2: // speak English
	    currentQuestionType = QuestionType.AUDIO;
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
	}
	else{
	    // wrong answer
	    //TODO
	    questionTypes.add(currentQuestionType);
	    wrongPhrases.add(currentPhrase);
	    wrongAnswers.add(view.getAnswerTextField().getText());

	    //view.getCorrectAnswerLabel().setText(currentPhrase.getEnglish());
	}
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
	case VERIFY_ANSWER:
	    verifyAnswer();
	    clearComponent();
	    showNextPhrase();
	    if(currentPhraseIndex == tenPhrases.size()-1){
		testState = TestState.FINISH_TEST;
	    }
	    break;
	case FINISH_TEST:
	    // TODO
	    new ResultController(wrongPhrases, questionTypes, wrongAnswers, tenPhrases.size());
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
    }
    
    public static void main(String[] args) throws Exception {

   	Class.forName("org.sqlite.JDBC");
   	Connection conn = DriverManager.getConnection("jdbc:sqlite:data/test.db");
   	Lesson l = new Lesson(conn, 1);

   	new TestController(l);
    }
}
