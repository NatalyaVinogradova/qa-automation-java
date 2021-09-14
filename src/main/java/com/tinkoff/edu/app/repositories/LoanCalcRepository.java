package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.models.LoanResponse;

import java.util.UUID;

public interface LoanCalcRepository {
    LoanResponse get(UUID requestId);
    LoanResponse update(LoanResponse response);
    LoanResponse save(LoanResponse response);
    void clean();
}
