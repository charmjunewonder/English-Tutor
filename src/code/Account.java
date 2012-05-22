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

    private ArrayList<Lesson> deleteLesson;
    private Set<String> newLessonNames;

    public Account(String name){
	try{
	    this.name = name;
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection("jdbc:sqlite:data/"+name+".db");
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("create table if not exists lesson_names (Name);");	    
	}		
	catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    public void loadLessonsFromDatabase(){
	Statement statement = connection.createStatement();

	// lessons
	ResultSet rs = statement.executeQuery("select * from lesson_names;");
	while(rs.next()){
	    String lName = rs.getString("Name");
	    Lesson l = new Lesson(lName);
	    l.loadFromDatabase();
	    lesson.add(l);
	    lessonNames.add(lName);
	}
	rs.close();
    }

    public void writeToDatabase(){
	Statement statement = connection.createStatement();

	// lesson names
	Iterator<String> nameItr = newLessonNames.iterator();
	PreparedStatement prep = connection.prepareStatement( "insert into lesson_names values (?);");	
	while(nameItr.hasNext()){
	    prep.setString(1, nameItr.next());
	    prep.addBatch();
	}
	connection.setAutoCommit(false);
	prep.executeBatch();
	connection.commit();

	// delete lessons
	Iterator<Lesson> deleteItr = deleteLesson.iterator();
	while(deleteItr.hasNext()){
	    deleteItr.next().deleteSelf();
	}

	// load lesson to database
	Iterator<Lesson> lessonItr = lessons.iterator();
	while(lessonItr.hasNext()){
	    lessonItr.next().writeToDatabase();
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
	    Lesson l = new Lesson(lessonName, connection, lessonName);
	    addLesson(l);
	    newLessonNames.add(lessonName);
	}
	else{
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
}
