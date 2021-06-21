package account;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class FetchXLS {

    public String fileName = "./src/main/resources/xls/transaktioner.xls";

    public static String KONTOFORM = "kontoform:";
    public void startReadXLS() throws IOException {
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

    public void createFileForHandelsbanken(HSSFSheet sheet) throws IOException {

        String name = "";
        String balance = "";
        String clearing = "";
        List<Record> records = new ArrayList<Record>();
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
                    if(rowCounter > 6 && row.getCell(cellCounter).toString() != "") {
                        records.add(new Record(row.getCell(cellCounter += 2).toString(),
                                row.getCell(cellCounter += 2).toString(),
                                row.getCell(cellCounter + 2).toString()));
                    }
            }
            rowCounter++;
            cellCounter = 0;
        }
        Account accountInfo = new Account(name, balance, clearing, records);
        // ObjectMapper is instantiated just like before
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        //jackson by default only detect fields that are not of the visibility: private/package
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        // We write the `Account` into `person2.yaml`
//        objectMapper.writeValue(new File("./src/main/resources/accountHistory.yaml"), accountInfo);
        objectMapper.writeValue(new File("./src/main/resources/accountHistory.yaml"), accountInfo);
        String yamlString = objectMapper.writeValueAsString(accountInfo);
        System.out.println(yamlString);
        //Account employee2 = objectMapper.readValue(yamlString, Account.class);
    }

    public static void main(String args[]) throws Exception
    {
        FetchXLS xlsOutput = new FetchXLS();
        xlsOutput.startReadXLS();
    }
}
