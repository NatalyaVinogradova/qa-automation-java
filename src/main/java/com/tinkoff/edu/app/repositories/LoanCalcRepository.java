package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;

public interface LoanCalcRepository {
    LoanResponse save(LoanRequest request);
}
