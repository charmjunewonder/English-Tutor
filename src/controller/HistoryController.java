package controller;

import java.util.ArrayList;

import view.HistoryPanel;


import model.Lesson;

public class HistoryController {

    private Lesson selectedLesson;
    private HistoryPanel view;
    
    public HistoryController(Lesson lesson){
	selectedLesson = lesson;
	view = HistoryPanel.getHistoryPanel();
	
	initAllHistory();
    }
    
    public void setSelectedLesson(Lesson l){
	selectedLesson = l;
	initAllHistory();
    }
    
    public void initAllHistory(){
	view.clearHistoryTableContent();
	ArrayList<Integer> scores = selectedLesson.getTestResultScores();
	ArrayList<String> times = selectedLesson.getTestResultTimes();
	int count = times.size();
	for(int i = 0; i < count; ++i){
	    view.addHistory(times.get(i), scores.get(i));
	}
    }
}
