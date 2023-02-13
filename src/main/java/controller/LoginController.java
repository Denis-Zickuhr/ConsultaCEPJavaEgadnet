package controller;

import model.UserEntity;
import model.UserRepository;
import view.LoginObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author denis
 */
public final class LoginController {

    private final List<LoginObserver> observer = new ArrayList<>();

    public void attach(LoginObserver observer) {
        this.observer.add(observer);
    }

    /**
     * Tries to perform a login, send a boolean trough observed screens to perform
     * or not a login
     */
    public void performLoginAttempt(String userName, String password) throws IOException {

        UserRepository repo = new UserRepository();

        for (LoginObserver obs : observer
        ) {
            obs.performLoginAttempt(repo.getAll().containsValue(new UserEntity(userName, password)));
        }
    }

}
