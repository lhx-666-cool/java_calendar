package org.hxzzz.calendar;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Date;

public class AccountingEntry {
    private int id;
    private String type;
    private double expense;
    private String keywords;
    LocalDate date;
    @ConstructorProperties({"id", "type", "expense", "keywords", "date"})
    public AccountingEntry(int id, String type, double expense, String keywords, LocalDate date) {
        this.id = id;
        this.type = type;
        this.expense = expense;
        this.keywords = keywords;
        this.date = date;
    }
    public LocalDate getDate() {
        return date;
    }
    public double getExpense() {
        return expense;
    }
    public String getType() {
        return type;
    }
    public String getKeywords() {
        return keywords;
    }
    public int getId() {
        return id;
    }
}
