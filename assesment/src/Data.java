import java.util.*;
import java.io.*;
public class Data {
    private HashMap<String, List<String>> map = new HashMap<>();

    public Data() {
        map.put("strings", new ArrayList<>());
        map.put("integers", new ArrayList<>());
        map.put("floats", new ArrayList<>());
    }

    public List<String> get(String name) {
        return map.get(name);
    }
    public void processData(TypeCommand command) {
        map.forEach((key, value) -> {
            for (String elem : value) {
                try {
                    Parser.write(key, elem, command);
                } catch (IOException exception) {
                    System.out.println("Warning write file: " + exception.getMessage());
                }
            }
        });
    }
}
