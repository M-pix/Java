import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please specify at least one file.");
            return;
        }
        Statistic stat = new Statistic();
        HashMap<String, List<String>> map;
        try {
            TypeCommand command = CommandParser.scannerLine(args);
            for (String fileName : command.pathFile) {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                map = Parser.read(reader, stat);
                Parser.allWriter(map, command);
            }
            if (command.f){
                stat.FullStatistic();
                stat.BriefStatistic();
            }
            if (command.s && !command.f){
                stat.BriefStatistic();
            }
        }
        catch (FileNotFoundException exception){
            System.out.println("File not found: " + exception.getMessage());
        }
        catch (IOException exception) {
            System.out.println("Warning read file: " + exception.getMessage());
        }

    }
}