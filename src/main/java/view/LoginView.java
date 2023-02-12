package view;

import controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public final class LoginView extends JFrame implements LoginObserver {

    private static JLabel msg;

    public LoginView() {
        loadStructures();
        loadElements();
    }

    void loadStructures() {
        LoginController controller = LoginController.getInstance();
        controller.attach(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 300));
        setLocationRelativeTo(this);
        setVisible(true);
    }

    void loadElements() {

        JPanel content_pane = new JPanel();
        JLabel infoLabel = new JLabel("User data :");
        JLabel userLabel = new JLabel("User :");
        JTextField userTextInput = new JTextField(13);
        JLabel userPassword = new JLabel("Password :");
        JPasswordField passwordField = new JPasswordField(13);
        JPanel buttonPanel = new JPanel();
        JButton jButton_ok = new JButton("Perform Login");

        content_pane.setLayout(new GridBagLayout());
        ((JComponent) getContentPane()).setBorder(
                new EmptyBorder(5, 8, 8, 8));

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        infoLabel.setFont(new Font("SansSerif",
                Font.BOLD, 14));
        infoLabel.setBorder(
                new EmptyBorder(0, 0, 5, 0));

        content_pane.add(infoLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 1;
        content_pane.add(userLabel, gridBagConstraints);

        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridx = 1;
        content_pane.add(userTextInput, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 0;
        content_pane.add(userPassword, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 1;
        content_pane.add(passwordField, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridwidth = 2;
        JRadioButton showPasswordBtn = new JRadioButton();
        showPasswordBtn.addActionListener(evt -> {
            if (showPasswordBtn.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }

        });
        content_pane.add(showPasswordBtn, gridBagConstraints);

        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        jButton_ok.addActionListener(evt -> {
            try {
                LoginController.getInstance().performLoginAttempt(userTextInput.getText(), new String(passwordField.getPassword()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonPanel.add(jButton_ok);

        content_pane.add(buttonPanel, gridBagConstraints);

        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        msg = new JLabel(" ");
        msg.setFont(new Font("SansSerif",
                Font.BOLD, 12));
        msg.setForeground(Color.RED);
        msg.setBorder(
                new EmptyBorder(0, 0, 5, 0));
        content_pane.add(msg, gridBagConstraints);

        setContentPane(content_pane);
        content_pane.updateUI();

    }

    @Override
    public void performLoginAttempt(boolean result) {
        if (result) {
            this.dispose();
            new CEPSearchView();
        } else {
            msg.setText("User or Password are incorrect!");
        }
    }

}
