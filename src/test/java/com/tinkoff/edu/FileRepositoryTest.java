package com.tinkoff.edu;

import com.tinkoff.edu.app.controllers.LoanCalcController;
import com.tinkoff.edu.app.enums.ResponseType;
import com.tinkoff.edu.app.models.EntryNotFoundException;
import com.tinkoff.edu.app.models.LoanBusinessException;
import com.tinkoff.edu.app.models.LoanRequest;
import com.tinkoff.edu.app.models.LoanResponse;
import com.tinkoff.edu.app.repositories.ArrayLoanCalcRepository;
import com.tinkoff.edu.app.repositories.FileLoanCalcRepository;
import com.tinkoff.edu.app.services.ConcreteLoanCalcService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static com.tinkoff.edu.app.enums.LoanType.OOO;
import static com.tinkoff.edu.app.enums.LoanType.PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FileRepositoryTest {
    private LoanCalcController controller;

    @BeforeEach
    public void init() {
        controller = new LoanCalcController(new ConcreteLoanCalcService(new FileLoanCalcRepository()));
    }

    @Test
    public void shouldFindResponseInRepository() {
        controller.clean();
        LoanRequest request = new LoanRequest(OOO, 10000, 11, "КРОВЬ И СЛЕЗЫ");
        LoanResponse response = controller.createRequest(request);
        LoanResponse loanResponse = controller.getLoanResponse(response.getRequestId());
        assertEquals(loanResponse, response);
    }

    @Test
    public void shouldGetNotFoundBusinessException() {
        assertThrows(LoanBusinessException.class, () -> {
            controller.getLoanResponse(UUID.randomUUID());
        });
    }

    @Test
    public void shouldGetBusinessExceptionForNullUpdate() {
        assertThrows(LoanBusinessException.class, () -> {
            controller.updateLoanResponse(null);
        });
    }

    @Test
    public void shouldGetBusinessExceptionForNotExistingId() {
        LoanRequest request = new LoanRequest(PERSON, 1000, 10, "Петров-Боткин");
        LoanResponse response = new LoanResponse(ResponseType.DENIED, request);
        assertThrows(EntryNotFoundException.class, () -> {
            ArrayLoanCalcRepository repository = new ArrayLoanCalcRepository();
            repository.update(response);
        });
    }

    @Test
    public void shouldUpdateSuccess() {
        controller.clean();
        LoanRequest request = new LoanRequest(PERSON, 1000, 10, "Петров-Боткин");
        LoanResponse response = controller.createRequest(request);
        response.setType(ResponseType.APPROVED);
        controller.updateLoanResponse(response);
        LoanResponse updatedResponse = controller.getLoanResponse(response.getRequestId());
        assertEquals(response.getType(), updatedResponse.getType());
    }

    @Test
    public void shouldGetAllRequestsByOoo() {
        controller.clean();
        for (int i = 0; i < 5; i++) {
            LoanRequest requestOoo = new LoanRequest(OOO, 11000 + i, 10, "Биг компани");
            LoanRequest requestPerson = new LoanRequest(PERSON, 1000 + i, 10, "Иванов Иван");
            controller.createRequest(requestOoo);
            controller.createRequest(requestPerson);
        }
        List<LoanResponse> responseList = controller.getLoanResponsesByLoanType(OOO);
        for (LoanResponse response : responseList) {
            assertEquals(response.getRequest().getType(), OOO);
        }
    }

    @Test
    public void shouldGetAmountSumAllRequestsByOoo() {
        controller.clean();
        for (int i = 0; i < 5; i++) {
            LoanRequest requestOoo = new LoanRequest(OOO, 11000 + i, 10, "Биг компани");
            LoanRequest requestPerson = new LoanRequest(PERSON, 1000 + i, 10, "Иванов Иван");
            controller.createRequest(requestOoo);
            controller.createRequest(requestPerson);
        }
        int sumAllRequestsByOoo = controller.getLoanResponsesAmountSumByLoanType(OOO);
        assertEquals(55010, sumAllRequestsByOoo);
    }
}
