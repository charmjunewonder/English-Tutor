package code;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import code.TestUnit.QuestionType;

public class ResultController {

    private ArrayList<Phrase> wrongPhrases;
    private ResultFrame view;
    private String[][] data;
    private int score;

    public ResultController(ArrayList<Phrase> wrongPhrases, 
	    ArrayList<QuestionType> questionTypes, 
	    ArrayList<String> wrongAnswers, 
	    int score){
	int count = wrongPhrases.size();
	this.wrongPhrases = wrongPhrases;
	this.score = score;
	data = new String[count][4];

	for(int i = 0; i < count; ++i){
	    Phrase p = wrongPhrases.get(i);
	    data[i][0] = p.getLessonName();
	    data[i][2] = wrongAnswers.get(i);
	    switch(questionTypes.get(i)){
	    case ENGLISH:
		data[i][1] = p.getEnglish();
		data[i][3] = p.getChinese();
		break;
	    case CHINESE:
		data[i][1] = p.getChinese();
		data[i][3] = p.getEnglish();
		break;
	    case AUDIO:
		data[i][1] = "AUDIO";
		data[i][3] = p.getEnglish();
	    }
	}


	view = new ResultFrame(data);
	addListener();
	setViewContent();
	view.setVisible(true);
    }

    public void addListener(){
	view.getFinishButton().addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		view.setVisible(false);
		view.dispose();
		MainController main = MainController.getMainController();
		main.getLessonController().initAllPhrases();
		main.getHistoryController().initAllHistory();
		main.setVisible(true);
	    }
	});

	view.addExitingReturnToMainController();
    }

    private void setViewContent(){
	//view.getTitleLabel().setText("Result");
	int wrongCount = wrongPhrases.size();
	view.getScoreLabel().setText("Score: " + score);
	if(wrongCount > 80){
	    view.getSuggestionTwoLabel().setText("Congraduration, you can learn next lesson");
	} else if(wrongCount > 60){
	    view.getSuggestionTwoLabel().setText("You are recommended to retest this lesson");
	} else{
	    view.getSuggestionTwoLabel().setText("You are recommended to relearn this lesson");
	}
    }

}
