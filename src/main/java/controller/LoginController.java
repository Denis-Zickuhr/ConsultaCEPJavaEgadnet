package controller;

import model.User;
import model.UserRepository;

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
        UserRepository repo = new UserRepository();

        boolean loginIsValid = repo.getAll().containsValue(user);

        for (LoginObs obs: observer
        ) {
            obs.performLoginAttempt(loginIsValid);
        }
    }

}
