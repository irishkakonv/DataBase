package server;

enum AnswerType {
    OK,
    FAIL,
    NON
}

/**
 * This class create the answer with answer type, key and maybe value
 */
public class Answer {
    private AnswerType answer;
    private RequestType request;
    String value;

    public Answer(RequestType r, AnswerType a, String v) {
        this.request = r;
        this.answer = a;
        this.value = v;

        System.out.println("The answer was created with request type = " + request
                + ", key = " + answer + " and value = " + value);
    }
}
