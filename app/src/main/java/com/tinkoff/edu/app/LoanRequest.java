package com.tinkoff.edu.app;

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
        if (amount > 0 && amount <= 5000000) {
            this.amount = amount;
        } else {
            this.amount = 0;
        }
        if (months > 0 && months <= 60) {
            this.months = months;
        } else {
            this.months = 0;
        }
    }

    public String toString() {
        return "RQ: {"
                + this.type + ", "
                + this.getAmount()
                + " for " + this.getMonths()
                + "}";
    }
}
