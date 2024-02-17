package groupid;

public class GameInterface {
    
    private Game game;

    public GameInterface(Game game){

        this.game = game;
    }

    public boolean isGameOver(){

        return this.game.isGameOver;
    }

    public boolean isGameWon(){

        return this.game.isGameWon;
    }

    public int getFlagCount(){

        return this.game.flagCount;
    }

    public int getOpenedCount(){

        return this.game.openedCount;
    }

    public int getGoalCount(){

        return this.game.goalCount;
    }

    public int getMineNumber(){

        return this.game.mineNumber;
    }

    public int getSatirSize(){

        return this.game.satirSize;
    }

    public int getSutunSize(){

        return this.game.sutunSize;
    }
    
    public boolean isPeekModeOn(){

        return this.game.isPeekModeOn;
    }

    public boolean isFirstMove(){

        return this.game.isFirstMove;
    }

    public boolean isAiStarted(){

        return this.game.isAiStarted;
    }

    public char[][] userBoard(){

        return this.game.userBoard;
    }

    public void openTile(int x, int y){

        this.game.openTile(x, y);
    }

    public void flagTile(int x, int y){

        this.game.flagTile(x, y);
    }

    public void unflagTile(int x, int y){

        this.game.unflagTile(x, y);
    }

    public void startThreadClock(){

        this.game.threadClock.execute();
    }

    public void startThreadDice(){

        this.game.threadDice.execute();
    }

    public void ThreadDiceSetMode(String mode){ //!    ["dice","brain","arrow"]

        this.game.threadDice.setMode(mode);
    }

    public void ThreadClockRestart(){

        this.game.threadClock.restart();
    }
}
