package com.tinkoff.edu;

import com.tinkoff.edu.app.controllers.LoanCalcController;
import com.tinkoff.edu.app.models.EntryNotFoundException;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.repositories.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.services.ConcreteLoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tinkoff.edu.app.enums.LoanType.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

/**
 * Created on 04.09.2021
 *
 * @author Natalya Vinogradova
 */
public class ArrayRepositoryTest {
    private LoanCalcController controller;

    @BeforeEach
    public void init() {
        controller = new LoanCalcController(new ConcreteLoanCalcService(new ArrayLoanCalcRepository()));
    }

    @Test
    public void shouldGetRandomUuidRequestId() {
        LoanRequest request = new LoanRequest(PERSON, 1000, 11, "Иванов Федор");
        UUID firstRequestId = controller.createRequest(request).getRequestId();
        UUID secondRequestId = controller.createRequest(request).getRequestId();
        assertNotEquals(firstRequestId, secondRequestId);
    }

    @Test
    public void shouldFindResponseInRepository() {
        LoanRequest request = new LoanRequest(OOO, 10000, 11, "КРОВЬ И СЛЕЗЫ");
        LoanResponse response = controller.createRequest(request);
        LoanResponse loanResponse = controller.getLoanResponse(response.getRequestId());
        assertEquals(loanResponse, response);
    }

    @Test
    public void shouldGetExceptionOnInvalidRequest() {
        LoanRequest request = new LoanRequest(PERSON, 0, 11, "HG");
        LoanBusinessException exception = assertThrows(LoanBusinessException.class, () -> {
            LoanResponse response = controller.createRequest(request);
        });
        assertEquals("Запрошена некорректная сумма кредита.", exception.getMessage());
    }

    @Test
    public void shouldGetNotFoundBusinessException() {
        assertThrows(LoanBusinessException.class, () -> {
            controller.getLoanResponse(UUID.randomUUID());
        });
    }
}
