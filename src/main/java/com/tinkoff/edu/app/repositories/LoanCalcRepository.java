package com.tinkoff.edu.app.repositories;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.models.LoanResponse;

import java.util.List;
import java.util.UUID;

public interface LoanCalcRepository {
    LoanResponse get(UUID requestId);
    LoanResponse update(LoanResponse response);
    LoanResponse save(LoanResponse response);
    void clean();

    List<LoanResponse> getLoanResponsesByLoanType(LoanType loanType);
    int getLoanResponsesAmountSumByLoanType(LoanType loanType);
}
