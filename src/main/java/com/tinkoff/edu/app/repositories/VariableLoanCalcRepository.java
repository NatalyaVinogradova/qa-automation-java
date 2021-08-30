package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class VariableLoanCalcRepository implements LoanCalcRepository {
    private int requestId;

    public VariableLoanCalcRepository(int requestId) {
        this.requestId = requestId;
    }

    public VariableLoanCalcRepository() {
        this(0);
    }

    public int getRequestId() {
        return requestId;
    }

    @Override
    public LoanResponse save(LoanRequest request) {
        return new LoanResponse(ResponseType.DENIED, ++requestId, request);
    }
}
