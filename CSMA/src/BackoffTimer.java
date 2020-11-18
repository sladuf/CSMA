
import java.lang.Math;
 
/**
 * BackOff timer generator
 */
public class BackoffTimer {
    /**
     *
     * Randomly selected back-off time,
     * Calculated according to the retransmission number
     * random multiples by k times for k-th retransmission
     * @param transNum  : number of retransmission
     * @return Random multiples
     */
    public int backoffTime(int transNum) { 
        int random;
        int temp;
        temp=Math.min(transNum,10);
        random=(int)(Math.random()*(Math.pow(2,temp)-1));
        return random;
    }
}
