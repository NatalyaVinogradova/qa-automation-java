package com.tinkoff.edu.app.services;

import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.repositories.LoanCalcRepository;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class ConcreteLoanCalcService implements LoanCalcService {
    private final LoanCalcRepository repository;

    /**
     * Loan calculation
     */
    public ConcreteLoanCalcService(LoanCalcRepository repository) {
        this.repository = repository;
    }

    public LoanResponse createRequest(LoanRequest request) {
        return repository.save(request);
    }
}
