import java.io.FileNotFoundException;
import java.util.Objects;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        Listener l = new Listener();
        boolean exit = false;
        while (!exit){
            l.displayCurrentDir();
            l.updateInput();
            if(Objects.equals(l.input, "q")) exit = true;
            l.pickMethod();
        }
    }
}
