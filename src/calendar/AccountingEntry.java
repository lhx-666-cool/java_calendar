package calendar;

public class AccountingEntry {
    private int id;
    private String type;
    private double expense;
    private String keywords;

    public AccountingEntry(int id, String type, double expense, String keywords) {
        this.id = id;
        this.type = type;
        this.expense = expense;
        this.keywords = keywords;
    }

    // 添加getters和setters
}
