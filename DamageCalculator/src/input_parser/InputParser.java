package input_parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InputParser {

    private static final String SEPARATOR = ":";
    private File fileToParse;

    public InputParser(String fileLocation) throws IOException {
        fileToParse = new java.io.File(fileLocation);
    }

    public Map<String, Integer> parse() throws IOException {
        Map<String, Integer> result = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(fileToParse));
        String st;
        while ((st = br.readLine()) != null) {
            if (st.isEmpty()) {
                continue;
            }

            String[] split = st.split(SEPARATOR);
            if (split[0] == null || split[0].isEmpty() || split[1] == null || split[1].isEmpty()) {
                System.out.println("ERROR: Cannot parse string: " + st);
            } else {
                logInfo("Parse results. Key: " + split[0] + ", Value: " + split[1]);
                result.put(split[0], Integer.valueOf(split[1]));
            }
        }
        br.close();

        return result;
    }

    public void logInfo(String str) {
        System.out.println("INFO: " + str);
    }

    public void logError(String str) {
        System.out.println("ERROR: " + str);
    }

    public void log(String str) {
        System.out.println(str);
    }

}
