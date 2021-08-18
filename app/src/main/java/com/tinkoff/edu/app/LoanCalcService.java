package com.tinkoff.edu.app;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcService {
    public LoanCalcRepository repository;
    /**
     * Loan calculation
     */
    public LoanCalcService() {
        repository = new LoanCalcRepository();
    }

    public int createRequest() {
        return repository.save();
    }
}
