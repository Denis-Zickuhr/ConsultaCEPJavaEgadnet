package controller;

import model.UserEntity;
import model.UserRepository;
import view.LoginObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    static LoginController instance;

    protected final List<LoginObserver> observer = new ArrayList<>();

    public static LoginController getInstance() {
        if (instance == null) {
            instance = new LoginController();
        }
        return instance;
    }

    public void attach(LoginObserver observer) {
        this.observer.add(observer);
    }

    public void performLoginAttempt(String userName, String password) throws IOException {

        UserEntity user = new UserEntity(userName, password);
        UserRepository repo = new UserRepository();

        boolean loginIsValid = repo.getAll().containsValue(user);

        for (LoginObserver obs : observer
        ) {
            obs.performLoginAttempt(loginIsValid);
        }
    }

}
