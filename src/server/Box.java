package server;

/** The instance of this class contains key and value */
public class Box {
    private String key;
    private String value;

    public Box(String k, String v) {
        this.key = k;
        this.value = v;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}