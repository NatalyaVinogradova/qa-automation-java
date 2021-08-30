package com.tinkoff.edu;

import com.tinkoff.edu.app.controllers.LoanCalcController;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.repositories.VariableLoanCalcRepository;
import com.tinkoff.edu.app.services.ConcreteLoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created on 11.08.2021
 *
 * @author Natalya Vinogradova
 */
public class AppTest {
    private LoanRequest request;
    private LoanCalcController controller;

    @BeforeEach
    public void init() {
        request = new LoanRequest(LoanType.PERSON, 2500000, 60);
    }

    @Test
    public void shouldGet1WhenFirstRequest() {
        controller = new LoanCalcController(new ConcreteLoanCalcService(new VariableLoanCalcRepository()));
        LoanResponse response = controller.createRequest(request);
        assertEquals(1, response.getRequestId());
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        controller = new LoanCalcController(new ConcreteLoanCalcService(new VariableLoanCalcRepository(2)));
        assertEquals(3, controller.createRequest(request).getRequestId());
        assertEquals(4, controller.createRequest(request).getRequestId());
    }
}
