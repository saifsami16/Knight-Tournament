package com.company;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class knightGameGUI {
    private JFrame frame;
    private Board board;
    Integer[] boardSizes = {4, 6, 8};
    public knightGameGUI() {
        frame = new JFrame("Knight Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int initialBoardSize = (int) JOptionPane.showInputDialog(null,
                "Select Board Size",
                "Board Size",
                JOptionPane.QUESTION_MESSAGE,
                null,
                boardSizes,
                boardSizes[0]);
        board = new Board(initialBoardSize);
        frame.getContentPane().add(board.getBoardPanel(), BorderLayout.CENTER);
        frame.pack();
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Menu");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("New Game");
        gameMenu.add(newMenu);
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        gameMenu.add(exitMenuItem);
        frame.setPreferredSize(new Dimension(initialBoardSize*70, initialBoardSize*70));
        frame.setLocationRelativeTo(null);

        for (int boardSize : boardSizes) {
            JMenuItem sizeMenuItem = new JMenuItem(boardSize + "x" + boardSize);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.getContentPane().remove(board.getBoardPanel());
                    board = new Board(boardSize);
                    frame.getContentPane().add(board.getBoardPanel(),
                            BorderLayout.CENTER);
                    frame.setPreferredSize(new Dimension(boardSize*70, boardSize*70));
                    frame.pack();
                }
            });
        }

        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        frame.pack();
        frame.setVisible(true);
    }
}
