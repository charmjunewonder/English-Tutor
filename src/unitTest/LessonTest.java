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
		conn = DriverManager.getConnection("jdbc:sqlite:test.db");
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
	public void testLesson() {
		Lesson l = new Lesson("Eric", conn, "Lesson_1");
		assertNotNull("new Lesson should not be null", l);
		assertEquals("lesson should contains correct account name", l.getAccountName(), "Eric");
		assertEquals("lesson should contains correct lesson name", l.getLessonName(), "Lesson_1");
	}

	/**
	 * Test method for {@link Lesson#addPharse(java.lang.String, java.lang.String, java.lang.String)}
	 * and {@link Lesson#getPhrase(int)}.
	 */
	@Test
	public void testAddAndGetPharse() {
		Lesson l = new Lesson("Eric", conn, "Lesson_1");
		l.addPharse("你好", "Hello", null);
		l.addPharse("好梦", "Nice Dream", null);
		Phrase p = l.getPhrase(2);
		assertEquals("check Chinese of the second phrase", p.chinese, "好梦");	
		assertEquals("check English of the second phrase", p.english, "Nice Dream");	
		p = l.getPhrase(1);
		assertEquals("check Chinese of the first phrase", p.chinese, "你好");	
		assertEquals("check English of the first phrase", p.english, "Hello");	
	}


	/**
	 * Test method for {@link Lesson#close()}.
	 */
	@Test
	public void testClose() {
		fail("Not yet implemented");
	}

}
