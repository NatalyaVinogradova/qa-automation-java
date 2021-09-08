package com.tinkoff.edu;

import com.tinkoff.edu.app.controllers.LoanCalcController;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.repositories.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.services.ConcreteLoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.tinkoff.edu.app.enums.LoanType.*;
import static com.tinkoff.edu.app.enums.ResponseType.APPROVED;
import static com.tinkoff.edu.app.enums.ResponseType.DENIED;
import static org.junit.jupiter.api.Assertions.*;

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
        request = new LoanRequest(PERSON, 10000, 12, "Иван Блинов");
        controller = new LoanCalcController(new ConcreteLoanCalcService(new ArrayLoanCalcRepository()));
    }

//    @Test
//    public void shouldGet1WhenFirstRequest() {
//        LoanResponse response = controller.createRequest(request);
//        assertEquals(1, response.getRequestId());
//    }

//    @Test
//    public void shouldGetIncrementedIdWhenAnyCall() {
//        controller = new LoanCalcController(new ConcreteLoanCalcService(new VariableLoanCalcRepository(2)));
//        assertEquals(3, controller.createRequest(request).getRequestId());
//        assertEquals(4, controller.createRequest(request).getRequestId());
//    }

    @Test
    public void shouldGetErrorWhenApplyNullRequest() {
        assertThrows(LoanBusinessException.class, () -> {
            controller.createRequest(null);
        });
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeAmountRequest() {
        request = new LoanRequest(PERSON, 0, 10, "Антон Семенов");
        assertThrows(LoanBusinessException.class, () -> {
            controller.createRequest(request);
        });
        request = new LoanRequest(PERSON, -10, 10, "Антон Семенов");
        assertThrows(LoanBusinessException.class, () -> {
            controller.createRequest(request);
        });
    }

    @Test
    public void shouldGetErrorWhenApplyZeroOrNegativeMonthsRequest() {
        request = new LoanRequest(PERSON, 1000, 0, "Анна Петрова");
        assertThrows(LoanBusinessException.class, () -> {
            controller.createRequest(request);
        });
        request = new LoanRequest(PERSON, 1000, -10, "Анна Петрова");
        assertThrows(LoanBusinessException.class, () -> {
            controller.createRequest(request);
        });
    }

    @Test
    public void shouldGetApprovedWhenPersonLess10000Less12() {
        request = new LoanRequest(PERSON, 9000, 11, "Анна Петрова");
        assertEquals(APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenPersonEquals10000Equals12() {
        request = new LoanRequest(PERSON, 10000, 12, "Анна Петрова");
        assertEquals(APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenPersonLess10000Equals12() {
        request = new LoanRequest(PERSON, 9000, 12, "Иван Блинов");
        assertEquals(APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenPersonEquals10000Less12() {
        request = new LoanRequest(PERSON, 10000, 9, "Иван Блинов");
        assertEquals(APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenPersonMore10000Amount() {
        request = new LoanRequest(PERSON, 11000, 11, "Иван Блинов");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenPersonMore12Months() {
        request = new LoanRequest(PERSON, 9000, 13, "Антон Семенов");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetApprovedWhenOooMore10000Less12() {
        request = new LoanRequest(OOO, 15000, 11, "ООО Армада");
        assertEquals(APPROVED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooLess10000More12() {
        request = new LoanRequest(OOO, 5000, 15, "ООО Армада");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooLess10000Less12() {
        request = new LoanRequest(OOO, 5000, 6, "ООО Армада");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooEquals10000More12() {
        request = new LoanRequest(OOO, 10000, 15, "ООО Котики");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooEquals10000Less12() {
        request = new LoanRequest(OOO, 10000, 1, "ООО Пёсики");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooLess10000Equals12() {
        request = new LoanRequest(OOO, 9000, 12, "ООО Котики");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooMore10000More12() {
        request = new LoanRequest(OOO, 15000, 15, "ООО Пёсики");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooMore10000Equals12() {
        request = new LoanRequest(OOO, 15000, 12, "ООО Котики");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenOooEquals10000Equals12() {
        request = new LoanRequest(OOO, 10000, 12, "ООО Пёсики");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }

    @Test
    public void shouldGetDeniedWhenIp() {
        request = new LoanRequest(IP, 10000, 12, "ИП Редиска");
        assertEquals(DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(IP, 15000, 13, "ИП Редиска");
        assertEquals(DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(IP, 2000, 5, "ИП Редиска");
        assertEquals(DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(IP, 2000, 13, "ИП Редиска");
        assertEquals(DENIED, controller.createRequest(request).getType());
        request = new LoanRequest(IP, 15000, 5, "ИП Редиска");
        assertEquals(DENIED, controller.createRequest(request).getType());
    }
}
