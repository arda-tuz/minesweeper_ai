package groupid;

import java.awt.*;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.event.MouseInputListener;



public class TilePanel extends ImagePanel implements MouseInputListener{

    Pair pair;
    int whichKindOfPanel;        
    //!    0 = oyunun oynandigi normal game panaller // 1 = mouse mode icin secim yapilan paneller   

    public TilePanel(MainFrame mainFrame, int satir, int sutun){

        super(mainFrame);
        this.pair = new Pair(satir, sutun);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public TilePanel(MainFrame mainFrame, String imageHash, int satir, int sutun){

        super(mainFrame, imageHash);
        this.pair = new Pair(satir, sutun);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(this.whichKindOfPanel == 0){

            if (e.getButton() == MouseEvent.BUTTON3) {  //! SAG BUTON 

                if(this.mainFrame.game.userBoard[pair.satir][pair.sutun] == 'f'){   // flag var
    
                    this.mainFrame.game.unflagTile(pair.satir, pair.sutun);
                }
                else{   // flag yok
    
                    this.mainFrame.game.flagTile(pair.satir, pair.sutun);
                }
            }
            else{  //! SOL BUTON 
    
                this.mainFrame.game.openTile(pair.satir, pair.sutun);
            }
        }
        else if(this.whichKindOfPanel == 1){
            
            if (e.getButton() == MouseEvent.BUTTON3) {  //! SAG BUTON 

                //TODO      removing mines
            }
            else{  //! SOL BUTON 
    
                //TODO     adding mines
            }
        }

        this.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        

    }

    @Override
    public void mouseExited(MouseEvent e) {
       

    }

    @Override
    public void mouseDragged(MouseEvent e) {
       
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        

    }
    

}
