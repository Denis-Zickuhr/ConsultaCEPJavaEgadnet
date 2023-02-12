package view;

public sealed interface LoginObserver permits LoginView {

    void performLoginAttempt(boolean result);

}
