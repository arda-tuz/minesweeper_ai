package groupid;
import java.util.List;
import javax.swing.*;



public class ThreadClock extends SwingWorker<Void, String>{

    private volatile boolean restart = false;
    public MainFrame mainFrame;
    public String[] frames;

    public boolean isOff = false;

    public ThreadClock(MainFrame mainFrame) {

        this.mainFrame = mainFrame;
        this.frames = new String[]{"clock0", "clock1","clock2","clock3","clock4",
        "clock5","clock6","clock7","clock8","clock9", "clock10", "clock11", "clock12"};
    }

    @Override
    protected Void doInBackground() throws Exception {

        if(this.isOff){

            //? wait for the thread to get cancalled 
        }
        else{

            int i = 0;
            while (!isCancelled()) {

                publish(frames[i]);
                i = (i + 1) % frames.length;
                Thread.sleep(190);          //todo      bu hiz AI' in hizina gore ayarlanacak

                if (restart) {

                    i = 0;
                    restart = false;
                }
            }
        }
        
        return null;
    }

    @Override
    protected void process(List<String> chunks) {
        
        if(this.isOff){

            //? wait for the thread to get cancalled 
        }
        else{

            String currImageHash = chunks.get(chunks.size() - 1);
            this.mainFrame.game.threadClockPanel.imageHash = currImageHash;
            this.mainFrame.game.threadClockPanel.repaint();
        }   
    }

    public void restart() {
        
        restart = true;
    }
}
