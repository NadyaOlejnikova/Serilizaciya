import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;

import au.com.bytecode.opencsv.CSVWriter;

public class ClientLog {
    List<String[]> log = new ArrayList<>();
    CSVWriter writer;
    String[] string = "productNum quantity".split(" ");


    // покупатель добавил покупку, то это действие должно быть сохранено.
    public void log(int productNum, int quantity) {
        log.add(new String[]{String.valueOf(productNum), String.valueOf(quantity)});

    }

    // для сохранения всего журнала действия в файл в формате csv
    public void exportAsCSV(String txtFile) throws IOException {
        try {

            writer = new CSVWriter(new FileWriter(txtFile), ',', CSVWriter.NO_QUOTE_CHARACTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        writer.writeNext(string);
        writer.writeAll(log);
        writer.close();
    }
}
