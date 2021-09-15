package com.tinkoff.edu.app.services;

import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;

import java.util.List;
import java.util.UUID;

public interface LoanCalcService {
    LoanResponse createRequest(LoanRequest request);
    LoanResponse getLoanResponseByRequestId(UUID requestId);
    void update(LoanResponse response);
    void clean();

    List<LoanResponse> getLoanResponsesByLoanType(LoanType loanType);
    int getLoanResponsesAmountSumByLoanType(LoanType loanType);
}
