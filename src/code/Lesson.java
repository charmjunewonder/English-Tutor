package code;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Iterator;
import java.util.HashSet;

public class Lesson {
	private ArrayList<Phrase> phrases;
	private Connection connection;
	private Statement statement;
	private PreparedStatement prep;
	private String accountName;
	private String lessonName;
	private int phraseCount;
	private int needToInsertAfterIndex; // indicate where to insert when existing
	private boolean isLearnComplete;
	private boolean isTested;
	
	public Lesson(String accountName, Connection connection, String lessonName) throws Exception{
		this.connection = connection;
		this.lessonName = lessonName;
		this.phraseCount = 0;
		this.accountName = accountName;
		statement = connection.createStatement();
		statement.executeUpdate("create table if not exists "+lessonName+" (English UNIQUE, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");
		prep = connection.prepareStatement( "insert into "+lessonName+" values (?, ?, ?, ?, ?, ?);");
		
		ResultSet rs = statement.executeQuery("select * from " + lessonName +";");
		while (rs.next()) {
			needToInsertAfterIndex++;
			Phrase p = new Phrase(rs.getString("English"), rs.getString("Chinese"), rs.getString("Audio"));
		}
		rs.close();
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
		try{
			while(itr.hasNext()) {
				Integer element = itr.next();
				phrases.add(getPhrase(element.intValue()));
			} 
		}catch(Exception e){
			System.out.println(e.toString());	
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
	
	public void addPharse(String english, String chinese, String audio) throws Exception{
		prep.setString(1, english);
		prep.setString(2, chinese);
		prep.setString(3, audio);
		prep.addBatch();
		connection.setAutoCommit(false);
		prep.executeBatch();
		connection.setAutoCommit(true);

		phraseCount++;
	}
	
	public void addPhrase(Phrase p) throws Exception{
		prep.setString(1, p.getEnglish());
		prep.setString(2, p.getChinese());
		prep.setString(3, p.getAudio());
		prep.addBatch();
		connection.setAutoCommit(false);
		prep.executeBatch();
		connection.setAutoCommit(true);

		phraseCount++;
	}
	
	public Phrase getPhrase(int row) throws Exception{
		Phrase p = null;
		row--;
		ResultSet result = statement.executeQuery(
				"SELECT * FROM "+lessonName+" LIMIT 1 OFFSET "+row+";");
		p = new Phrase(result.getString("English"), result.getString("Chinese"), result.getString("Audio"));
		result.close();
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

			
}
