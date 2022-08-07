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

    @Test
    public void swanTest() {
        int a = 1;
        int b = 2;
        assertEquals(3,a+b);
    }

    @Test
    public void philipTest() {
        int one = 1;
        int three = 3;
        assertEquals(4,one+three);
    }

    @Test
    public void corentinTest() {
        int a = 9;
        int b = 3;
        assertEquals(12, a+b);
    }
}
