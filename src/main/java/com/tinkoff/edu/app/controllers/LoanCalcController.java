package com.tinkoff.edu.app.controllers;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.loggers.LoanCalcLogger;
import com.tinkoff.edu.app.services.LoanCalcService;

import java.util.UUID;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcController {
    private final LoanCalcService service;

    public LoanCalcController(LoanCalcService service) {
        this.service = service;
    }

    public LoanResponse createRequest(LoanRequest request) {
        LoanCalcLogger.log();
        validate(request);
        //if (request == null) throw new IllegalArgumentException();
        return service.createRequest(request);
    }

    public LoanResponse getLoanResponse(UUID requestId) {
        return service.getLoanResponseByRequestId(requestId);
    }

    public void updateLoanResponse(LoanResponse response) {
        service.update(response);
    }

    public void clean() {
        service.clean();
    }

    private void validate(LoanRequest request) {
        if (request == null) {
            throw new LoanBusinessException("Передана пустая заявка.");
        }
        if (request.getAmount() <= 0 || request.getAmount() > 50_000_000) {
            throw new LoanBusinessException("Запрошена некорректная сумма кредита.");
        }
        if (request.getMonths() > 100 || request.getMonths() <= 0) {
            throw new LoanBusinessException("Запрошен некорректный срок кредита.");
        }
        if (request.getClientFullName().length() < 10 || request.getClientFullName().length() > 100) {
            throw new LoanBusinessException("Указано некорректное ФИО.");
        }
    }
}
