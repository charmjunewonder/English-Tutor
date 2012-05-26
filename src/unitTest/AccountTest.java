/**
 * 
 */
package unitTest;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import code.Account;
import code.Lesson;

/**
 * @author Eric
 * @version 0.1
 */

public class AccountTest {

    //private Connection conn;

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
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link code.Account#Account(java.lang.String)}.
     */
    @Test
    public void testAccount() {
	Account a = new Account("Eric");
	assertNotNull("new Account should not be null", a);
	assertEquals("account should contains correct account name", a.getName(), "Eric");
    }

    /**
     * Test method for {@link code.Account#createNewLesson(java.lang.String)}.
     */
    @Test
    public void testCreateNewLesson() {
	fail("Not yet implemented");
    }

    /**
     * Test method for {@link code.Account#getRandomTenPhrase()}.
     */
    @Test
    public void testGetRandomTenPhrase() {
	fail("Not yet implemented");
    }

}
