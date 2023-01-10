import java.awt.*;

public class Block {

    private int x, y;
    private Color color;
    private boolean deleted;

    public Block(int x, int y) {
        this.x = x;
        this.y = y;
        this.color = getRandomColor();
        this.deleted = false;
    }

    public Color getRandomColor() {
        switch ((int) (Math.random() * 4)) {
            case 1 -> {
                return Color.YELLOW;
            }
            case 2 -> {
                return Color.GREEN;
            }
            case 3 -> {
                return Color.BLUE;
            }
            default -> {
                return Color.MAGENTA;
            }
        }
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void delete() {
        this.deleted = true;
    }
}
