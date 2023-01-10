import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    public MainFrame() throws HeadlessException {
        TitlePanel titlePanel = new TitlePanel();
        GamePanel gamePanel = new GamePanel(titlePanel);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                gamePanel.keyEventProcess(e);
            }
        });
        initSplitPane(titlePanel, gamePanel);
        initMenu(gamePanel);
        initFrame();
    }

    private void initSplitPane(JPanel titlePanel, JPanel gamePanel) {
        JSplitPane verticalPane = new JSplitPane();
        verticalPane.setDividerSize(0);
        verticalPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        verticalPane.setDividerLocation(200);
        verticalPane.setBorder(BorderFactory.createEmptyBorder());
        verticalPane.setTopComponent(titlePanel);
        verticalPane.setBottomComponent(gamePanel);
        add(verticalPane);
    }

    private void initMenu(GamePanel gamePanel) {
        JMenuBar bar = new JMenuBar();
        bar.setBorder(BorderFactory.createEmptyBorder());
        this.setJMenuBar(bar);

        JMenu gameMenu = new JMenu("Game");
        JMenuItem restartItem = new JMenuItem("Restart");
        restartItem.addActionListener(e -> gamePanel.startGame());
        gameMenu.add(restartItem);
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(1));
        gameMenu.add(exitItem);
        bar.add(gameMenu);

        JMenu levelMenu = new JMenu("Level");
        JMenuItem[] levelItem = new JMenuItem[3];
        for (int i = 0; i < levelItem.length; i++) {
            levelItem[i] = new JMenuItem("Level" + (i + 1));
            levelMenu.add(levelItem[i]);
            int level = i + 1;
            levelItem[i].addActionListener(e -> gamePanel.setLevel(level));
        }
        bar.add(levelMenu);

    }

    private void initFrame() {
        setTitle("미니 테트리스"); // 타이틀 설정
        setSize(1000, 800); // 메인 프레임 사이즈
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false); // 사이즈 조절 방지
        setVisible(true);
    }
}
