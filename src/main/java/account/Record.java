package account;

public class Record {

    public Record(String date, String event, String amount) {
        this.date = date;
        this.amount = amount;
        this.event = event;
    }
//from testbranch
    public Record() {}

    String date;
    String amount;
    String event;
}
