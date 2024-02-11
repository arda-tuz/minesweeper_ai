package groupid;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.font.ImageGraphicAttribute;
import java.time.Period;



public class PanelButtonListener implements MouseInputListener {

    MainFrame mainFrame;
    ImagePanel peekButtonPanel;
    ImagePanel startButonPanel;
    ImagePanel restartButtonPanel;
    ImagePanel menuButtonPanel;

    ImagePanel windowsButtonPanel;
    ImagePanel customButtonPanel;

    String whichOne;  
    //!         "menu", "restart", "peek", "start" |||||||||||  "windows", "custom"

    public PanelButtonListener(MainFrame mainFrame, ImagePanel windowsButtonPanel, ImagePanel customButtonPanel, String whichOne){

        this.mainFrame = mainFrame;
        this.windowsButtonPanel = windowsButtonPanel;
        this.customButtonPanel = customButtonPanel;
        this.whichOne = whichOne;
    }

    public PanelButtonListener(MainFrame mainFrame, ImagePanel peekButtonPanel,
    ImagePanel startButtonPanel, ImagePanel restartButtonPanel, ImagePanel menuButtonPanel,
    String whichOne){

        this.whichOne = whichOne;
        this.mainFrame = mainFrame; this.peekButtonPanel = peekButtonPanel; this.startButonPanel = startButtonPanel;
        this.restartButtonPanel = restartButtonPanel; this.menuButtonPanel = menuButtonPanel;
    }

    public void destroyThreads(){   //!     bu method 1) restart  2) menu 'de tum threadler icin kesin cagirilmali 

        //TODO  bu metodu ThreadDice'ida yok edecek sekilde guncelle 

        if(this.mainFrame.game == null){

            //?     thread zaten olusturulmamis henuz game olusturulmadigina gore
            //?     o zaman yok yoketmeye de  gerek yok
        }
        else{

            this.mainFrame.game.stopThreadClock();
            this.mainFrame.game.stopThreadDice();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(this.whichOne.equals("menu")){

            destroyThreads();

            this.mainFrame.game = null;  // delete the older game object 
            this.mainFrame.cl.show(mainFrame.clPanel, "cl_menu");
            mainFrame.currentPanel = "cl_menu";
        }
        else if(this.whichOne.equals("restart")){

            destroyThreads();

            mainFrame.initializeGame(mainFrame, mainFrame.game.restartInformationArr[0] , 
            mainFrame.game.restartInformationArr[1], mainFrame.game.restartInformationArr[2]);
            mainFrame.currentPanel = "cl_game";
        }
        else if(this.whichOne.equals("peek")){

            this.mainFrame.game.peek();

            if(this.mainFrame.game.isPeekModeOn){

                peekButtonPanel.imageHash = "eyeOpen2";
            }
            else{

                peekButtonPanel.imageHash = "eyeClosed2";
            }

            peekButtonPanel.repaint();
        }
        else if(this.whichOne.equals("start")){

            if(this.mainFrame.game.isAiStarted){

                return; //?     1'den fazla AI objesi yaratilamaz
            }
            else{

                this.mainFrame.game.isAiStarted = true;

                //TODO          bu satirda yeni AI objesi yaratilacak sadece 

                startButonPanel.imageHash = "startClosed";

                //!=====================================================================
                //!             DEBUG BOLGESI   (AI simule etmek icin)
                //!=====================================================================   
                // this.mainFrame.game.startThreadClock();
                // this.mainFrame.game.startThreadDice();
                //!=====================================================================
                startButonPanel.repaint();
            }
        }
        else if(this.whichOne.equals("windows")){

            mainFrame.cl.show(mainFrame.clPanel, "cl_win7");
            mainFrame.currentPanel = "cl_win7";
        }
        else if(whichOne.equals("custom")){

            mainFrame.cl.show(mainFrame.clPanel, "cl_custom");
            mainFrame.currentPanel = "cl_custom";
        }
        else{

            //  imkansiz 
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {   

        if(this.whichOne.equals("menu")){

            menuButtonPanel.imageHash = "menuIcon2";
            menuButtonPanel.repaint();
        }
        else if(this.whichOne.equals("restart")){

            restartButtonPanel.imageHash = "restart2";
            restartButtonPanel.repaint();
        }
        else if(this.whichOne.equals("peek")){

            if(peekButtonPanel.imageHash.equals("eyeOpen")){

                peekButtonPanel.imageHash = "eyeOpen2";
            }
            else{

                peekButtonPanel.imageHash = "eyeClosed2";
            }
            peekButtonPanel.repaint();
        }
        else if(this.whichOne.equals("start")){

            if(startButonPanel.imageHash.equals("startOpen")){

                startButonPanel.imageHash = "startOpen2";
            }
            else{

                // startButonPanel.imageHash = "startClosed2";
            }
            startButonPanel.repaint();
        }
        else if(this.whichOne.equals("windows")){

            windowsButtonPanel.imageHash = "windowsIcon2";
            windowsButtonPanel.repaint();
        }
        else if(whichOne.equals("custom")){

            customButtonPanel.imageHash = "customIcon2";
            customButtonPanel.repaint();
        }
        else{

            //  imkansiz 
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if(this.whichOne.equals("menu")){

            menuButtonPanel.imageHash = "menuIcon";
            menuButtonPanel.repaint();
        }
        else if(this.whichOne.equals("restart")){

            restartButtonPanel.imageHash = "restart";
            restartButtonPanel.repaint();
        }
        else if(this.whichOne.equals("peek")){

            if(peekButtonPanel.imageHash.equals("eyeOpen2")){

                peekButtonPanel.imageHash = "eyeOpen";
            }
            else{

                peekButtonPanel.imageHash = "eyeClosed";
            }
            peekButtonPanel.repaint();
        }
        else if(this.whichOne.equals("start")){

            if(startButonPanel.imageHash.equals("startOpen2")){

                startButonPanel.imageHash = "startOpen";
            }
            else{

                startButonPanel.imageHash = "startClosed";
            }
            startButonPanel.repaint();
        }
        else if(this.whichOne.equals("windows")){

            windowsButtonPanel.imageHash = "windowsIcon1";
            windowsButtonPanel.repaint();
        }
        else if(whichOne.equals("custom")){

            customButtonPanel.imageHash = "customIcon1";
            customButtonPanel.repaint();
        }
        else{

            //  imkansiz 
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
