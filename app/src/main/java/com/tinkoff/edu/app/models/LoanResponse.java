package com.tinkoff.edu.app.models;

import com.tinkoff.edu.app.enums.ResponseType;

/**
 * Created on 20.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanResponse {
    private final ResponseType type;
    private final int requestId;

    public LoanResponse(ResponseType type, int requestId) {
        this.type = type;
        this.requestId = requestId;
    }

    public LoanResponse(ResponseType type) {
        this.type = type;
        this.requestId = 0;
    }

    public ResponseType getType() {
        return type;
    }

    public int getRequestId() {
        return requestId;
    }
}
