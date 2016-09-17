package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Request;
import server.RequestType;
import user.Main;
import user.UserBox;
import user.UserType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.*;

/**
 * The test of class Main
 */
public class MainTest {
    Main mainTest;
    UserBox userBoxTest;
    private String loginTest = "irishkakonv";
    private String passwdTest = "1111";
    private UserType userTypeTest = UserType.USER;
    public String userFilePathTest = "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\usersTest";
    public String userFilePathTest2 = "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\usersTest2";
    public String userFilePathTestNotExists = "C:\\Java\\MyProjects\\DataBase\\src\\dbfiles\\usersTests";
    private LinkedList<UserBox> usersDataTest;

    @Before
    public void setUp() throws Exception {
        mainTest = new Main(userFilePathTest);
        userBoxTest = new UserBox("bobik", "3333", UserType.USER);
    }

    @After
    public void tearDown() throws Exception {
        mainTest = null;
        userBoxTest = null;
    }

    @Test
    public void test_void_getLogin_String() throws Exception {
        mainTest.setLogin(loginTest);
        assertEquals(loginTest, mainTest.getLogin());
    }

    @Test
    public void test_String_setLogin() throws Exception {
        loginTest = "stratopedarx";
        mainTest.setLogin(loginTest);
        assertEquals(loginTest, mainTest.getLogin());
    }

    @Test
    public void test_void_getPasswd_String() throws Exception {
        mainTest.setPasswd(passwdTest);
        assertEquals(passwdTest, mainTest.getPasswd());
    }

    @Test
    public void test_String_setPasswd() throws Exception {
        passwdTest = "1234";
        mainTest.setPasswd(passwdTest);
        assertEquals(passwdTest, mainTest.getPasswd());
    }

    @Test
    public void test_void_getUserType_UserType() throws Exception {
        mainTest.setUserType(userTypeTest);
        assertEquals(userTypeTest, mainTest.getUserType());
    }

    @Test
    public void test_UserType_setUserType() throws Exception {
        userTypeTest = UserType.ADMIN;
        mainTest.setUserType(userTypeTest);
        assertEquals(userTypeTest, mainTest.getUserType());
    }

    @Test
    public void test_String_mapUserType_UserType() throws Exception {
        assertEquals(UserType.USER, mainTest.mapUserType("user"));

        assertEquals(UserType.ADMIN, mainTest.mapUserType("admin"));
    }

    @Test
    public void test_UserType_mapStrUserType_String() throws Exception {
        assertEquals("user", mainTest.mapStrUserType(UserType.USER));

        assertEquals("admin", mainTest.mapStrUserType(UserType.ADMIN));
    }

    @Test(expected = RuntimeException.class)
    public void test_void_readFile_FileNotFoundException() throws Exception {
        Main mainTestFileNotExists = new Main(userFilePathTestNotExists);
        mainTestFileNotExists.readFile();
    }

    @Test(expected = RuntimeException.class)
    public void test_void_readFile_RuntimeException() throws Exception {
        mainTest.readFile();
    }

    @Test(expected = IOException.class)
    public void test_String_String_checkUser_IOException() throws Exception {
        mainTest.usersData = new LinkedList<>();
        mainTest.usersData.add(userBoxTest);
        mainTest.checkUser("bobik", "222");
    }


    @Test
    public void test_String_userExists_boolean() throws Exception {
        mainTest.usersData = new LinkedList<>();
        mainTest.usersData.add(userBoxTest);

        assertEquals(true, mainTest.userExists("bobik"));

        assertEquals(false, mainTest.userExists("bobikaaa"));
    }

    @Test
    public void test_Request_handleAdminCommand_String() throws Exception {
        Main mainTest2 = new Main(userFilePathTest2);
        UserBox userBoxTest2 = new UserBox("fish", "4444", UserType.USER);
        UserBox userBoxTest3 = new UserBox("irishkakonv", "1234", UserType.ADMIN);
        mainTest2.usersData = new LinkedList<>();
        mainTest2.usersData.add(userBoxTest);
        mainTest2.usersData.add(userBoxTest2);


        Request requestTest = new Request(RequestType.ADDUSER, "vasya", "1111");
        assertEquals("ADDUSER OK", mainTest2.handleAdminCommand(requestTest));
        assertEquals("ADDUSER FAIL",  mainTest2.handleAdminCommand(requestTest));

        Request requestTest1 = new Request(RequestType.RMUSER, "vasya", "1111");
        assertEquals("RMUSER OK", mainTest2.handleAdminCommand(requestTest1));
        assertEquals("RMUSER FAIL",  mainTest2.handleAdminCommand(requestTest1));

        Request requestTest2 = new Request(RequestType.LSUSER, "vasya", "1111");
        assertEquals("LSUSER OK bobik 3333; fish 4444; ", mainTest2.handleAdminCommand(requestTest2));

        Request requestTest3 = new Request(RequestType.RMUSERS, "vasya", "1111");
        assertEquals("RMUSERS OK", mainTest2.handleAdminCommand(requestTest3));
    }

    @Test(expected = IOException.class)
    public void test_Request_handleAdminCommand_IOException() throws IOException {
        Request requestTest = new Request(RequestType.FIND, "vasya", "1111");
        mainTest.handleAdminCommand(requestTest);
    }

}