import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    public static void allWriter(Data map, TypeCommand command) throws IOException {
        String path = command.path != null ? command.path : "";
        Path directoryPath = Paths.get(path);
        if (!Files.exists(directoryPath)) {
            Files.createDirectories(directoryPath);
        }
        File file;
        List<String> typeFile = new ArrayList<>();
        if (!map.get("strings").isEmpty()){
            typeFile.add("strings");
        }
        if (!map.get("floats").isEmpty()){
            typeFile.add("floats");
        }
        if (!map.get("integers").isEmpty()){
            typeFile.add("integers");
        }
        for (int i = 0; i < typeFile.size(); ++i) {
            if(command.path == null){
                file = new File(command.prefix != null ? command.prefix + typeFile.get(i) + ".txt" : "sample-" + typeFile.get(i) + ".txt");
            }
            else {
                file = new File(directoryPath.toFile(), command.prefix != null ? command.prefix + typeFile.get(i) + ".txt" : "sample-" + typeFile.get(i) + ".txt");
            }
            boolean appendMode = command.a && file.exists();
            if (!appendMode && file.exists()) {
                try (FileWriter cleaner = new FileWriter(file, false)) {
                    cleaner.write("");
                }
            }
        }
        map.processData(command);
    }
    public static void write(String typeFile, String line,TypeCommand command) throws IOException {
        String path = command.path != null ? command.path : "";
        Path directoryPath = Paths.get(path);
        File file;
        if(command.path == null){
            file = new File(command.prefix != null ? command.prefix + typeFile + ".txt" : "sample-" + typeFile + ".txt");
        }
        else {
            file = new File(directoryPath.toFile(), command.prefix != null ? command.prefix + typeFile + ".txt" : "sample-" + typeFile + ".txt");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.append(line).append("\n");
        }
    }

    public static Data read(BufferedReader reader, Statistic stat) throws IOException {
        Data map = new Data();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            if (isInteger(line)) {
                stat.amountLine_[0]++;
                if (map.get("integers").isEmpty()) {
                    stat.statisticInt_[0]=Integer.parseInt(line);
                    stat.statisticInt_[1]=Integer.parseInt(line);
                }
                map.get("integers").add(line);
                if (stat.statisticInt_[0]>Integer.parseInt(line)){
                    stat.statisticInt_[0]=Integer.parseInt(line);
                }
                if (stat.statisticInt_[1]<Integer.parseInt(line)) {
                    stat.statisticInt_[1] = Integer.parseInt(line);
                }
                stat.statisticInt_[2] += Integer.parseInt(line);
                BigDecimal numerator = new BigDecimal(stat.statisticInt_[2]);
                BigDecimal denominator = new BigDecimal(stat.amountLine_[0]);
                int scale = 5;
                RoundingMode roundingMode = RoundingMode.HALF_UP;
                BigDecimal result = numerator.divide(denominator, scale, roundingMode);
                stat.average_[0] = result.floatValue();
            } else if (isFloat(line)) {
                stat.amountLine_[1]++;
                if (map.get("floats").isEmpty()) {
                    stat.statisticFloat_[0]=Float.parseFloat(line);
                    stat.statisticFloat_[1]=Float.parseFloat(line);
                }
                map.get("floats").add(line);
                if (stat.statisticFloat_[0]>Float.parseFloat(line)){
                    stat.statisticFloat_[0]=Float.parseFloat(line);
                }
                if (stat.statisticFloat_[1]<Float.parseFloat(line)) {
                    stat.statisticFloat_[1] = Float.parseFloat(line);
                }
                stat.statisticFloat_[2] += Float.parseFloat(line);
                stat.average_[1] = stat.statisticFloat_[2] / stat.amountLine_[1];
            } else {
                stat.amountLine_[2]++;
                map.get("strings").add(line);
                if (stat.statisticStr_[0]>line.length()){
                    stat.statisticStr_[0]=line.length();
                }
                if (stat.statisticStr_[1]<line.length()){
                    stat.statisticStr_[1]=line.length();
                }
            }
        }
        return map;
    }

    private static boolean isInteger(String line) {
        return line.matches("-?(?!0{1,})\\d+");
    }

    private static boolean isFloat(String line) {
        return line.matches("-?(?!0{2,})\\d+\\.\\d+([eE][-+]?\\d+)?");
    }
}
