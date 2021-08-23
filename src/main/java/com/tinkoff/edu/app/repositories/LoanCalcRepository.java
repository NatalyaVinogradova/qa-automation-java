package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.models.LoanRequest;

public interface LoanCalcRepository {
    int save(LoanRequest request);
}
