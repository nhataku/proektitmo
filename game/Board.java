package game;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class Board extends JPanel {
    private   int N = 20;
    private   int M = 20;
    public static final int DRAW = 0;
    public static final int WIN = 1;
    public static final int NORMAL = 2;
    public EndGame endGame;
    public Image imgX;
    public Image imgO;
    public Cell[][] matrix = new Cell[N][M];
    public String currentPlayer = Cell.EMPTY;
    public Board(String player){
        this.currentPlayer = player;
    }
    public void setCurrentPlayer(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    public void setEndGame(EndGame endGame) {this.endGame = endGame;}
    public String getCurrentPlayer() {
        return currentPlayer;
    }
    public void paint(Graphics g) {
        super.paintComponent(g);
        int w = getWidth() / M;
        int h = getHeight() / N;
        Graphics2D graphic2d = (Graphics2D) g;
        for (int i = 0; i < N; i++){
            for (int j = 0; j < M; j++){
                g.drawLine(j * w, 0, j * w, getWidth());
                g.drawLine(0, i * h,  getHeight(), i * h);
                int x = j * w;
                int y = i * h;
                Cell cell = matrix[i][j];
                cell.setX(x);
                cell.setY(y);
                cell.setW(w);
                cell.setH(h);
                if(cell.getValue().equals(Cell.X)){
                    Image img = imgX;
                    graphic2d.drawImage(img, x, y, w, h, this);
                }else if(cell.getValue().equals(Cell.O)){
                    Image img = imgO;
                    graphic2d.drawImage(img, x, y, w, h, this);
                }
            }
        }
    }
    private void initMatrix(){
        for(int i = 0 ; i < N; i++){
            for(int j = 0 ; j < M; j++){
                Cell cell = new Cell();
                matrix[i][j] = cell;
            }
        }
    }
    public Board(){
        this.initMatrix();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                int x = e.getX();
                int y = e.getY();
                if(currentPlayer.equals(Cell.EMPTY)){
                    return;
                }
                for(int i = 0 ; i < N; i++){
                    for(int j = 0 ; j < M; j++){
                        Cell cell = matrix[i][j];
                        int cXStart = cell.getX();
                        int cYStart = cell.getY();
                        int cXEnd = cXStart + cell.getW()-1;
                        int cYEnd = cYStart + cell.getH()-1;
//-1 чтобы при клике между 2 ячейками компьютер не выскакивал сразу 2 символа
                        if(x >= cXStart && x <= cXEnd && y >= cYStart && y <= cYEnd){
                            if(cell.getValue().equals(Cell.EMPTY)){
                                cell.setValue(currentPlayer);
                                repaint();
                                int result = checkWin(currentPlayer, i, j);
                                if(endGame != null){
                                    endGame.end(currentPlayer, result);
                                }

                                if(result == NORMAL){
                                    currentPlayer = currentPlayer.equals(Cell.O) ? Cell.X : Cell.O;
                                }
                            }
                        }
                    }
                }
            }
        });
        try{
            imgX = ImageIO.read(getClass().getResource("img/x.jpg"));
            imgO = ImageIO.read(getClass().getResource("img/o.jpg"));
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void reset(){
        this.initMatrix();
        this.setCurrentPlayer(Cell.EMPTY);
        repaint();
    }
    public int checkWin(String player, int i, int j){
        int count = 0;
        for(int col = 0; col < M; col++){
            Cell cell = matrix[i][col];
            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if(count == 5){
                    return WIN;
                }
            }else {
                count = 0;
            }
        }
        count = 0;
        for(int row = 0; row < N; row++) {
            Cell cell = matrix[row][j];
            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if (count == 5) {
                    return WIN;
                }
            } else {
                count = 0;
            }
        }

        count = 0;int col=j;
        for(int row =i;row<N;row++){
            Cell cell = matrix[row][col];

            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if(count == 5){
                    return WIN;
                }
            }else {
                count = 0;
            }
            if(col==M-1){break;}
            col++;
        }
        count = 0; col=j;
        for(int row =i;row>=0;row--){
            Cell cell = matrix[row][col];

            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if(count == 5){
                    return WIN;
                }
            }else {
                count = 0;
            }
            if(col==0){break;}
            col--;
        }
        count = 0; col=j;
        for(int row =i;row>=0;row--){
            Cell cell = matrix[row][col];

            if (cell.getValue().equals(currentPlayer)) {
                count++;
                if(count == 5){
                    return WIN;
                }
            }else {
                count = 0;
            }
            if(col==M-1){break;}
            col++;
        }
            count = 0; col=j;
            for(int row =i;row<N;row++){
                Cell cell = matrix[row][col];

                if (cell.getValue().equals(currentPlayer)) {
                    count++;
                    if(count == 5){
                        return WIN;
                    }
                }else {
                    count = 0;
                }
                if(col==0){break;}
                col--;
            }

        if(this.isFull()){
            return DRAW;
        }
        return NORMAL;
    }
    private boolean isFull(){
        int number = N * M;
        int k = 0;
        for(int i = 0 ; i < N; i++){
            for(int j = 0 ; j < M; j++){
                Cell cell = matrix[i][j];
                if(cell.getValue().equals(Cell.EMPTY)){
                    k++;
                }
            }
        }
        return k == number;
    }



}
