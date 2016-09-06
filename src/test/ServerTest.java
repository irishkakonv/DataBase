package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import server.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static junit.framework.TestCase.assertEquals;

/**
 * The test Server class
 */
public class ServerTest {
    private Server server;


    @Before
    public void setUp() throws Exception {
        server = new Server(4749);
        server.dbFilePath = "/home/stratopedarx/Java/Projects/DataBase/src/dbfiles/testFile";
//        server2 = new Server(4749, )

    }

    @After
    public void tearDown() throws Exception {
        server = null;
    }

    @Test
    public void start() throws Exception {


    }


    /** The method's tests of parseClientCommand() */
    @Test
    public void test_void_parseClientCommand() throws IOException {
        Request reqTest = new Request(RequestType.ADD, "key", "value");

        server.parseClientCommand("ADD:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.DELETE);
        server.parseClientCommand("DELETE:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.FIND);
        server.parseClientCommand("FIND:key=value");
        assertEquals(reqTest.getType(), server.request.getType());
        assertEquals(reqTest.getKey(), server.request.getKey());
        assertEquals(reqTest.getValue(), server.request.getValue());

        reqTest.setType(RequestType.UNKNOUWN);
        server.parseClientCommand("UNKNOUWN:key=value");
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


    /** The method's test of exists(File file) */
    @Ignore
    @Test(expected = FileNotFoundException.class)
    public void test_file_exists_FileNotFoundException() throws FileNotFoundException {
        /** File does not exist */
        String name = "C:\\Java\\MyProjects\\DataBase\\out\\production\\DataBase\\dbfiles\\bdb.txt";
        File file = new File(name);
        Server.exists(file);
    }


    /** The method's tests of readFile() */
    @Ignore
    @Test
    public void test_void_readFile() throws FileNotFoundException {
        Box box = new Box("irina", "konv");
        server.readFile();
        assertEquals(box.getKey(), server.data.get(0).getKey());
        assertEquals(box.getValue(), server.data.get(0).getValue());

        Box box1 = new Box("sergey", "lobanov");
        server.readFile();
        assertEquals(box1.getKey(), server.data.get(1).getKey());
        assertEquals(box1.getValue(), server.data.get(1).getValue());
    }

    /** The method's test of  keyExists(String key) */
    @Ignore
    @Test
    public void test_key_keyExists_boolean() throws FileNotFoundException {
        server.readFile();
        assertEquals(false, server.keyExists("irinka"));

        assertEquals(true, server.keyExists("sergey"));
    }

    /** The method's tests of handleRequest() */
    @Ignore
    @Test
    public void test_void_handleRequest() {
        Answer ansTest = new Answer(RequestType.ADD, AnswerType.FAIL, "", "The key sergey already exists");

        server.exec("ADD:sergey=lobanov");
        assertEquals(ansTest.getRequest(), server.answer.getRequest());
        assertEquals(ansTest.getAnswer(), server.answer.getAnswer());
        assertEquals(ansTest.getMessage(), server.answer.getMessage());
    }




    @Test
    public void exec() throws Exception {

    }
}