package com.tinkoff.edu.app.services;

import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.repositories.LoanCalcRepository;

/**
 * Created on 21.08.2021
 *
 * @author Natalya Vinogradova
 */
public class IpNotFriendlyService extends ConcreteLoanCalcService {
    public IpNotFriendlyService(LoanCalcRepository repository) {
        super(repository);
    }

    @Override
    public LoanResponse createRequest(LoanRequest request) {
        if (request.getType().equals(LoanType.IP)) return new LoanResponse(ResponseType.DENIED);
        return super.createRequest(request);
    }
}
