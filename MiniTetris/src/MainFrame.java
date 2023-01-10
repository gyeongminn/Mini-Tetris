import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        TitlePanel titlePanel = new TitlePanel();
        GamePanel gamePanel = new GamePanel();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                gamePanel.keyEventProcess(e);
            }
        });
        initSplitPane(titlePanel, gamePanel);
        initFrame();
    }

    private void initSplitPane(JPanel titlePanel, JPanel gamePanel) {
        JSplitPane verticalPane = new JSplitPane();
        verticalPane.setDividerSize(0);
        verticalPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalPane.setDividerLocation(150);
        verticalPane.setBorder(BorderFactory.createEmptyBorder());
        verticalPane.setTopComponent(titlePanel);
        verticalPane.setBottomComponent(gamePanel);
        add(verticalPane);
    }

    private void initFrame() {
        setTitle("미니 테트리스"); // 타이틀 설정
        setSize(1000, 800); // 메인 프레임 사이즈
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); // 사이즈 조절 방지
        setVisible(true);
    }
}
