package code;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class Account {
    private Connection connection;
    private String name;
    private ArrayList<Lesson> lessons;
    private Set<String> lessonNames;

    private ArrayList<Lesson> deleteLesson;
    private Set<String> newLessonNames;

    public Account(String name){
	try{
	    this.name = name;
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection("jdbc:sqlite:data/"+name+".db");
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS lesson_names (Name);");	    
	}		
	catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    public void loadDefaultLessons(){
	try{
	    for(int i = 1; i <= 18; ++i){
		Lesson l = new Lesson(connection, i);
		lessons.add(l);
		lessonNames.add(l.getLessonName());
	    }
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }    

    public void loadLessonsFromDatabase(){
	try{
	    Statement statement = connection.createStatement();

	    // lessons
	    ResultSet rs = statement.executeQuery("SELECT * FROM lesson_names;");
	    while(rs.next()){
		String lName = rs.getString("Name");
		Lesson l = new Lesson(connection,  lName);
		l.loadFromDatabase();
		lessons.add(l);
		lessonNames.add(lName);
	    }
	    rs.close();
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    public void writeToDatabase(){
	try{
	    // lesson names
	    Iterator<String> nameItr = newLessonNames.iterator();
	    PreparedStatement prep = connection.prepareStatement("INSERT INTO lesson_names VALUES (?);");	
	    while(nameItr.hasNext()){
		prep.setString(1, nameItr.next());
		prep.addBatch();
	    }
	    connection.setAutoCommit(false);
	    prep.executeBatch();
	    connection.commit();

	    // delete lessons
	    Iterator<Lesson> deleteItr = deleteLesson.iterator();
	    Statement statement = connection.createStatement();
	    while(deleteItr.hasNext()){
		Lesson l = deleteItr.next();
		l.deleteSelf();
		statement.executeUpdate("DELETE FROM lesson_names WHERE Name = " + l.getLessonName() + ";");
	    }

	    // load lesson to database
	    Iterator<Lesson> lessonItr = lessons.iterator();
	    while(lessonItr.hasNext()){
		Lesson l = lessonItr.next();
		if(l.isNeededToRestore()){
		    l.writeToDatabase();
		}
	    }
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    private void addLesson(Lesson lesson){
	lessons.add(lesson);
    }

    public void deleteLesson(Lesson l){
	lessons.remove(l);
	deleteLesson.add(l);
    }

    public void createNewLesson(String lessonName) throws Exception{
	if (lessonNames.add(lessonName)){
	    Lesson l = new Lesson(connection, lessonName);
	    addLesson(l);
	    newLessonNames.add(lessonName);
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

    public void deleteSelf(){
	try{
	    connection.createStatement().executeUpdate("drop table lesson_names;");
	    int count = lessons.size();
	    for(int i = 0; i < count; ++i){
		Lesson l = lessons.get(i);
		l.deleteSelf();
	    }
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }
}
