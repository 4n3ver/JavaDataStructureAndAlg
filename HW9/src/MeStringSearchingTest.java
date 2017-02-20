import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertArrayEquals;

/**
 * Created by Yoel Ivan on 4/2/2015.
 */
public class MeStringSearchingTest {
    private static final int TEST_SIZE = 10000;
    private static final int RABIN_LIM = 19291018;
    private static final int BOYER_LIM = 2550397;
    private final static int KMP_LIM   = 9761367;
    private SearchableString     hamlet;
    private Map<String, Integer> expected;
    private StringSearching      search;

    @Before
    public void setUp() throws Exception {
        File filename = new File("hamlet.txt");
        Scanner in = new Scanner(filename);
        StringBuilder buffer = new StringBuilder();
        while (in.hasNextLine()) {
            buffer.append(in.nextLine() + " ");
        }
        hamlet = new SearchableString(buffer.toString());
        expected = new HashMap<>(10);
        expected.put("you", 780);
        expected.put("Hamlet", 83);
        expected.put("lord", 215);
        expected.put("your", 246);
        expected.put("what", 124);
        expected.put("king", 107);
        expected.put("him", 217);
        expected.put("our", 502);
        expected.put("Horatio", 31);
        expected.put("queen", 26);
        search = new StringSearching();
    }

    @Test
    public void testKmp() throws Exception {
        int count = 0;
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, exp, search.kmp(key, hamlet).size());
            count += hamlet.getCount();
        }
        assertTrue(count <= KMP_LIM);
        System.out.println("KMP " + count + " charAt(s)");
    }

    @Test
    public void testBoyerMoore() throws Exception {
        int count = 0;
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, exp, search.boyerMoore(key, hamlet).size());
            count += hamlet.getCount();
        }
        assertTrue(count <= BOYER_LIM);
        System.out.println("Boyer-Moore " + count + " charAt(s)");
    }

    @Test
    public void testRabinKarp() throws Exception {
        int count = 0;
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, exp, search.rabinKarp(key, hamlet).size());
            count += hamlet.getCount();
        }
        assertTrue(count <= RABIN_LIM);
        System.out.println("Rabin-Karp " + count + " charAt(s)");
    }

    @Test
    public void testBoyerMooreRabinKarp() throws Exception {
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, search.boyerMoore(key, hamlet), search.rabinKarp(
                    key, hamlet));
        }
    }

    @Test
    public void testBoyerMooreKMP() throws Exception {
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, search.boyerMoore(key, hamlet), search.kmp(key,
                    hamlet));
        }
    }

    @Test
    public void testRabinKarpKMP() throws Exception {
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, search.kmp(key, hamlet), search.rabinKarp(key,
                    hamlet));
        }
    }

    @Test
    public void testFailTable() throws Exception {
        List<String> gibberish = randomStringGenerator(97, 3);
        for (String stuff : gibberish) {
            assertArrayEquals(prefixSuffix(stuff), search.buildFailureTable(
                    stuff));
        }
    }

    private static List<String> randomStringGenerator(int start,
                                                      int variance) {
        Random rand = new Random(System.currentTimeMillis());
        List<String> randWord = new LinkedList<>();
        for (int count = 0; count < TEST_SIZE; count++) {
            String randString = "";
            for (int i = 0, len = 5 + rand.nextInt(20); i < len; i++) {
                randString += (char) (start + rand.nextInt(variance));
            }
            randWord.add(randString);
        }
        return randWord;
    }

    // extremely inefficient method to compute fail table
    private static int[] prefixSuffix(String word) {
        int[] failtable = new int[word.length()];
        for (int i = word.length() - 1; i > 0; i--) {
            String pre = word.substring(0, i);
            for (int j = 1; j + pre.length() <= word.length(); j++) {
                if (word.startsWith(pre, j)) {
                    failtable[j + pre.length() - 1] = Math.max(
                            failtable[j + pre.length() - 1], pre.length());
                }
            }
        }
        return failtable;
    }

    @Test
    public void testHashing() throws Exception {
        List<String> gibberish = randomStringGenerator(0,
                Character.MAX_VALUE + 1);
        for (String stuff : gibberish) {
            String sub = stuff.substring(0, stuff.length() - 1);
            int lastIndex = stuff.length() - 1;
            assertEquals(search.generateHash(stuff.substring(1),
                    stuff.length() - 1), search.updateHash(search.generateHash(
                    sub, lastIndex), lastIndex, stuff.charAt(0), stuff.charAt(
                    lastIndex)));
        }
    }

    @Test
    public void testBruteForce() throws Exception {
        int count = 0;
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, exp, search.bruteForce(key, hamlet).size());
            count += hamlet.getCount();
        }
        //assertTrue(count <= KMP_LIM);
        System.out.println("Brute Force " + count + " charAt(s)");
    }

    @Test
    public void testBruteForceBoyerMoore() throws Exception {
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, search.bruteForce(key, hamlet), search.boyerMoore(
                    key, hamlet));
        }
    }

    @Test
    public void testBruteForceRabinKarp() throws Exception {
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, search.bruteForce(key, hamlet), search.rabinKarp(
                    key, hamlet));
        }
    }

    @Test
    public void testBruteForceKMP() throws Exception {
        for (String key : expected.keySet()) {
            int exp = expected.get(key);
            assertEquals(key, search.bruteForce(key, hamlet), search.kmp(key,
                    hamlet));
        }
    }

    @Test
    public void testConsecutive() throws Exception {
        assertEquals(search.bruteForce("aa", "aaaaa"), search.boyerMoore("aa", "aaaaa"));
    }

    @Test
    public void testBruteForceT() throws Exception {
        int count = 0;
        SearchableString test = new SearchableString("abacaabaccabacab");
        search.bruteForce("abacab", test);
        count = test.getCount();
        //assertTrue(count <= KMP_LIM);
        System.out.println("Brute ForceT " + count + " charAt(s)");
    }

    @Test
    public void testKmpT() throws Exception {
        int count = 0;
        SearchableString test = new SearchableString("abacaabaccabacab");
        search.kmp("abacab", test);
        count = test.getCount();
        //assertTrue(count <= KMP_LIM);
        System.out.println("KmpT " + count + " charAt(s)");
    }
}