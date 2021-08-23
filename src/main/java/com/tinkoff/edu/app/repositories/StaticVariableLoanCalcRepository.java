package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class StaticVariableLoanCalcRepository implements LoanCalcRepository {
    private static int requestId = 0;

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    @Override
    public LoanResponse save(LoanRequest request) {
        return new LoanResponse(ResponseType.DENIED, ++requestId, request);
    }
}
