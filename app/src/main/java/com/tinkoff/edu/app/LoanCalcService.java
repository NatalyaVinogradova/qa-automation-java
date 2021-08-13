package com.tinkoff.edu.app;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcService {
    /**
     * Loan calculation
     */
    public static int createRequest() {
        return LoanCalcRepository.save();
    }
}
