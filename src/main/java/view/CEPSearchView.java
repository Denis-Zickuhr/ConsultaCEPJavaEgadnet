package view;

import controller.CEPSearchController;
import controller.CEPSearchObs;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

public class CEPSearchView extends JFrame implements CEPSearchObs {

    protected static JPanel content_pane = new JPanel();
    protected static CEPSearchController controller;
    public CEPSearchView() {
        loadStructures();
        loadElements();
        setContentPane(content_pane);
        content_pane.updateUI();
    }

    void loadStructures(){
        controller = CEPSearchController.getInstance();
        controller.attach(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(250,250));
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
        JTextField cepTextInput = new JTextField(8);
        cepTextInput.setColumns(14);
        content_pane.add(cepTextInput, gbc);

        gbc.gridy = 2;
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;

        JPanel buttonPanel = new JPanel();

        JButton okbtn = new JButton("OK");
        okbtn.addActionListener(evt ->{
            try {
                CEPSearchController.getInstance().search(cepTextInput.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonPanel.add(okbtn);
        content_pane.add(buttonPanel, gbc);
    }

    @Override
    public void search(JSONObject response) {
        new ResultView(response);
    }
}
