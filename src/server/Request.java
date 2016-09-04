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

    void setType(RequestType t){
        type = t;
    }

    void setKey(String k){
        key = k;
    }

    void setValue(String v){
        value = v;
    }

    RequestType getType() {
        return type;
    }

    String getKey() {
        return key;
    }

    String getValue() {
        return value;
    }
}
