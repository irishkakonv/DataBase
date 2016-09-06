package server;

/**
 * This class create the answer with answer type, key and maybe value
 */
public class Answer {
    private AnswerType answer;
    private RequestType request;
    private String value;
    private String message;

    public Answer(RequestType r, AnswerType a, String v, String m) {
        this.request = r;
        this.answer = a;
        this.value = v;
        this.message = m;

        System.out.println("The answer was created with request type = " + request
                + ", key = " + answer + " and value = " + value);
    }

    public void setAnswer(AnswerType a) {
        answer = a;
    }

    public void setRequest(RequestType r) {
        request = r;
    }

    public void setValue(String v) {
        value = v;
    }

    public void setMessage(String m) {
        message = m;
    }

    public AnswerType getAnswer() {
        return answer;
    }

    public RequestType getRequest() {
        return request;
    }

    public String getValue() {
        return value;
    }

    public String getMessage() {
        return message;
    }


}
