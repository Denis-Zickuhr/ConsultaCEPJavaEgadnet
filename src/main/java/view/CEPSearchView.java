package view;

import controller.CEPSearchController;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 * @author denis
 */
public final class CEPSearchView extends JFrame implements CEPSearchObserver {

    public CEPSearchView() {
        loadStructures();
        loadElements();
    }

    void loadStructures() {
        CEPSearchController controller = CEPSearchController.getInstance();
        controller.attach(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(new Dimension(250, 250));
        setLocationRelativeTo(this);
        setVisible(true);
    }

    void loadElements() {

        JPanel content_pane = new JPanel();
        JLabel infoLabel = new JLabel("Inform a \"CEP\":");
        JLabel userLabel = new JLabel("CEP:");
        JTextField cepTextInput = new JTextField(8);
        JPanel buttonPanel = new JPanel();
        JButton jButton_ok = new JButton("Search");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        content_pane.setLayout(new GridBagLayout());

        ((JComponent) getContentPane()).setBorder(
                new EmptyBorder(5, 8, 8, 8));

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
        gridBagConstraints.gridwidth = 2;
        cepTextInput.addActionListener(e -> {
            if (cepTextInput.getText().length() == 8)
                try {
                    CEPSearchController.getInstance().search(cepTextInput.getText());
                } catch (IOException err) {
                    throw new RuntimeException(err);
                }
        });
        cepTextInput.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                textUpdated(e, cepTextInput, jButton_ok);
            }
        });
        cepTextInput.setColumns(14);
        content_pane.add(cepTextInput, gridBagConstraints);

        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        jButton_ok.setEnabled(false);
        jButton_ok.addActionListener(evt -> {
            try {
                CEPSearchController.getInstance().search(cepTextInput.getText());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        buttonPanel.add(jButton_ok);
        content_pane.add(buttonPanel, gridBagConstraints);

        setContentPane(content_pane);
        content_pane.updateUI();

    }

    @Override
    public void search(JSONObject response) {
        new ResultDisplay(response);
    }

    public void textUpdated(KeyEvent e, JTextField listener, JButton target) {
        if (listener.getText().length() > 7 ){
            e.consume();
        }
        try {
            Integer.parseInt(String.valueOf(e.getKeyChar()));
            target.setEnabled(listener.getText().length() >= 7);
        }catch (NumberFormatException err){
            target.setEnabled(listener.getText().length() >= 8);
            e.consume();
        }
    }

}
