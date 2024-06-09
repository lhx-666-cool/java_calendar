package org.hxzzz.calendar;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Accountings {
    ObjectMapper objectMapper = new ObjectMapper();

    String filename = "./calendar_maven/src/main/resources/save.json";
    File fileSave;
    List<AccountingEntry> accountings = new ArrayList<>();
    public Accountings() throws IOException {
        fileSave = new File(filename);
        objectMapper.registerModule(new JavaTimeModule());
        accountings = objectMapper.readValue(fileSave, new TypeReference<List<AccountingEntry>>(){});
        for (AccountingEntry a: accountings) {
            System.out.println(a.getType());
        }
    }
    public void add(AccountingEntry accountingEntry) throws IOException {
        accountings.add(accountingEntry);
        save();
    }
    public void save() throws IOException {
        objectMapper.writeValue(fileSave, accountings);
    }
}
