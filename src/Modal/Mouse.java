package Modal;

public class Mouse {
    char mouse;
    int mouseLocationRow;
    int mouseLocationColumn;

    public Mouse(char mouse, int mouseLocationRow, int mouseLocationColumn) {
        this.mouse = mouse;
        this.mouseLocationRow = mouseLocationRow;
        this.mouseLocationColumn = mouseLocationColumn;
    }

    public char getMouse() {
        return mouse;
    }

    public int getMouseLocationColumn() {
        return mouseLocationColumn;
    }

    public int getMouseLocationRow() {
        return mouseLocationRow;
    }

//    public boolean checkValidIndex(int mouseLocationRow, int mouseLocationColumn) {
//        char[][] maze = MazeGenerator.getMazeByReference();
//        if (mouseLocationRow <= 0 || mouseLocationRow >= MazeGenerator.totalRows - 1 || mouseLocationColumn <= 0
//                || mouseLocationColumn >= MazeGenerator.totalColumns - 1 || maze[mouseLocationRow][mouseLocationColumn] == MazeGenerator.WALL) {
//            return false;
//        }
//        return true;
//    }

    public void setMouseLocation(int mouseLocationRow, int mouseLocationColumn) {

        this.mouseLocationRow = mouseLocationRow;
        this.mouseLocationColumn = mouseLocationColumn;

    }

    public void makeMouseListenForKeyEvents() {

    }
}
