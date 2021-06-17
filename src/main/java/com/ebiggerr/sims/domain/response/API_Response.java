package com.ebiggerr.sims.domain.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class API_Response implements Serializable {

    private HttpStatus httpStatus;
    private String message;
    private Object data;

    private final String SUCCESS_MSG="Success";
    private final String UNAUTHORIZED_MSG="Unauthorized";
    private final String INVALID_CREDENTIALS="Invalid Credentials";
    private final String NO_RECORD_FOUND="No Record Found";

    public API_Response(){

    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    // for operation such as logout that does not require data in response body.
    public API_Response Success(){

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage(SUCCESS_MSG);
        response.setData(null);

        return response;
    }

    public API_Response Success(JWTToken token){

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage(SUCCESS_MSG);
        response.setData(token);

        return response;
    }

    public API_Response Success(Object object){

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.OK);
        response.setMessage(SUCCESS_MSG);
        response.setData(object);

        return response;
    }

    public API_Response Unauthorized(){

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(UNAUTHORIZED_MSG);
        response.setData(null);

        return response;
    }

    public API_Response UserNotFound(){

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.UNAUTHORIZED);
        response.setMessage(INVALID_CREDENTIALS);
        response.setData(null);

        return response;
    }

    public API_Response NoRecordFound(){

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(NO_RECORD_FOUND);
        response.setData(null);

        return response;
    }


    public API_Response Error(String message) {

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setMessage(message);
        response.setData(null);

        return response;
    }

    public API_Response Failed(String message) {

        API_Response response=new API_Response();
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        response.setMessage(message);
        response.setData(null);

        return response;
    }
}
