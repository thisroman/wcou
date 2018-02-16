import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Stream;


public class WordCount {

    static boolean isTest = false;

    public static void main(String[] args) {
        PrintStream ps = System.out;
        String fileName = "lines.txt";
        Integer outCount = 5;
        //args = new String[2];
        //args[0] = fileName;
        //args[1] = String.valueOf(outCount);
        //System.out.println("*** START WORD COUNT. ***");
        if (!validateInput(args)) {
            System.out.println("*** INCORRECT INPUT. ***");
            return;
        }
        outCount = Integer.parseInt(args[1]);
        Long startTime = (new Date()).getTime();

        Map<String, Integer> mainMap = new HashMap<String, Integer>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            mainMap = fillMainMap(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<Integer, SortedSet<String>> resultMap = fillSortedMap(mainMap);

        printResult(outCount, resultMap, ps);

        Long processTime = (new Date()).getTime() - startTime;
        ps.println("*** END WORD COUNT. *** time(ms):" + processTime);
    }

    private static boolean validateInput(String[] args) {
        if (args == null || args.length != 2)
            return false;
        String fileName = args[0];
        File f = new File(fileName);
        if (!f.exists() || f.isDirectory() || !tryParseInt(args[1]))
            return false;
        return true;
    }

    static void printResult(Integer outCount, Map<Integer, SortedSet<String>> resultMap,
                            PrintStream ps) {
        int count = 0;
        for (Entry<Integer, SortedSet<String>> entry : resultMap.entrySet()) {
            for (String word : entry.getValue()) {
                if (count >= outCount)
                    break;
                ps.println(word + "=" + entry.getKey());
                count++;
            }
        }
    }

    static Map<Integer, SortedSet<String>> fillSortedMap(Map<String, Integer> mainMap) {
        Map<Integer, SortedSet<String>> resultMap = new TreeMap<>(Collections.reverseOrder());
        mainMap.forEach((K, V) -> {
            SortedSet<String> words = resultMap.get(V);
            if (words == null)
                words = new TreeSet<>();
            words.add(K);
            resultMap.put(V, words);
        });
        return resultMap;
    }

    static Map<String, Integer> fillMainMap(Stream<String> lines) {
        Map<String, Integer> mainMap = new HashMap<String, Integer>();
        if(!isTest)
            lines=lines.parallel();
        lines.map(item -> item.replaceAll(",", "\\.")).map(item -> item.replaceAll("\\s+", "\\."))
                .map(item -> item.replaceAll("\\.\\.", "\\."))
                .forEach(line -> Arrays.stream(line.split("\\.")).forEach(item -> {
                    if (!item.trim().isEmpty()) {
                        Integer count = mainMap.get(item.toLowerCase());
                        if (count != null) {
                            mainMap.put(item.toLowerCase(), count + 1);
                        } else {
                            mainMap.put(item.toLowerCase(), 1);
                        }
                    }
                }));
        return mainMap;
    }

    private static boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}