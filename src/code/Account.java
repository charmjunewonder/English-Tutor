package code;
import java.sql.*;
import java.util.ArrayList;
import java.util.Set;
import java.util.Random;

public class Account {
	private Connection connection;
	private String name;
	private ArrayList<Lesson> lessons;
	private Set<String> lessonNames;

	public Account(String name){
		try{
			this.name = name;
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:data/"+name+".db");
		}		
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	private void addLesson(Lesson lesson){
		lessons.add(lesson);
	}

	public void deleteLesson(Lesson l){
		lessons.remove(l);
	}

	public void createNewLesson(String lessonName) throws Exception{
		if (lessonNames.add(lessonName)){
			Lesson l = new Lesson(name, connection, lessonName);
			addLesson(l);
		}
		else{
			throw new Exception("This lesson name exists, please use another name");
		}
	}

	public Connection getDatabaseConnection(){
		return connection;
	}

	public ArrayList<Phrase> getRandomTenPhrase(){
		ArrayList<Phrase> phrases = new ArrayList<Phrase>();
		Random random = new Random();
		
		int count = 0;
		int lessonCount = lessons.size();
		for(int i = 0; i < lessonCount; ++i){
			count += lessons.get(i).getPhraseCount();
		}
		if(count>10){
			count = 10;
		}
		
		for(int i = 0; i < count; ++i){
			int lessonRandomNum = random.nextInt() % count;
			Lesson l = lessons.get(lessonRandomNum);
			int phraseRandomNum = random.nextInt() % l.getPhraseCount();
			phrases.add(l.getPhrase(phraseRandomNum));
		}
		return phrases;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	public String toString(){
		return name;
	}
}
