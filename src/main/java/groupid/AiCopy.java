package groupid;
import java.util.*;
import javax.swing.SwingWorker;


public class AiCopy extends SwingWorker<Void, String>{

    //!===============================================================
    //!  KAC DENEMEDE EXPERT'DE BASARILI OLDU AI TESTLER:
    //!  6, 2, 10, 5, 4, 2, 8, 4, 2, 1, 2, 6, 4, 9, 7 
    //!===============================================================

    //?  userBoard = [0, 1, 2, 3, 4, 5, 6, 7, 8, f, c] // f = flag // c = closed

    public GameInterface gameInterface;
    public boolean isOff = false;
    public boolean isAiFirstMove = true;
    public int endGameLimit = 9;
    public boolean isEndGame = false;
    public ArrayList<boolean[]> backtrackingAlgoSolutions;

    public AiCopy(GameInterface gameInterface){

        System.out.println("AI 2 IS RUNNING");

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
            System.out.println("=========================================================================================================== FIRST_MOVE");
            gameInterface.openTile((gameInterface.getSatirSize() - 1) / 2, (gameInterface.getSutunSize() - 1) / 2);
            this.isAiFirstMove = false;
        }

        aiFlag();
        aiOpen();
    }

    public void aiFlag(){   // attempFlagMine()

        //?  Note that we only flag a mine if we are certain that it is a mine
        //? that's why aiFlag() method only consists of the brainFlagAlgo() method

        arrowFlagAlgo();
    }

    public void aiOpen(){   // attemptMove()

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

    public boolean arrowOpenAlgo() {   //?  kesinlikle acilmasi gereken tile'lari acan BRAIN algo

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
    boolean[][] knownEmpty = null;
    boolean[][] knownMine = null;

    public void brainDiceAlgo(){ //! tankSolver()

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
        double prob_best = 0; 
        int prob_besttile = -1;
        int prob_best_s = -1;

        for(int recursiveTiles_i = 0; recursiveTiles_i < seperateRecursiveTilesList.size(); recursiveTiles_i++){

            this.backtrackingAlgoSolutions = new ArrayList<boolean[]>(); //? yeni recursiveList icin solution list'i sifirla

            this.knownEmpty = new boolean[gameInterface.getSatirSize()][gameInterface.getSutunSize()];
            this.knownMine = new boolean[gameInterface.getSatirSize()][gameInterface.getSutunSize()];

            for(int i = 0; i < userBoardSnapShot.length; i ++){

                for(int j = 0; j < userBoardSnapShot[0].length; j++){

                    if(userBoardSnapShot[i][j] == 'f'){

                        knownMine[i][j] = true;
                    }
                    else{
                        
                        knownMine[i][j] = false;
                    }

                    if(isTileNumberUserBoardSnapshot(userBoardSnapShot, i, j)){

                        knownEmpty[i][j] = true;
                    }
                    else{

                        knownEmpty[i][j] = false;
                    }
                }
            }

            backtrackingAlgoRecurse(seperateRecursiveTilesList.get(recursiveTiles_i), 0, userBoardSnapShot);

            if(this.backtrackingAlgoSolutions.size() == 0){

                return;
            }

            for(int i = 0; i < seperateRecursiveTilesList.get(recursiveTiles_i).size(); i++){

                boolean allMine = true;
                boolean allEmpty = true;

                for(boolean[] sln : backtrackingAlgoSolutions){

                    if(!sln[i]){

                        allMine = false;
                    }

                    if(sln[i]){

                        allEmpty = false;
                    }
                }

                Pair q = seperateRecursiveTilesList.get(recursiveTiles_i).get(i);
                int qi = q.satir;
                int qj = q.sutun;

                // Muahaha
                if(allMine){

                    userBoardSnapShot[qi][qj] = 'f';
                    gameInterface.flagTile(qi, qj);
                }
                if(allEmpty){

                    basariliMi = true;
                    gameInterface.openTile(qi, qj);
                }
            }

            if(basariliMi){

                continue;
            }

            int maxEmpty = -10000; int iEmpty = -1;

            for(int i = 0; i < seperateRecursiveTilesList.get(recursiveTiles_i).size(); i++){

                int nEmpty = 0;

                for(boolean[] sln : backtrackingAlgoSolutions){

                    if(!sln[i]){

                        nEmpty ++;
                    }
                }

                if(nEmpty > maxEmpty){

                    maxEmpty = nEmpty;
                    iEmpty = i;
                }
            }

            double probability = (double)maxEmpty / (double)backtrackingAlgoSolutions.size();

            if(probability > prob_best){
        
                prob_best = probability;
                prob_besttile = iEmpty;
                prob_best_s = recursiveTiles_i;
              }
        }
        
        if(basariliMi){

            return; //? no guessing
        }

        gameInterface.ThreadDiceSetMode("arrow");
        System.out.println("$$$------------- DICE");

        Pair q = seperateRecursiveTilesList.get(prob_best_s).get(prob_besttile);
        int qi = q.satir;
        int qj = q.sutun;
        gameInterface.openTile(qi, qj);
    }
//?==========================================================================================================================================
    public void backtrackingAlgoRecurse(ArrayList<Pair> recursiveTiles, int currIndex, char[][] userBoardSnapShot){    //! tankRecurse()

        int flagCount = 0;

        for(int i = 0; i < userBoardSnapShot.length; i ++){

            for(int j = 0; j < userBoardSnapShot[0].length; j++){

                if(knownMine[i][j]){

                    flagCount++;
                }

                if(! isTileNumberUserBoardSnapshot(userBoardSnapShot, i, j)){

                    //? Zaten hakkinda bir bilgim yok neyin infeasible'ligini kontrol edicem
                    continue;
                }

                int surround = 0;
                if((i==0 && j==0) || (i== gameInterface.getSatirSize()-1 && j==gameInterface.getSutunSize()-1)){

                    surround = 3;
                }
                else if(i==0 || j==0 || i==gameInterface.getSatirSize()-1 || j==gameInterface.getSutunSize()-1){

                    surround = 5;
                }
                else{

                    surround = 8;
                }

                int numFlags = countTruesAround(knownMine, i,j);
                int numFree = countTruesAround(knownEmpty, i,j);

                int num = Character.getNumericValue(userBoardSnapShot[i][j]);
                // Scenario 1: too many mines
                if(numFlags > num) return;

                // Scenario 2: too many empty
                if(surround - numFree < num) return;
            }
        }

        if(flagCount > gameInterface.getMineNumber()){  // we have to many flags

            return;
        }

        if(currIndex == recursiveTiles.size()){

            if(isEndGame && flagCount < gameInterface.getMineNumber()){

                return;
            }

            boolean[] solution = new boolean[recursiveTiles.size()];

            for(int i = 0; i < recursiveTiles.size(); i++){

                Pair pair = recursiveTiles.get(i);

                solution[i] = knownMine[pair.satir][pair.sutun];
            }

            backtrackingAlgoSolutions.add(solution);
            return;
        }

        Pair q = recursiveTiles.get(currIndex);
        int qi = q.satir;
        int qj = q.sutun;

        // Recurse two positions: mine and no mine
        knownMine[qi][qj] = true;
        backtrackingAlgoRecurse(recursiveTiles, currIndex+1, userBoardSnapShot);
        knownMine[qi][qj] = false;

        knownEmpty[qi][qj] = true;
        backtrackingAlgoRecurse(recursiveTiles, currIndex+1, userBoardSnapShot);
        knownEmpty[qi][qj] = false;

    }
//?==========================================================================================================================================
    public ArrayList<ArrayList<Pair>> seperateBorderTiles(ArrayList<Pair> recursiveTiles, char[][] userBoardSnapShot){ //! tankSegregate()

        ArrayList<ArrayList<Pair>> allRegions = new ArrayList<ArrayList<Pair>>();
        ArrayList<Pair> covered = new ArrayList<Pair>();

        while(true){

            LinkedList<Pair> queue = new LinkedList<Pair>();
            ArrayList<Pair> finishedRegion = new ArrayList<Pair>();

            // Find a suitable starting point
            for(Pair firstT : recursiveTiles){

                if(!covered.contains(firstT)){

                    queue.add(firstT);
                    break;
                }
            }

            if(queue.isEmpty()){
                
                break;
            }

            while(!queue.isEmpty()){

                Pair curTile = queue.poll();
                int ci = curTile.satir;
                int cj = curTile.sutun;

                finishedRegion.add(curTile);
                covered.add(curTile);

                // Find all connecting tiles
                for(Pair tile : recursiveTiles){

                    int ti = tile.satir;
                    int tj = tile.sutun;

                    boolean isConnected = false;

                    if(finishedRegion.contains(tile)){

                        continue;
                    }
                    
                    if(Math.abs(ci-ti)>2 || Math.abs(cj-tj) > 2){

                        isConnected = false;
                    }

                    else{
                        // Perform a search on all the tiles
                        tilesearch:

                        for(int i=0; i<gameInterface.getSatirSize(); i++){

                            for(int j=0; j<gameInterface.getSutunSize(); j++){

                                if(userBoardSnapShot[i][j] != '0' && isTileNumberUserBoardSnapshot(userBoardSnapShot, i, j)){

                                    if(Math.abs(ci-i) <= 1 && Math.abs(cj-j) <= 1 &&
                                        Math.abs(ti-i) <= 1 && Math.abs(tj-j) <= 1){

                                        isConnected = true;
                                        break tilesearch;
                                    }
                                }
                            }
                        }
                    }
                    
                    if(!isConnected){

                        continue;
                    }

                    if(!queue.contains(tile)){

                        queue.add(tile);
                    }
                }
            }

            allRegions.add(finishedRegion);
        }

        return allRegions;
    }
//?==========================================================================================================================================

    public int dummyMineBoardCountMine(char[][] dummyMineBoard, int x, int y){

        int count = 0;

        char[][] userBoard = dummyMineBoard;
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && userBoard[x-1][y] == 'm') count++;
        if(!leftEdge && userBoard[x][y-1] == 'm') count++;
        if(!downEdge && userBoard[x+1][y] == 'm') count++;
        if(!rightEdge && userBoard[x][y+1] == 'm') count++;
        if(!upEdge && !leftEdge && userBoard[x-1][y-1] == 'm') count++;
        if(!upEdge && !rightEdge && userBoard[x-1][y+1] == 'm') count++;
        if(!downEdge && !leftEdge && userBoard[x+1][y-1] == 'm') count++;
        if(!downEdge && !rightEdge && userBoard[x+1][y+1]  == 'm') count++;

        return count;
    }

    public int dummyMineBoardCountEmpty(char[][] dummyMineBoard, int x, int y){

        int count = 0;

        char[][] userBoard = dummyMineBoard;
        int satirSize = gameInterface.getSatirSize();
        int sutunSize = gameInterface.getSutunSize();

        boolean upEdge = false, downEdge = false, leftEdge = false, rightEdge = false;

        if(x == 0) upEdge = true;
        if(y == 0) leftEdge = true;
        if(x == satirSize-1) downEdge = true;
        if(y == sutunSize-1) rightEdge = true;

        if(!upEdge && userBoard[x-1][y] == '-') count++;
        if(!leftEdge && userBoard[x][y-1] == '-') count++;
        if(!downEdge && userBoard[x+1][y] == '-') count++;
        if(!rightEdge && userBoard[x][y+1] == '-') count++;
        if(!upEdge && !leftEdge && userBoard[x-1][y-1] == '-') count++;
        if(!upEdge && !rightEdge && userBoard[x-1][y+1] == '-') count++;
        if(!downEdge && !leftEdge && userBoard[x+1][y-1] == '-') count++;
        if(!downEdge && !rightEdge && userBoard[x+1][y+1]  == '-') count++;

        return count;
    } 

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
