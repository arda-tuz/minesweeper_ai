package groupid;
import java.util.Objects;

public class Pair {
    
    public int satir;
    public int sutun;

    public Pair(int satir, int sutun) {

        this.satir = satir;
        this.sutun = sutun;
    }

    @Override
    public boolean equals(Object o) {
        
        if(o instanceof Pair){

            if(this.satir == ((Pair)o).satir && this.sutun == ((Pair)o).sutun){

                return true;
            }
            else{

                return false;
            }
        }
        else{

            return false;
        }
    }

    @Override
    public int hashCode() {
        
        return Objects.hash(satir, sutun);
    }
}