package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import user.UserBox;
import user.UserType;

import static org.junit.Assert.*;

/**
 * The test UserBox class
 */
public class UserBoxTest {
    UserBox userBoxTest;

    @Before
    public void setUp() throws Exception {
        userBoxTest = new UserBox("irishkonv", "1234", UserType.ADMIN);
    }

    @After
    public void tearDown() throws Exception {
        userBoxTest = null;
    }

    @Test
    public void test_void_getLogin_String() throws Exception {
        assertEquals("irishkonv", userBoxTest.getLogin());
    }

    @Test
    public void test_String_setLogin() throws Exception {
        userBoxTest.setLogin("stratopedarx");
        assertEquals("stratopedarx", userBoxTest.getLogin());
    }

    @Test
    public void test_void_getPasswd_String() throws Exception {
        assertEquals("1234", userBoxTest.getPasswd());
    }

    @Test
    public void test_String_setPasswd() throws Exception {
        userBoxTest.setPasswd("555");
        assertEquals("555", userBoxTest.getPasswd());
    }

    @Test
    public void test_void_getUserType_UserType() throws Exception {
        assertEquals(UserType.ADMIN, userBoxTest.getUserType());
    }

    @Test
    public void test_UserType_setUserType() throws Exception {
        userBoxTest.setUserType(UserType.USER);
        assertEquals(UserType.USER, userBoxTest.getUserType());
    }
}