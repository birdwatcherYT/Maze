package a_star;

import java.util.*;

public class CreateMaze {

    static final int UP = 0, DOWN = 1, LEFT = 2, RIGHT = 3, FLOOR = 0, WALL = 1, COUNT_MAX = 100000;
    int row, col, maze[][];
    Position startPos, goalPos, nowPos;
    Random rand;

    public CreateMaze(int row, int col) {
        this.row = row;
        this.col = col;
        rand = new Random();
        maze = new int[row][col];
//        buildFromBarFallMethod();//棒倒し法
        buildFromDigMethod();//穴掘り法
    }

    /**
     * スタートにキャラクターセット
     */
    void fromStart() {
        nowPos.setPosition(startPos.row, startPos.col);
    }

    /**
     * キャラクターを移動させる
     *
     * @param action 上、下、左、右 のいずれかの行動番号
     */
    void next(int action) {
        switch (action) {
            case UP:
                if (!isWall(nowPos.row - 1, nowPos.col)) {
                    nowPos.row--;
                }
                break;
            case DOWN:
                if (!isWall(nowPos.row + 1, nowPos.col)) {
                    nowPos.row++;
                }
                break;
            case LEFT:
                if (!isWall(nowPos.row, nowPos.col - 1)) {
                    nowPos.col--;
                }
                break;
            case RIGHT:
                if (!isWall(nowPos.row, nowPos.col + 1)) {
                    nowPos.col++;
                }
                break;
        }
    }

    /**
     * 指定した場所が壁かどうか判断する
     */
    boolean isWall(int row, int col) {
        return maze[row][col] == WALL;
    }

    /**
     * 現在の地点がゴールかどうか判断する
     */
    boolean isGoal() {
        return (nowPos.row == goalPos.row && nowPos.col == goalPos.col);
    }

    /**
     * 棒倒し法によるランダム迷路の形成。
     */
    void buildFromBarFallMethod() {
        this.startPos = new Position(1, 1);
        this.goalPos = new Position(row - 2, col - 2);
        nowPos = new Position(startPos.row, startPos.col);
        fromStart();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = FLOOR;
            }
        }
        //外壁
        for (int i = 0; i < col; i++) {
            maze[0][i] = WALL;
            maze[row - 1][i] = WALL;
        }
        for (int i = 0; i < row; i++) {
            maze[i][0] = WALL;
            maze[i][col - 1] = WALL;
        }
        // 内壁を1つおきに作る
        for (int i = 0; i < row; i += 2) {
            for (int j = 0; j < col; j += 2) {
                maze[i][j] = WALL;
            }
        }
        //一番上の内壁のみから、棒を倒す
        for (int j = 2; j < col - 1; j += 2) {
            int i = 2;
            boolean flag = true;
            while (flag) {
                int dir = rand.nextInt(4);
                switch (dir) {
                    case UP:
                        if (!isWall(i - 1, j)) {
                            maze[i - 1][j] = WALL;
                            flag = false;
                        }
                        break;
                    case DOWN:
                        if (!isWall(i + 1, j)) {
                            maze[i + 1][j] = WALL;
                            flag = false;
                        }
                        break;
                    case LEFT:
                        if (!isWall(i, j - 1)) {
                            maze[i][j - 1] = WALL;
                            flag = false;
                        }
                        break;
                    case RIGHT:
                        if (!isWall(i, j + 1)) {
                            maze[i][j + 1] = WALL;
                            flag = false;
                        }
                        break;
                }
            }
        }
        //内壁から各方向に壁を作る
        for (int i = 4; i < row - 1; i += 2) {
            for (int j = 2; j < col - 1; j += 2) {
                boolean flag = true;
                while (flag) {
                    //UP以外で倒す
                    int dir = rand.nextInt(3) + 1;
                    switch (dir) {
                        /*
                         case UP:
                         if (!isWall(i - 1, j)) {
                         maze[i - 1][j] = WALL;
                         flag = false;
                         }
                         break;
                         */
                        case DOWN:
                            if (!isWall(i + 1, j)) {
                                maze[i + 1][j] = WALL;
                                flag = false;
                            }
                            break;
                        case LEFT:
                            if (!isWall(i, j - 1)) {
                                maze[i][j - 1] = WALL;
                                flag = false;
                            }
                            break;
                        case RIGHT:
                            if (!isWall(i, j + 1)) {
                                maze[i][j + 1] = WALL;
                                flag = false;
                            }
                            break;
                    }
                }
            }
        }
    }

    /**
     * 道延ばし法(穴彫り法)によるランダム迷路形成
     */
    void buildFromDigMethod() {
        startPos = new Position(2, 2);
        goalPos = new Position(row - 3, col - 3);
        nowPos = new Position(startPos.row, startPos.col);
        fromStart();
        //すべて壁にする
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                maze[i][j] = WALL;
            }
        }
        //外周を道で囲む
        for (int i = 0; i < col; i++) {
            maze[0][i] = FLOOR;
            maze[row - 1][i] = FLOOR;
        }
        for (int i = 0; i < row; i++) {
            maze[i][0] = FLOOR;
            maze[i][col - 1] = FLOOR;
        }
        int i = 2, j = 2, count = 0;
        maze[i][j] = FLOOR;
        boolean right, left, up, down;
        while (!end() && count < COUNT_MAX) {
            right = left = up = down = true;
            count++;
//            System.out.println(count);
            while (right || left || up || down) {
                int dir = rand.nextInt(4);
                switch (dir) {
                    case UP:
                        if (isWall(i - 2, j)) {
                            i--;
                            maze[i][j] = FLOOR;
                            i--;
                            maze[i][j] = FLOOR;
                        } else {
                            up = false;
                        }
                        break;
                    case DOWN:
                        if (isWall(i + 2, j)) {
                            i++;
                            maze[i][j] = FLOOR;
                            i++;
                            maze[i][j] = FLOOR;
                        } else {
                            down = false;
                        }
                        break;
                    case LEFT:
                        if (isWall(i, j - 2)) {
                            j--;
                            maze[i][j] = FLOOR;
                            j--;
                            maze[i][j] = FLOOR;
                        } else {
                            left = false;
                        }
                        break;
                    case RIGHT:
                        if (isWall(i, j + 2)) {
                            j++;
                            maze[i][j] = FLOOR;
                            j++;
                            maze[i][j] = FLOOR;
                        } else {
                            right = false;
                        }
                        break;
                }
            }
            i = rand.nextInt(row / 2 - 2) * 2 + 2;
            j = rand.nextInt(col / 2 - 2) * 2 + 2;
            while (maze[i][j] == WALL) {
                i = rand.nextInt(row / 2 - 2) * 2 + 2;
                j = rand.nextInt(col / 2 - 2) * 2 + 2;
            }
        }
        if (count == COUNT_MAX) {
            maze[row - 3][col - 3] = FLOOR;
            if (rand.nextInt(2) == UP) {
                maze[row - 4][col - 3] = FLOOR;
            } else {
                maze[row - 3][col - 4] = FLOOR;
            }
        }
    }

    boolean end() {
        for (int i = 2; i < row; i += 2) {
            for (int j = 2; j < col; j += 2) {
                if (isWall(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
}
