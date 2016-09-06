package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Request;
import server.RequestType;

import static org.junit.Assert.assertEquals;

/**
 * Test the Request class
 */
public class RequestTest {
    private Request request;

    @Before
    public void setUp() throws Exception {
        request = new Request(RequestType.UNKNOUWN, "key", "value");
    }

    @After
    public void tearDown() throws Exception {
        request = null;
    }

    @Test
    public void test_setType() throws Exception {
        assertEquals(RequestType.UNKNOUWN, request.getType());

        request.setType(RequestType.ADD);
        assertEquals(RequestType.ADD, request.getType());

        request.setType(RequestType.DELETE);
        assertEquals(RequestType.DELETE, request.getType());

        request.setType(RequestType.FIND);
        assertEquals(RequestType.FIND, request.getType());
    }

    @Test
    public void test_setKey() throws Exception {
        assertEquals("key", request.getKey());
    }

    @Test
    public void test_setValue() throws Exception {
        assertEquals("value", request.getValue());
    }

    @Test
    public void getType() throws Exception {
        assertEquals(RequestType.UNKNOUWN, request.getType());
    }

    @Test
    public void getKey() throws Exception {
        assertEquals("key", request.getKey());
    }

    @Test
    public void getValue() throws Exception {
        assertEquals("value", request.getValue());
    }

}