package code;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.HashSet;

public class Lesson {
	private Connection connection;
	private Statement statement;
	private PreparedStatement prep;
	private String accountName;
	private String lessonName;
	private int phraseCount;
	private boolean isLearnComplete;
	private boolean isTested;
	
	public Lesson(String accountName, Connection connection, String lessonName){
		try{
			this.connection = connection;
			this.lessonName = lessonName;
			this.phraseCount = 0;
			this.accountName = accountName;
			statement = connection.createStatement();
			statement.executeUpdate("drop table if exists "+accountName+"_"+lessonName+";");
			statement.executeUpdate("create table "+accountName+"_"+lessonName+" (Chinese, English, Audio, isLearned);");
			prep = connection.prepareStatement( "insert into "+accountName+"_"+lessonName+" values (?, ?, ?, ?);");
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public int getPhraseCount(){
		return phraseCount;
	}

	public ArrayList<Phrase> getRandomTenPhrase(){
		ArrayList<Phrase> phrases = new ArrayList<Phrase>();
		
		int count = phraseCount;
		if(phraseCount>10){
			count = 10;
		}

		HashSet<Integer> randomNums = getRandomNumSet(count, phraseCount);
		Iterator<Integer> itr = randomNums.iterator(); 
		while(itr.hasNext()) {
			Integer element = itr.next();
			phrases.add(getPhrase(element.intValue()));
		} 

		return phrases;
	}
	
	public HashSet<Integer> getRandomNumSet(int num, int range){
		HashSet<Integer> randomNums = new HashSet<Integer>(num);
		Random random = new Random();
		while(randomNums.size() < num){
			int randomNum = random.nextInt(range) + 1;
			if(randomNum < 0) randomNum = -randomNum;
			randomNums.add(new Integer(randomNum));
		}
		return randomNums;
	}
	
	public void addPharse(String chinese, String english, String audio, boolean isLearned){
		try{
			prep.setString(1, chinese);
			prep.setString(2, english);
			prep.setString(3, audio);
			prep.setBoolean(4, isLearned);
			prep.addBatch();
			connection.setAutoCommit(false);
			prep.executeBatch();
			connection.setAutoCommit(true);

			phraseCount++;
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public void addPhrase(Phrase p){
		try{
			prep.setString(1, p.chinese);
			prep.setString(2, p.english);
			prep.setString(3, p.audio);
			prep.setBoolean(4, p.isLearned);
			prep.addBatch();
			connection.setAutoCommit(false);
			prep.executeBatch();
			connection.setAutoCommit(true);

			phraseCount++;
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}
	
	public Phrase getPhrase(int row){
		Phrase p = null;
		try{
			row--;
			ResultSet result = statement.executeQuery(
					"SELECT * FROM "+accountName+"_"+lessonName+" LIMIT 1 OFFSET "+row+";");
			p = new Phrase(result.getString("Chinese"), result.getString("English"), result.getString("Audio"));
			result.close();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		return p;
	}
	
	public void close(){
		try{
			connection.close();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
	}

	/**
	 * @return the lessonName
	 */
	public String getLessonName() {
		return lessonName;
	}

	/**
	 * @param lessonName the lessonName to set
	 */
	public void setLessonName(String lessonName) {
		this.lessonName = lessonName;
	}

	/**
	 * @return the accountName
	 */
	public String getAccountName() {
		return accountName;
	}
}
			
