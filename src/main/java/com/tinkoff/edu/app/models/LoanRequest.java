package com.tinkoff.edu.app.models;

import com.tinkoff.edu.app.enums.LoanType;

/**
 * Created on 20.08.2021
 *
 * @author Natalya Vinogradova
 */
public class LoanRequest {
    private final LoanType type;
    private final int amount;
    private final int months;

    public int getAmount() {
        return amount;
    }

    public int getMonths() {
        return months;
    }

    public LoanType getType() {
        return type;
    }

    public LoanRequest(LoanType type, int amount, int months) {
        this.type = type;
        this.amount = amount;
        this.months = months;
    }

    public String toString() {
        return "RQ: {"
                + this.type + ", "
                + this.getAmount()
                + " for " + this.getMonths()
                + "}";
    }
}
