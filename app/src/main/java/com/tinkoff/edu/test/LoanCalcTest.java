package com.tinkoff.edu.test;

import com.tinkoff.edu.app.*;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcTest {
    public static void main(String... args) {
        LoanCalcController controller = new LoanCalcController(new ConcreteLoanCalcService(new StaticVariableLoanCalcRepository()));
        LoanRequest request = new LoanRequest(LoanType.PERSON, 2500000, 60);
        LoanResponse response = controller.createRequest(request);

        System.out.println("Request: " + request);
        System.out.println(response.getRequestId() + " must be 1");
    }
}
