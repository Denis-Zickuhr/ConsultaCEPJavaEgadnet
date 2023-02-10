package view;

import controller.CEPSearchController;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class CEPSearchView extends JFrame implements CEPSearchObserver {

    protected static JPanel content_pane = new JPanel();
    protected static CEPSearchController controller;
    protected static JTextField cepTextInput;
    protected static JButton jButton_ok;

    public CEPSearchView() {
        loadStructures();
        loadElements();
        setContentPane(content_pane);
        content_pane.updateUI();
    }

    void loadStructures() {
        controller = CEPSearchController.getInstance();
        controller.attach(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(250, 250));
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
        JLabel infoLabel = new JLabel("Informe o CEP:");
        infoLabel.setFont(new Font("SansSerif",
                Font.BOLD, 14));
        infoLabel.setBorder(
                new EmptyBorder(0, 0, 5, 0));

        content_pane.add(infoLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;

        JLabel userLabel = new JLabel("CEP:");
        content_pane.add(userLabel, gbc);

        gbc.gridy = 1;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        cepTextInput = new JTextField(8);
        cepTextInput.addActionListener(e -> {
            if (cepTextInput.getText().length() == 8)
                try {
                    CEPSearchController.getInstance().search(cepTextInput.getText());
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
        });

        // For text input validation eg. max size and alphanumeric characters
        cepTextInput.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                textUpdated(e);
            }
        });

        cepTextInput.setColumns(14);
        content_pane.add(cepTextInput, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel();

        jButton_ok = new JButton("OK");
        jButton_ok.setEnabled(false);
        jButton_ok.addActionListener(evt -> {
            try {
                CEPSearchController.getInstance().search(cepTextInput.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonPanel.add(jButton_ok);
        content_pane.add(buttonPanel, gbc);
    }

    @Override
    public void search(JSONObject response) {
        new ResultDisplay(response);
    }

    public void textUpdated(KeyEvent e) {
        if (cepTextInput.getText().length() > 7 ){
            e.consume();
        }
        try {
            Integer.parseInt(String.valueOf(e.getKeyChar()));
            jButton_ok.setEnabled(cepTextInput.getText().length() >= 7);
        }catch (NumberFormatException err){
            jButton_ok.setEnabled(cepTextInput.getText().length() >= 8);
            e.consume();
        }
    }

}
