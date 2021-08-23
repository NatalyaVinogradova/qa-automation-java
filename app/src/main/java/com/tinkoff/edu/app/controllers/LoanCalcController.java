package com.tinkoff.edu.app.controllers;

import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.loggers.LoanCalcLogger;
import com.tinkoff.edu.app.services.LoanCalcService;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcController {
    /**
     * TODO Validates and logs request.
     */
    private final LoanCalcService service;

    public LoanCalcController(LoanCalcService service) {
        this.service = service;
    }

    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcLogger.log();
        return service.createRequest(request);
    }
}
