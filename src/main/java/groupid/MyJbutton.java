package groupid;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputListener;


public class MyJbutton extends JButton implements ActionListener, MouseInputListener{

    public MainFrame mainFrame;
    public Color pressedColor;

    public boolean boyansinMi = false;

    public MyJbutton(MainFrame mainFrame, String text){

        super(text);
        setContentAreaFilled(false);  // Add this line
        this.mainFrame = mainFrame;
        this.pressedColor = mainFrame.lightBlue;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addActionListener(this);
    }

    public MyJbutton(MainFrame mainFrame){

        super();
        setContentAreaFilled(false);  // Add this line
        this.mainFrame = mainFrame;
        this.pressedColor = mainFrame.lightBlue;
        
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addActionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(pressedColor);
        } else {
            g.setColor(getBackground());
        }
        g.fillRect(0, 0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getActionCommand().equals("win7")){

            mainFrame.cl.show(mainFrame.clPanel, "cl_win7");
            mainFrame.currentPanel = "cl_win7";
        }
        else if(e.getActionCommand().equals("number")){

            mainFrame.cl.show(mainFrame.clPanel,"cl_number");
            mainFrame.currentPanel = "cl_number";
        }
        else if(e.getActionCommand().equals("mouse")){

            mainFrame.cl.show(mainFrame.clPanel, "cl_mouse");
            mainFrame.currentPanel = "cl_mouse";
        }
        else if(e.getActionCommand().equals("beginner")){   

            mainFrame.initializeGame(mainFrame, 10 , 9  , 9);
        }
        else if(e.getActionCommand().equals("intermediate")){

            mainFrame.initializeGame(mainFrame, 40 , 16  , 16);
        }
        else if(e.getActionCommand().equals("expert")){

            mainFrame.initializeGame(mainFrame, 99 , 30, 16);
        }  
        else if(e.getActionCommand().equals("quitYes")){

            System.exit(0);
        }
        else if(e.getActionCommand().equals("quitNo")){

            this.mainFrame.cl.show(this.mainFrame.clPanel, this.mainFrame.currentPanel);
        }
        else if(e.getActionCommand().equals("")){


        }
        else if(e.getActionCommand().equals("")){


        }
        else if(e.getActionCommand().equals("")){


        }
        else if(e.getActionCommand().equals("")){


        }
        else{

            //   NOTHING HAPPENS
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {  
        
    }   

    @Override
    public void mouseEntered(MouseEvent e) {   //!!!!!!!
        
        if(boyansinMi == false){ // elleme 

            return;
        }       
        else{

            this.setBackground(mainFrame.lightBlue);
            
            this.repaint();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) { //!!!!!!!
       
        if(boyansinMi == false){ // elleme 

            return;
        }       
        else{

            this.setBackground(mainFrame.greyBlue);

            this.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        
    }


}
