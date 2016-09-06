package server;

/** Enum with with different request type */
enum RequestType {
    ADD,        // add the key and value
    FIND,       // find the value by the key
    DELETE,     // delete the key and the value
    RMALL,      // delete all key and value (for only admin)
    UNKNOUWN    // the default value
}
