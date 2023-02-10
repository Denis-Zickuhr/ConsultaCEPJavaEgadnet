package controller;

import filereader.TextReader;
import model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController{

    static LoginController instance;

    protected final List<LoginObs> observer = new ArrayList<>();

    public static LoginController getInstance() {
        if(instance == null){
            instance = new LoginController();
        }
        return instance;
    }

    public void attach(LoginObs observer){
        this.observer.add(observer);
    }
    public void performLoginAttempt (String userName, String password) throws IOException {

        User user = new User(userName, password);
        TextReader tx = new TextReader("public/users.txt");
        List<String> users = tx.returnAsList();

        boolean loginIsValid = false;

        for (int i = 0; i < users.size(); i = i + 2) {
            if (users.get(i).equals(user.getUserName()) & users.get(i + 1).equals(user.getPassword())){
                loginIsValid = true;
                break;
            }
        }

        for (LoginObs obs: observer
        ) {
            obs.performLoginAttempt(loginIsValid);
        }
    }

}
