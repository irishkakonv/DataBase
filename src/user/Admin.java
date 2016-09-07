package user;

import server.RequestType;

import java.io.IOException;


/**
 * Admin class with additional methods
 */
public class Admin extends  AuthUser {
    private AdminCommand adminCommand;
    private String paramsCommand;

    public Admin(String login, String passwd) {
        super(login, passwd);
        this.userType = UserType.ADMIN;
        permissions = new RequestType[] {RequestType.FIND, RequestType.ADD,
                                         RequestType.DELETE, RequestType.RMALL};
    }

    public String getParamsCommand() {
        return paramsCommand;
    }

    public void setParamsCommand(String paramsCommand) {
        this.paramsCommand = paramsCommand;
    }

    public AdminCommand getAdminCommand() {
        return adminCommand;
    }

    public void setAdminCommand(AdminCommand adminCommand) {
        this.adminCommand = adminCommand;
    }

    public void parseAdminCommand(String command) throws IOException {

        if (command == "LSUSER") {
            this.setAdminCommand(AdminCommand.LSUSER);
        }
        else if (command == "RMUSERS") {
            this.setAdminCommand(AdminCommand.RMUSERS);
        }
        else if (command.contains(":")) {
            String[] strings = command.split(":");
            switch (strings[0]) {
                case ("ADDUSER"):
                    this.setAdminCommand(AdminCommand.ADDUSER);
                    break;

                case ("RMUSER"):
                    this.setAdminCommand(AdminCommand.RMUSER);
                    break;

                case ("LSUSER"):
                    this.setAdminCommand(AdminCommand.LSUSER);
                    break;

                case ("RMUSERS"):
                    this.setAdminCommand(AdminCommand.RMUSERS);
                    break;

                default:
                    System.out.println("The command is not correct. Can't parse the command");
                    throw new IOException("The command is not correct. Can't parse the command");
            }
            /** Parse the command ADDUSER:login=passwd or RMUSER:login */
            try {
                if (strings[1].equals("")) {  // handle the command RMUSERS: or LSUSER:
                    if (this.getAdminCommand() == AdminCommand.LSUSER ||
                            this.getAdminCommand() == AdminCommand.RMUSERS) {
                        this.setParamsCommand("");
                    }
                } else {
                    this.setParamsCommand(strings[1]);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.err.println("ERROR: The key is required for this command!");
                throw new IOException("ERROR: The key is required for this command!");
            }

        }
    }
}
