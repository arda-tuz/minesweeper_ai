package groupid;

import javax.swing.event.MouseInputListener;
import javax.swing.text.AbstractDocument.LeafElement;

import org.w3c.dom.html.HTMLIsIndexElement;

import java.awt.event.MouseEvent;
import java.security.KeyManagementException;


public class PanelButtonListener implements MouseInputListener {

    MainFrame mainFrame;
    ImagePanel peekButtonPanel;
    ImagePanel startButonPanel;
    ImagePanel restartButtonPanel;
    ImagePanel menuButtonPanel;

    ImagePanel windowsButtonPanel;
    ImagePanel customButtonPanel;

    String whichOne;  

    //=========================================================================
    public boolean isCustomPanelRelated = false;
    public ImagePanel custom_0;
    public ImagePanel custom_1;
    public ImagePanel custom_2;
    public ImagePanel custom_3;
    public ImagePanel custom_4;
    public ImagePanel custom_5;
    public ImagePanel custom_6;
    public ImagePanel custom_7;
    public ImagePanel custom_8;
    public ImagePanel custom_9;
    public ImagePanel custom_delete;
    public ImagePanel custom_deleteAll;
    public ImagePanel custom_push;
    public ImagePanel custom_left;
    public ImagePanel custom_middle;
    public ImagePanel custom_right;
    //=========================================================================

    //!     isCustomPanelRelated = FALSE :    {"menu", "restart", "peek", "start" |||||||||||  "windows", "custom"}
    //!     isCustomPanelRelated = TRUE :    {"0", 1", "2", "3", "4", "5", "6", "7", "8", "9", "delete", "deleteAll", "push", "left", "middle", "right"}

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

    public PanelButtonListener(MainFrame mainFrame, ImagePanel imagePanel, String whichOne){   //! BU CONSTRUCTER SADECE "isCustomRelated=true" olanlarda kullanilmali !!!

        this.mainFrame = mainFrame;
        this.whichOne = whichOne;
        this.isCustomPanelRelated = true;

        if(whichOne.equals("0")){

            this.custom_0 = imagePanel;
        }
        else if(whichOne.equals("1")){

            this.custom_1 = imagePanel;
        }
        else if(whichOne.equals("2")){

            this.custom_2 = imagePanel;
        }
        else if(whichOne.equals("3")){

            this.custom_3 = imagePanel;
        }
        else if(whichOne.equals("4")){

            this.custom_4 = imagePanel;
        }
        else if(whichOne.equals("5")){

            this.custom_5 = imagePanel;
        }
        else if(whichOne.equals("6")){

            this.custom_6 = imagePanel;
        }
        else if(whichOne.equals("7")){

            this.custom_7 = imagePanel;
        }
        else if(whichOne.equals("8")){

            this.custom_8 = imagePanel;
        }
        else if(whichOne.equals("9")){

            this.custom_9 = imagePanel;
        }
        else if(whichOne.equals("delete")){

            this.custom_delete = imagePanel;
        }
        else if(whichOne.equals("deleteAll")){

            this.custom_deleteAll = imagePanel;
        }
        else if(whichOne.equals("push")){

            this.custom_push = imagePanel;
        }   
        else if(whichOne.equals("left")){

            this.custom_left = imagePanel;
        }
        else if(whichOne.equals("middle")){

            this.custom_middle = imagePanel;
        }
        else if(whichOne.equals("right")){

            this.custom_right = imagePanel;
        }
        else{
            // IMPOSSIBLE
        }
    }

    public void destroyThreads(){   //!     bu method 1) restart  2) menu 'de tum threadler icin kesin cagirilmali 

        if(this.mainFrame.game == null){

            //?     thread zaten olusturulmamis henuz game olusturulmadigina gore
            //?     o zaman yok yoketmeye de  gerek yok
        }
        else{

            this.mainFrame.game.destroyAll3Threads();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(isCustomPanelRelated){

            if(whichOne.equals("0")){

                updateString(0);
            }
            else if(whichOne.equals("1")){

                updateString(1);
            }
            else if(whichOne.equals("2")){

                updateString(2);
            }
            else if(whichOne.equals("3")){

                updateString(3);
            }
            else if(whichOne.equals("4")){

                updateString(4);
            }
            else if(whichOne.equals("5")){

                updateString(5);
            }   
            else if(whichOne.equals("6")){

                updateString(6);
            }
            else if(whichOne.equals("7")){

                updateString(7);
            }
            else if(whichOne.equals("8")){

                updateString(8);
            }
            else if(whichOne.equals("9")){

                updateString(9);
                this.mainFrame.updateTextLabels();
            }
            else if(whichOne.equals("delete")){

                if(this.mainFrame.strPointer == 0){

                    if(mainFrame.leftLabelStr.length() <= 1){

                        mainFrame.leftLabelStr = "";
                    }
                    else{

                        mainFrame.leftLabelStr = mainFrame.leftLabelStr.substring(0, mainFrame.leftLabelStr.length() - 1);
                    }
                }
                else if(this.mainFrame.strPointer == 1){

                    if(mainFrame.middleLabelStr.length() <= 1){

                        mainFrame.middleLabelStr = "";
                    }
                    else{

                        mainFrame.middleLabelStr = mainFrame.middleLabelStr.substring(0, mainFrame.middleLabelStr.length() - 1);
                    }
                }
                else{

                    if(mainFrame.rightLabelStr.length() <= 1){

                        mainFrame.rightLabelStr = "";
                    }
                    else{

                        mainFrame.rightLabelStr = mainFrame.rightLabelStr.substring(0, mainFrame.rightLabelStr.length() - 1);
                    }
                }

                mainFrame.updateTextLabels();
            }
            else if(whichOne.equals("deleteAll")){

                if(this.mainFrame.strPointer == 0){

                    mainFrame.leftLabelStr = "";
                }
                else if(this.mainFrame.strPointer == 1){

                    mainFrame.middleLabelStr = "";
                }
                else{

                    mainFrame.rightLabelStr = "";
                }

                mainFrame.updateTextLabels();
            }
            else if(whichOne.equals("push")){

                if(mainFrame.leftLabelStr.length() == 0 || mainFrame.rightLabelStr.length() == 0 || mainFrame.middleLabelStr.length() == 0){

                    return;
                }

                if(mainFrame.leftLabelStr.charAt(0) == '0' || mainFrame.rightLabelStr.charAt(0) == '0' || mainFrame.middleLabelStr.charAt(0) == '0'){

                    return;
                }

                mainFrame.initializeGame(mainFrame, this.strToInt(mainFrame.rightLabelStr), this.strToInt(mainFrame.leftLabelStr), this.strToInt(mainFrame.middleLabelStr)); 
            }
            else if(whichOne.equals("left")){

                this.mainFrame.strPointer = 0;

                this.custom_left.imageHash = "yellowPointer";
                this.custom_middle.imageHash = "whiteBlue";
                this.custom_right.imageHash = "whiteBlue";

                this.custom_left.repaint();
                this.custom_middle.repaint();
                this.custom_right.repaint();
            }
            else if(whichOne.equals("middle")){

                this.mainFrame.strPointer = 1;

                this.custom_left.imageHash = "whiteBlue";
                this.custom_middle.imageHash = "yellowPointer";
                this.custom_right.imageHash = "whiteBlue";

                this.custom_left.repaint();
                this.custom_middle.repaint();
                this.custom_right.repaint();
            }
            else if(whichOne.equals("right")){

                this.mainFrame.strPointer = 2;

                this.custom_left.imageHash = "whiteBlue";
                this.custom_middle.imageHash = "whiteBlue";
                this.custom_right.imageHash = "yellowPointer";

                this.custom_left.repaint();
                this.custom_middle.repaint();
                this.custom_right.repaint();
            }
            else{
                // IMPOSSIBLE
            }
        }   
        else{

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
    
                    startButonPanel.imageHash = "startClosed";
                    
                    //!=====================================================================
                    //!     Ai ve gameInterface burada yaratiliyor
                    this.mainFrame.game.initializeEverythingAboutAi();
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
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {   

        if(isCustomPanelRelated){

            if(whichOne.equals("0")){

                this.custom_0.imageHash = "green0";    
                this.custom_0.repaint();            
            }
            else if(whichOne.equals("1")){

                this.custom_1.imageHash = "green1"; 
                this.custom_1.repaint();
            }
            else if(whichOne.equals("2")){

                this.custom_2.imageHash = "green2"; 
                this.custom_2.repaint();
            }
            else if(whichOne.equals("3")){

                this.custom_3.imageHash = "green3"; 
                this.custom_3.repaint();
            }
            else if(whichOne.equals("4")){

                this.custom_4.imageHash = "green4"; 
                this.custom_4.repaint();
            }
            else if(whichOne.equals("5")){

                this.custom_5.imageHash = "green5"; 
                this.custom_5.repaint();
            }
            else if(whichOne.equals("6")){

                this.custom_6.imageHash = "green6"; 
                this.custom_6.repaint();
            }
            else if(whichOne.equals("7")){

                this.custom_7.imageHash = "green7"; 
                this.custom_7.repaint();
            }
            else if(whichOne.equals("8")){

                this.custom_8.imageHash = "green8"; 
                this.custom_8.repaint();
            }
            else if(whichOne.equals("9")){

                this.custom_9.imageHash = "green9"; 
                this.custom_9.repaint();
            }
            else if(whichOne.equals("delete")){

                this.custom_delete.imageHash = "custom_delete2";
                this.custom_delete.repaint();
            }
            else if(whichOne.equals("deleteAll")){

                this.custom_deleteAll.imageHash = "custom_deleteAll2";
                this.custom_deleteAll.repaint();
            }
            else if(whichOne.equals("push")){

                if(mainFrame.leftLabelStr.length() == 0 || mainFrame.rightLabelStr.length() == 0 || mainFrame.middleLabelStr.length() == 0){

                    return;
                }

                if(mainFrame.leftLabelStr.charAt(0) == '0' || mainFrame.rightLabelStr.charAt(0) == '0' || mainFrame.middleLabelStr.charAt(0) == '0'){

                    return;
                }

                this.custom_push.imageHash = "custom_push2";
                this.custom_push.repaint();
            }
            else if(whichOne.equals("left")){

                if(mainFrame.strPointer == 0){

                    this.custom_left.imageHash = "yellowPointer";
                    this.custom_left.repaint();
                }
                else{

                    this.custom_left.imageHash = "redPointer";
                    this.custom_left.repaint();
                }
            }
            else if(whichOne.equals("middle")){

                if(mainFrame.strPointer == 1){

                    this.custom_middle.imageHash = "yellowPointer";
                    this.custom_middle.repaint();
                }
                else{

                    this.custom_middle.imageHash = "redPointer";
                    this.custom_middle.repaint();
                }
            }
            else if(whichOne.equals("right")){

                if(mainFrame.strPointer == 2){

                    this.custom_right.imageHash = "yellowPointer";
                    this.custom_right.repaint();
                }
                else{

                    this.custom_right.imageHash = "redPointer";
                    this.custom_right.repaint();
                }
            }
            else{
                // IMPOSSIBLE
            }
        }
        else{

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
    }

    @Override
    public void mouseExited(MouseEvent e) {

        if(isCustomPanelRelated){

            if(whichOne.equals("0")){

                this.custom_0.imageHash = "yellow0"; 
                this.custom_0.repaint();
            }
            else if(whichOne.equals("1")){

                this.custom_1.imageHash = "yellow1";
                this.custom_1.repaint();
            }
            else if(whichOne.equals("2")){

                this.custom_2.imageHash = "yellow2";
                this.custom_2.repaint();
            }
            else if(whichOne.equals("3")){

                this.custom_3.imageHash = "yellow3";
                this.custom_3.repaint();
            }
            else if(whichOne.equals("4")){

                this.custom_4.imageHash = "yellow4";
                this.custom_4.repaint();
            }
            else if(whichOne.equals("5")){

                this.custom_5.imageHash = "yellow5";
                this.custom_5.repaint();
            }
            else if(whichOne.equals("6")){

                this.custom_6.imageHash = "yellow6";
                this.custom_6.repaint();
            }
            else if(whichOne.equals("7")){

                this.custom_7.imageHash = "yellow7";
                this.custom_7.repaint();
            }
            else if(whichOne.equals("8")){

                this.custom_8.imageHash = "yellow8";
                this.custom_8.repaint();
            }
            else if(whichOne.equals("9")){

                this.custom_9.imageHash = "yellow9";
                this.custom_9.repaint();
            }
            else if(whichOne.equals("delete")){

                this.custom_delete.imageHash = "custom_delete1";
                this.custom_delete.repaint();
            }
            else if(whichOne.equals("deleteAll")){

                this.custom_deleteAll.imageHash = "custom_deleteAll1";
                this.custom_deleteAll.repaint();
            }
            else if(whichOne.equals("push")){

                this.custom_push.imageHash = "custom_push1";
                this.custom_push.repaint();
            }
            else if(whichOne.equals("left")){

                if(mainFrame.strPointer == 0){

                    this.custom_left.imageHash = "yellowPointer";
                    this.custom_left.repaint();
                }
                else{

                    this.custom_left.imageHash = "whiteBlue";
                    this.custom_left.repaint();
                }
            }
            else if(whichOne.equals("middle")){

                if(mainFrame.strPointer == 1){

                    this.custom_middle.imageHash = "yellowPointer";
                    this.custom_middle.repaint();
                }
                else{

                    this.custom_middle.imageHash = "whiteBlue";
                    this.custom_middle.repaint();
                }
            }
            else if(whichOne.equals("right")){

                if(mainFrame.strPointer == 2){

                    this.custom_right.imageHash = "yellowPointer";
                    this.custom_right.repaint();
                }
                else{

                    this.custom_right.imageHash = "whiteBlue";
                    this.custom_right.repaint();
                }
            }
            else{
                // IMPOSSIBLE
            }
        }
        else{

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
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    public void updateString(int numberBetween0_9){

        if(this.mainFrame.strPointer == 0){ //?  left

            if(this.mainFrame.leftLabelStr.length() == 10){

                return; //? length limit 
            }

            switch (numberBetween0_9) {
                case 0:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "0";
                    break;
                case 1:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "1";
                    break;
                case 2:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "2";
                    break;
                case 3:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "3";
                    break;
                case 4:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "4";
                    break;
                case 5:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "5";
                    break;
                case 6:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "6";
                    break;
                case 7:
                this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "7";
                    break;
                case 8:
                    this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "8";;
                    break;
                case 9:
                    this.mainFrame.leftLabelStr = this.mainFrame.leftLabelStr + "9";
                    break;
                default:
                    break;
            }
        }
        else if(this.mainFrame.strPointer == 1){ //? middle

            if(this.mainFrame.middleLabelStr.length() == 10){

                return; //? length limit 
            }

            switch (numberBetween0_9) {
                case 0:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "0";
                    break;
                case 1:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "1";
                    break;
                case 2:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "2";
                    break;
                case 3:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "3";
                    break;
                case 4:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "4";
                    break;
                case 5:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "5";
                    break;
                case 6:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "6";
                    break;
                case 7:
                this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "7";
                    break;
                case 8:
                    this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "8";;
                    break;
                case 9:
                    this.mainFrame.middleLabelStr = this.mainFrame.middleLabelStr + "9";
                    break;
                default:
                    break;
            }
        }
        else{ //?   right

            if(this.mainFrame.leftLabelStr.length() == 10){

                return; //? length limit 
            }

            switch (numberBetween0_9) {
                case 0:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "0";
                    break;
                case 1:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "1";
                    break;
                case 2:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "2";
                    break;
                case 3:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "3";
                    break;
                case 4:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "4";
                    break;
                case 5:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "5";
                    break;
                case 6:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "6";
                    break;
                case 7:
                this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "7";
                    break;
                case 8:
                    this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "8";;
                    break;
                case 9:
                    this.mainFrame.rightLabelStr = this.mainFrame.rightLabelStr + "9";
                    break;
                default:
                    break;
            }
        }

        this.mainFrame.updateTextLabels();
    }

    public int strToInt(String str){

        int sum = 0;
        int iteration = 0;

        for(int i = str.length() - 1 ; i >= 0; i --){

            char chr = str.charAt(i);
            int chrValue = Character.getNumericValue(chr);
            int basamakCarpani = (int) Math.pow(10, iteration);

            sum = sum + (basamakCarpani * chrValue);

            iteration ++;
        }

        return sum;
    }
}
