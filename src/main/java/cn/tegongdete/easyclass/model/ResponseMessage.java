package cn.tegongdete.easyclass.model;

public class ResponseMessage<T> {
    private static final String CODE_SUCCESS = "200";

    private static final String CODE_FAIL = "400";

    private static final String MSG_SUCCESS="success";

    private static final String MSG_FAIL="failed";

    public ResponseMessage(){
    }
    public ResponseMessage(String code ){
        this.code=code;
    }
    public ResponseMessage(String code,T entity ){
        this.code=code;
        this.entity=entity;
    }
    public ResponseMessage(String code, String msg){
        this.code = code;
        this.msg = msg;
    }
    public ResponseMessage(String code, String msg,T entity) {
        this.code = code;
        this.msg = msg;
        this.entity=entity;
    }
    public static ResponseMessage success(){
        return new ResponseMessage(CODE_SUCCESS,MSG_SUCCESS);
    }

    public static <T> ResponseMessage<T> success(T data) {
        return new ResponseMessage(CODE_SUCCESS,MSG_SUCCESS, data);
    }

    public static ResponseMessage fail(){
        return new ResponseMessage(CODE_FAIL, MSG_FAIL);
    }

    private String code;

    private String msg;

    public T entity;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }
}

