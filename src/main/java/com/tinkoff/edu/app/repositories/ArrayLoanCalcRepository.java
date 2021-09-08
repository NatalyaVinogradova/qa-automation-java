package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.models.EntryNotFoundException;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanResponse;

import java.util.UUID;

/**
 * Created on 04.09.2021
 *
 * @author Natalya Vinogradova
 */
public class ArrayLoanCalcRepository implements LoanCalcRepository {
    private LoanResponse[] responsesArray = new LoanResponse[100000];
    private int nextIndex = 0;

    @Override
    public LoanResponse get(UUID requestId) throws LoanBusinessException {
        int index = getIndexByRequestId(requestId);
        return responsesArray[index];
    }

    @Override
    public LoanResponse update(LoanResponse response) throws EntryNotFoundException {
        int index = getIndexByRequestId(response.getRequestId());
        responsesArray[index] = response;
        return response;
    }

    @Override
    public LoanResponse save(LoanResponse response) {
        responsesArray[nextIndex++] = response;
        return response;
    }

    public void clean() {
        responsesArray = new LoanResponse[100000];
        nextIndex = 0;
    }

    private int getIndexByRequestId(UUID requestId) throws EntryNotFoundException {
        int index = 0;
        int result = -1;
        for (LoanResponse item : responsesArray) {
            if (requestId == item.getRequestId()) {
                result = index;
                break;
            } else {
                index++;
            }
        }
        if (result == -1) {
            throw new EntryNotFoundException(requestId);
        }
        return result;
    }
}
