package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Answer;
import server.AnswerType;
import server.RequestType;

import static org.junit.Assert.*;

/**
 * Test the Answer class
 */
public class AnswerTest{
    private Answer ans;
    @Before
    public void setUp() throws Exception {
        ans = new Answer(RequestType.UNKNOUWN, AnswerType.NON, "testValue", "All right");
    }

    @After
    public void tearDown(){
        ans = null;
    }

    @Test
    public void test_AnswerType_setAnswer() throws Exception {
        assertEquals(AnswerType.NON, ans.getAnswer());

        ans.setAnswer(AnswerType.OK);
        assertEquals(AnswerType.OK, ans.getAnswer());

        ans.setAnswer(AnswerType.FAIL);
        assertEquals(AnswerType.FAIL, ans.getAnswer());
    }

    @Test
    public void test_RequestType_setRequest() throws Exception {
        assertEquals(RequestType.UNKNOUWN, ans.getRequest());

        ans.setRequest(RequestType.ADD);
        assertEquals(RequestType.ADD, ans.getRequest());

        ans.setRequest(RequestType.FIND);
        assertEquals(RequestType.FIND, ans.getRequest());

        ans.setRequest(RequestType.DELETE);
        assertEquals(RequestType.DELETE, ans.getRequest());

        ans.setRequest(RequestType.RMALL);
        assertEquals(RequestType.RMALL, ans.getRequest());

        ans.setRequest(RequestType.LOGIN);
        assertEquals(RequestType.LOGIN, ans.getRequest());

        ans.setRequest(RequestType.LOGOFF);
        assertEquals(RequestType.LOGOFF, ans.getRequest());

        ans.setRequest(RequestType.ADDUSER);
        assertEquals(RequestType.ADDUSER, ans.getRequest());

        ans.setRequest(RequestType.LSUSER);
        assertEquals(RequestType.LSUSER, ans.getRequest());

        ans.setRequest(RequestType.RMUSER);
        assertEquals(RequestType.RMUSER, ans.getRequest());

        ans.setRequest(RequestType.RMUSERS);
        assertEquals(RequestType.RMUSERS, ans.getRequest());
    }

    @Test
    public void test_String_setValue() throws Exception {
        assertEquals("testValue", ans.getValue());

        ans.setValue("someTest");
        assertEquals("someTest", ans.getValue());
    }

    @Test
    public void test_String_setMessage() throws Exception {
        assertEquals("All right", ans.getMessage());

        ans.setMessage("RIGHT!");
        assertEquals("RIGHT!", ans.getMessage());
    }

    @Test
    public void test_void_getAnswer_AnswerType() throws Exception {
        assertEquals(AnswerType.NON, ans.getAnswer());
    }

    @Test
    public void test_void_getRequest_RequestType() throws Exception {
        assertEquals(RequestType.UNKNOUWN, ans.getRequest());
    }

    @Test
    public void test_void_getValue_Sring() throws Exception {
        assertEquals("testValue", ans.getValue());
    }

    @Test
    public void test_void_getMessage_String() throws Exception {
        assertEquals("All right", ans.getMessage());
    }
}