package a_star;

import static java.lang.Math.*;
import java.util.ArrayList;

public class AstarRun {

    final static int RIGHT = 0, LEFT = 1, DOWN = 2, UP = 3;
    ArrayList<Z2> open, closed;
    int cost = 0, startRow, startCol, endRow, endCol, rowMax, colMax;
    public Z2 z2[][];

    AstarRun(int rowMax, int colMax) {
        this.rowMax = rowMax;
        this.colMax = colMax;
        open = new ArrayList<>();
        closed = new ArrayList<>();
        z2 = new Z2[rowMax][colMax];
        for (int i = 0; i < rowMax; i++) {
            for (int j = 0; j < colMax; j++) {
                z2[i][j] = new Z2(i, j);
                z2[i][j].score = 0;
                z2[i][j].direction = -1;
            }
        }
    }

    public void run() {
        int minIndex, nowRow, nowCol;
        open.add(z2[startRow][startCol]);
        while (!open.isEmpty()) {
            minIndex = 0;
            for (int i = 0; i < open.size(); i++) {
                if (open.get(minIndex).score > open.get(i).score) {
                    minIndex = i;
                    break;
                }
            }
            nowRow = open.get(minIndex).i;
            nowCol = open.get(minIndex).j;

            if (z2[nowRow][nowCol] == z2[endRow][endCol]) {
                break;
            } else {
                open.remove(minIndex);
                closed.add(z2[nowRow][nowCol]);
                cost++;
                //ã
                if (nowRow - 1 >= 0 && !open.contains(z2[nowRow - 1][nowCol]) && !closed.contains(z2[nowRow - 1][nowCol])) {
                    z2[nowRow - 1][nowCol].score = calcu(nowRow - 1, nowCol);
                    z2[nowRow - 1][nowCol].direction = DOWN;
                    open.add(z2[nowRow - 1][nowCol]);
                }
                //‰º
                if (nowRow + 1 < rowMax && !open.contains(z2[nowRow + 1][nowCol]) && !closed.contains(z2[nowRow + 1][nowCol])) {
                    z2[nowRow + 1][nowCol].score = calcu(nowRow + 1, nowCol);
                    z2[nowRow + 1][nowCol].direction = UP;
                    open.add(z2[nowRow + 1][nowCol]);
                }
                //¶
                if (nowCol - 1 >= 0 && !open.contains(z2[nowRow][nowCol - 1]) && !closed.contains(z2[nowRow][nowCol - 1])) {
                    z2[nowRow][nowCol - 1].score = calcu(nowRow, nowCol - 1);
                    z2[nowRow][nowCol - 1].direction = RIGHT;
                    open.add(z2[nowRow][nowCol - 1]);
                }
                //‰E
                if (nowCol + 1 < colMax && !open.contains(z2[nowRow][nowCol + 1]) && !closed.contains(z2[nowRow][nowCol + 1])) {
                    z2[nowRow][nowCol + 1].score = calcu(nowRow, nowCol + 1);
                    z2[nowRow][nowCol + 1].direction = LEFT;
                    open.add(z2[nowRow][nowCol + 1]);
                }
            }
        }
    }

    int calcu(int row, int col) {
        return cost + abs(endRow - row) + abs(endCol - col);
    }

    public void start(int row, int col) {
        startRow = row;
        startCol = col;
    }

    public void end(int row, int col) {
        endRow = row;
        endCol = col;
    }

    public void wall(int row, int col) {
        closed.add(z2[row][col]);
    }

    class Z2 {

        int i, j, score, direction;

        Z2(int i, int j) {
            this.i = i;
            this.j = j;
        }
    }
}
