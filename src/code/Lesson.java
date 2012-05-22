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
    //private int needToInsertAfterIndex; // indicate where to insert when existing

    // there is no need to use Hash for times and scores for test result, 
    // we do not need to get one according to another, we just need to iterate them.
    private ArrayList<String> testResultTimes;
    private ArrayList<Integer> testResultScores;

    /*private ArrayList<Phrase> deletePhrases;
    private ArrayList<Phrase> addPhrases; // list of phrases that will write to database if save the changes;
    private ArrayList<Phrase> testedOrLearnedPhrases;*/
    private ArrayList<String> newTestResultTimes;
    private ArrayList<Integer> newTestResultScores;

    private boolean isLearnComplete;
    private boolean isTested;

    public Lesson(String accountName, Connection connection, String lessonName) throws Exception{
	this.connection = connection;
	this.lessonName = lessonName;
	this.phraseCount = 0;
	this.accountName = accountName;
	statement = connection.createStatement();
    }

    public void loadDefaultLessons(){
	try{
	    statement.executeUpdate("create table )
	}
    }

    public void loadFromDatabase(){
	try{
	    // phrases
	    statement.executeUpdate("create table if not exists "+lessonName+" (English, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");
	    ResultSet rs = statement.executeQuery("select * from " + lessonName +";");
	    while (rs.next()) {
		//needToInsertAfterIndex++;
		Phrase p = new Phrase(rs.getString("English"), rs.getString("Chinese"), rs.getString("Audio"));
		phrases.add(p);
	    }
	    rs.close();

	    // test results
	    statement.executeUpdate("create table if not exists "+lessonName+"_result (Time, Score);");
	    rs = statement.executeQuery("select * from " + lessonName +"_result ;");
	    while (rs.next()) {
		Phrase p = new Phrase(rs.getString("English"), rs.getString("Chinese"), rs.getString("Audio"));
		testResultTimes.add(rs.getString("Time"));
		testResultScores.add(rs.getInt("Score"));
	    }
	    rs.close(); 
	}catch(Exception e){
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

    public void addPhrase(Phrase p){
	phrases.add(p);
    }

    public void deletePhrase(Phrase p){
	phrases.remove(p);
    }

    /*private void addPhraseToDatabase(Phrase p) throws Exception{
	prep.setString(1, p.getEnglish());
	prep.setString(2, p.getChinese());
	prep.setString(3, p.getAudio());
	prep.addBatch();
	connection.setAutoCommit(false);
	prep.executeBatch();
	connection.setAutoCommit(true);

	phraseCount++;
    }*/

    /*public void deletePhrase(Phrase p){
	deletePhrases.add(p);
    }*/

    /*public Phrase getPhrase(int row) throws Exception{
	Phrase p = null;
	row--;
	ResultSet result = statement.executeQuery(
		"SELECT * FROM "+lessonName+" LIMIT 1 OFFSET "+row+";");
	p = new Phrase(result.getString("English"), result.getString("Chinese"), result.getString("Audio"));
	result.close();
	return p;
    }*/

    public Phrase getPhrase(int row){
	return phrases.get(row);
    }

    public ArrayList<Phrase> getAllPhrases(){
	return phrases;
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
     * This method seems crude to delete the old lesson table and create a new one.
     * We can implement another method that we can create several ArrayList to store the 
     * deleting, adding, editing phrases and optimally write the delete, insert and update
     * statements.
     * But we have to pay attention to the two facts. The first one is that when the user learn 
     * or test the whole lesson, which happens frequently, the whole lesson must be updated,
     * which cost more than deleting all and inserting them back. Secondly, the amount of 
     * phrases of one lesson is no more than a thousand at most time. Rewriting it may cost
     * slightly more. On the other hand, the method of second version gains little, but
     * introduces unnecessary complexity and more likely introduces bugs in the program and
     * difficulty in maintenance.
     */
    public void writeToDatabase(){
	try{
	    // phrase
	    statement.executeUpdate("drop table if exists" + lessonName + ";");
	    statement.executeUpdate("create table "+lessonName+" (English, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");
	    prep = connection.prepareStatement( "insert into "+lessonName+" values (?, ?, ?, ?, ?, ?);");
	    Iterator<Phrase> itr = phrases.iterator();
	    while(itr.hasNext()){
		Phrase p = itr.next();
		prep.setString(1, p.getEnglish());
		prep.setString(2, p.getChinese());
		prep.setString(3, p.getAudio());
		prep.setString(4, p.getLastReviewTime());
		prep.setInt(5, p.getAccuracy());
		prep.setInt(6, p.getTestCount());
		prep.addBatch();
	    }
	    connection.setAutoCommit(false);
	    prep.executeBatch();
	    connection.commit();

	    // result
	    prep = connection.prepareStatement( "insert into "+lessonName+"_result values (?, ?);");
	    Iterator<String> timeItr = newTestResultTimes.iterator();
	    Iterator<Integer> scoreItr = newTestResultScores.iterator();
	    while(timeItr.hasNext() && scoreItr.hasNext()){
		prep.setString(1, timeItr.next());
		prep.setInt(2, scoreItr.next());
		prep.addBatch();
	    }
	    connection.setAutoCommit(false);
	    prep.executeBatch();
	    connection.commit();

	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    public void addTestResult(String time, int score){
	newTestResultTimes.add(time);
	newTestResultScores.add(score);
    }

    public void deleteSelf(){
	try{
	    statement.executeUpdate("drop table " + lessonName + ";");
	    statement.executeUpdate("drop table " + lessonName + "_result;");
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }
}
