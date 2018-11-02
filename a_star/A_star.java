package a_star;

import java.awt.*;
import javax.swing.*;
import static a_star.AstarRun.*;

/* A*�[���R�[�h
 --------------------------------------------------------
 �I�[�v�����X�g�ɊJ�n�m�[�h��ǉ�����
 while(�I�[�v�����X�g����łȂ�){
 ���݂̃m�[�h=�I�[�v�����X�g�̍ł������ȃm�[�h
 if(���݂̃m�[�h==�ړI�n�̃m�[�h){
 �o�H����
 }else{
 ���݂̃m�[�h���N���[�Y�h���X�g�Ɉڂ�
 for(���݂̃m�[�h�ɗאڂ���e�m�[�h){
 if(�I�[�v�����X�g�Ɋ܂܂�ĂȂ�&&�N���[�Y�h���X�g�Ɋ܂܂�ĂȂ�&&��Q���łȂ�){
 �I�[�v�����X�g�Ɉڂ��ăR�X�g���v�Z����
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
        super("A-star�T��");

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
            ROW = 35, COL = 61,//�K������m
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
                    g.drawString("��", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                } else if (DOWN == a.z2[i][j].direction) {
                    g.drawString("��", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                } else if (LEFT == a.z2[i][j].direction) {
                    g.drawString("��", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                } else if (RIGHT == a.z2[i][j].direction) {
                    g.drawString("��", j * GRID_SIZE, i * GRID_SIZE + GRID_SIZE);
                }
            }
        }
        g.setColor(Color.red);
        g.fillRect(a.startCol * GRID_SIZE, a.startRow * GRID_SIZE, GRID_SIZE, GRID_SIZE);
        g.setColor(Color.blue);
        g.fillRect(a.endCol * GRID_SIZE, a.endRow * GRID_SIZE, GRID_SIZE, GRID_SIZE);
    }
}
