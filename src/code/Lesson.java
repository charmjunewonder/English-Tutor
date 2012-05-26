package code;

/**
 * @author Eric
 * @version 0.1
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

public class Lesson {
    private ArrayList<Phrase> phrases;
    private Connection connection;
    private Statement statement;
    private PreparedStatement prep;
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

    //private boolean isLearnComplete;
    //private boolean isTested;
    private boolean isNeededToRestore;

    public Lesson(Connection connection, String lessonName) throws Exception{
	this.connection = connection;
	this.lessonName = lessonName;
	this.phraseCount = 0;

	phrases = new ArrayList<Phrase>();
	testResultTimes = new ArrayList<String>();
	testResultScores = new ArrayList<Integer>();
	newTestResultTimes = new ArrayList<String>();
	newTestResultScores = new ArrayList<Integer>();

	statement = connection.createStatement();
	statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+lessonName+" (English, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");
    }

    // load default lesson_index
    public Lesson(Connection connection, int index) throws Exception{
	this.connection = connection;
	this.phraseCount = 0;

	phrases = new ArrayList<Phrase>();
	testResultTimes = new ArrayList<String>();
	testResultScores = new ArrayList<Integer>();
	newTestResultTimes = new ArrayList<String>();
	newTestResultScores = new ArrayList<Integer>();

	statement = connection.createStatement();
	loadDefaultLesson(1);
    }

    public void loadDefaultLesson(int index){
	try{
	    //statement.executeUpdate("create table if not exists Lesson_" + index + "(English, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");	
	    prep = connection.prepareStatement( "INSERT INTO "+lessonName+" VALUES (?, ?, ?, ?, ?, ?);");

	    BufferedReader br = new BufferedReader(new FileReader("data/DefaultLessons/Lesson_" + index + ".csv"));
	    String line;
	    while ( (line=br.readLine()) != null)
	    {
		String[] values = line.split(",");    //separator

		Phrase p = new Phrase(values[0], values[1], values[2]);
		phrases.add(p);
		//Use a PeparedStatemant, it?s easier and safer 
		prep.setString(1, values[0]);
		prep.setString(2, values[1]);
		prep.setString(3, values[2]);
		prep.addBatch();

	    }
	    br.close();

	    connection.setAutoCommit(false);
	    prep.executeBatch();
	    connection.commit();

	    lessonName = "Lesson_" + index;
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    public boolean isNeededToRestore() {
        return isNeededToRestore;
    }

    public void setNeededToRestore(boolean isNeededToRestore) {
        this.isNeededToRestore = isNeededToRestore;
    }

    public void loadFromDatabase(){
	try{
	    // phrases
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+lessonName+" (English, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");
	    ResultSet rs = statement.executeQuery("SELECT * FROM " + lessonName +";");
	    while (rs.next()) {
		Phrase p = new Phrase(rs.getString("English"), rs.getString("Chinese"), rs.getString("Audio"));
		phrases.add(p);
	    }
	    rs.close();

	    // test results
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS "+lessonName+"_result (Time, Score);");
	    rs = statement.executeQuery("SELECT * FROM " + lessonName +"_result ;");
	    while (rs.next()) {
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
	prep = connection.prepareStatement( "INSERT INTO "+lessonName+" VALUES (?, ?, ?, ?, ?, ?);");
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
	return phrases.get(row-1);
    }

    public ArrayList<Phrase> getAllPhrases(){
	return phrases;
    }

    public void close(){
	try{
	    connection.close();
	} catch(Exception e){
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
	    statement.executeUpdate("DROP TABLE IF EXISTS" + lessonName + ";");
	    statement.executeUpdate("CREATE TABLE "+lessonName+" (English, Chinese, Audio, LastReviewTime, Accuracy, TestCount);");
	    prep = connection.prepareStatement("INSERT INTO "+lessonName+" VALUES (?, ?, ?, ?, ?, ?);");
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
	    prep = connection.prepareStatement( "INSERT INTO "+lessonName+"_result VALUES (?, ?);");
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
	    statement.executeUpdate("DROP TABLE " + lessonName + ";");
	    statement.executeUpdate("DROP TABLE " + lessonName + "_result;");
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }
}
