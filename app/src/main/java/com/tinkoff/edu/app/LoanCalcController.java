package com.tinkoff.edu.app;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     */
    public static int createRequest() {
        LoanCalcLogger.log();
        return LoanCalcService.createRequest();
    }
}
