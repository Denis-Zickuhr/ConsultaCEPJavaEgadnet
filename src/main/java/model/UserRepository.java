package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {

    private final Map<Integer, UserEntity> users;

    public UserRepository() throws IOException {
        TextReader tx = new TextReader("public/users.txt");
        List<String> data = tx.returnAsList();
        this.users = new HashMap<>(data.size()/3);
        for (int i = 0; i < data.size(); i = i+3) {
            this.users.put(Integer.valueOf(data.get(i)), new UserEntity(data.get(i+1), data.get(i+2)));
        }
    }

    public Map<Integer, UserEntity> getAll(){
        return users;
    }
    
    public static class TextReader {
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

}
