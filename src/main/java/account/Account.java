package account;

import java.util.List;

public class Account {

    public Account(String name, String balance, String clearing, List<Record> Records) {
        this.name = name;
        this.balance = balance;
        this.clearing = clearing;
        this.Records = Records;
    }

    String name;
    String balance;
    String clearing;
    List<Record> Records;


    @Override
    public String toString() {
        return "\nAccountname: " + name + "\nbalance: " + balance + "\nClearing: " + clearing + "\nRecords: " + Records + "\n";
    }

}
