package code;

public class Phrase {
	public String chinese;
	public String english;
	public String audio;
	public boolean isLearned;
	public Phrase(String chinese, String english, String audio){
		this.chinese = chinese;
		this.english = english;
		this.audio = audio;
	}
	
	public Phrase(Phrase p){
		chinese = p.chinese;
		english = p.english;
		audio = p.audio;
	}
}
