import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;


public class Listener{

    public String input;
    private String currentDir;
    Listener() {
        this.input = "";
        this.currentDir = "C:\\Users\\User";
    }
    public void pickMethod() throws FileNotFoundException {
        String[] tokens = this.input.split(" ");
        if(tokens.length!=0) {
            switch (tokens[0]) {
                case "cd" -> this.changeDir();
                case "cat" -> this.cat(tokens[1]);
                case "mkdir" -> this.makeDir(tokens[1]);
                case "ls" -> this.listDir();
                case "rm" -> this.remove(tokens[1]);
                case "mv" -> this.rename(tokens[1], tokens[2]);
            }
        }
    }

    private void cat(String fileName) throws FileNotFoundException {
        File file = new File(this.currentDir + "/" + fileName);
        OutputManager output = new OutputManager();
        if(file.isFile())
        {
            if(file.canRead())
            {
                Scanner sc = new Scanner(file);
                sc.useDelimiter("\\Z");
                System.out.println(ConsoleColors.BLACK+sc.next());
            }
            else
                output.errorMessage("Cannot read file");
        }
        else
            output.errorMessage("Not a file");
    }

    private void rename(String name, String renameTo) {
        File old = new File(this.currentDir + "/" + name);
        File newOne = new File(this.currentDir + "/" + renameTo);
        OutputManager output = new OutputManager();
        if(!old.renameTo(newOne)){
            output.errorMessage("Couldn't successfully rename");
        }
    }

    private void remove(String name) {
        File removed = new File(this.currentDir + "/" + name);
        if(removed.isDirectory()&&removed.list().length == 0)
        {
            if(!removed.delete()){
                System.out.println(ConsoleColors.RED + "Couldn't successfully remove");
            }
        }
        else {
            System.out.println(ConsoleColors.RED + "Cannot remove not empty directory");
        }
    }

    public void updateInput(){
        Scanner in = new Scanner(System.in);
        this.input = in.nextLine();
    }

    public void changeDir(){
        String[] tokens = this.input.split(" ");
        File oldDir = new File(this.currentDir);
        OutputManager output = new OutputManager();
        if(Objects.equals(tokens[1], "..")){
            if(oldDir.getParentFile() != null) {
                this.currentDir = oldDir.getParentFile().getPath();
            }
            else {
                output.errorMessage("Cannot go any further");
            }
        }
        else {
            File newDir = new File(tokens[1]);
            File newDirInside = new File(this.currentDir + "\\" + tokens[1]);
            if(Arrays.stream(oldDir.listFiles()).anyMatch(i-> i.getPath().equals(newDirInside.getPath())))
            {
                this.currentDir = newDirInside.getPath();
            }
            else if (newDir.exists()) {
                this.currentDir = newDir.getPath();
            }
            else {
                output.errorMessage("Such directory doesn't exist");
            }
        }
    }
    void makeDir(String dirName){

        File newDir = new File(this.currentDir+"/" + dirName);
        if(!newDir.mkdir()) {
            System.out.print("Failed to create directory");
        }

    }
    void listDir(){
        File currentDir = new File(this.currentDir);
        OutputManager output = new OutputManager();
        for (File element: currentDir.listFiles()
        ) {
            if(element.isDirectory()){
                output.outputDir(element.getName());
            }
            else {
                output.outputFile(element.getName());
            }
        }
    }

    void displayCurrentDir(){
        System.out.print(this.currentDir+" ");
    }
}
