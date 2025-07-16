import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
public class CommandParser {
    public static TypeCommand scannerLine(String[] input) throws IOException {
        TypeCommand command = new TypeCommand();
        if (isValidString(String.join(" ", input))){
            for (int i = 0; i < input.length; ++i){
                switch (input[i]) {
                    case "-a":
                        command.a = true;
                        break;
                    case "-s":
                        command.s = true;
                        break;
                    case "-f":
                        command.f = true;
                        break;
                    case "-o":
                        command.path = input[i + 1];
                        i++;
                        break;
                    case "-p":
                        command.prefix = input[i + 1];
                        i++;
                        break;
                    default:
                        command.pathFile.add(input[i]);
                        break;
                }
            }
        }
        else{
            throw new IOException("Is non valid input string");
        }
        StringBuilder pathBuilder = new StringBuilder();
        if (command.path != null) {
            pathBuilder.append(command.path);
        }
        String path = pathBuilder.toString();
        if (!isValidFilePath(path)){
            throw new IOException("Non Valid file path");
        }
        return command;
    }
    private static boolean isValidString(String line) {
        return line.matches("(?:(-p\\s[^\\s]+)?\\s*(-o\\s[^\\s]+)?\\s*(-s)?\\s*(-f)?\\s*(-a)?\\s*)*([^\\s]+\\.txt)(\\s+[^\\s]+\\.txt)*");
    }
    private static boolean isValidFilePath(String path) {
        Path directoryPath = Paths.get(path);
        try {
           if (!Files.exists(directoryPath)) {
               Files.createDirectories(directoryPath);
           }
            File file;
           if (!directoryPath.toString().isEmpty()) {
               file = new File(directoryPath.toString(), "test.txt");
           }
           else{
               file = new File("test.txt");
           }
           if (file.createNewFile()) {
                file.delete();
            }
            return true;
        } catch (IOException exception) {
            System.err.println("Warning create file: " + exception.getMessage());
            return false;
        }
    }
}
