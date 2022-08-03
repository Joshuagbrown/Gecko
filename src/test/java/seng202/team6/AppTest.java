package seng202.team6;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test for simple App, default from Maven
 */
public class AppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void testApp()
    {
        assertTrue( true );
    }
    @Test
    public void joshTest()
    {
        int a = 1;
        int b =2;
        assertEquals(3,a+b);
    }

    @Test
    public void taraTest() {
        int a = 7;
        int b = 8;
        assertEquals(15,a+b);
    }
}
