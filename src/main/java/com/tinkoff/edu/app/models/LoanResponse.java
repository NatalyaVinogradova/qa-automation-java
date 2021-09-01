package com.tinkoff.edu.app.models;

import com.tinkoff.edu.app.enums.ResponseType;

/**
 * Created on 20.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanResponse {
    private ResponseType type;
    private final int requestId;
    private final LoanRequest request;

    public LoanResponse(ResponseType type, int requestId, LoanRequest request) {
        this.request = request;
        this.type = type;
        this.requestId = requestId;
    }

    public ResponseType getType() {
        return type;
    }

    public void setType(ResponseType type) {
        this.type = type;
    }

    public int getRequestId() {
        return requestId;
    }

    public LoanRequest getRequest() {
        return request;
    }
}
