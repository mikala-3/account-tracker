package account;

import java.util.List;

public class Account {

    private String name;
    private String balance;
    private String clearing;
    private List<Record> Records;

    public Account(String name, String balance, String clearing, List<Record> Records)
    {
        this.name = name;
        this.balance = balance;
        this.clearing = clearing;
        this.Records = Records;
    }

    public List<Record> getRecords()
    {
        return this.Records;
    }


    @Override
    public String toString() {
        return "\nAccountname: " + name + "\nbalance: " + balance + "\nClearing: " + clearing + "\nRecords: " + Records + "\n";
    }
}
