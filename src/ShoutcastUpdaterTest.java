

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class ShoutcastUpdaterTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class ShoutcastUpdaterTest
{
    private ShoutcastUpdater shoutcas1;

    /**
     * Default constructor for test class ShoutcastUpdaterTest
     */
    public ShoutcastUpdaterTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
        shoutcas1 = new ShoutcastUpdater("engorgio.themediadudes.com:8000", "changeme", "/Users/Anthony/Desktop/SLOF/SLOF.txt");
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
}
