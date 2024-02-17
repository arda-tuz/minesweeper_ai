package groupid;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;

/* 
 * Asil game grid'in oldugu sliding panel'in herseyini bu Game objesi control edecek tum tile'lari burasi yaratip atayacak 
 * En son obje yok olurken o panelide temizleyecek 
 * 
*/
//! NOT:    custom denerken    40 x 40 ve mineCount = 300 ile dene 

public class Game { //  bu obje asil game olacak Ai ile gui arasinda interface olarak duracak

    GameInterface gameInterface;
    Ai ai;

    public CardLayout clPeek;
    public ImagePanel startPanelRightClContainer;
    public ImagePanel timePanel;
    public CardLayout clTimePanel;
    public ImagePanel timePanelWinLose;

    public ImagePanel threadDicePanel;
    public ImagePanel threadClockPanel;
    public ThreadClock threadClock;
    public ThreadDice threadDice;
    

    public MainFrame mainFrame;
    public ImagePanel gamePanel;
    public JLabel mineCounterTextLabel;
    public boolean isAiStarted = false;

    public int[] restartInformationArr;

    public boolean isPeekModeOn = false;

    public PrintWriter printFile1;
    public Scanner scanFile1;
    public File srcFile;
    
    public boolean isGameOver = false;
    public boolean isGameWon = false;
    
    public ImagePanel gamePlayGridPanel;

    public int mineNumber;
    public int satirSize;
    public int sutunSize;

    public TilePanel[][] tileBoard;     // bu 3' ude ai' in kendi boardi degil 
    public char[][] mineBoard;
    public char[][] userBoard; // user board ai ve gui icin interface  
    public boolean[][] ffIsVisitedArr;  // eski oyundaki $ lara denk geiyor 

    public int flagCount;  // koyulan flag sayisi  
    public int openedCount; // mayin olmayan kac tane tile acilmis
    public int goalCount;   // toplam acilmasi gereken mine olmayan cell sayisi
    public int mineCounterText;

    //  mineBoard = [m, -]  // m = mine // - = no mine //
    //  userBoard = [0, 1, 2, 3, 4, 5, 6, 7, 8, f, c] // f = flag // c = closed

    public boolean isFirstMove;
    public Pair firstMineIndexPair;

    public Game(MainFrame mainFrame, int mineNumber, int satirSize, int sutunSize){

        //! initialize the thread objects at the same time with the game object
        this.threadClock = new ThreadClock(mainFrame);  
        this.threadDice = new ThreadDice(mainFrame);

        //?         restart game yapinca onceki oyunun bilgileri buradan alinacak
        this.restartInformationArr = new int[3];
        this.restartInformationArr[0] = mineNumber;
        this.restartInformationArr[1] = satirSize;      
        this.restartInformationArr[2] = sutunSize;

        this.mainFrame = mainFrame;
        this.gamePanel = new ImagePanel(mainFrame);
        this.mainFrame.clPanelMap.put("cl_game", gamePanel); // Hizli erisebilmek icin gamePanel instance yaptim 

        //? initialize the srcFile to write mine map on it 
        this.srcFile =  new File(mainFrame.fixPath("src\\main\\resources\\mineMapFile.txt")); 

        this.mineNumber = mineNumber;
        this.mineCounterText = mineNumber;
        this.satirSize = satirSize;
        this.sutunSize = sutunSize;

        this.tileBoard = new TilePanel[satirSize][sutunSize];
        this.mineBoard = new char[satirSize][sutunSize];
        this.userBoard = new char[satirSize][sutunSize];
        this.ffIsVisitedArr = new boolean[satirSize][sutunSize];
        initialize_ffIsVisitedArr();

        this.flagCount = 0;
        this.openedCount = 0;
        this.goalCount = (this.satirSize * this.sutunSize) - mineNumber;

        this.isFirstMove = true; // First move asla mayin olamaz

        this.initialize_cl_game();
    }

    public void initialize_cl_game(){   //?     cl_game'e ait tum compenentlar run time da burada yaratilcak ve ayarlancak !!!

        ImagePanel cl_game = this.gamePanel;

        cl_game.setLayout(new BorderLayout());

        this.gamePlayGridPanel = new ImagePanel(mainFrame);
        ImagePanel southPanel = new ImagePanel(mainFrame);

        //------------------------------------------------------------------------
        //?             tile'larida initialize et
        gamePlayGridPanel.setLayout(new GridLayout(satirSize, sutunSize));
        this.initializeMineBoard();

        this.initializeUserBoard();
        //------------------------------------------------------------------------

        southPanel.setLayout(new GridLayout(1, 3));

        ImagePanel mineCountPanel = new ImagePanel(mainFrame, "blackBorder");
        this.timePanel = new ImagePanel(mainFrame);  
        ImagePanel startPanel = new ImagePanel(mainFrame);
        startPanel.setLayout(new GridLayout(1,2));

        southPanel.add(mineCountPanel); southPanel.add(timePanel); southPanel.add(startPanel);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        ImagePanel startPanelLeftContainer = new ImagePanel(mainFrame, "startOpen");
        startPanel.add(startPanelLeftContainer);

        this.startPanelRightClContainer = new ImagePanel(mainFrame);
        this.clPeek = new CardLayout();
        startPanelRightClContainer.setLayout(clPeek);

        ImagePanel cardPanelPeek = new ImagePanel(mainFrame, "eyeClosed");
        ImagePanel cardPanelEnd = new ImagePanel(mainFrame);
        cardPanelEnd.setLayout(new GridLayout(1,2));

        ImagePanel cardPanelEndLeft = new ImagePanel(mainFrame, "restart"); 
        ImagePanel cardPanelEndRight = new ImagePanel(mainFrame, "menuIcon");
        cardPanelEnd.add(cardPanelEndLeft); cardPanelEnd.add(cardPanelEndRight);

        startPanelRightClContainer.add(cardPanelPeek, "peekMode"); //?   peek olayi icin eye icon gosterilir
        startPanelRightClContainer.add(cardPanelEnd,"endMode");   //?   oyun bitince restart ve main menu icon gosterilir

        startPanel.add(startPanelRightClContainer);
        clPeek.show(startPanelRightClContainer, "peekMode");

        PanelButtonListener panelButtonListener1 = new PanelButtonListener(mainFrame, cardPanelPeek, startPanelLeftContainer, cardPanelEndLeft, cardPanelEndRight
        ,"peek");
        cardPanelPeek.addMouseListener(panelButtonListener1);
        cardPanelPeek.addMouseMotionListener(panelButtonListener1);

        PanelButtonListener panelButtonListener2  = new PanelButtonListener(mainFrame, cardPanelPeek, startPanelLeftContainer, cardPanelEndLeft, cardPanelEndRight
        ,"start");
        startPanelLeftContainer.addMouseListener(panelButtonListener2);
        startPanelLeftContainer.addMouseMotionListener(panelButtonListener2);

        PanelButtonListener panelButtonListener3  = new PanelButtonListener(mainFrame, cardPanelPeek, startPanelLeftContainer, cardPanelEndLeft, cardPanelEndRight
        ,"restart");
        cardPanelEndLeft.addMouseListener(panelButtonListener3);
        cardPanelEndLeft.addMouseMotionListener(panelButtonListener3);

        PanelButtonListener panelButtonListener4  = new PanelButtonListener(mainFrame, cardPanelPeek, startPanelLeftContainer, cardPanelEndLeft, cardPanelEndRight
        ,"menu");
        cardPanelEndRight.addMouseListener(panelButtonListener4);
        cardPanelEndRight.addMouseMotionListener(panelButtonListener4);

        //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        cl_game.add(gamePlayGridPanel, BorderLayout.CENTER);
        cl_game.add(southPanel, BorderLayout.SOUTH);

        cl_game.panelMap.put("gamePlayGridPanel", gamePlayGridPanel);
        cl_game.panelMap.put("southPanel", southPanel);
        cl_game.panelMap.put("mineCountPanel", mineCountPanel);
        cl_game.panelMap.put("timePanel", timePanel);
        cl_game.panelMap.put("startPanel", startPanel);


        this.clTimePanel = new CardLayout();
        this.timePanel.setLayout(this.clTimePanel);
        ImagePanel timePanelCl1 = new ImagePanel(mainFrame, "blackBorder"); //? bu dice ve saati tuan card
        this.timePanel.add(timePanelCl1, "game");

        this.timePanelWinLose = new ImagePanel(mainFrame);    //? bu win / lose yazisini tutan card
        this.timePanel.add(timePanelWinLose, "endGame");

        timePanelCl1.setLayout(new  GridLayout(1,2));
        mineCountPanel.setLayout(new GridLayout(1,2));

        this.clTimePanel.show(this.timePanel, "game"); //? ilk basta dice ve saat tutan card ile baslanir hep

        ImagePanel mineCounterImagePanel = new ImagePanel(mainFrame, "mineCounter");
        ImagePanel mineCounterTextPanel = new ImagePanel(mainFrame);

        this.mineCounterTextLabel = new JLabel("" + this.mineNumber);
        mineCounterTextLabel.setFont(new Font("Courier New", Font.BOLD, 30));
        mineCounterTextPanel.add(mineCounterTextLabel);

        mineCountPanel.add(mineCounterImagePanel); mineCountPanel.add(mineCounterTextPanel);
        cl_game.panelMap.put("mineCounterImagePanel", mineCounterImagePanel);
        cl_game.panelMap.put("mineCounterTextPanel",mineCounterTextPanel);

        ImagePanel threadLeftPanel = new ImagePanel(mainFrame, "dice");
        threadLeftPanel.isThreadPanel = true;
        ImagePanel threadRightPanel = new ImagePanel(mainFrame, "clock0");
        threadRightPanel.isThreadPanel = true;

        this.threadDicePanel = threadLeftPanel;
        this.threadClockPanel = threadRightPanel;

        timePanelCl1.add(threadLeftPanel); timePanelCl1.add(threadRightPanel);
        cl_game.panelMap.put("threadLeftPanel", threadLeftPanel);
        cl_game.panelMap.put("threadRightPanel",threadRightPanel);

        this.writeToFile(); //? write the mine map to a file

        this.initializeTileBoard();     // gamePlayGridPanel'i TILE'lar ile doldur 

        //      will go on ....

        this.mainFrame.clPanel.add(cl_game,"cl_game");

        this.mainFrame.cl.show(mainFrame.clPanel, "cl_game");
        mainFrame.currentPanel = "cl_game";
    }

    private void initializeEndGameSequence(String winOrLose){ // "win" // "lose" olarak input alcak

        if(winOrLose.equals("win")){

            this.clPeek.show(this.startPanelRightClContainer, "endMode");
            this.timePanelWinLose.imageHash = "youWin";
            this.clTimePanel.show(this.timePanel, "endGame");
        }
        else{

            this.clPeek.show(this.startPanelRightClContainer, "endMode");
            this.timePanelWinLose.imageHash = "youLose";
            this.clTimePanel.show(this.timePanel, "endGame");
        }

        this.timePanel.repaint();
    }

    private void writeToFile(){

        try{

            this.printFile1 = new PrintWriter(new FileOutputStream(this.srcFile));

        }catch(FileNotFoundException e){
            System.out.println("file not found ex");
        }

        for(int i = 0; i < satirSize; i ++){

            String line = "";

            for(int j = 0; j < sutunSize; j++){

                line = line + mineBoard[i][j];
            }
            
            printFile1.println(line);
        } 

        // Close the PrintWriter
        printFile1.close();
    }

    private void initializeTileBoard(){

        for(int i = 0; i < satirSize; i ++){

            for(int j = 0; j < sutunSize; j ++){

                TilePanel newPanel = new TilePanel(mainFrame, "c", i, j);  // all geme tiles start off as closed
                newPanel.whichKindOfPanel = 0;  //?         they are game panels
                this.tileBoard[i][j] = newPanel;

                gamePanel.panelMap.get("gamePlayGridPanel").add(newPanel);
            }
        }
    }
    
    private void initialize_ffIsVisitedArr(){

        for(int i = 0; i < satirSize; i ++){

            for(int j = 0; j < sutunSize; j ++){

                this.ffIsVisitedArr[i][j] = false;
            }
        }
    }

    private HashSet<Pair> scatterTheMines() {

        HashSet<Pair> set = new HashSet<>(satirSize); 
    
        while(set.size() <= mineNumber) {

            Random rand = new Random();
            int randSatir = rand.nextInt(satirSize);
            int randSutun = rand.nextInt(sutunSize);
    
            Pair newPair = new Pair(randSatir, randSutun);
            set.add(newPair);
        }   

        for(Pair each : set){

            this.firstMineIndexPair = each;
            set.remove(each);
            break;
        }

        return set;
    }

    private void initializeMineBoard(){

        for(int i = 0; i < satirSize; i ++){

            for(int j = 0; j < sutunSize; j ++){

                this.mineBoard[i][j] = '-';
            }
        }

        HashSet<Pair> set = this.scatterTheMines();

        for (Pair pair : set) {
            
            int satirIndex = pair.satir;
            int sutunIndex = pair.sutun;

            this.mineBoard[satirIndex][sutunIndex] = 'm';
        }
    }   

    private void initializeUserBoard(){

        for(int i = 0; i < satirSize; i ++){

            for(int j = 0; j < sutunSize; j ++){

                this.userBoard[i][j] = 'c'; // start off all closed
            }
        }
    }

    private void updateMineCounter(){

        this.mineCounterText = this.mineNumber - this.flagCount;
        this.mineCounterTextLabel.setText("" + this.mineCounterText);
    }

    private char howManyKomsuMine(int x, int y){

            int count = 0;
            int xBoy = this.mineBoard.length;
            int yBoy = this.mineBoard[0].length;
            char[][] array = this.mineBoard;

            if(x == 0 && y ==0)// sol ust kose 
            {
                count = 0;
                if(array[0][1] == 'm')
                {
                    count++;
                }
                if(array[1][0] == 'm')
                {
                    count ++;
                }
                if(array[1][1] == 'm')
                {
                    count ++;
                }
            }
            else if(x == xBoy-1 && y == yBoy-1)// sag alt kose 
            {
                count =0;
                if(array[xBoy-2][yBoy-1] == 'm')
                {
                    count++;
                }
                if(array[xBoy-1][yBoy-2] == 'm')
                {
                    count ++;
                }
                if(array[xBoy-2][yBoy-2] == 'm')
                {
                    count ++;
                }
            }
            else if(x == xBoy-1  && y == 0) // sol alt kose 
            {
                count =0;
                if(array[xBoy -2][0] == 'm')
                {
                    count++;
                }
                if(array[xBoy-1][1] == 'm')
                {
                    count ++;
                }
                if(array[xBoy-2][1] == 'm')
                {
                    count ++;
                }
            }
            else if(x == 0 && y == yBoy-1) // sag ust kose 
            {
                count =0;
                if(array[0][yBoy-2] == 'm')
                {
                    count++;
                }
                if(array[1][yBoy-1] == 'm')
                {
                    count ++;
                }
                if(array[1][yBoy-2] == 'm')
                {
                    count ++;
                }
            }
            else if(y == 0 && x != 0 && x != xBoy-1) //sol kenar        
            {
                count = 0;
                if(array[x-1][0] == 'm')
                {
                    count++;
                }
                if(array[x+1][0] == 'm')
                {
                    count++;
                }
                if(array[x][1] == 'm')
                {
                    count++;
                }
                if(array[x-1][1] == 'm')
                {
                    count++;
                }
                if(array[x+1][1] == 'm')
                {
                    count++;
                }

            }
            else if(x != 0 && x != xBoy-1 && y == yBoy -1) //sag kenar 
            {
                count = 0;
                if(array[x-1][yBoy-1] == 'm')
                {
                    count++;
                }
                if(array[x+1][yBoy-1] == 'm')
                {
                    count++;
                }
                if(array[x][yBoy-2] == 'm')
                {
                    count++;
                }
                if(array[x-1][yBoy-2] == 'm')
                {
                    count++;
                }
                if(array[x+1][yBoy-2] == 'm')
                {
                    count++;
                }

            }

            else if(x == 0 && y != yBoy-1 && y != 0) //ust kenar 
            {
                count = 0;
                if(array[0][y-1] == 'm')
                {
                    count++;
                }
                if(array[0][y+1] == 'm')
                {
                    count++;
                }
                if(array[1][y] == 'm')
                {
                    count++;
                }
                if(array[1][y-1] == 'm')
                {
                    count++;
                }
                if(array[1][y+1] == 'm')
                {
                    count++;
                }

            }

            else if(x == xBoy-1 && y != 0 && y != yBoy -1) //alt kenar 
            {
                count = 0;
                if(array[xBoy-1][y-1] == 'm')
                {
                    count++;
                }
                if(array[xBoy-1][y+1] == 'm')
                {
                    count++;
                }
                if(array[xBoy-2][y] == 'm')
                {
                    count++;
                }
                if(array[xBoy-2][y-1] == 'm')
                {
                    count++;
                }
                if(array[xBoy-2][y+1] == 'm')
                {
                    count++;
                }

            }
            else // icerdeki noktalar 
            {
                count = 0;

                if(array[x-1][y] == 'm')//
                {
                    count++;
                }
                if(array[x+1][y] == 'm')//
                {
                    count++;
                }
                if(array[x][y-1] == 'm')//
                {
                    count++;
                }
                if(array[x][y+1] == 'm')//
                {
                    count++;
                }
                if(array[x-1][y-1] == 'm')//
                {
                    count++;
                }
                if(array[x-1][y+1] == 'm')//
                {
                    count++;
                }
                if(array[x+1][y+1] == 'm')//
                {
                    count++;
                }
                if(array[x+1][y-1] == 'm')//
                {
                    count++;
                }
            }

            return Character.forDigit(count, 10);
        }

    public void openTile(int x, int y){ // floodfill icersinde 

        if(this.isFirstMove){   // Ilk move'da mayina basmak imkansiz olmali 
        
            if(mineBoard[x][y] == 'm'){

                mineBoard[x][y] = '-';
                mineBoard[this.firstMineIndexPair.satir][this.firstMineIndexPair.sutun] = 'm';

                this.writeToFile(); //? write the mine map to a file again because the mine map has changed

                if(this.isPeekModeOn){

                    //? run the peek algo again bacause the mine map has changed 
                    for(int i = 0; i < satirSize; i ++){

                        for(int j = 0; j < sutunSize; j++){
        
                            if(userBoard[i][j] == 'c' && mineBoard[i][j] == 'm'){
        
                                tileBoard[i][j].imageHash = "peek";
                            }
                            if(userBoard[i][j] == 'f' && mineBoard[i][j] == 'm'){
        
                                tileBoard[i][j].imageHash = "peekFlag";
                            }
                        }
                    }
                }
            }
            else{

                // devamke 
            }

            this.isFirstMove = false;
        }

        if(this.isGameOver || this.isGameWon){

            return;
        }  
        
        if(ffIsVisitedArr[x][y] == true){ //? if we visited before return 

            return;
        }
        if(userBoard[x][y] != 'c'){

            return;
        }
        if(userBoard[x][y] == 'f'){ //? if flagged return
            
            return;
        }

        checkGameOver(x, y);

        if(this.isGameOver){

            return;
        }

        char adjacentMineNumber = howManyKomsuMine(x, y);
        
        if(adjacentMineNumber== '1' || adjacentMineNumber == '2' ||adjacentMineNumber == '3' || adjacentMineNumber == '4' ||
        adjacentMineNumber == '5' || adjacentMineNumber == '6' || adjacentMineNumber == '7' || adjacentMineNumber == '8') {
            
            userBoard[x][y] = adjacentMineNumber;
            tileBoard[x][y].imageHash = "" + adjacentMineNumber;
            this.gamePlayGridPanel.repaint();

            this.ffIsVisitedArr[x][y] = true;

            //!----------------------------------------
            //!     BASE CASE 1
            //!----------------------------------------
            this.openedCount ++;

            checkGameWon();

            if(this.isGameWon){

                return;
            }
        }
        if(adjacentMineNumber == '0'){

            int xBoy = this.satirSize;
            int yBoy = this.sutunSize;

            userBoard[x][y] = '0';
            tileBoard[x][y].imageHash = "0";
            this.gamePlayGridPanel.repaint();

            this.ffIsVisitedArr[x][y] = true;

            //!----------------------------------------
            //!     BASE CASE 1
            //!----------------------------------------
            this.openedCount ++;

            checkGameWon();

            if(this.isGameWon){

                return;
            }

            if(x == 0 && y == 0)// sol ust kose 
            {
                openTile(0, 1);
                
                openTile(1, 0);
            
                openTile(1, 1);
            }
            else if(x == xBoy-1 && y == yBoy-1)// sag alt kose 
            {
                openTile(xBoy-2, yBoy-1);
                
                openTile(xBoy-1, yBoy-2);
            
                openTile(xBoy-2, yBoy-2);
                
            }
            else if(x == xBoy-1  && y == 0) // sol alt kose 
            {
                openTile(xBoy-1, 1);
            
                openTile(xBoy-2, 0);
                
                openTile(xBoy-2, 1);
                
            }
            else if(x == 0 && y == yBoy-1) // sag ust kose 
            {
                openTile(0, yBoy-2);
                
                openTile(1, yBoy-2);
                
                openTile(1, yBoy -1);
            }
            else if(y == 0 && x != 0 && x != xBoy-1) //sol kenar  
            {
                
                openTile(x-1, 0);
                
                openTile(x+1, 0);
                
                openTile(x, 1);
                
                openTile(x-1, 1);
                
                openTile(x+1, 1);

            }
            else if(x != 0 && x != xBoy-1 && y == yBoy -1) //sag kenar 
            {   
            
                openTile(x-1, yBoy-1);
                
                openTile(x+1, yBoy-1);

                openTile(x, yBoy-2);
            
                openTile(x-1, yBoy-2);
            
                openTile(x+1, yBoy-2);

            }

            else if(x == 0 && y != yBoy-1 && y != 0) //ust kenar 
            {
                openTile(0, y-1);
                
                openTile(0, y+1);
                
                openTile(1, y);
                
                openTile(1, y-1);
            
                openTile(1, y+1);

            }

            else if(x == xBoy-1 && y != 0 && y != yBoy -1) //alt kenar 
            {
                openTile(xBoy-1, y-1);
            
                openTile(xBoy-1, y+1);
                
                openTile(xBoy-2, y);
                
                openTile(xBoy-2, y-1);
            
                openTile(xBoy-2, y+1);
            }
            else // icerdeki noktalar 
            {
                openTile(x-1, y);
                
                openTile(x+1, y);
                
                openTile(x, y-1);
                
                openTile(x, y+1);
                
                openTile(x-1, y-1);
            
                openTile(x-1, y+1);
            
                openTile(x+1, y+1);
                
                openTile(x+1, y-1);
            }
        }
    }

    private void checkGameOver(int x, int y){   //? check if we stepper overa  mine or not 

        if(mineBoard[x][y] == 'm'){

            for(int i = 0; i < satirSize; i++){

                for(int j = 0; j < sutunSize; j++){

                    if(mineBoard[i][j] == 'm'){

                        tileBoard[i][j].imageHash = "m";
                    }
                }
            }

            tileBoard[x][y].imageHash = "gameOverMine";
            
            this.gamePlayGridPanel.repaint();
            this.isGameOver = true;
            this.initializeEndGameSequence("lose");

            destroyAll3Threads();
        }  
    }

    private void checkGameWon(){

        if(this.openedCount == this.goalCount){ 

            for(int i = 0; i < satirSize; i++){

                for(int j = 0; j < sutunSize; j++){

                    if(mineBoard[i][j] == 'm' && userBoard[i][j] != 'f'){

                        tileBoard[i][j].imageHash = "greenM";
                    }
                    else if(mineBoard[i][j] == 'm' && userBoard[i][j] == 'f'){

                        tileBoard[i][j].imageHash = "greenF";
                    }
                }
            }

            this.gamePlayGridPanel.repaint();
            this.isGameWon = true;
            this.initializeEndGameSequence("win");

            destroyAll3Threads();
        }
    }

    public void flagTile(int x, int y){

        if(this.isGameOver == true || this.isGameWon == true){

            return;
        }

        if(this.userBoard[x][y] == 'f'){    //? FLAGGED 

            return;
        }
        if(userBoard[x][y] == '0' || userBoard[x][y] == '1' || userBoard[x][y] == '2' || userBoard[x][y] =='3' ||
        userBoard[x][y] == '4' || userBoard[x][y] == '5' || userBoard[x][y] == '6' || userBoard[x][y] == '7' || userBoard[x][y] == '8') {   //? OPEN
            
            return;
        }

        userBoard[x][y] = 'f';

        if(this.isPeekModeOn){

            if(mineBoard[x][y] == 'm'){

                this.tileBoard[x][y].imageHash = "peekFlag";
            }
            else{

                this.tileBoard[x][y].imageHash = "f"; 
            }
        }
        else{

            this.tileBoard[x][y].imageHash = "f";
        }
    
        flagCount ++;
        updateMineCounter();

        this.gamePlayGridPanel.repaint();
    }

    public void unflagTile(int x, int y){

        if(this.isGameOver == true || this.isGameWon == true){

            return;
        }

        if(userBoard[x][y] != 'f'){

            return;
        }

        userBoard[x][y] = 'c';

        if(this.isPeekModeOn){

            if(mineBoard[x][y] == 'm'){

                this.tileBoard[x][y].imageHash = "peek";
            }
            else{

                this.tileBoard[x][y].imageHash = "c";
            }
        }
        else{

            this.tileBoard[x][y].imageHash = "c";
        }

        flagCount --;
        updateMineCounter();

        this.gamePlayGridPanel.repaint();
    }

    public void peek(){

        if(this.isGameOver || this.isGameWon){

            this.isPeekModeOn = false;
            return;
        }

        if(this.isPeekModeOn){

            for(int i = 0; i < satirSize; i ++){

                for(int j = 0; j < sutunSize; j++){

                    if(tileBoard[i][j].imageHash.equals("peek")){

                        tileBoard[i][j].imageHash = "c";
                    }
                    if(tileBoard[i][j].imageHash.equals("peekFlag")){

                        tileBoard[i][j].imageHash = "f";
                    }
                }
            }

            this.isPeekModeOn = false;
        }       
        else{

            for(int i = 0; i < satirSize; i ++){

                for(int j = 0; j < sutunSize; j++){

                    if(userBoard[i][j] == 'c' && mineBoard[i][j] == 'm'){

                        tileBoard[i][j].imageHash = "peek";
                    }
                    if(userBoard[i][j] == 'f' && mineBoard[i][j] == 'm'){

                        tileBoard[i][j].imageHash = "peekFlag";
                    }
                }
            }

            this.isPeekModeOn = true;
        }

        gamePlayGridPanel.repaint();
    }

    public void startThreadClock(){

        threadClock.execute();
    }

    public void startThreadDice(){

        threadDice.execute();
    }

    public void stopThreadClock(){

        if(this.threadClock == null){

            //? zaten thread henuz yaratilmamis yada zaten yok edilmis.
            return;
        }
        else{

            this.threadClock.cancel(true);
            this.threadClock.isOff = true;
        }
    }

    public void stopThreadDice(){

        if(this.threadDice == null){

            //? zaten thread henuz yaratilmamis yada zaten yok edilmis.
            return;
        }
        else{

            this.threadDice.cancel(true);
            this.threadDice.isOff = true;
        }
    }

    public void stopThreadAi(){

        if(this.ai == null){

            //? zaten thread henuz yaratilmamis yada zaten yok edilmis.
            return;
        }
        else{

            this.ai.cancel(true);
            this.ai.isOff = true;
        }
    }

    public void destroyAll3Threads(){

        this.stopThreadClock();
        this.stopThreadDice();
        this.stopThreadAi();
    }

    public void initializeEverythingAboutAi(){

        this.gameInterface = new GameInterface(this);
        this.ai = new Ai(gameInterface);
    }

    public void printBoard(char[][] board){

        for(int i = 0; i < satirSize; i++){

            for(int j = 0; j < sutunSize; j ++){

                System.out.print(board[i][j] + "  ");
            }

            System.out.println();
        }

        System.out.println();
    }
}

