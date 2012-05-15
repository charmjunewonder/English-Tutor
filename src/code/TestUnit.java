package code;

import java.util.ArrayList;

public class TestUnit {

	private Account acccont;
	private Lesson selectedLesson;

	public TestUnit(){
	}

	public void Test(Lesson lesson){
		selectedLesson = lesson;
		ArrayList<Phrase> phrases = lesson.getRandomTenPhrase();
	}

	public void TestAll(){
		ArrayList<Phrase> phrases = acccont.getRandomTenPhrase();
	}

}
