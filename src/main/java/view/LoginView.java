package view;

import controller.LoginController;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class LoginView extends JFrame implements LoginObserver {

    protected static JPanel content_pane = new JPanel();
    protected static JLabel msg;
    protected static JPasswordField passwordField;
    protected static JTextField userTextInput;
    protected static LoginController controller;

    protected static JButton jButton_ok;

    public LoginView() {
        loadStructures();
        loadElements();
        setContentPane(content_pane);
        content_pane.updateUI();
    }

    void loadStructures() {
        controller = LoginController.getInstance();
        controller.attach(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(300, 300));
        setLocationRelativeTo(this);
        setVisible(true);
    }

    void loadElements() {

        content_pane.setLayout(new GridBagLayout());

        ((JComponent) getContentPane()).setBorder(
                new EmptyBorder(5, 8, 8, 8));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel infoLabel = new JLabel("Dados do Usuário:");
        infoLabel.setFont(new Font("SansSerif",
                Font.BOLD, 14));
        infoLabel.setBorder(
                new EmptyBorder(0, 0, 5, 0));

        content_pane.add(infoLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        JLabel userLabel = new JLabel("Usuário:");
        content_pane.add(userLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        userTextInput = new JTextField(13);
        content_pane.add(userTextInput, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel userPassword = new JLabel("Senha:");
        content_pane.add(userPassword, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        passwordField = new JPasswordField(13);
        content_pane.add(passwordField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 2;
        gbc.gridwidth = 2;
        JRadioButton showPasswordBtn = new JRadioButton();
        showPasswordBtn.addActionListener(evt -> {
            if (showPasswordBtn.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }

        });
        content_pane.add(showPasswordBtn, gbc);

        gbc.gridy = 3;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel();

        jButton_ok = new JButton("OK");
        jButton_ok.addActionListener(evt -> {
            try {
                LoginController.getInstance().performLoginAttempt(userTextInput.getText(), new String(passwordField.getPassword()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonPanel.add(jButton_ok);
        content_pane.add(buttonPanel, gbc);

        gbc.insets = new Insets(3, 3, 3, 3);
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        msg = new JLabel(" ");
        msg.setFont(new Font("SansSerif",
                Font.BOLD, 12));
        msg.setForeground(Color.RED);
        msg.setBorder(
                new EmptyBorder(0, 0, 5, 0));

        content_pane.add(msg, gbc);
    }

    @Override
    public void performLoginAttempt(boolean result) {
        if (result) {
            this.dispose();
            new CEPSearchView();
        } else {
            msg.setText("Senha/Usuário incorretos..");
        }
    }

}
