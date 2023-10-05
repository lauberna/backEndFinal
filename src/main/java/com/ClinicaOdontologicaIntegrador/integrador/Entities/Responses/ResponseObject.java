package com.ClinicaOdontologicaIntegrador.integrador.Entities.Responses;

import java.time.LocalDateTime;

public class ResponseObject {
    private LocalDateTime timestamp;
    private String status;
    private boolean response;
    private Object body;
    private StringBuffer path;

    public ResponseObject(LocalDateTime timestamp, String status,boolean response, Object body, StringBuffer path) {
        this.timestamp = timestamp;
        this.status = status;
        this.response = response;
        this.body = body;
        this.path = path;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public StringBuffer getPath() {
        return path;
    }

    public void setPath(StringBuffer path) {
        this.path = path;
    }
}
