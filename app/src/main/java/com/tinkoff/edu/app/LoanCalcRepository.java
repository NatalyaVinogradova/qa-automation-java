package com.tinkoff.edu.app;

/**
 * Created on 13.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanCalcRepository {
    private static int requestId;

    /**
     * TODO persists request
     *
     * @return Request Id
     */
    public static int save() {
        return ++requestId;
    }
}
