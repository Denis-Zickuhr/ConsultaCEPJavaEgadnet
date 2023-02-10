package filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextReader {
    private final String filepath;
    private BufferedReader file;
    private final List<String> txtList = new ArrayList<>();

    public TextReader(String filepath) throws IOException {
        this.filepath = filepath;
        read();
    }

    private void open() throws FileNotFoundException {
        file = new BufferedReader(new FileReader(this.filepath));
    }

    private void close() throws IOException {
        file.close();
    }

    private void record() throws IOException {
        String line = file.readLine();
        while (line != null) {
            txtList.add(line);
            line = file.readLine();
        }
    }

    private void read() throws IOException {
        open();
        record();
        close();
    }

    public List<String> returnAsList(){
        return txtList;
    }

}
