/**
 * 
 */
package unitTest;
import java.sql.*;

import code.Lesson;
import code.Phrase;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
/**
 * @author charmjunewonder
 *
 */
public class LessonTest {

    private Connection conn;
    //private Statement statement;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {

    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	Class.forName("org.sqlite.JDBC");
	conn = DriverManager.getConnection("jdbc:sqlite:data/test.db");
	//statement = conn.createStatement();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link Lesson#Lesson(java.sql.Connection, int)}.
     */
    @Test
    public void testLesson() throws Exception{
	Lesson l = new Lesson(conn, "Lesson_0");
	assertNotNull("new Lesson should not be null", l);
	assertEquals("lesson should contains correct account name", l.getAccountName(), "Eric");
	assertEquals("lesson should contains correct lesson name", l.getLessonName(), "Lesson_0");
    }

    /**
     * Test method for {@link Lesson#addPharse(java.lang.String, java.lang.String, java.lang.String)}
     * and {@link Lesson#getPhrase(int)}.
     */
    @Test
    public void testAddAndGetPharse() throws Exception{
	Statement statement = conn.createStatement();
	statement.executeUpdate("drop table if exists Lesson_0;");
	Lesson l = new Lesson(conn, "Lesson_0");
	Phrase p = new Phrase("Hello", "你好", null);
	l.addPhrase(p);
	p = new Phrase("Nice Dream", "好梦", null);	
	l.addPhrase(p);
	p = l.getPhrase(2);
	assertEquals("check Chinese of the second phrase", "好梦", p.getChinese());	
	assertEquals("check English of the second phrase", "Nice Dream", p.getEnglish());	
	p = l.getPhrase(1);
	assertEquals("check Chinese of the first phrase", "你好", p.getChinese());	
	assertEquals("check English of the first phrase", "Hello", p.getEnglish());	
    }

    /**
     * Test method for {@link Lesson#getPhrase(int)}.
     *
    @Test
    public void testGetPharse() throws Exception{
	Lesson l = new Lesson("Eric", conn, "Lesson_1");
	Phrase p = l.getPhrase(2);
	assertEquals("check Chinese of the second phrase", "好梦", p.getChinese());	
	assertEquals("check English of the second phrase", "Nice Dream", p.getEnglish());	
	p = l.getPhrase(1);
	assertEquals("check Chinese of the first phrase", "你好", p.getChinese());	
	assertEquals("check English of the first phrase", "Hello", p.getEnglish());
    }*/

    
    @Test
    public void testLoadDefaultLesson() throws Exception{
	Lesson l = new Lesson(conn, "Lesson_1");
	l.loadDefaultLesson(1);
	assertEquals("", "Hello", l.getPhrase(1).getEnglish());
    }

    /**
     * Test method for {@link Lesson#close()}.
     */
    @Test
    public void testClose() {
	fail("Not yet implemented");
    }

}
