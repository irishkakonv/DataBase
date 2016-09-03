package server;

/** Enum with with different request type */
enum RequestType {
    ADD,
    FIND,
    DELETE,
    UNKNOUWN
}

/**
 * This class create the request with the request type,
 * key and value
 */
public class Request {
    private RequestType type;
    private String key;
    private String value;

    public Request(RequestType t, String k, String v){
        this.type = t;
        this.key = k;
        this.value = v;

        System.out.println("The request was created with request type = " + this.type
        + ", key = " + this.key + " and value = " + this.value);
    }

    public RequestType getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
