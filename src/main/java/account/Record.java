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


/*
    This comment i want to use with the cherry pick, and now i changed it for another commit (this should not be cherry picked)
 */