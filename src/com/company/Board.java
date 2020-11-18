package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class Board {
    private JButton[][] buttons;
    private static JPanel Panel;
    private int prevRow;
    private int prevCol;
    private int target = 4;
    private boolean picked = false;
    String turn[] = {"W","B"};
    int countTurns = 0;
    Icon white;
    Icon black;
    public Board(int BoardSize) {
        String Knight;
        Panel = new JPanel();
        Panel.setLayout(new GridLayout(BoardSize, BoardSize));
        JButton[][] b = new JButton[BoardSize][BoardSize];
        white = new ImageIcon((new ImageIcon("white.png").getImage()).getScaledInstance((int) (Math.sqrt(BoardSize)*20), (int) (Math.sqrt(BoardSize)*20), java.awt.Image.SCALE_SMOOTH));
        black = new ImageIcon((new ImageIcon("black.png").getImage()).getScaledInstance((int) (Math.sqrt(BoardSize)*20), (int) (Math.sqrt(BoardSize)*20), java.awt.Image.SCALE_SMOOTH));
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[i].length; j++) {
                Knight = "";
                if (j == 0 || j == BoardSize - 1) {
                    if(i==0)Knight = "W";
                    if(i==BoardSize - 1)Knight = "B";
                }
                JButton button;
                if(Knight.equals("W"))button = new JButton(white);
                else if(Knight.equals("B"))button = new JButton(black);
                else button = new JButton(Knight);
                button.setActionCommand(Knight);    //allocating hidden value to the button to read it easily
                button.setBackground(Color.LIGHT_GRAY);
                b[i][j] = button;
                b[i][j].addActionListener(new MoveActionListener(i, j));
                Panel.add(b[i][j]);
            }
        }
        buttons = b;
    }
    public JPanel getBoardPanel() {
        return Panel;
    }

    public void Refresh(String Winner) {
        JOptionPane.showMessageDialog(Panel, ("W".equals(Winner)?"White":"Black")+" player wins!", "Game End", JOptionPane.PLAIN_MESSAGE);
        System.exit(1);
    }
    class MoveActionListener implements ActionListener {
        private int row;
        private int col;

        public MoveActionListener(int x, int y) {
            row = x;
            col = y;
        }

        public void hints(int a, int b){        //showing hints for the possible knight moves
            int X[] = { 2, 1, -1, -2, -2, -1, 1, 2 };//possible knight moves
            int Y[] = { 1, 2, 2, 1, -1, -2, -2, -1 };
            for (int i = 0; i < 8; i++) {
                int x = a + X[i];
                int y = b + Y[i];
                if (x >= 0 && y >= 0 && x < buttons.length && y < buttons.length){
                    if ("".equals(buttons[x][y].getActionCommand())){
                        if (buttons[x][y].getText().equals("X"))buttons[x][y].setText("");
                        else{
                            buttons[x][y].setText("X");
                            buttons[x][y].setForeground(Color.RED);
                        }
                    }
                }
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton currButton = (JButton) e.getSource();
            String currTurn = turn[countTurns%2];
            if (picked && currTurn.equals(currButton.getActionCommand())) {
                hints(prevRow, prevCol);
                picked = false;
            }
            if (!picked){
                if(currButton.getActionCommand().equals(currTurn)){   //check for right persons turn
                    prevRow = row;
                    prevCol = col;
                    picked = true;
                    hints(row,col);
                }
                else{
                    JOptionPane.showMessageDialog(Panel, "Please pick the right knight", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
            else if (picked){
                if ("X".equals(currButton.getText())) { //check for the allowed position drop
                    hints(prevRow,prevCol);
                    currButton.setActionCommand(currTurn);
                    currButton.setIcon(currTurn.equals("W")?white:black);
                    buttons[prevRow][prevCol].setIcon(null);
                    buttons[prevRow][prevCol].setActionCommand("");
                    buttons[prevRow][prevCol].setBackground(currTurn.equals("W")?Color.WHITE:Color.BLACK);
                    currButton.setBackground(Color.LIGHT_GRAY);
                    countTurns++;
                    picked = false;

                    int hor = 1, ver =  1, diag = 1;        //Horizontal + Vertical Check
                    int i = prevRow, x = prevRow, y = prevCol;
                    for (int j = 1; j < buttons[i].length; j++) {
                        if(buttons[j][y].getBackground() == buttons[j-1][y].getBackground() && buttons[j][y].getBackground() != Color.LIGHT_GRAY)ver++; //vertical check
                        else ver = 1;
                        if(buttons[i][j].getBackground() == buttons[i][j-1].getBackground() && buttons[i][j].getBackground() != Color.LIGHT_GRAY)hor++; //horizontal check
                        else hor = 1;
                        if(ver == target || hor == target) Refresh(currTurn);
                    }
                    if(x > y){
                        x-=y;
                        y = 0;
                    }
                    else{
                        y-=x;
                        x = 0;
                    }
                    while(x < buttons.length-1  && y < buttons.length-1){       //Diagonal Check
                        if(buttons[x][y].getBackground() == buttons[x+1][y+1].getBackground() && buttons[x][y].getBackground() != Color.LIGHT_GRAY)diag++; //daigonal check
                        else diag = 1;
                        x++;y++;
                        if(diag == target) Refresh(currTurn);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(Panel, "The Knight cannot be placed here", "Error", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
    }
}
