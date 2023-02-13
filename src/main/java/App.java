import controller.LoginController;
import view.LoginView;

public class App
{
    public static void main(String[] args) {
        new LoginView(new LoginController());
    }
}
