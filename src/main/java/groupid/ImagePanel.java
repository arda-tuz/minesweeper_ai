package groupid;

import java.awt.*;
import java.util.*;
import javax.swing.*;


public class ImagePanel extends JPanel {

    public boolean isStartButton = false;
    public boolean isRestartButton = false;
    public boolean isMenuButton = false;
    public boolean isPeekButton = false;

    MainFrame mainFrame;

    public HashMap<String, ImagePanel> panelMap;
    public HashMap<String, MyJbutton> buttonMap; 

    public boolean isThreadPanel = false; //?    CLOCK'LARA BAKAN PANEL HIZLANSIN DIYE EKLEDIM SADECE ONA TRUE OLCAK IF-ELSE SAYISINI DUSURECEK

    public ArrayList<Image> imageList = new ArrayList<>(100); 
    public String imageHash;

    public ImagePanel(MainFrame mainFrame){    

        super();
        this.mainFrame = mainFrame;
        this.imageHash = "whiteBlue";

        initImageList();

        this.panelMap = new HashMap<>();
        this.buttonMap = new HashMap<>();
    }

    public ImagePanel(MainFrame mainFrame, String imageHash){

        super();
        this.mainFrame = mainFrame;
        this.imageHash = imageHash;

        initImageList();

        this.panelMap = new HashMap<>();
        this.buttonMap = new HashMap<>();
    }

    private void initImageList(){

        try {

            Image tile_0 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_0.png")).getImage();
            Image tile_1 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_1.png")).getImage();
            Image tile_2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_2.png")).getImage();
            Image tile_3 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_3.png")).getImage();
            Image tile_4 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_4.png")).getImage();
            Image tile_5 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_5.png")).getImage();
            Image tile_6 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_6.png")).getImage();
            Image tile_7 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_7.png")).getImage();
            Image tile_8 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_8.png")).getImage();
            Image tile_closed = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_closed.png")).getImage();
            Image tile_flag = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_flag.png")).getImage();
            Image tile_mine = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\tile_mine.png")).getImage();
            Image orange = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\orange.png")).getImage();
            Image imageCyan = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\cyan.jpg")).getImage();
            Image mainMenu = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\mainMenu.jpg")).getImage();
            Image whiteBlue = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\whiteBlue.png")).getImage();

            Image brain = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\brain.jpg")).getImage();
            Image dice = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\dice.jpg")).getImage();
            Image arrow = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\arrow.jpg")).getImage();
            Image clock1 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock1.jpg")).getImage();
            Image clock2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock2.jpg")).getImage();
            Image clock3 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock3.jpg")).getImage();
            Image clock4 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock4.jpg")).getImage();
            Image clock5 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock5.jpg")).getImage();
            Image clock6 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock6.jpg")).getImage();
            Image clock7 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock7.jpg")).getImage();
            Image clock8 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock8.jpg")).getImage();
            Image clock9 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock9.jpg")).getImage();
            Image clock10 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock10.jpg")).getImage();
            Image clock11 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock11.jpg")).getImage();
            Image clock12 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock12.jpg")).getImage();

            Image mineCounter = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\mineCounter.jpg")).getImage();
            Image whiteBorder = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\whiteBorder.jpg")).getImage();
            Image blackBorder = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\blackBorder.jpg")).getImage();   

            Image peekMine = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\peekMine.jpg")).getImage();
            Image youWin = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\youWin.jpg")).getImage(); 
            Image youLose = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\youLose.jpg")).getImage();    

            Image quitMessage = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\quitMessage.jpg")).getImage();
            Image yellowPointer = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellowPointer.jpg")).getImage(); 
            Image yellowTik = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellowTik.jpg")).getImage();
            Image yellow0 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow0.jpg")).getImage();
            Image yellow1 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow1.jpg")).getImage(); 
            Image yellow2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow2.jpg")).getImage();
            Image yellow3 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow3.jpg")).getImage();
            Image yellow4 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow4.jpg")).getImage(); 
            Image yellow5 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow5.jpg")).getImage();
            Image yellow6 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow6.jpg")).getImage();
            Image yellow7 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow7.jpg")).getImage(); 
            Image yellow8 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow8.jpg")).getImage();
            Image yellow9 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\yellow9.jpg")).getImage();

            Image mainMenuBlue = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\mainMenuBlue.png")).getImage();
            Image greenM = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\greenM.jpg")).getImage();
            Image greenF = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\greenF.jpg")).getImage();

            Image startOpenIcon = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\startOpenIcon.jpg")).getImage();
            Image startClosedIcon = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\startClosedIcon.jpg")).getImage();
            Image restartIcon = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\restartIcon.jpg")).getImage();
            Image eyeOpenIcon = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\eyeOpenIcon.jpg")).getImage();
            Image eyeClosedIcon = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\eyeClosedIcon.jpg")).getImage();
            Image menuIcon = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\menuIcon.jpg")).getImage();

            Image whiteBlueDarker1 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\whiteBlueDarker1.png")).getImage();
            Image startOpenIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\startOpenIcon2.jpg")).getImage();
            Image startClosedIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\startClosedIcon2.jpg")).getImage();
            Image restartIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\restartIcon2.jpg")).getImage();
            Image eyeOpenIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\eyeOpenIcon2.jpg")).getImage();
            Image eyeClosedIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\eyeClosedIcon2.jpg")).getImage();
            Image menuIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\menuIcon2.jpg")).getImage();

            Image clock0 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\clock0.jpg")).getImage();
            Image windowsIcon1 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\windowsIcon1.jpg")).getImage();
            Image windowsIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\windowsIcon2.jpg")).getImage();
            Image customIcon1 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\customIcon1.jpg")).getImage();
            Image customIcon2 = new ImageIcon(mainFrame.fixPath("src\\main\\resources\\customIcon2.jpg")).getImage();



            imageList.add(tile_0);
            imageList.add(tile_1);
            imageList.add(tile_2);
            imageList.add(tile_3);
            imageList.add(tile_4);
            imageList.add(tile_5);
            imageList.add(tile_6);
            imageList.add(tile_7);
            imageList.add(tile_8);
            imageList.add(tile_closed);
            imageList.add(tile_flag);
            imageList.add(tile_mine);
            imageList.add(orange);
            imageList.add(imageCyan);
            imageList.add(mainMenu);
            imageList.add(whiteBlue);

            imageList.add(brain);
            imageList.add(dice);
            imageList.add(arrow);
            imageList.add(clock1);
            imageList.add(clock2);
            imageList.add(clock3);
            imageList.add(clock4);
            imageList.add(clock5);
            imageList.add(clock6);
            imageList.add(clock7);
            imageList.add(clock8);
            imageList.add(clock9);
            imageList.add(clock10);
            imageList.add(clock11);
            imageList.add(clock12);

            imageList.add(mineCounter);
            imageList.add(whiteBorder);
            imageList.add(blackBorder);

            imageList.add(peekMine);
            imageList.add(youWin);
            imageList.add(youLose);

            imageList.add(quitMessage);
            imageList.add(yellowPointer);
            imageList.add(yellowTik);
            imageList.add(yellow0);
            imageList.add(yellow1);
            imageList.add(yellow2);
            imageList.add(yellow3);
            imageList.add(yellow4);
            imageList.add(yellow5);
            imageList.add(yellow6);
            imageList.add(yellow7);
            imageList.add(yellow8);
            imageList.add(yellow9);

            imageList.add(mainMenuBlue);
            imageList.add(greenM);
            imageList.add(greenF);

            imageList.add(startOpenIcon);
            imageList.add(startClosedIcon);
            imageList.add(restartIcon);
            imageList.add(eyeOpenIcon);
            imageList.add(eyeClosedIcon);
            imageList.add(menuIcon);

            imageList.add(whiteBlueDarker1);
            imageList.add(startOpenIcon2);
            imageList.add(startClosedIcon2);
            imageList.add(restartIcon2);
            imageList.add(eyeOpenIcon2);
            imageList.add(eyeClosedIcon2);
            imageList.add(menuIcon2);

            imageList.add(clock0);
            imageList.add(windowsIcon1);
            imageList.add(windowsIcon2);
            imageList.add(customIcon1);
            imageList.add(customIcon2);
        
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
        public void paintComponent(Graphics g) {        //! EN SON EKLENEN INDEX = 70

            super.paintComponent(g);

            if(this.isThreadPanel == false){    //? THREAD = FALSE

                if(this.imageHash.equals("0")){

                    g.drawImage(imageList.get(0), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("1")){
    
                    g.drawImage(imageList.get(1), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("2")){
    
                    g.drawImage(imageList.get(2), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("3")){
    
                    g.drawImage(imageList.get(3), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("4")){
    
                    g.drawImage(imageList.get(4), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("5")){
    
                    g.drawImage(imageList.get(5), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("6")){
    
                    g.drawImage(imageList.get(6), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("7")){
    
                    g.drawImage(imageList.get(7), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("8")){
    
                    g.drawImage(imageList.get(8), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("c")){
    
                    g.drawImage(imageList.get(9), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("f")){
    
                    g.drawImage(imageList.get(10), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("m")){
    
                    g.drawImage(imageList.get(11), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("orange")){
    
                    g.drawImage(imageList.get(12), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("cyan")){
    
                    g.drawImage(imageList.get(13), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("mainMenu")){
    
                    g.drawImage(imageList.get(14), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("whiteBlue")){
    
                    g.drawImage(imageList.get(15), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("mineCounter")){

                    g.drawImage(imageList.get(31), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("whiteBorder")){

                    g.drawImage(imageList.get(32), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("blackBorder")){

                    g.drawImage(imageList.get(33), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("peek")){

                    g.drawImage(imageList.get(34), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("youWin")){

                    g.drawImage(imageList.get(35), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("youLose")){

                    g.drawImage(imageList.get(36), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("quitMessage")){  

                    g.drawImage(imageList.get(37), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellowPointer")){

                    g.drawImage(imageList.get(38), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellowTik")){

                    g.drawImage(imageList.get(39), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow0")){

                    g.drawImage(imageList.get(40), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow1")){

                    g.drawImage(imageList.get(41), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow2")){

                    g.drawImage(imageList.get(42), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow3")){

                    g.drawImage(imageList.get(43), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow4")){

                    g.drawImage(imageList.get(44), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow5")){

                    g.drawImage(imageList.get(45), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow6")){

                    g.drawImage(imageList.get(46), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow7")){

                    g.drawImage(imageList.get(47), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow8")){

                    g.drawImage(imageList.get(48), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("yellow9")){

                    g.drawImage(imageList.get(49), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("mainMenuBlue")){

                    g.drawImage(imageList.get(50), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("greenM")){

                    g.drawImage(imageList.get(51), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("greenF")){

                    g.drawImage(imageList.get(52), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("startOpen")){

                    g.drawImage(imageList.get(53), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("startClosed")){

                    g.drawImage(imageList.get(54), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("restart")){

                    g.drawImage(imageList.get(55), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("eyeOpen")){

                    g.drawImage(imageList.get(56), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("eyeClosed")){

                    g.drawImage(imageList.get(57), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("menuIcon")){

                    g.drawImage(imageList.get(58), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("whiteBlueDarker1")){

                    g.drawImage(imageList.get(59), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("startOpen2")){

                    g.drawImage(imageList.get(60), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("startClosed2")){

                    g.drawImage(imageList.get(61), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("restart2")){

                    g.drawImage(imageList.get(62), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("eyeOpen2")){

                    g.drawImage(imageList.get(63), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("eyeClosed2")){

                    g.drawImage(imageList.get(64), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("menuIcon2")){

                    g.drawImage(imageList.get(65), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("windowsIcon1")){

                    g.drawImage(imageList.get(67), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("windowsIcon2")){

                    g.drawImage(imageList.get(68), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("customIcon1")){

                    g.drawImage(imageList.get(69), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("customIcon2")){

                    g.drawImage(imageList.get(70), 0, 0, getWidth(), getHeight(), this);
                }
                else{
                }
            }
            else{   //? THREAD = TRUE

                if(this.imageHash.equals("brain")){
    
                    g.drawImage(imageList.get(16), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("dice")){
    
                    g.drawImage(imageList.get(17), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("arrow")){
    
                    g.drawImage(imageList.get(18), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock1")){
    
                    g.drawImage(imageList.get(19), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock2")){
    
                    g.drawImage(imageList.get(20), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock3")){
    
                    g.drawImage(imageList.get(21), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock4")){
    
                    g.drawImage(imageList.get(22), 0, 0, getWidth(), getHeight(), this);
                }   
                else if(this.imageHash.equals("clock5")){
    
                    g.drawImage(imageList.get(23), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock6")){
    
                    g.drawImage(imageList.get(24), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock7")){
    
                    g.drawImage(imageList.get(25), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock8")){
    
                    g.drawImage(imageList.get(26), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock9")){
    
                    g.drawImage(imageList.get(27), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock10")){
    
                    g.drawImage(imageList.get(28), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock11")){
    
                    g.drawImage(imageList.get(29), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock12")){
    
                    g.drawImage(imageList.get(30), 0, 0, getWidth(), getHeight(), this);
                }
                else if(this.imageHash.equals("clock0")){
    
                    g.drawImage(imageList.get(66), 0, 0, getWidth(), getHeight(), this);
                }
                else{
                    
                 
                }
            }
        }
}
