package account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import database.MysqlTutorial;
import http.JettyServer;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class FetchXLS {

    private String fileName = "./src/main/resources/xls/transaktioner.xls";
    private static String KONTOFORM = "kontoform:";
    private MysqlTutorial mysqlTutorial;

    public void startReadXLS() throws IOException, SQLException {
        FileInputStream excelFile = new FileInputStream(new File(fileName));

        //HSSF for .xls and XSSF .xlsx
        //getting the workbook instance for XLS file
        HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

        //creating a Sheet object to retrieve the object
        HSSFSheet sheet = workbook.getSheetAt(0);

        //evaluating cell type
        FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
        createFileForHandelsbanken(sheet);
    }

    public void createFileForHandelsbanken(HSSFSheet sheet) throws IOException, SQLException {

        String name = "";
        String balance = "";
        String clearing = "";
        List<Record> records = new ArrayList<>();
        int cellCounter = 0;
        int rowCounter = 0;
        for(Row row: sheet)     //iteration over row using for each loop
        {
            switch (row.getCell(cellCounter).toString()) {
                case "":
                    break;
                case "Kontoform:":
                    name = row.getCell(++cellCounter).toString();
                    clearing = row.getCell(cellCounter + 3).toString();
                    break;
                case "Saldo:":
                    balance = row.getCell(++cellCounter).toString();
                    break;
                default:
                    if(rowCounter > 6 && row.getCell(cellCounter).toString() != "")
                    {
                        records.add(new Record(row.getCell(cellCounter += 2).toString(),
                                row.getCell(cellCounter += 2).toString(),
                                row.getCell(cellCounter + 2).toString()));
                    }
            }
            rowCounter++;
            cellCounter = 0;
        }
        Account accountInfo = new Account(name, balance, clearing, records);

        //TODO, skicka alla records i accountInfo till databasen. date, amount och event (allt är strings)
        MysqlTutorial mysqlTutorial = new MysqlTutorial();
        mysqlTutorial.makeJDBCConnection();

        for (Record record : accountInfo.getRecords())
            mysqlTutorial.insertRecord(record);

        mysqlTutorial.closeSQLConnection();
    }

    public static void main(String args[]) throws Exception
    {
        JettyServer httpserver = new JettyServer();
        httpserver.start();
        FetchXLS xlsOutput = new FetchXLS();
        xlsOutput.startReadXLS();
    }
}
