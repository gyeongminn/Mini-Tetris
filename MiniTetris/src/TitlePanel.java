import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    public TitlePanel() {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("미니 테트리스");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("맑은 고딕", Font.PLAIN, 50));
        add(title, BorderLayout.CENTER);
    }
}
