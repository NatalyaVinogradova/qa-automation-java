package com.tinkoff.edu.app.services;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.repositories.LoanCalcRepository;

import java.util.List;
import java.util.UUID;

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
        ResponseType responseType = calculateResponseType(request);
        LoanResponse response = new LoanResponse(responseType, request);
        try {
            response = repository.save(response);
        } catch (RuntimeException exception) {
            throw new LoanBusinessException("Закончилось место в хранилище.", exception);
        }
        return response;
    }

    public LoanResponse getLoanResponseByRequestId(UUID requestId) {
        LoanResponse response;
        try {
            response = repository.get(requestId);
        } catch (RuntimeException exception) {
            throw new LoanBusinessException("Не удалось получить заявку.", exception);
        }
        return response;
    }

    public void update(LoanResponse response) {
        try {
            repository.update(response);
        } catch (RuntimeException exception) {
            throw new LoanBusinessException("Не удалось обновить заявку.", exception);
        }
    }

    public void clean() {
        repository.clean();
    }

    @Override
    public List<LoanResponse> getLoanResponsesByLoanType(LoanType loanType) {
        return repository.getLoanResponsesByLoanType(loanType);
    }

    @Override
    public int getLoanResponsesAmountSumByLoanType(LoanType loanType) {
        return repository.getLoanResponsesAmountSumByLoanType(loanType);
    }

    private ResponseType calculateResponseType(LoanRequest request) {
        ResponseType result = ResponseType.APPROVED;
        switch (request.getType()) {
            case PERSON:
                if (request.getAmount() > 10000) {
                    result = ResponseType.DENIED;
                }
                if (request.getMonths() > 12) {
                    result = ResponseType.DENIED;
                }
                break;
            case OOO:
                if (request.getAmount() <= 10000 || request.getMonths() >= 12) {
                    result = ResponseType.DENIED;
                }
                break;
            case IP:
                result = ResponseType.DENIED;
        }
        return result;
    }
}
