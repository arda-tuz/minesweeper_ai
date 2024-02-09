package groupid;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.io.*;
import java.lang.ref.Cleaner.Cleanable;
import java.util.*;

/* 
 * Asil game grid'in oldugu sliding panel'in herseyini bu Game objesi control edecek tum tile'lari burasi yaratip atayacak 
 * En son obje yok olurken o panelide temizleyecek 
 * 
*/

public class Game { //  bu obje asil game olacak Ai ile gui arasinda interface olarak duracak
    
    public MainFrame mainFrame;
    public ImagePanel gamePanel;
    public JLabel mineCounterTextLabel;

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

        this.mainFrame = mainFrame;
        this.gamePanel = mainFrame.clPanelMap.get("cl_game"); // Hizli erisebilmek icin gamePanel instance yaptim 

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

        HashMap<String, ImagePanel> clPanelMap = this.mainFrame.clPanelMap;

        ImagePanel cl_game = clPanelMap.get("cl_game");
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
        ImagePanel timePanel = new ImagePanel(mainFrame, "blackBorder");  
        ImagePanel startPanel = new ImagePanel(mainFrame);
        startPanel.setLayout(new FlowLayout());

        southPanel.add(mineCountPanel); southPanel.add(timePanel); southPanel.add(startPanel);

        MyJbutton startGameButton = new MyJbutton(mainFrame, "START");
        startPanel.add(startGameButton);
        startGameButton.setFont(new Font("Serif", Font.BOLD, 25));
        startGameButton.setActionCommand("startGame");
        startGameButton.setBackground(mainFrame.greyBlue);

        MyJbutton peekGameButton = new MyJbutton(mainFrame, "PEEK");
        startPanel.add(peekGameButton);
        peekGameButton.setFont(new Font("Serif", Font.ITALIC, 25));
        peekGameButton.setActionCommand("peek");
        peekGameButton.setBackground(mainFrame.lighterGreyBlue);

        cl_game.add(gamePlayGridPanel, BorderLayout.CENTER);
        cl_game.add(southPanel, BorderLayout.SOUTH);

        cl_game.buttonMap.put("startGameButton", startGameButton);
        cl_game.buttonMap.put("peekGameButton", peekGameButton);

        cl_game.panelMap.put("gamePlayGridPanel", gamePlayGridPanel);
        cl_game.panelMap.put("southPanel", southPanel);
        cl_game.panelMap.put("mineCountPanel", mineCountPanel);
        cl_game.panelMap.put("timePanel", timePanel);
        cl_game.panelMap.put("startPanel", startPanel);

        timePanel.setLayout(new  GridLayout(1,2));
        mineCountPanel.setLayout(new GridLayout(1,2));

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
        ImagePanel threadRightPanel = new ImagePanel(mainFrame, "clock1");
        threadRightPanel.isThreadPanel = true;
        timePanel.add(threadLeftPanel); timePanel.add(threadRightPanel);
        cl_game.panelMap.put("threadLeftPanel", threadLeftPanel);
        cl_game.panelMap.put("threadRightPanel",threadRightPanel);

        this.writeToFile(); //? write the mine map to a file

        this.initializeTileBoard();     // gamePlayGridPanel'i TILE'lar ile doldur 

        //      will go on ....

        this.mainFrame.cl.show(mainFrame.clPanel, "cl_game");
        mainFrame.currentPanel = "cl_game";
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

                //? run the peek algo again bacause the mine map has changed 
                for(int i = 0; i < satirSize; i ++){

                    for(int j = 0; j < sutunSize; j++){
    
                        if(userBoard[i][j] == 'c' && mineBoard[i][j] == 'm'){
    
                            tileBoard[i][j].imageHash = "peek";
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

        checkGameOver(x, y);

        if(ffIsVisitedArr[x][y] == true){ //? if we visited before return 

            return;
        }
        if(userBoard[x][y] != 'c'){

            return;
        }
        if(userBoard[x][y] == 'f'){ //? if flagged return
            
            return;
        }

        char adjacentMineNumber = howManyKomsuMine(x, y);
        
        if(adjacentMineNumber== '1' || adjacentMineNumber == '2' ||adjacentMineNumber == '3' || adjacentMineNumber == '4' ||
        adjacentMineNumber == '5' || adjacentMineNumber == '6' || adjacentMineNumber == '7' || adjacentMineNumber == '8') {
            
            userBoard[x][y] = adjacentMineNumber;
            tileBoard[x][y].imageHash = "" + adjacentMineNumber;
            this.gamePlayGridPanel.repaint();

            this.ffIsVisitedArr[x][y] = true;


            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            this.openedCount ++;

            checkGameWon(); 
        }
        if(adjacentMineNumber == '0'){

            int xBoy = this.satirSize;
            int yBoy = this.sutunSize;

            userBoard[x][y] = '0';
            tileBoard[x][y].imageHash = "0";
            this.gamePlayGridPanel.repaint();

            this.ffIsVisitedArr[x][y] = true;

            //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
            this.openedCount ++;

            checkGameWon();

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
            else if(y == 0 && x != 0 && x != xBoy-1) //sol kenar  //!!!!!
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

    private void changePeekButton(){

        MyJbutton peekButton = this.gamePanel.buttonMap.get("peekGameButton");

        peekButton.boyansinMi = false;
        peekButton.pressedColor = this.mainFrame.orange;
        peekButton.setActionCommand("createEndGame");
        peekButton.setText("Finish");
        peekButton.setBackground(this.mainFrame.orange);
        peekButton.repaint();
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
            
            this.gamePlayGridPanel.repaint();
            this.isGameOver = true;
            this.initialize_cl_endgame("lose");
            this.changePeekButton();
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
            this.initialize_cl_endgame("win");
            this.changePeekButton();
        }
    }

    public void flagTile(int x, int y){

        //printMineBoard();  //! for debug purposes only 

        if(this.isGameOver == true || this.isGameWon == true){

            return;
        }

        if(this.tileBoard[x][y].imageHash.equals("peek")){ //? peek modu acik iken siyah mine'larin ustune flag konulamaz

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
        this.tileBoard[x][y].imageHash = "f";
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
        this.tileBoard[x][y].imageHash = "c";
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
                }
            }

            this.isPeekModeOn = true;
        }

        gamePlayGridPanel.repaint();
    }

    private void initialize_cl_endgame(String winOrLose){ // "win" // "lose" olarak input alcak

        ImagePanel cl_endgame = this.mainFrame.clPanelMap.get("cl_endgame");
        cl_endgame.setLayout(new BorderLayout());    

        ImagePanel centerBorderPanel = new ImagePanel(mainFrame);
        centerBorderPanel.setLayout(new BorderLayout());

        ImagePanel outerSouthPanel = new ImagePanel(mainFrame);
        ImagePanel innerSouthPanel = new ImagePanel(mainFrame);
        outerSouthPanel.setLayout(new FlowLayout());
        innerSouthPanel.setLayout(new GridLayout(1,2));

        ImagePanel innerSouthPanel_left = new ImagePanel(mainFrame);
        ImagePanel innerSouthPanel_right = new ImagePanel(mainFrame);

        JLabel endGameTextLabelRight = new JLabel("" + this.openedCount + " / " + "" + this.goalCount);
        endGameTextLabelRight.setFont(new Font("Courier New", Font.BOLD, 40));
        JLabel endGameTextLabelLeft = new JLabel("Progress : ");
        endGameTextLabelLeft.setFont(new Font("Courier New", Font.BOLD, 40));

        MyJbutton restartGameButton = new MyJbutton(mainFrame, "Restart");
        restartGameButton.setBackground(this.mainFrame.greyBlue);
        restartGameButton.boyansinMi = true;
        restartGameButton.setForeground(Color.black);
        restartGameButton.setActionCommand("restartGame");
        restartGameButton.setFont(new Font("Courier New", Font.ITALIC, 60));

        ImagePanel araPanel = new ImagePanel(mainFrame);
        Dimension preferredSize = new Dimension(40, araPanel.getPreferredSize().height); 
        araPanel.setPreferredSize(preferredSize);

        MyJbutton mainMenuButton = new MyJbutton(mainFrame, "Menu");
        mainMenuButton.setForeground(Color.black);
        mainMenuButton.setBackground(this.mainFrame.greyBlue);
        mainMenuButton.boyansinMi = true;
        mainMenuButton.setActionCommand("mainMenu");
        mainMenuButton.setFont(new Font("Courier New", Font.ITALIC, 60));

        outerSouthPanel.add(restartGameButton);
        outerSouthPanel.add(araPanel);
        outerSouthPanel.add(mainMenuButton);

        ImagePanel youWinLosePanel = null;

        if(winOrLose.equals("win")){

            youWinLosePanel = new ImagePanel(mainFrame, "youWin");
        }
        else{

            youWinLosePanel = new ImagePanel(mainFrame, "youLose");
        }

        centerBorderPanel.add(youWinLosePanel, BorderLayout.CENTER);
        centerBorderPanel.add(innerSouthPanel, BorderLayout.SOUTH); 

        cl_endgame.add(centerBorderPanel, BorderLayout.CENTER);
        cl_endgame.add(outerSouthPanel, BorderLayout.SOUTH);

        innerSouthPanel.add(innerSouthPanel_left); innerSouthPanel.add(innerSouthPanel_right);
        innerSouthPanel_left.add(endGameTextLabelLeft); innerSouthPanel_right.add(endGameTextLabelRight);

        cl_endgame.repaint();
    }
}

