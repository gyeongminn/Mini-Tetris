import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class GamePanel extends JPanel {

    final int N_BLOCK = 10;
    final int BLOCK_SIZE = 50;

    private GameThread gameThread;
    private ArrayList<Block> blocks;

    public GamePanel() {
        setLayout(null);
        startGame();
    }

    private void startGame() {
        blocks = new ArrayList<>(100);
        gameThread = new GameThread();
        gameThread.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 블럭 모두 그리기
        for (Block block : blocks) {
            int x = block.getX() * BLOCK_SIZE + 250;
            int y = block.getY() * BLOCK_SIZE + 50;
            g.setColor(block.getColor());
            g.fillRect(x, y, BLOCK_SIZE, BLOCK_SIZE);
        }

        // 배경 격자 그리기
        g.setColor(Color.BLACK);
        for (int i = 0; i < 11; i++) {
            int x = i * 50 + 250;
            g.drawLine(x, 50, x, 550);
            int y = i * 50 + 50;
            g.drawLine(250, y, 750, y);
        }
    }

    public void keyEventProcess(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT, KeyEvent.VK_A -> gameThread.moveLeft();
            case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> gameThread.moveRight();
            case KeyEvent.VK_SPACE, KeyEvent.VK_DOWN, KeyEvent.VK_S -> gameThread.moveBottom();
            case KeyEvent.VK_R -> startGame();
        }
    }

    private class GameThread extends Thread {

        private Block currentBlock;

        @Override
        public void run() {
            super.run();
            currentBlock = getNewBlock(); // 블럭 초기화

            while (true) {
                boolean flag = true;
                if (isBottom(currentBlock)) { // 블럭이 바닥에 닿으면
                    while (checkBlock() && flag) {
                        flag = false;
                        for (Block block : blocks) {
                            if (!isBottom(block)) {
                                block.setY(block.getY() + 1);
                                flag = true;
                            }
                        }
                    }
                    currentBlock = getNewBlock(); // 블럭 새로 생성
                } else { // 내려오는 중이면
                    currentBlock.setY(currentBlock.getY() + 1); // 블럭 내리기
                }
                Iterator<Block> iterator = blocks.iterator(); // 안전한 삭제를 위해 iterator 사용
                while (iterator.hasNext()) {
                    Block block = iterator.next();
                    if (block.isDeleted()) { // 블럭 삭제 처리
                        iterator.remove();
                    }
                    if (block != currentBlock && block.getY() == 0) { // 게임 종료 판정
                        gameOver();
                        return;
                    }
                }
                repaint(); // 그리기
                try {
                    sleep(500); // 딜레이
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        private boolean checkBlock() {
            boolean isFound = false;
            boolean[][] marked = new boolean[N_BLOCK][N_BLOCK];
            Color[][] map = new Color[N_BLOCK][N_BLOCK];
            for (int i = 0; i < N_BLOCK; i++) {
                map[i] = new Color[N_BLOCK];
                marked[i] = new boolean[N_BLOCK];
            }
            for (Block block : blocks) {
                map[block.getY()][block.getX()] = block.getColor();
            }
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    // 가로 3개
                    if (j < 8 && map[i][j] == map[i][j + 1] && map[i][j] == map[i][j + 2]) {
                        marked[i][j] = true;
                        marked[i][j + 1] = true;
                        marked[i][j + 2] = true;
                    }
                    // 세로 3개
                    if (i < 8 && map[i][j] == map[i + 1][j] && map[i][j] == map[i + 2][j]) {
                        marked[i][j] = true;
                        marked[i + 1][j] = true;
                        marked[i + 2][j] = true;
                    }
                    if (i > 7 || j > 7) continue;
                    // 대각선 3개 (우하향)
                    if (map[i][j] == map[i + 1][j + 1] && map[i][j] == map[i + 2][j + 2]) {
                        marked[i][j] = true;
                        marked[i + 1][j + 1] = true;
                        marked[i + 2][j + 2] = true;
                    }
                    // 대각성 3개 (우상향)
                    if (map[i + 2][j] == map[i + 1][j + 1] && map[i + 2][j] == map[i][j + 2]) {
                        marked[i + 2][j] = true;
                        marked[i + 1][j + 1] = true;
                        marked[i][j + 2] = true;
                    }
                }
            }
            for (Block block : blocks) {
                if (marked[block.getY()][block.getX()]) {
                    block.delete();
                    isFound = true;
                }
            }
            return isFound;
        }

        private void gameOver() {
            interrupt();
        }

        private boolean isBottom(Block currentBlock) {
            if (currentBlock.getY() >= 9) {
                return true;
            }
            for (Block block : blocks) {
                if (!block.isDeleted() && block.getX() == currentBlock.getX() && block.getY() == currentBlock.getY() + 1) {
                    return true;
                }
            }
            return false;
        }

        private Block getNewBlock() {
            int x = (int) (Math.random() * 10); // 랜덤 x 좌표
            Block block = new Block(x, 0);
            blocks.add(block); // ArrayList 에 블럭 추가
            return block;
        }

        private void moveCurrentBlock(int x, int y) {
            for (Block block : blocks) { // 중복 방지
                if (block != currentBlock && block.getX() == x && block.getY() == y) {
                    return;
                }
            }
            if (x >= 0 && x <= N_BLOCK - 1 && y >= 0 && y <= N_BLOCK - 1) {
                currentBlock.setX(x);
                currentBlock.setY(y);
            }
        }

        public void moveLeft() {
            moveCurrentBlock(currentBlock.getX() - 1, currentBlock.getY());
        }

        public void moveRight() {
            moveCurrentBlock(currentBlock.getX() + 1, currentBlock.getY());
        }

        public void moveBottom() {
            while (!isBottom(currentBlock)) { // 블럭이 바닥에 닿을 때까지
                moveCurrentBlock(currentBlock.getX(), currentBlock.getY() + 1);
            }
        }
    }
}
