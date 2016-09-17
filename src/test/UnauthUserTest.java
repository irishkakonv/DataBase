package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.RequestType;
import user.UnauthUser;

import static org.junit.Assert.*;

/**
 * The test UnauthUser class
 */
public class UnauthUserTest {
    UnauthUser unauthUserTest;

    @Before
    public void setUp() throws Exception {
        unauthUserTest = new UnauthUser("irishkakonv");
    }

    @After
    public void tearDown() throws Exception {
        unauthUserTest = null;
    }

    @Test
    public void test_RequestType_checkPermissions_boolean() throws Exception {
        assertEquals(true, unauthUserTest.checkPermissions(RequestType.FIND));
        assertEquals(false, unauthUserTest.checkPermissions(RequestType.ADD));
    }
}