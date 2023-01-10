import javax.swing.*;
import java.awt.*;

public class TitlePanel extends JPanel {

    private JLabel scoreLabel;
    private JLabel levelLabel;

    public TitlePanel() {
        setLayout(new GridLayout(1, 3));

        levelLabel = new JLabel();
        levelLabel.setHorizontalAlignment(JLabel.CENTER);
        levelLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(levelLabel);

        JLabel titleLabel = new JLabel("미니 테트리스");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 50));
        add(titleLabel);

        scoreLabel = new JLabel();
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 30));
        add(scoreLabel);
    }

    public void setScore(int score) {
        scoreLabel.setText("Score : " + score);
    }

    public void setLevel(int level) {
        levelLabel.setText("Level : " + level);
    }
}
