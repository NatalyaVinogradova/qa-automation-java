package com.tinkoff.edu;

import com.tinkoff.edu.app.controllers.LoanCalcController;
import com.tinkoff.edu.app.enums.LoanType;
import com.tinkoff.edu.app.enums.ResponseType;
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
        controller = new LoanCalcController(new ConcreteLoanCalcService(new VariableLoanCalcRepository()));
    }

    @Test
    public void shouldGet1WhenFirstRequest() {
        LoanResponse response = controller.createRequest(request);
        assertEquals(1, response.getRequestId());
    }

    @Test
    public void shouldGetIncrementedIdWhenAnyCall() {
        controller = new LoanCalcController(new ConcreteLoanCalcService(new VariableLoanCalcRepository(2)));
        assertEquals(3, controller.createRequest(request).getRequestId());
        assertEquals(4, controller.createRequest(request).getRequestId());
    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        assertEquals(-1, controller.createRequest(null).getRequestId());
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        request = new LoanRequest(LoanType.PERSON, 0, 10);
        assertEquals(-1, controller.createRequest(request).getRequestId());
        request = new LoanRequest(LoanType.PERSON, -10, 10);
        assertEquals(-1, controller.createRequest(request).getRequestId());
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        request = new LoanRequest(LoanType.PERSON, 1000, 0);
        assertEquals(-1, controller.createRequest(request).getRequestId());
        request = new LoanRequest(LoanType.PERSON, 1000, -10);
        assertEquals(-1, controller.createRequest(request).getRequestId());
    }

    @Test
    public void shouldGetApprovedWhenPersonLess10000Less12() {
        request = new LoanRequest(LoanType.PERSON, 9000, 11);
        assertEquals(ResponseType.APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenPersonEquals10000Equals12() {
        request = new LoanRequest(LoanType.PERSON, 10000, 12);
        assertEquals(ResponseType.APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenPersonLess10000Equals12() {
        request = new LoanRequest(LoanType.PERSON, 9000, 12);
        assertEquals(ResponseType.APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenPersonEquals10000Less12() {
        request = new LoanRequest(LoanType.PERSON, 10000, 9);
        assertEquals(ResponseType.APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenPersonMore10000Amount() {
        request = new LoanRequest(LoanType.PERSON, 11000, 11);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenPersonMore12Months() {
        request = new LoanRequest(LoanType.PERSON, 9000, 13);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenOooMore10000Less12() {
        request = new LoanRequest(LoanType.OOO, 15000, 11);
        assertEquals(ResponseType.APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooLess10000More12() {
        request = new LoanRequest(LoanType.OOO, 5000, 15);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooLess10000Less12() {
        request = new LoanRequest(LoanType.OOO, 5000, 6);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooEquals10000More12() {
        request = new LoanRequest(LoanType.OOO, 10000, 15);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooEquals10000Less12() {
        request = new LoanRequest(LoanType.OOO, 10000, 1);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooLess10000Equals12() {
        request = new LoanRequest(LoanType.OOO, 9000, 12);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooMore10000More12() {
        request = new LoanRequest(LoanType.OOO, 15000, 15);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooMore10000Equals12() {
        request = new LoanRequest(LoanType.OOO, 15000, 12);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooEquals10000Equals12() {
        request = new LoanRequest(LoanType.OOO, 10000, 12);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenIp() {
        request = new LoanRequest(LoanType.IP, 10000, 12);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(LoanType.IP, 15000, 13);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(LoanType.IP, 2000, 5);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(LoanType.IP, 2000, 13);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(LoanType.IP, 15000, 5);
        assertEquals(ResponseType.DENIED, controller.createRequest(request).getType());
    }
}
