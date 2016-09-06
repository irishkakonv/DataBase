//package server;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Ignore;
//import org.junit.Test;
//
//import java.io.IOException;
//
//import static org.junit.Assert.*;
//
///**
// * The test Server class
// */
//public class ServerTest {
//    private Server server;
//
//
//    @Before
//    public void setUp() throws Exception {
//        server = new Server(4749);
////        server2 = new Server(4749, )
//
//
//
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        server = null;
//    }
//
//    @Test
//    public void start() throws Exception {
//
//
//    }
//
//    @Test(expected = IOException.class)
//    public void test_parseClientCommand_Exception() throws IOException{
//        /** Incorrect format */
//        server.parseClientCommand("ADD=key=value");
//    }
//
//    @Test
//    public void test_parseClientCommand() throws IOException {
//        Request reqTest = new Request(RequestType.ADD, "key", "value");
//
//        server.parseClientCommand("ADD:key=value");
//        reqTest.equals(server.request);
//
//        reqTest.setType(RequestType.DELETE);
//        server.parseClientCommand("DELETE:key=value");
//        reqTest.equals(server.request);
//
//        reqTest.setType(RequestType.FIND);
//        server.parseClientCommand("FIND:key=value");
//        reqTest.equals(server.request);
//
//        reqTest.setType(RequestType.UNKNOUWN);
//        server.parseClientCommand("UNKNOUWN:key=value");
//        reqTest.equals(server.request);
//    }
//
//    @Test(expected = IOException.class)
//    public void test_parseClientCommand_IOException() throws IOException{
//        /** Incorrect format */
//        server.parseClientCommand("ADDD:key=value");
//    }
//
//    @Test(expected = IOException.class)
//    public void test_parseClientCommand_IOException_() throws IOException{
//        /** Incorrect format */
//        server.parseClientCommand("ADD:key");
//    }
//
//
//    @Test
//    public void exec() throws Exception {
//
//    }
//}