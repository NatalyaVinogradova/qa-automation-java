package com.tinkoff.edu.app.services;

import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;

import java.util.UUID;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest request);
    LoanResponse getLoanResponseByRequestId(UUID requestId);
    void update(LoanResponse response);
    void clean();
}
