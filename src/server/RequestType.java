package server;

/** Enum with with different request type */
public enum RequestType {
    ADD,        // add the key and value
    FIND,       // find the value by the key
    DELETE,     // delete the key and the value
    RMALL,      // delete all key and value (for only admin)
    UNKNOUWN,   // the default value
    LOGIN,      // log in the service
    LOGOFF,     // log out

    // the following request type only for admin
    ADDUSER,    // add new user if doesn't exist
    LSUSER,     // print list of available users
    RMUSER,     // delete user by login
    RMUSERS     // delete all users except admin
}
