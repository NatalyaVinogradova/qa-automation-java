package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanResponse;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MapLoanCalcRepository implements LoanCalcRepository {
    private HashMap<UUID, LoanResponse> storage = new HashMap<>();

    @Override
    public LoanResponse get(UUID requestId) throws LoanBusinessException {
        if (!storage.containsKey(requestId)) {
            throw new LoanBusinessException("Не найдена запись по " + requestId);
        }
        return storage.get(requestId);
    }

    @Override
    public LoanResponse update(LoanResponse response) {
        if (response == null) {
            throw new LoanBusinessException("Передан пустой ответ");
        }
        if (response.getRequestId() == null) {
            throw new LoanBusinessException("Передан пустой идентификатор");
        }
        storage.put(response.getRequestId(), response);
        return response;
    }

    @Override
    public LoanResponse save(LoanResponse response) throws LoanBusinessException {
        if (response == null) {
            throw new LoanBusinessException("Передан пустой ответ");
        }
        if (response.getRequestId() == null) {
            throw new LoanBusinessException("Передан пустой идентификатор");
        }
        if (storage.containsKey(response.getRequestId())) {
            throw new LoanBusinessException("Ответ с таким идентификатором уже существует");
        }
        storage.put(response.getRequestId(), response);
        return response;
    }

    @Override
    public void clean() {
        storage.clear();
    }

    @Override
    public List<LoanResponse> getLoanResponsesByLoanType(LoanType loanType) {
        if (loanType == null) {
            throw new LoanBusinessException("Некорректный тип клиента");
        }
        List<LoanResponse> loanResponses = storage.values().stream()
                .filter(response -> response.getRequest().getType() == loanType)
                .collect(Collectors.toList());
        return loanResponses;
    }

    public int getLoanResponsesAmountSumByLoanType(LoanType loanType) {
        if (loanType == null) {
            throw new LoanBusinessException("Некорректный тип клиента");
        }
        int sum = storage.values().stream()
                .filter(response -> response.getRequest().getType() == loanType)
                .mapToInt(response -> response.getRequest().getAmount())
                .sum();
        return sum;
    }
}
