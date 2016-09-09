package test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import server.Answer;
import server.AnswerType;
import server.RequestType;
import user.Admin;
import user.AdminCommand;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * The test of class Admin
 */
public class AdminTest {
    Admin adminTest;
    private String paramsCommandTest = "paramsCommandTest";
    private AdminCommand adminCommand = AdminCommand.ADDUSER;

    @Before
    public void setUp() throws Exception {
        adminTest = new Admin("irishkakonv", "lookAndDo");
    }

    @After
    public void tearDown(){
        adminTest = null;
    }

    @Test
    public void test_void_getParamsCommand_String() throws Exception {
        adminTest.setParamsCommand(paramsCommandTest);
        assertEquals(paramsCommandTest, adminTest.getParamsCommand());
    }

    @Test
    public void test_String_setParamsCommand() throws Exception {
        adminTest.setParamsCommand(paramsCommandTest);
        assertEquals(paramsCommandTest, adminTest.getParamsCommand());
    }

    @Test
    public void test_void_getAdminCommand_AdminCommand() throws Exception {
        adminTest.setAdminCommand(AdminCommand.ADDUSER);
        assertEquals(AdminCommand.ADDUSER, adminTest.getAdminCommand());
    }

    @Test
    public void test_AdminCommand_setAdminCommand() throws Exception {
        adminTest.setAdminCommand(AdminCommand.LSUSER);
        assertEquals(AdminCommand.LSUSER, adminTest.getAdminCommand());
    }

    /** The method's tests of parseAdminCommand(): if - else operators */
    @Test
    public void test_String_parseAdminCommand() throws Exception {
        adminCommand = AdminCommand.LSUSER;
        adminTest.parseAdminCommand("LSUSER");
        assertEquals(adminCommand, adminTest.getAdminCommand());

        adminTest.parseAdminCommand("LSUSER:lala=1235");
        assertEquals(adminCommand, adminTest.getAdminCommand());

        adminCommand = AdminCommand.RMUSERS;
        adminTest.parseAdminCommand("RMUSERS");
        assertEquals(adminCommand, adminTest.getAdminCommand());

        adminTest.parseAdminCommand("RMUSERS:lala=1235");
        assertEquals(adminCommand, adminTest.getAdminCommand());

        adminCommand = AdminCommand.ADDUSER;
        adminTest.parseAdminCommand("ADDUSER:lala=1235");
        assertEquals(adminCommand, adminTest.getAdminCommand());

        adminCommand = AdminCommand.RMUSER;
        adminTest.parseAdminCommand("RMUSER:lala=1235");
        assertEquals(adminCommand, adminTest.getAdminCommand());

    }

    /** The method's tests of parseAdminCommand(): try - catch operators */
    @Test
    public void test_String_parseAdminCommand_() throws Exception {
        paramsCommandTest = "";
        adminCommand = AdminCommand.LSUSER;
        adminTest.parseAdminCommand("LSUSER:");
        assertEquals(paramsCommandTest, adminTest.getParamsCommand());

        adminCommand = AdminCommand.RMUSERS;
        adminTest.parseAdminCommand("RMUSERS:");
        assertEquals(paramsCommandTest, adminTest.getParamsCommand());

        paramsCommandTest = "user=parol";
        adminCommand = AdminCommand.ADDUSER;
        adminTest.parseAdminCommand("ADDUSER:user=parol");
        assertEquals(paramsCommandTest, adminTest.getParamsCommand());
    }

    @Test(expected = IOException.class)
    public void test_String_parseAdminCommand_IOException() throws Exception {
        adminTest.parseAdminCommand("RMUSERSS:lala=1235");
    }

    @Test(expected = IOException.class)
    public void test_String_parseAdminCommand_IOException_() throws Exception {
        adminTest.parseAdminCommand("ADDUSER:");
    }
}