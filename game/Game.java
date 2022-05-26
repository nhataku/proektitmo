package game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Game {
    private static JButton Start;
    private static Board board;
    public Game(){
        View();
    }
    public  void View() {
        board = new Board();
        board.setEndGame(new EndGame() {
            @Override
            public void end(String player, int st) {
                if (st == Board.WIN) {
                    JOptionPane.showMessageDialog(null, "Игок " + player + " победил!!!");
                    stopGame();
                } else if (st == Board.DRAW) {

                    JOptionPane.showMessageDialog(null, "Ничья!!!");

                }
            }
        });

        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);


        board.setPreferredSize(new Dimension(600, 600));

        FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, 0);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(flowLayout);

        Start = new JButton("СТАРТ");
        bottomPanel.add(Start);
        Start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Start.getText().equals("СТАРТ")) {
                    startGame();
                } else {
                    stopGame();
                }
            }
        });

        jPanel.add(board);
        jPanel.add(bottomPanel);
        JFrame jFrame = new JFrame("ГОМОКУ");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.add(jPanel);
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

    }
    public void startGame() {
        Object[] possibleValues = {"Игрок O начинает первым", "Игрок X начинает первым"};
        Object selectedValue = JOptionPane.showInputDialog(null,
                "Выберите один", "Кто начинает?",
                JOptionPane.INFORMATION_MESSAGE, null,
                possibleValues, possibleValues[0]);
        board.reset();
        String currentPlayer = (selectedValue == "Игрок O начинает первым") ? Cell.O : Cell.X;
        board.setCurrentPlayer(currentPlayer);
        Start.setText("СТОП");
    }


    public  void stopGame() {
        Start.setText("СТАРТ");
        board.reset();
    }

}
