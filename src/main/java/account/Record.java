package account;

public class Record {

    private String date;
    private String amount;
    private String event;

    public Record(String date, String event, String amount) {
        this.date = date;
        this.amount = amount;
        this.event = event;
    }

    public String getDate() { return this.date; }
    public String getAmount() { return this.amount; }
    public String getEvent() { return this.event; }


}