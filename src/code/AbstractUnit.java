package code;

import java.util.ArrayList;

public abstract class AbstractUnit {
    protected Lesson selectedLesson;
    protected int currentPhraseIndex;
    protected Account account;
    protected ArrayList<Phrase> tenPhrases;
    protected Phrase currentPhrase;

    public AbstractUnit(Lesson l){
	tenPhrases = new ArrayList<Phrase>();
	currentPhraseIndex = -1;
	selectedLesson = l;
    }

    //public abstract void se();
    public void setSelectedLesson(Lesson lesson){
	selectedLesson = lesson;
    }
}
