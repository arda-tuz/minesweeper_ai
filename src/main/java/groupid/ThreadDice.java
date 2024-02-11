package groupid;
import java.util.List;
import javax.swing.SwingWorker;


public class ThreadDice extends SwingWorker<Void, String>{
    
    private volatile String mode = "dice";
    public MainFrame mainFrame;
    public String[] frames;

    public boolean isOff = false;

    public ThreadDice(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        this.frames = new String[]{"dice","brain","arrow"};
    }

    @Override
    protected Void doInBackground() throws Exception {

        if(this.isOff){

            //? wait for the thread to get cancalled 
        }
        else{

            while (!isCancelled()) {

                if(this.mode.equals("dice")){

                    publish(frames[0]);
                }
                else if(this.mode.equals("brain")){

                    publish(frames[1]);
                }
                else if(mode.equals("arrow")){
                    
                    publish(frames[2]);
                }

                Thread.sleep(10);
            }
        }
        
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        
        if(this.isOff){

            //?     wait for the thread to get cancalled 
        }
        else{

            String currImageHash = chunks.get(chunks.size() - 1);
            this.mainFrame.game.threadDicePanel.imageHash = currImageHash;
            this.mainFrame.game.threadDicePanel.repaint();
        }   
    }

    public void setMode(String newMode){
        
        this.mode = newMode;
    }
}
