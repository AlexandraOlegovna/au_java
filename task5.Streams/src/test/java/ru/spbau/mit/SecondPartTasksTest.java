package ru.spbau.mit;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SecondPartTasksTest {

    @Rule
    public TemporaryFolder tmpFolder = new TemporaryFolder();

    private List<String> initFiles() throws IOException {
        final File tmp1 = tmpFolder.newFile("file1.txt");
        final File tmp2 = tmpFolder.newFile("file2.txt");
        final File tmp3 = tmpFolder.newFile("file3.txt");

        FileWriter writer1 = new FileWriter(tmp1);
        FileWriter writer2 = new FileWriter(tmp2);
        FileWriter writer3 = new FileWriter(tmp3);

        writer1.write("Hello, World!");
        writer2.write("Hello, big World!");
        writer3.write("Hello, big little World!");

        writer1.close();
        writer2.close();
        writer3.close();

        return Arrays.asList(
            tmp1.getAbsolutePath(),
            tmp2.getAbsolutePath(),
            tmp3.getAbsolutePath());
    }

    @Test
    public void testFindQuotes() throws IOException {

        List<String> paths = initFiles();

        assertEquals(
            Arrays.asList("Hello, World!", "Hello, big World!", "Hello, big little World!"),
            SecondPartTasks.findQuotes(paths, "Hello"));

        assertEquals(
            Arrays.asList("Hello, big World!", "Hello, big little World!"),
            SecondPartTasks.findQuotes(paths, "ig"));

        assertEquals(
            Collections.singletonList("Hello, big little World!"),
            SecondPartTasks.findQuotes(paths, "ttle"));

        assertEquals(
            Collections.emptyList(),
            SecondPartTasks.findQuotes(paths, ":"));
    }

    @Test
    public void testPiDividedBy4() {
        double radius = 0.5;
        double answer = Math.PI * Math.pow(radius, 2);

        assertEquals(answer, SecondPartTasks.piDividedBy4(), 0.1);
    }

    @Test
    public void testFindPrinter() {
        Map<String, List<String>> compositions = new HashMap<>();

        assertThrows(RuntimeException.class, () -> SecondPartTasks.findPrinter(compositions));

        compositions.put("author1", Arrays.asList("a", "b"));
        assertEquals("author1", SecondPartTasks.findPrinter(compositions));

        compositions.put("author2", Arrays.asList("a", "b", "c"));
        assertEquals("author2", SecondPartTasks.findPrinter(compositions));

        compositions.put("author3", Arrays.asList("a", "b", "c", "d"));
        assertEquals("author3", SecondPartTasks.findPrinter(compositions));

        compositions.put("author4", Arrays.asList("a", "b", "c", "d"));
        assertEquals("author4", SecondPartTasks.findPrinter(compositions));
    }

    @Test
    public void testCalculateGlobalOrder() {
        List<Map<String, Integer>> orders = new ArrayList<>();

        orders.add(new HashMap<>());
        assertEquals(
            new HashMap<String, Integer>(),
            SecondPartTasks.calculateGlobalOrder(orders));

        Map<String, Integer> map1 = new HashMap<>();
        map1.put("item1", 1);
        orders.add(map1);
        assertEquals(
            new HashMap<String, Integer>() {{ put("item1", 1);}},
            SecondPartTasks.calculateGlobalOrder(orders));

        Map<String, Integer> map2 = new HashMap<>();
        map2.put("item1", 1);
        map2.put("item2", 1);
        orders.add(map2);
        assertEquals(
            new HashMap<String, Integer>() {{ put("item1", 2); put("item2", 1);}},
            SecondPartTasks.calculateGlobalOrder(orders));

        Map<String, Integer> map3 = new HashMap<>();
        map3.put("item1", 1);
        map3.put("item2", 1);
        map3.put("item3", 1);
        orders.add(map3);
        assertEquals(
            new HashMap<String, Integer>() {{ put("item1", 3); put("item2", 2); put("item3", 1);}},
            SecondPartTasks.calculateGlobalOrder(orders));

        orders.add(map3);
        assertEquals(
            new HashMap<String, Integer>() {{ put("item1", 4); put("item2", 3); put("item3", 2);}},
            SecondPartTasks.calculateGlobalOrder(orders));

    }
}