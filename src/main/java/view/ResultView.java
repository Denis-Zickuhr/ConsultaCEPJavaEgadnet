package view;
import org.json.JSONObject;
import javax.swing.*;
import java.awt.*;

public class ResultView extends JFrame {

    protected static JPanel content_pane = new JPanel();
    public ResultView(JSONObject result) {
        loadStructures();
        loadElements(result);
        setContentPane(content_pane);
        content_pane.updateUI();
    }

    void loadStructures(){
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(new Dimension(250,250));
        setLocationRelativeTo(this);
        setVisible(true);
    }
    void loadElements(JSONObject result) {
        content_pane = new JPanel();
        content_pane.add(new JTextArea(result.toString().replace(",", ",\n"), 20, 10));
    }
}
