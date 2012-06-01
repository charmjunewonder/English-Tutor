package model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.io.FilenameUtils;

import program.InvalidFileNameException;


/**
 * @author Eric
 * @version 0.1
 */

public class Account {
    private Connection connection;
    private String name;
    private ArrayList<Lesson> lessons;
    private HashSet<String> lessonNames;

    //private ArrayList<Lesson> deleteLesson;
    //private ArrayList<String> newLessonNames;

    private boolean isModify;

    public Account(String name) 
	    throws InvalidFileNameException{

	if(name.contains("\\") ||
		name.contains("/") ||
		name.contains(":") ||
		name.contains("*") ||
		name.contains("?") ||
		name.contains("\"") ||
		name.contains("<") ||
		name.contains(">") ||
		name.contains("|") ||
		name.contains("?") ){
	    throw new InvalidFileNameException();
	}

	this.name = name;
	lessons = new ArrayList<Lesson>();
	lessonNames = new HashSet<String>();
	//deleteLesson = new ArrayList<Lesson>();
	//newLessonNames = new ArrayList<String>();

	try{
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection("jdbc:sqlite:" + 
		    FilenameUtils.separatorsToSystem("data/"+name+".db"));
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS lesson_names (Name UNIQUE, Enable);");
	    //statement.executeUpdate("CREATE TABLE IF NOT EXISTS lesson_enable (Enable);");
	}		
	catch(Exception e){
	    e.printStackTrace();
	}
    }

    /**
     * @return the isModify
     */
    public boolean isModify() {
	for(Lesson l : lessons){
	    if(l.isNeededToRestore()){
		return true;
	    }
	}
	return isModify;
    }

    /**
     * @param isModify the isModify to set
     */
    public void setModify(boolean isModify) {
	this.isModify = isModify;
    }

    public void loadDefaultLessons(){
	try{
	    for(int i = 1; i <= 18; ++i){
		Lesson l = new Lesson(connection, i);
		lessons.add(l);
		lessonNames.add(l.getLessonName());
		//newLessonNames.add(l.getLessonName());
	    }
	    writeToDatabase();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }    

    public void loadLessonsFromDatabase(){
	try{
	    Statement statement = connection.createStatement();

	    // lessons
	    ResultSet rs = statement.executeQuery("SELECT * FROM lesson_names;");
	    while(rs.next()){
		String lName = rs.getString("Name");
		boolean bool = rs.getBoolean("Enable");
		Lesson l = new Lesson(connection, lName, bool);
		l.loadFromDatabase();
		lessons.add(l);
		lessonNames.add(lName);
	    }
	    rs.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    public Lesson getLesson(int index){
	return lessons.get(index);
    }

    public void writeToDatabase(){
	try{
	    // lesson names
	    connection.createStatement().executeUpdate("drop table lesson_names;");
	    connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS lesson_names (Name UNIQUE, Enable);");
	    int count = lessons.size();
	    PreparedStatement prep = connection.prepareStatement("INSERT INTO lesson_names VALUES (?, ?);");
	    int index = 0;
	    for(int i = 0; i < count; ++i){
		prep.setString(1, lessons.get(index).getLessonName());
		//boolean b = lessons.get(index).isEnabled();
		prep.setBoolean(2, lessons.get(index++).isEnabled());
		prep.addBatch();
	    }
	    connection.setAutoCommit(false);
	    prep.executeBatch();
	    connection.commit();

	    // load lesson to database
	    Iterator<Lesson> lessonItr = lessons.iterator();
	    while(lessonItr.hasNext()){
		Lesson l = lessonItr.next();
		if(l.isNeededToRestore()){
		    l.writeToDatabase();
		}
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    private void addLesson(Lesson lesson){
	lessons.add(lesson);
    }

    public void deleteLesson(Lesson l){
	isModify = true;
	lessons.remove(l);
	//deleteLesson.add(l);
    }

    public void deleteLesson(int index){
	Lesson l = getLesson(index);
	deleteLesson(l);
    }

    public void createNewLesson(String lessonName) throws Exception{
	if (lessonNames.add(lessonName)){
	    isModify = true;
	    Lesson l = new Lesson(connection, lessonName, false);
	    addLesson(l);
	    //newLessonNames.add(lessonName);
	} else{
	    throw new Exception("This lesson name exists, please use another name");
	}
    }

    public Connection getDatabaseConnection(){
	return connection;
    }

    public ArrayList<Lesson> getAllLesson(){
	return lessons;
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

	for(int i = 0; i < count; ++i){ // choose random phrase in random lesson
	    int phraseCountOfLesson = 0;
	    Lesson l = null;
	    while(phraseCountOfLesson <= 0){
		int lessonRandomNum = random.nextInt(lessonCount);
		l = lessons.get(lessonRandomNum);
		phraseCountOfLesson = l.getPhraseCount();
	    }
	    l.setNeededToRestore(true);
	    int phraseRandomNum = random.nextInt(phraseCountOfLesson);
	    Phrase p = l.getPhrase(phraseRandomNum);
	    p.setLessonName(l.getLessonName());
	    phrases.add(p);
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

    public void deleteSelf(){
	try{
	    connection.createStatement().executeUpdate("drop table lesson_names;");
	    int count = lessons.size();
	    for(int i = 0; i < count; ++i){
		Lesson l = lessons.get(i);
		l.deleteSelf();
	    }
	}catch(Exception e){
	    e.printStackTrace();
	}
    }
}
