/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package trongame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 * Window class for the project
 * @author v7i2jb
 */
public class Window extends JPanel {
    private Image background;
    private JLabel scoreLabel;
    
    private Player player1;
    private Player player2;
    String name1;
    String name2;
    
    Color color1;
    Color color2;
    private Bike bike1;
    private Bike bike2;
    
    //  Coordinates
    private TopScores topScores;
//    private final int FPS = 250;
//    private final int BIKE_X = 40;
//    private final int BIKE_Y = 150;
//    private final int BIKE_WIDTH = 80;
//    private final int BIKE_HEIGHT = 80;
//    private final int BIKE_MOVEMENT = 2;
    
    private final int FPS = 250;
    private final int BIKE_X = 0;
    private final int BIKE_Y = 235;
    private final int BIKE_WIDTH = 80;
    private final int BIKE_HEIGHT = 80;
    private final int BIKE_MOVEMENT = 2;
    
    //  Timer
    private Timer newFrameTimer;
    private JLabel timeLabel;
    private long startTime;
    private Timer timer;
    private long elapsedTime;
    private double elapsedTimeInSeconds;
    
    // Game logic, movements are implemented here
    public Window(String name1,Color color1,String name2,Color color2) {
        super();

        background = new ImageIcon("images/back.jpg").getImage();
        this.name1 = name1;
        this.name2 = name2;
        this.color1 = color1;
        this.color2 = color2;

        
        scoreLabel = new JLabel( name1 + " and " + name2 + " are playing!");
        
        try {   
            topScores = new TopScores(10);
        } catch (SQLException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //  MOVEMENT
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setSpeedX(-BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("images/bike_left.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("A"), "pressed A");
        this.getActionMap().put("pressed A", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setSpeedX(-BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("images/bike_left.png").getImage());
            }
        });
        
        
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setSpeedX(BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("images/bike_right.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("D"), "pressed D");
        this.getActionMap().put("pressed D", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setSpeedX(BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("images/bike_right.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setSpeedY(BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("images/bike_down.png").getImage());
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("S"), "pressed S");
        this.getActionMap().put("pressed S", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setSpeedY(BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("images/bike_down.png").getImage());
            }
        });
        
        this.getInputMap().put(KeyStroke.getKeyStroke("UP"), "pressed up");
        this.getActionMap().put("pressed up", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player2.getBike().setSpeedY(-BIKE_MOVEMENT);
                player2.getBike().setImage(new ImageIcon("images/bike_up.png").getImage());
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("W"), "pressed w");
        this.getActionMap().put("pressed w", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                player1.getBike().setSpeedY(-BIKE_MOVEMENT);
                player1.getBike().setImage(new ImageIcon("images/bike_up.png").getImage());
            }
        });
        
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
        startTimer();
    }

    public TopScores getHs() {
        return topScores;
    }
    
    /**
    * Starts a timer that updates every 100ms and puts it on the frame
    */
    public void startTimer(){
        timeLabel = new JLabel(" ");
        timeLabel.setHorizontalAlignment(JLabel.CENTER);
        startTime = System.currentTimeMillis();
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime = System.currentTimeMillis() - startTime;
                elapsedTimeInSeconds = (double)elapsedTime/1000;
                timeLabel.setText("Game time: " + elapsedTimeInSeconds + " s");
            }
        });
        timer.start();
    }  

    public void restartTimer(){
        startTime = System.currentTimeMillis();
        if(timer != null) {
            timer.restart();   
        }
    }

    public JLabel getTimer(){
        return timeLabel;
    }
    
    /**
     *  Restart game command, used when game is first created and when clicked from the MainMenu, resets already set values.
    */
    public void restart() {
        Image first = new ImageIcon("images/bike_right.png").getImage();
        bike1 = new Bike(BIKE_X, BIKE_Y, BIKE_WIDTH, BIKE_HEIGHT, first);
        player1 = new Player(name1,color1,bike1);
    
        Image second = new ImageIcon("images/bike_left.png").getImage();
        bike2 = new Bike(700,  BIKE_Y, BIKE_WIDTH, BIKE_HEIGHT, second);
        player2 = new Player(name2, color2,bike2);
        restartTimer();
    }
    
    
    //  Bikes and their trails painting
    @Override
    protected void paintComponent(Graphics graphix) {
        super.paintComponent(graphix);
        graphix.drawImage(background, 0, 0, 800, 600, null);
        player1.getBike().draw(graphix);
        player2.getBike().draw(graphix);
        player1.getBike().drawTrail(graphix,color1);
        player2.getBike().drawTrail(graphix,color2);
        
    }
    
    public JLabel getTurnLabel() {
        return scoreLabel;
    }

    private TopScores TopScores(int score) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
        
    /**
    *   Modifed ActionListener to check the current status of the bikes and evaluate if a winner is determined 
    */  
    class NewFrameListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ae) {
            player1.getBike().move();
            player2.getBike().move();
            ArrayList<TopScore> scores = new ArrayList<>();
            try {
                scores = topScores.getTopScores();
            } catch (SQLException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            //  If a collision with a player or with the bounds happened
            if (player1.isInLightTrace(player2) || player2.isInLightTrace(player1) || player1.isOutOfBoundary() || player2.isOutOfBoundary()) {
                
                timer.stop();

                //  Player1 wins if player2 collapses with player1's trail or if simply player2 hit the bounds
                if(player1.isInLightTrace(player2) || player2.isOutOfBoundary() )
                {
                    JOptionPane.showMessageDialog(null,name1 + " has won the game! Time elapsed: "+elapsedTimeInSeconds+" seconds");
                    boolean name_exist = false;
                    try{
                        for(int i=0;i<scores.size();++i){
                            if(name1.equals(scores.get(i).getName())){
                                name_exist = true;
                                break;
                            }
                        }
                        if(name_exist){
                            topScores.increaseScore(name1);
                        }else{
                            topScores.putTopScore(name1, 1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                //  Player2 wins if player1 collapses with player2's trail or if simply player1 hit the bounds
                else if(player2.isInLightTrace(player1) || player1.isOutOfBoundary()){
                    
                    boolean name_exist = false;
                    try{
                        for(int i=0;i<scores.size();++i){
                            if(name2.equals(scores.get(i).getName())){
                                name_exist = true;
                            }
                        }
                        if(name_exist){
                            topScores.increaseScore(name2);
                        }else{
                            topScores.putTopScore(name2, 1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    JOptionPane.showMessageDialog(null,name2 + " has won the game! Time elapsed: "+elapsedTimeInSeconds+" seconds");
                }
                restart();
            }
           
            repaint();
        }

    }
}
