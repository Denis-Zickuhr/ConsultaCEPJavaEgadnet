package view;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;

public final class ResultDisplay extends JFrame {

    public ResultDisplay(JSONObject result) {
        loadStructures();
        loadElements(result);

    }

    void loadStructures(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(250,250));
        setLocationRelativeTo(this);
        setVisible(true);

    }
    void loadElements(JSONObject result) {
        JPanel content_pane = new JPanel();
        content_pane.add(new JTextArea(result.toString().replace(",", ",\n"), 20, 10));
        setContentPane(content_pane);
        content_pane.updateUI();
    }
}
