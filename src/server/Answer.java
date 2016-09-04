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

    void setAnswer(AnswerType a){
        answer = a;
    }

    void setRequest(RequestType r){
        request = r;
    }

    void setValue(String v){
        value = v;
    }

    void setMessage(String m){
        message = m;
    }

    AnswerType getAnswer(){
        return answer;
    }

    RequestType getRequest(){
        return request;
    }

    String getValue(){
        return value;
    }

    String getMessage(){
        return message;
    }


}
