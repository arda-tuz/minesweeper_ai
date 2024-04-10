package groupid;
import java.util.*;
import javax.swing.SwingWorker;


public class Ai extends SwingWorker<Void, String>{

    //!===============================================================
    //!  KAC DENEMEDE EXPERT'DE BASARILI OLDU AI TESTLER:
    //!  6, 2, 10, 5, 4, 2, 8, 4, 2, 1, 2, 6, 4, 9, 7 
    //!  expertte ortalamada 7 elde 1 basarili oluyor genelde  ...
    //!===============================================================

    //?  userBoard = [0, 1, 2, 3, 4, 5, 6, 7, 8, f, c] // f = flag // c = closed

    public GameInterface gameInterface;
    public boolean isOff = false;
    public boolean isAiFirstMove = true;
    public int endGameLimit = 9;
    public boolean isEndGame = false;
    public ArrayList<boolean[]> backtrackingAlgoSolutions;

    public Ai(GameInterface gameInterface){

        this.gameInterface = gameInterface;
        this.gameInterface.startThreadClock();
        this.gameInterface.startThreadDice();

        this.execute();
    }

    @Override
    protected Void doInBackground() throws Exception {
        
        while(!isCancelled() && !isOff){

            if(gameInterface.isGameOver() || gameInterface.isGameWon()){

                break;//? OYUN BITTI ZATEN BU AI THREADININ ISI BITTI ARTIK
            }

            this.play();    //?     Tum AI hareketleri burada donecek

            Thread.sleep(10);
        }

        return null;
    }

    public void play(){

        if(this.isAiFirstMove){

            gameInterface.ThreadClockRestart();
            gameInterface.ThreadDiceSetMode("dice");
            System.out.println();
            System.out.println();
            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ FIRST_MOVE");
            gameInterface.openTile((gameInterface.getSatirSize() - 1) / 2, (gameInterface.getSutunSize() - 1) / 2);
            this.isAiFirstMove = false;
        }

        aiFlag();
        aiOpen();
    }

    public void aiFlag(){   

        //?  Note that we only flag a mine if we are certain that it is a mine
        //? that's why aiFlag() method only consists of the brainFlagAlgo() method

        arrowFlagAlgo();
    }

    public void aiOpen(){  

        //? BrainOpenAlgo() kesin olanlari acan algo.
        boolean isArrowOpenAlgoWorked = arrowOpenAlgo();

        if(!isArrowOpenAlgoWorked){ //? Basic algo calismadi ise o zaman backtracking'e gec

            brainDiceAlgo();
        }
    }

//?=======================================================================================================================================================
//?                     ALGORITHMIC AUXILARY METHODS ...
//?=======================================================================================================================================================

    public int countFlagTilesAround(int x, int y){

        char[][] userBoard = gameInterface.userBoard();
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        int count = 0;
        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && userBoard[x-1][y] == 'f') count++;
        if(!leftEdge && userBoard[x][y-1] == 'f') count++;
        if(!downEdge && userBoard[x+1][y] == 'f') count++;
        if(!rightEdge && userBoard[x][y+1] == 'f') count++;
        if(!upEdge && !leftEdge && userBoard[x-1][y-1] == 'f') count++;
        if(!upEdge && !rightEdge && userBoard[x-1][y+1] == 'f') count++;
        if(!downEdge && !leftEdge && userBoard[x+1][y-1] == 'f') count++;
        if(!downEdge && !rightEdge && userBoard[x+1][y+1]  == 'f') count++;

        return count;
    }

    public int countClosedTilesAround(int x, int y){

        char[][] userBoard = gameInterface.userBoard();
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        int count = 0;
        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && userBoard[x-1][y] == 'c') count++;
        if(!leftEdge && userBoard[x][y-1] == 'c') count++;
        if(!downEdge && userBoard[x+1][y] == 'c') count++;
        if(!rightEdge && userBoard[x][y+1] == 'c') count++;
        if(!upEdge && !leftEdge && userBoard[x-1][y-1] == 'c') count++;
        if(!upEdge && !rightEdge && userBoard[x-1][y+1] == 'c') count++;
        if(!downEdge && !leftEdge && userBoard[x+1][y-1] == 'c') count++;
        if(!downEdge && !rightEdge && userBoard[x+1][y+1]  == 'c') count++;

        return count;
    }

    public boolean isTileNumber(int x, int y){

        //? Checks whether the given tile has [0-8] or else on top of it 
        char[][] userBoard = gameInterface.userBoard();
        
        if(userBoard[x][y] == '0' || userBoard[x][y] == '1' || userBoard[x][y] == '2' || userBoard[x][y] == '3' ||
        userBoard[x][y] == '4' || userBoard[x][y] == '5' || userBoard[x][y] == '6' || userBoard[x][y] == '7' || userBoard[x][y] == '8') {

            return true;
        } else {

            return false;
        }
    }

    public boolean isTileNumberUserBoardSnapshot(char[][] userBoardSnapShot, int x, int y){

        //? Checks whether the given tile has [0-8] or else on top of it 
        char[][] userBoard = userBoardSnapShot;
        
        if(userBoard[x][y] == '0' || userBoard[x][y] == '1' || userBoard[x][y] == '2' || userBoard[x][y] == '3' ||
        userBoard[x][y] == '4' || userBoard[x][y] == '5' || userBoard[x][y] == '6' || userBoard[x][y] == '7' || userBoard[x][y] == '8') {

            return true;
        } else {

            return false;
        }
    }

    public boolean isEdgeTile(int x, int y){

        //?   eger kapali bir cell'in etrafinda birtane bile [0-8] square varsa o boundry dir
        char[][] userBoard = gameInterface.userBoard();
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();
    
        if(userBoard[x][y] != 'c'){
    
            return false; // kapali olmali
        }
    
        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;
    
        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;
        boolean isBoundry = false;
    
        if(!upEdge && isTileNumber(x-1, y)) isBoundry = true;
        if(!leftEdge && isTileNumber(x, y-1)) isBoundry = true;
        if(!downEdge && isTileNumber(x+1, y)) isBoundry = true;
        if(!rightEdge && isTileNumber(x, y+1)) isBoundry = true;
        if(!upEdge && !leftEdge && isTileNumber(x-1, y-1)) isBoundry = true;
        if(!upEdge && !rightEdge && isTileNumber(x-1, y+1)) isBoundry = true;
        if(!downEdge && !leftEdge && isTileNumber(x+1, y-1)) isBoundry = true;
        if(!downEdge && !rightEdge && isTileNumber(x+1, y+1)) isBoundry = true;
    
        return isBoundry;
    }

    public ArrayList<Pair> getAllAdjacentTiles(int x, int y){   

        ArrayList<Pair> returnList = new ArrayList<>(8);
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge) returnList.add(new Pair(x-1, y));
        if(!leftEdge ) returnList.add(new Pair(x, y-1));
        if(!downEdge ) returnList.add(new Pair(x+1, y));
        if(!rightEdge ) returnList.add(new Pair(x, y+1));
        if(!upEdge ) returnList.add(new Pair(x-1, y-1));
        if(!upEdge ) returnList.add(new Pair(x-1, y+1));
        if(!downEdge ) returnList.add(new Pair(x+1, y-1));
        if(!downEdge ) returnList.add(new Pair(x+1, y+1));

        return returnList;
    }

    public ArrayList<Pair> getAdjacentClosedTiles(int x, int y){   

        ArrayList<Pair> returnList = new ArrayList<>(8);
        char[][] userBoard = gameInterface.userBoard();
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && userBoard[x-1][y] == 'c') returnList.add(new Pair(x-1, y));
        if(!leftEdge && userBoard[x][y-1] == 'c') returnList.add(new Pair(x, y-1));
        if(!downEdge && userBoard[x+1][y] == 'c') returnList.add(new Pair(x+1, y));
        if(!rightEdge && userBoard[x][y+1] == 'c') returnList.add(new Pair(x, y+1));
        if(!upEdge && !leftEdge && userBoard[x-1][y-1] == 'c') returnList.add(new Pair(x-1, y-1));
        if(!upEdge && !rightEdge && userBoard[x-1][y+1] == 'c') returnList.add(new Pair(x-1, y+1));
        if(!downEdge && !leftEdge && userBoard[x+1][y-1] == 'c') returnList.add(new Pair(x+1, y-1));
        if(!downEdge && !rightEdge && userBoard[x+1][y+1]  == 'c') returnList.add(new Pair(x+1, y+1));

        return returnList;
    }

    public ArrayList<Pair> getAdjacentFlagTiles(int x, int y){   

        ArrayList<Pair> returnList = new ArrayList<>(8);
        char[][] userBoard = gameInterface.userBoard();
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && userBoard[x-1][y] == 'f') returnList.add(new Pair(x-1, y));
        if(!leftEdge && userBoard[x][y-1] == 'f') returnList.add(new Pair(x, y-1));
        if(!downEdge && userBoard[x+1][y] == 'f') returnList.add(new Pair(x+1, y));
        if(!rightEdge && userBoard[x][y+1] == 'f') returnList.add(new Pair(x, y+1));
        if(!upEdge && !leftEdge && userBoard[x-1][y-1] == 'f') returnList.add(new Pair(x-1, y-1));
        if(!upEdge && !rightEdge && userBoard[x-1][y+1] == 'f') returnList.add(new Pair(x-1, y+1));
        if(!downEdge && !leftEdge && userBoard[x+1][y-1] == 'f') returnList.add(new Pair(x+1, y-1));
        if(!downEdge && !rightEdge && userBoard[x+1][y+1]  == 'f') returnList.add(new Pair(x+1, y+1));

        return returnList;
    }

    public void arrowFlagAlgo(){

        //? if the number on the cell == num of unopened cell's around it then all of the unopened cells are mines flag them
    
        gameInterface.ThreadClockRestart();

        gameInterface.ThreadDiceSetMode("arrow");

        char[][] userBoard = gameInterface.userBoard();

        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        for(int i = 0; i < satirSize; i ++){

            for(int j = 0; j < sutunSize; j ++){

                ArrayList<Pair> closedTileList = getAdjacentClosedTiles(i, j);
                int adjacentFlagCount = countFlagTilesAround(i, j);

                if(userBoard[i][j] != '0' && isTileNumber(i, j)){   //? uzerindeki sayi >= 1 ise yani

                    int numberOnTile = Character.getNumericValue(userBoard[i][j]);
                    int safeTileCount = numberOnTile - adjacentFlagCount;

                    if(safeTileCount == closedTileList.size()){

                        for(Pair each : closedTileList){

                            gameInterface.flagTile(each.satir, each.sutun);
                        }
                    }
                }
            }
        }
    }

    public boolean arrowOpenAlgo() {   //?  kesinlikle acilmasi gereken tile'lari acan ARROW algo

        //? if the num of flags around a cell == number on the cell open every unoppened cell around that cell (chording)
    
        gameInterface.ThreadClockRestart();
        gameInterface.ThreadDiceSetMode("arrow");
        System.out.println("--------------ARROW");

        char[][] userBoard = gameInterface.userBoard();
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean isThisAlgoWorked = false;

        for(int i = 0; i < satirSize; i ++){

            for(int j = 0; j < sutunSize; j ++){

                ArrayList<Pair> closedTileList = getAdjacentClosedTiles(i, j);
                int adjacentFlagCount = countFlagTilesAround(i, j);

                if(userBoard[i][j] != '0' && isTileNumber(i, j)){   //? uzerindeki sayi >= 1 ise yani

                    int numberOnTile = Character.getNumericValue(userBoard[i][j]);

                    if(numberOnTile == adjacentFlagCount){

                        if(closedTileList.size() != 0){

                            isThisAlgoWorked = true;

                            for(Pair each : closedTileList){

                                gameInterface.openTile(each.satir, each.sutun);
                            }
                        }
                    }
                }
            }
        }

        return isThisAlgoWorked;
    }
//?==========================================================================================================================================
    boolean[][] enumEmptyBoard = null;
    boolean[][] enumMineBoard = null;

    public void brainDiceAlgo(){

        gameInterface.ThreadClockRestart();
        gameInterface.ThreadDiceSetMode("brain");
        
        System.out.println("--------------------------------BRAIN");

        char[][] userBoardSnapShot = new char[gameInterface.getSatirSize()][gameInterface.getSutunSize()];

        for(int i = 0; i < gameInterface.getSatirSize(); i++){

            for(int j = 0; j < gameInterface.getSutunSize(); j++){

                userBoardSnapShot[i][j] = gameInterface.userBoard()[i][j];
            }
        }

        ArrayList<Pair> edgeTileList = new ArrayList<Pair>();
        ArrayList<Pair> unOpenedTileList = new ArrayList<Pair>();

        for(int i = 0; i < userBoardSnapShot.length; i++){

            for(int j = 0; j < userBoardSnapShot[0].length; j++){

                if(userBoardSnapShot[i][j] == 'c'){

                    unOpenedTileList.add(new Pair(i, j));
                }
                if(isEdgeTile(i, j)){

                    edgeTileList.add(new Pair(i, j));
                }
            }
        }

        int howManyMinesAreClosedButNotEdge = unOpenedTileList.size() - edgeTileList.size();
        if(howManyMinesAreClosedButNotEdge >= this.endGameLimit){

            this.isEndGame = false;
        }
        else{

            this.isEndGame = true;
        }

        ArrayList<ArrayList<Pair>> seperateRecursiveTilesList;
        ArrayList<Pair> recursiveTiles;

        if(! this.isEndGame){

            recursiveTiles = edgeTileList;
            seperateRecursiveTilesList = seperateBorderTiles(recursiveTiles, userBoardSnapShot);
        }
        else{

            recursiveTiles = unOpenedTileList;
            seperateRecursiveTilesList = new ArrayList<ArrayList<Pair>>();
            seperateRecursiveTilesList.add(recursiveTiles);
            //? eger endGame'de isek bosuna seperation() fln ugrasma ve tum tile'lara uygula sadece edge'lere degil 
        }

        if(recursiveTiles.size() == 0){

            return;
        }

        boolean basariliMi = false;
        int globalBestProbability = 0; 
        int globalBestProbTile = Integer.MIN_VALUE;
        int globalBestProb_recursiveTiles_i = Integer.MIN_VALUE;

        for(int recursiveTiles_i = 0; recursiveTiles_i < seperateRecursiveTilesList.size(); recursiveTiles_i++){

            this.backtrackingAlgoSolutions = new ArrayList<boolean[]>(); //? yeni recursiveList icin solution list'i sifirla

            this.enumEmptyBoard = new boolean[gameInterface.getSatirSize()][gameInterface.getSutunSize()];
            this.enumMineBoard = new boolean[gameInterface.getSatirSize()][gameInterface.getSutunSize()];

            for(int i = 0; i < userBoardSnapShot.length; i ++){

                for(int j = 0; j < userBoardSnapShot[0].length; j++){

                    if(userBoardSnapShot[i][j] == 'f'){

                        enumMineBoard[i][j] = true;
                    }
                    else{
                        
                        enumMineBoard[i][j] = false;
                    }

                    if(isTileNumberUserBoardSnapshot(userBoardSnapShot, i, j)){

                        enumEmptyBoard[i][j] = true;
                    }
                    else{

                        enumEmptyBoard[i][j] = false;
                    }
                }
            }

            backtrackingAlgoEnumarate(seperateRecursiveTilesList.get(recursiveTiles_i), 0, userBoardSnapShot);

            if(this.backtrackingAlgoSolutions.size() == 0){

                return;
            }

            for(int i = 0; i < seperateRecursiveTilesList.get(recursiveTiles_i).size(); i++){

                boolean isAllEnumarationsMine = true;
                boolean isAllEnumarationsEmpty = true;

                for(boolean[] solution : backtrackingAlgoSolutions){

                    if(!solution[i]){

                        isAllEnumarationsMine = false;
                    }

                    if(solution[i]){

                        isAllEnumarationsEmpty = false;
                    }
                }

                Pair pair = seperateRecursiveTilesList.get(recursiveTiles_i).get(i);
                int sat = pair.satir;
                int sut = pair.sutun;

                if(isAllEnumarationsMine){
                    //! eger all enum. mine ise onu direk flagla ve bunu snapshot'a dahil et

                    userBoardSnapShot[sat][sut] = 'f';
                    gameInterface.flagTile(sat, sut);
                }
                if(isAllEnumarationsEmpty){
                    //! eger all enum. open ise onu open'la ama snapshot'a dahil etme !!!

                    basariliMi = true;
                    gameInterface.openTile(sat, sut);
                }
            }

            if(basariliMi){ //! eger 1 tane bile open tile yaptiysak bu seperatedListin probabilitysini local optimal hesabina dahil etme 

                continue;
            }

            int localBestProbability = Integer.MIN_VALUE; int localBestProbabilityIndex = 0;

            for(int i = 0; i < seperateRecursiveTilesList.get(recursiveTiles_i).size(); i++){

                int emptyCount = 0;

                for(boolean[] sln : backtrackingAlgoSolutions){

                    if(!sln[i]){

                        emptyCount ++;
                    }
                }

                if(emptyCount > localBestProbability){

                    localBestProbability = emptyCount;
                    localBestProbabilityIndex = i;
                }
            }

            //! local probabilityler o an tum herhangi bir open islemi yapamamis seperateTileList'ler arasindan hesaplaniyor
            //! ... Sonra globalProbability tum loalllerin max'i olarak hesaplaniyor 

            if(localBestProbability > globalBestProbability){
        
                globalBestProbability = localBestProbability;
                globalBestProbTile = localBestProbabilityIndex;
                globalBestProb_recursiveTiles_i = recursiveTiles_i;
            }
        }
        
        if(basariliMi){

            return; //? no guessing
        }

        gameInterface.ThreadDiceSetMode("dice");
        System.out.println("$$$__DICE");

        Pair pair = seperateRecursiveTilesList.get(globalBestProb_recursiveTiles_i).get(globalBestProbTile);
        gameInterface.openTile(pair.satir, pair.sutun);
    }
//?==========================================================================================================================================
    public void backtrackingAlgoEnumarate(ArrayList<Pair> recursiveTiles, int currIndex, char[][] userBoardSnapShot){  

        int allFlagsCount = 0;

        for(int i = 0; i < userBoardSnapShot.length; i ++){

            for(int j = 0; j < userBoardSnapShot[0].length; j++){

                if(enumMineBoard[i][j]){

                    allFlagsCount++;
                }

                if(! isTileNumberUserBoardSnapshot(userBoardSnapShot, i, j)){

                    //? Zaten hakkinda bir bilgim yok neyin infeasible'ligini kontrol edicem direk continue at gitsin
                    continue;
                }

                int adjacentTileCount = 0;
                if((i==0 && j==0) || (i== gameInterface.getSatirSize()-1 && j==gameInterface.getSutunSize()-1)){

                    adjacentTileCount = 3;
                }
                else if(i==0 || j==0 || i==gameInterface.getSatirSize()-1 || j==gameInterface.getSutunSize()-1){

                    adjacentTileCount = 5;
                }
                else{

                    adjacentTileCount = 8;
                }

                int adjacentFlagCount = countTruesAround(enumMineBoard, i,j);
                int adjacentEmptyCount = countTruesAround(enumEmptyBoard, i,j);

                int num = Character.getNumericValue(userBoardSnapShot[i][j]);
                
                if(adjacentFlagCount > num){    //! INFEASIBLE 1 } MAYIN SAYISI UZERINDE YAZANLA UYUSMUYOR

                    return;
                }

                if(adjacentTileCount - adjacentEmptyCount < num){ //! INFEASIBLE 2 } EMPTY TILE SAYISI UZERINDE YAZANLA UYUSMUYOR

                    return;
                }
            }
        }

        if(allFlagsCount > gameInterface.getMineNumber()){ //! oyundaki toplam mayin sayisindan daha fazla flag kullanmissin infeasible

            return;
        }

        if(currIndex == recursiveTiles.size()){ //! ENUMARATION SONUNA GELDIK 

            if(isEndGame && allFlagsCount < gameInterface.getMineNumber()){

                return; //? OYUN SONUNDA FLAG SAYIM TAM OLARAK GERCEK MINE SAYISINI TUTMALI YOKSA INFEASIBLE
            }

            boolean[] solution = new boolean[recursiveTiles.size()];

            for(int i = 0; i < recursiveTiles.size(); i++){

                Pair pair = recursiveTiles.get(i);

                solution[i] = enumMineBoard[pair.satir][pair.sutun];
            }

            backtrackingAlgoSolutions.add(solution);
            return;
        }

        Pair pair = recursiveTiles.get(currIndex);
        int x = pair.satir;
        int y = pair.sutun;

        //! ENUMARATION STEP BURDA BIR MAYIN'LI BIRDE EMPTY OLARAK RECURSE ET 
        enumMineBoard[x][y] = true;
        backtrackingAlgoEnumarate(recursiveTiles, currIndex+1, userBoardSnapShot);
        enumMineBoard[x][y] = false;

        enumEmptyBoard[x][y] = true;
        backtrackingAlgoEnumarate(recursiveTiles, currIndex+1, userBoardSnapShot);
        enumEmptyBoard[x][y] = false;
    }
//?==========================================================================================================================================
    public ArrayList<ArrayList<Pair>> seperateBorderTiles(ArrayList<Pair> recursiveTiles, char[][] userBoardSnapShot){ //! tankSegregate()

        ArrayList<ArrayList<Pair>> allSeperatedLists = new ArrayList<ArrayList<Pair>>();
        ArrayList<Pair> listsThatAreProcessed = new ArrayList<Pair>();

        while(true){

            LinkedList<Pair> queue = new LinkedList<Pair>();
            ArrayList<Pair> finishedPairList = new ArrayList<Pair>();

            for(Pair firstT : recursiveTiles){  

                if(!listsThatAreProcessed.contains(firstT)){

                    queue.add(firstT);
                    break;
                }
            }       //!  baslangic yeri bulmaya calis buldugun anda break et. Algo ondan baslicak 

            if(queue.isEmpty()){
                
                break;
            }

            while(!queue.isEmpty()){

                Pair currentTile = queue.poll();
                int currentTile_i = currentTile.satir;
                int currentTile_j = currentTile.sutun;

                finishedPairList.add(currentTile);
                listsThatAreProcessed.add(currentTile);

                for(Pair tile : recursiveTiles){

                    int tile_i = tile.satir;
                    int tile_j = tile.sutun;

                    boolean areWeDone = false;

                    if(finishedPairList.contains(tile)){

                        continue;
                    }
                    
                    if(Math.abs(currentTile_i-tile_i) > 2 || Math.abs(currentTile_j-tile_j) > 2){

                        areWeDone = false;
                    }

                    else{
                        
                        breakLabel:

                        for(int i=0; i<gameInterface.getSatirSize(); i++){

                            for(int j=0; j<gameInterface.getSutunSize(); j++){

                                if(userBoardSnapShot[i][j] != '0' && isTileNumberUserBoardSnapshot(userBoardSnapShot, i, j)){

                                    if(Math.abs(currentTile_i-i) <= 1 && Math.abs(currentTile_j-j) <= 1 &&
                                        Math.abs(tile_i-i) <= 1 && Math.abs(tile_j-j) <= 1){

                                        areWeDone = true;
                                        break breakLabel;
                                    }
                                }
                            }
                        }
                    }
                    
                    if(!areWeDone){

                        continue;
                    }

                    if(!queue.contains(tile)){

                        queue.add(tile);
                    }
                }
            }

            allSeperatedLists.add(finishedPairList);
        }

        return allSeperatedLists;
    }
//?==========================================================================================================================================
    //!     Auxilary method to count the trues in the enumarationMineBoard and enumrationEmptyBoard
    public int countTruesAround(boolean[][] arr, int x, int y){

        int count = 0;

        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && arr[x-1][y] ) count++;
        if(!leftEdge && arr[x][y-1] ) count++;
        if(!downEdge && arr[x+1][y] ) count++;
        if(!rightEdge && arr[x][y+1] ) count++;
        if(!upEdge && !leftEdge && arr[x-1][y-1]) count++;
        if(!upEdge && !rightEdge && arr[x-1][y+1]) count++;
        if(!downEdge && !leftEdge && arr[x+1][y-1] ) count++;
        if(!downEdge && !rightEdge && arr[x+1][y+1]) count++;

        return count;
    }
}
