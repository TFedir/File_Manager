public class OutputManager {
    void errorMessage(String msg){
        System.out.println(ConsoleColors.RED + msg);
    }
    void outputDir(String msg){
        System.out.println(ConsoleColors.GREEN + msg);
    }
    void outputFile(String msg){
        System.out.println(msg);
    }
}
