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
    public LoanCalcService service;

    public LoanCalcController(){
        service = new LoanCalcService();
    }

    public int createRequest() {
        LoanCalcLogger.log();
        return service.createRequest();
    }
}
