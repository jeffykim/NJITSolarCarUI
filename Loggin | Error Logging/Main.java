import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main{
    public static final Logger log = LogManager.getLogger(Main.class.getName());
    public static void main(String [] args){
        System.out.println("Start");
        for(Integer i = 0; i<=10; i++){
            log.error("error "+i);
            log.fatal("fatal "+i);
            log.warn("warn "+i);
        }
    }
}