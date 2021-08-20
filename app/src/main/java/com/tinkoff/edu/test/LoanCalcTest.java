package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcTest {
    public static void main(String... args) {
        int requestId = LoanCalcController.createRequest();
        System.out.println(requestId + " must be 1");
    }
}
