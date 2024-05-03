/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Graphical user interface class for the project
 * @author v7i2jb
 */
public class GUI {
    private JFrame jframe;
    private Window window;
    private String[]  colors = {"cyan","magneta","yellow","red","green","blue","gray","white","black"};
    
    public GUI() {
        jframe = new JFrame("Tron Game");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Main Menu");
        JMenuItem menuTopScore = new JMenuItem("Leaderboard");
        JMenuItem menuRestart = new JMenuItem(new AbstractAction("Restart!") {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.restart();
                window.restartTimer();
            }
        });
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit!") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuGame.add(menuGameExit);
        menuGame.add(menuRestart);
        menuBar.add(menuGame);
        jframe.setJMenuBar(menuBar);
        
        //  When clicked on <Leaderboard>
        menuTopScore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg) {
                JFrame tableFrame = new JFrame();
                //  Uncomment size and its related matters to show the whole table!
                int size = 0;
                try {
                    size = window.getHs().getTopScores().size();
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                tableFrame.setTitle("Top Scores");
                String[] header = {"Name", "Score"};
                String[][] body = new String[10][2];
                
                try {
                    for(int i = 0; i < size; i++) {
                        body[i][0] = window.getHs().getTopScores().get(i).getName();
                        body[i][1] = Integer.toString(window.getHs().getTopScores().get(i).getScore());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }
               
                //  Creation of table and Scroll object
                JTable table = new JTable(body, header);
                JScrollPane sp = new JScrollPane(table);
                tableFrame.add(sp);
                tableFrame.setSize(625, 300);
                tableFrame.setVisible(true);
            }
        });
        
        menuGame.add(menuTopScore);
        
        //  Get player1 name, then color
        String firstPlayerName;
        firstPlayerName = JOptionPane.showInputDialog("Player1 name: ");
        String fCol = (String)JOptionPane.showInputDialog(null, "Choose a color for Player1: ", 
                "Pick a color", JOptionPane.QUESTION_MESSAGE, null, colors, colors[3]);
        Color firstColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(fCol);
            firstColor = (Color)field.get(null);
        } catch (Exception e) { //  Incase the color was undefined.
            firstColor = Color.RED; 
        }
        
        
        //  Get player2 name, then color
        String secondPlayerName;
        secondPlayerName = JOptionPane.showInputDialog("Player2 name: ");
      
        String sCol = (String)JOptionPane.showInputDialog(null, "Choose a color for Player2: ", 
                "Pick a color", JOptionPane.QUESTION_MESSAGE, null, colors, colors[5]);
        Color secondColor;
        try {
            Field field = Class.forName("java.awt.Color").getField(sCol);
            secondColor = (Color)field.get(null);
            if (secondColor == firstColor) {
                if (secondColor == Color.BLUE) {
                    secondColor = Color.RED;
                }
                else {
                    secondColor = Color.BLUE;
                }
            }
        } catch (Exception e) { //  Incase the color was undefined.
            if(firstColor != Color.BLUE) {
                secondColor = Color.BLUE;
            }
            else {
                secondColor = Color.RED;
            }
        }

        //  Name checkers
        if (firstPlayerName == null || firstPlayerName.equals("")) {
            int randomNum = ((int) ((Math.random() * (126 - 33)) + 33));
            firstPlayerName = "Player1_"+(char)randomNum+randomNum;
        }
        if (secondPlayerName == null || secondPlayerName.equals("")) {
            int randomNum = ((int) ((Math.random() * (126 - 33)) + 33));
            secondPlayerName = "Player2_"+(char)randomNum+randomNum;
        }
        if (secondPlayerName.equals(firstPlayerName)) {
            secondPlayerName += "theSecond";
        }
        

        //  General formation of the game theFrame
        window = new Window(firstPlayerName,firstColor,secondPlayerName,secondColor);
        jframe.getContentPane().add(window);
        jframe.getContentPane().add(window.getTurnLabel(), BorderLayout.NORTH);
        jframe.getContentPane().add(window.getTimer(), BorderLayout.SOUTH);
        jframe.setPreferredSize(new Dimension(800, 600));
        jframe.setResizable(false);
        jframe.pack();
        jframe.setIconImage(Toolkit.getDefaultToolkit().getImage("images/logo.jpg"));
        jframe.setVisible(true);
    }
}
