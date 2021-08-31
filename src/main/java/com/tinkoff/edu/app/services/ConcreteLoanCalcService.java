package com.tinkoff.edu.app.services;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
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
        if (request == null){
            return new LoanResponse(ResponseType.DENIED, -1, null);
        }
        if (request.getAmount() <= 0){
            return new LoanResponse(ResponseType.DENIED, -1, request);
        }
        if (request.getMonths() <= 0){
            return new LoanResponse(ResponseType.DENIED, -1, request);
        }
        LoanResponse response = repository.save(request);
        if (request.getType() == LoanType.PERSON && request.getAmount() <= 10000 && request.getMonths() <= 12) {
            response.setType(ResponseType.APPROVED);
        } else {
            response.setType(ResponseType.DENIED);
        }
        if (request.getType() == LoanType.OOO && request.getAmount() > 10000 && request.getMonths() < 12){
            response.setType(ResponseType.APPROVED);
        }else if (request.getType() == LoanType.OOO && (request.getAmount() <= 10000 || request.getMonths() >= 12)){
            response.setType(ResponseType.DENIED);
        }
        if (request.getType() == LoanType.IP){
            response.setType(ResponseType.DENIED);
        }
        return response;
    }
}
