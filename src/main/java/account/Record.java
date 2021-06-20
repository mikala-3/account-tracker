package account;

public class Record {
    public Record(String date, String amount, String event) {
        this.date = date;
        this.amount = amount;
        this.event = event;
    }

    String date;
    String amount;
    String event;
}
