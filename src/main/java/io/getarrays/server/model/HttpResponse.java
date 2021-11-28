package io.getarrays.server.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Map;

@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL) //when something is null we wont show it to the user
public class HttpResponse {

    protected LocalDateTime timStamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String reason;
    protected String message;
    protected String developerMessage;
    protected Map<?, ?> data;   //map of anything

    public HttpResponse(LocalDateTime timStamp, int statusCode, HttpStatus status, String reason, String message, String developerMessage, Map<?, ?> data) {
        this.timStamp = timStamp;
        this.statusCode = statusCode;
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.developerMessage = developerMessage;
        this.data = data;
    }

    public LocalDateTime getTimStamp() {
        return timStamp;
    }

    public void setTimStamp(LocalDateTime timStamp) {
        this.timStamp = timStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDeveloperMessage() {
        return developerMessage;
    }

    public void setDeveloperMessage(String developerMessage) {
        this.developerMessage = developerMessage;
    }

    public Map<?, ?> getData() {
        return data;
    }

    public void setData(Map<?, ?> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "HttpResponse{" +
                "timStamp=" + timStamp +
                ", statusCode=" + statusCode +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", message='" + message + '\'' +
                ", developerMessage='" + developerMessage + '\'' +
                ", data=" + data +
                '}';
    }
}
