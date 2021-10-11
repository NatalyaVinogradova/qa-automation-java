package com.tinkoff.edu.app.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tinkoff.edu.app.enums.ResponseType;

import java.util.UUID;

/**
 * Created on 20.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanResponse {
    private ResponseType type;
    private final UUID requestId;
    private final LoanRequest request;

    public LoanResponse(ResponseType type, LoanRequest request) {
        this.request = request;
        this.type = type;
        this.requestId = UUID.randomUUID();
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public LoanResponse(@JsonProperty("type") ResponseType type, @JsonProperty("request") LoanRequest request, @JsonProperty("requestId") UUID requestId) {
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

    public UUID getRequestId() {
        return requestId;
    }

    public LoanRequest getRequest() {
        return request;
    }
}
