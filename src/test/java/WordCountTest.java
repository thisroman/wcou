import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Map;
import java.util.SortedSet;
import java.util.stream.Stream;


public class WordCountTest {
    @Test
    public void testFailed() throws InterruptedException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        String[] data = new String[] { "line.", ".,LINE " };
        Stream<String> stream = Arrays.stream(data);
        Map<String, Integer> mainMap = WordCount.fillMainMap(stream);
        Map<Integer, SortedSet<String>> resultMap = WordCount.fillSortedMap(mainMap);
        WordCount.printResult(12, resultMap, ps);
        String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        System.out.println("testFailed:" + Thread.currentThread().getName());
        Assert.assertFalse(content.equals("line=1" + '\r' + '\n'));
    }

    @Test
    public void testEasy() throws InterruptedException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        String[] data = new String[] { "line.", ".,LINE " };
        Stream<String> stream = Arrays.stream(data);
        Map<String, Integer> mainMap = WordCount.fillMainMap(stream);
        Map<Integer, SortedSet<String>> resultMap = WordCount.fillSortedMap(mainMap);
        WordCount.printResult(12, resultMap, ps);
        String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        System.out.println("test:" + Thread.currentThread().getName());
        Assert.assertTrue(content.equals("line=2" + System.getProperty("line.separator")));
    }

    @Before
    public void setUp() {
        WordCount.isTest = true;
    }

    @Test
    public void testCount() throws InterruptedException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        String[] data = getTestText();
        Stream<String> stream = Arrays.stream(data);
        Map<String, Integer> mainMap = WordCount.fillMainMap(stream);
        Map<Integer, SortedSet<String>> resultMap = WordCount.fillSortedMap(mainMap);
        WordCount.printResult(5, resultMap, ps);
        String content = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        String correctResult = "merry=16" + System.getProperty("line.separator") + "christmas=8"
                + System.getProperty("line.separator") + "their=8" + System.getProperty("line.separator") + "to=8"
                + System.getProperty("line.separator") + "and=6" + System.getProperty("line.separator");

        System.out.println("testCount:" + Thread.currentThread().getName());
        Assert.assertTrue(content.equals(correctResult));
    }

    private static String[] getTestText() {
        String[] data = new String[] { "Hark. How the bells, sweet silver bells", "All seem to say, Throw cares away.",
                "Christmas is here, bringing good cheer", "To young and old, meek and the bold",
                "Ding, dong, ding, dong, that is their song,", "With joyful ring, all caroling",
                "One seems to hear words of good cheer", "From everywhere, filling the air",
                "Oh, how they pound, raising their sound", "Over hill and dale, telling their tale",
                "Gaily they ring, while people sing", "Songs of good cheer, Christmas is here.",
                "Merry, merry, merry, merry Christmas.", "Merry, merry, merry, merry Christmas.",
                "On, on they send, on without end", "Their joyful tone to every home",
                "Hark. How the bells, sweet silver bells", "All seem to say, Throw cares away.",
                "Christmas is here, bringing good cheer", "To young and old, meek and the bold",
                "Ding, dong, ding, dong, that is their song", "With joyful ring, all caroling.",
                "One seems to hear words of good cheer", "From everywhere, filling the air",
                "O, how they pound, raising their sound", "Over hill and dale, telling their tale",
                "Gaily they ring, while people sing", "Songs of good cheer, Christmas is here.",
                "Merry, merry, merry, merry Christmas.", "Merry, merry, merry, merry Christmas.",
                "On, on they send, on without end", "Their joyful tone to every home.", "Ding dong ding dong." };
        return data;
    }
}