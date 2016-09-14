package user;

import server.Request;
import server.RequestType;

import java.io.IOException;


/**
 * Admin class with additional methods
 */
public class Admin extends  AuthUser {
    private RequestType adminCommand;
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

    public RequestType getAdminCommand() {
        return adminCommand;
    }

    public void setAdminCommand(RequestType adminCommand) {
        this.adminCommand = adminCommand;
    }

//    public void parseAdminCommand(Request req) throws IOException {
//
//        if (req.getType().equals(RequestType.LSUSER)) {
//            this.setAdminCommand(RequestType.LSUSER);
//        }
//        else if (req.getType().equals(RequestType.RMUSERS)) {
//            this.setAdminCommand(RequestType.RMUSERS);
//        }
//        else if (command.contains(":")) {
//            String[] strings = command.split(":");
//            switch (strings[0]) {
//                case ("ADDUSER"):
//                    this.setAdminCommand(AdminCommand.ADDUSER);
//                    break;
//
//                case ("RMUSER"):
//                    this.setAdminCommand(AdminCommand.RMUSER);
//                    break;
//
//                case ("LSUSER"):
//                    this.setAdminCommand(AdminCommand.LSUSER);
//                    break;
//
//                case ("RMUSERS"):
//                    this.setAdminCommand(AdminCommand.RMUSERS);
//                    break;
//
//                default:
//                    System.out.println("The command is not correct. Can't parse the command");
//                    throw new IOException("The command is not correct. Can't parse the command");
//            }
//            /** Parse the command ADDUSER:login=passwd or RMUSER:login */
//            try {
//                if (strings[1].equals("")) {  // handle the command RMUSERS: or LSUSER:
//                    if (this.getAdminCommand().equals(AdminCommand.LSUSER) ||
//                            this.getAdminCommand().equals(AdminCommand.RMUSERS)) {
//                        this.setParamsCommand("");
//                    }
//                } else {
//                    this.setParamsCommand(strings[1]);
//                }
//            } catch (ArrayIndexOutOfBoundsException ex) {
//                System.err.println("ERROR: The key is required for this command!");
//                throw new IOException("ERROR: The key is required for this command!");
//            }
//        }
//    }
}
