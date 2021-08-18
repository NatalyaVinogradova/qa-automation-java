package com.tinkoff.edu.test;

import com.tinkoff.edu.app.LoanCalcController;
import com.tinkoff.edu.app.LoanCalcService;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcTest {
    public static void main(String... args) {
        LoanCalcController controller;
        controller = new LoanCalcController();
        int requestId = controller.createRequest();

        LoanCalcService a = controller.service;

        System.out.println(requestId + " must be 1");
    }
}
