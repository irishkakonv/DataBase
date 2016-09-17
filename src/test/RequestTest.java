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
    public void test_RequestType_setType() throws Exception {
        assertEquals(RequestType.UNKNOUWN, request.getType());

        request.setType(RequestType.ADD);
        assertEquals(RequestType.ADD, request.getType());

        request.setType(RequestType.FIND);
        assertEquals(RequestType.FIND, request.getType());

        request.setType(RequestType.DELETE);
        assertEquals(RequestType.DELETE, request.getType());

        request.setType(RequestType.RMALL);
        assertEquals(RequestType.RMALL, request.getType());

        request.setType(RequestType.LOGIN);
        assertEquals(RequestType.LOGIN, request.getType());

        request.setType(RequestType.LOGOFF);
        assertEquals(RequestType.LOGOFF, request.getType());

        request.setType(RequestType.ADDUSER);
        assertEquals(RequestType.ADDUSER, request.getType());

        request.setType(RequestType.LSUSER);
        assertEquals(RequestType.LSUSER, request.getType());

        request.setType(RequestType.RMUSER);
        assertEquals(RequestType.RMUSER, request.getType());

        request.setType(RequestType.RMUSERS);
        assertEquals(RequestType.RMUSERS, request.getType());
    }

    @Test
    public void test_String_setKey() throws Exception {
        assertEquals("key", request.getKey());
    }

    @Test
    public void test_String_setValue() throws Exception {
        assertEquals("value", request.getValue());
    }

    @Test
    public void test_void_getType_RequestType() throws Exception {
        assertEquals(RequestType.UNKNOUWN, request.getType());
    }

    @Test
    public void test_void_getKey_String() throws Exception {
        assertEquals("key", request.getKey());
    }

    @Test
    public void test_void_getValue_String() throws Exception {
        assertEquals("value", request.getValue());
    }

}