package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import server.*;
import user.UnauthUser;
import user.UserType;

import java.io.*;
import java.util.LinkedList;

import static junit.framework.TestCase.assertEquals;

/**
 * The test Server class
 */
public class ServerTest {
    private Server server;
    private Server server2;
    private Box boxText;
    LinkedList<Box> test;
    private String dbFilePathNotExist = "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\dbb.txt";
    private String dbFilePath2 = "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\testFile2";
    private BufferedReader in;


    @Before
    public void setUp() throws Exception {
        server = new Server(4749, "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\testFile",
                                  "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\usersTest");

        server2 = new Server(4749, dbFilePath2,"C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\usersTest");

        boxText = new Box("key", "value");
    }

    @After
    public void tearDown() throws Exception {
        server = null;
        server2 = null;
        boxText = null;
    }

    /** The method's tests of parseClientCommand() */
    @Test
    public void test_void_parseClientCommand() throws IOException {
        Request request1 = new Request(RequestType.RMALL, "", "");

        server.parseClientCommand("RMALL");
        assertEquals(request1.getType(), server.request.getType());
        assertEquals(request1.getValue(), server.request.getValue());
        assertEquals(request1.getKey(), server.request.getKey());

        request1.setType(RequestType.LOGOFF);
        server.parseClientCommand("LOGOFF");
        assertEquals(request1.getType(), server.request.getType());
        assertEquals(request1.getValue(), server.request.getValue());
        assertEquals(request1.getKey(), server.request.getKey());

        request1.setType(RequestType.LSUSER);
        server.parseClientCommand("LSUSER");
        assertEquals(request1.getType(), server.request.getType());
        assertEquals(request1.getValue(), server.request.getValue());
        assertEquals(request1.getKey(), server.request.getKey());

        request1.setType(RequestType.RMUSERS);
        server.parseClientCommand("RMUSERS");
        assertEquals(request1.getType(), server.request.getType());
        assertEquals(request1.getValue(), server.request.getValue());
        assertEquals(request1.getKey(), server.request.getKey());

        Request reqTest = new Request(RequestType.ADD, "key", "value");

        server.parseClientCommand("ADD:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.LOGIN);
        server.parseClientCommand("LOGIN:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.FIND);
        server.parseClientCommand("FIND:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.DELETE);
        server.parseClientCommand("DELETE:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.RMALL);
        server.parseClientCommand("RMALL:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.UNKNOUWN);
        server.parseClientCommand("UNKNOUWN:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.ADDUSER);
        server.parseClientCommand("ADDUSER:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.RMUSER);
        server.parseClientCommand("RMUSER:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());
    }

    @Test(expected = IOException.class)
    public void test_void_parseClientCommand_Exception() throws IOException{
        /** Incorrect format */
        server.parseClientCommand("ADD=key=value");
    }

    @Test(expected = IOException.class)
    public void test_void_parseClientCommand_IOException() throws IOException{
        /** Incorrect format */
        server.parseClientCommand("ADDD:key=value");
    }

    @Test(expected = IOException.class)
    public void test_void_parseClientCommand_IOException_() throws IOException{
        /** Incorrect format */
        server.parseClientCommand("ADD:key");
    }

    /** The method's tests of handleRequest() */
    @Test
    public void test_void_handleRequest() throws IOException {
        Answer ansTest = new Answer(RequestType.ADD, AnswerType.FAIL, "", "The key sergey already exists");

        Request reqTest = new Request(RequestType.ADD, "key1", "value1");

        server2.request = reqTest;
        server2.handleRequest();

//        server.exec("ADD:key=value");
        assertEquals(ansTest.getRequest(), server2.answer.getRequest());
        assertEquals(ansTest.getAnswer(), server2.answer.getAnswer());

        ansTest.setAnswer(AnswerType.OK);
        reqTest.setKey("key");
        reqTest.setValue("value");
        server2.handleRequest();
        Box boxTest = new Box("key", "value");
        test = new LinkedList<Box>();
        test.add(boxTest);
        server2.data = test;
        assertEquals(boxTest, server2.data.get(0));
        assertEquals(ansTest.getAnswer(), server2.answer.getAnswer());


//         server.exec("FIND:key=value");
        reqTest.setType(RequestType.FIND);
        ansTest.setAnswer(AnswerType.OK);
        server2.handleRequest();
        assertEquals(reqTest.getValue(), server2.answer.getValue());
        assertEquals(ansTest.getAnswer(), server2.answer.getAnswer());

//         server.exec("DELETE:key=value");
        reqTest.setType(RequestType.DELETE);
        ansTest.setAnswer(AnswerType.OK);
        server2.handleRequest();
        assertEquals(ansTest.getAnswer(), server2.answer.getAnswer());

        ansTest.setAnswer(AnswerType.FAIL);
        server2.handleRequest();
        assertEquals(ansTest.getAnswer(), server2.answer.getAnswer());

//        server.exec("RMALL:key=value");
        reqTest.setType(RequestType.RMALL);
        ansTest.setAnswer(AnswerType.OK);
        server2.handleRequest();

        assertEquals(ansTest.getAnswer(), server2.answer.getAnswer());
}

    /** The method's tests of readFile() */
    @Test(expected = RuntimeException.class)
    public void test_void_readFile() throws FileNotFoundException {
        server = new Server(4749,dbFilePathNotExist, "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\users");
        server.readFile();
    }

    /** The method's test of findBox(String key) */
    @Test
    public void test_String_findBox_String() throws Exception {
        server.data.add(boxText);
        assertEquals("value", server.findBox("key"));

        assertEquals("key=value; ", server.findBox("ke"));
    }

    /** The method's test of findBox(String key) */
    @Test(expected = Exception.class)
    public void test_String_findBox_Exception() throws Exception {
        assertEquals("", server.findBox("val"));
    }

    /** The method's test of removeBox() */
    @Test
    public void test_String_removeBox(){
        server.data.add(boxText);
        server.removeBox("key");
        test = new LinkedList<>();
        assertEquals(test, server.data);
    }

    /** The method's test of  keyExists(String key) */
    @Test
    public void test_key_keyExists_boolean() throws FileNotFoundException {
        server.data.add(boxText);

        assertEquals(false, server.keyExists("irinka"));
        assertEquals(true, server.keyExists("key"));
    }

    /** The method's test of exists(File file) */
    @Test(expected = FileNotFoundException.class)
    public void test_file_exists_FileNotFoundException() throws FileNotFoundException {
        /** File does not exist */
        String name = "C:\\Java\\MyProjects\\DataBase\\out\\production\\DataBase\\dbfiles\\bdb.txt";
        File file = new File(name);
        Server.exists(file);
    }
}