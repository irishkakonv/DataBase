package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Box;

import static org.junit.Assert.*;

/**
 * The test of class Box
 */
public class BoxTest {
    private Box box;

    @Before
    public void setUp() throws Exception {
        box = new Box("key", "value");

    }

    @After
    public void tearDown() throws Exception {
        box = null;
    }

    @Test
    public void test_void_getKey_String() throws Exception {
        assertEquals("key", box.getKey());
    }

    @Test
    public void test_void_getValue_String() throws Exception {
        assertEquals("value", box.getValue());
    }

}