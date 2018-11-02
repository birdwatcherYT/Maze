package a_star;

import java.awt.*;
import javax.swing.*;
import static a_star.AstarRun.*;

/* A*擬似コード
 --------------------------------------------------------
 オープンリストに開始ノードを追加する
 while(オープンリストが空でない){
 現在のノード=オープンリストの最も安価なノード
 if(現在のノード==目的地のノード){
 経路完成
 }else{
 現在のノードをクローズドリストに移す
 for(現在のノードに隣接する各ノード){
 if(オープンリストに含まれてない&&クローズドリストに含まれてない&&障害物でない){
 オープンリストに移してコストを計算する
 }
 }
 }
 }
 --------------------------------------------------------
 */

public class A_star extends JFrame {

    public static void main(String[] args) {
        new A_star();
    }

    A_star() {
        super("A-star探索");

        MyPanel panel = new MyPanel();
        Container contentPain = getContentPane();
        contentPain.add(panel);

        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class MyPanel extends JPanel {

    final static int GRID_SIZE = 20,
            ROW = 35, COL = 61,//必ず奇数同士
            STEP_MAX = 10000,
            W = GRID_SIZE * COL, H = GRID_SIZE * ROW;
    CreateMaze createMaze;

    static AstarRun a;

    MyPanel() {
        setPreferredSize(new Dimension(W, H));
        createMaze = new CreateMaze(ROW, COL);

        a = new AstarRun(ROW, COL);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (createMaze.maze[i][j] == CreateMaze.WALL) {
                    a.wall(i, j);
                }
            }
        }

        a.start(createMaze.startPos.row, createMaze.startPos.col);
        a.end(createMaze.goalPos.row, createMaze.goalPos.col);
        a.run();
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, W, H);
        g.setColor(Color.black);
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (createMaze.maze[i][j] == CreateMaze.WALL) {
                    g.fillRect(j * GRID_SIZE, i * GRID_SIZE, GRID_SIZE, GRID_SIZE);
                }
            }
        }
        g.setFont(new Font(null, Font.BOLD, GRID_SIZE));
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                if (UP == a.z2[i][j].direction) {
                    g.drawString("↑", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                } else if (DOWN == a.z2[i][j].direction) {
                    g.drawString("↓", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                } else if (LEFT == a.z2[i][j].direction) {
                    g.drawString("←", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                } else if (RIGHT == a.z2[i][j].direction) {
                    g.drawString("→", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                }
            }
        }
        g.setColor(Color.red);
        g.fillRect(a.startCol * GRID_SIZE, a.startRow * GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.blue);
        g.fillRect(a.endCol * GRID_SIZE, a.endRow * GRID_SIZE, GRID_SIZE, GRID_SIZE);
    }
}
