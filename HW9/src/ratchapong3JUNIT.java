import org.junit.Before;
import org.junit.Test;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static junit.framework.Assert.assertEquals;


public class ratchapong3JUNIT {
    public static final int MAX_LENGTH = 5000;
    public static final int TIMEOUT = 200;
    private StringSearching mySearch;
    private SearchableString emptyString;
    private SearchableString stringSearch;
    private SearchableString myPattern;
    private SearchableString mySequence;
    private List<Integer> answer;
    private List<Integer> hardAnswer;
    private Random rand;


    @Before
    public void setUp() {
        mySearch = new StringSearching();
        emptyString = new SearchableString("");
        stringSearch = new SearchableString("Shouldn't Find Anything");
        rand = new Random(System.currentTimeMillis());
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyListBM() {
        hardAnswer = new ArrayList<>();
        answer = mySearch.boyerMoore(stringSearch, emptyString);
        assertEquals(hardAnswer, answer);
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyListRP() {

        hardAnswer = new ArrayList<>();
        answer = mySearch.rabinKarp(stringSearch, emptyString);
        assertEquals(hardAnswer, answer);
    }

    @Test(timeout = TIMEOUT)
    public void testEmptyListKMP() {
        hardAnswer = new ArrayList<>();
        answer = mySearch.kmp(stringSearch, emptyString);
        assertEquals(hardAnswer, answer);

    }

    @Test//timeout = TIMEOUT)
    public void testBM() {
        int m = rand.nextInt(MAX_LENGTH - 1 + 1) + 1;
        String randSeq = randomSequence(m);
        for (int i = 1; i <= m; i ++) {
            String randPat = randomPattern(i);
            hardAnswer = bruteForceSearch(randPat, randSeq);
            mySequence = new SearchableString(randSeq);
            myPattern = new SearchableString(randPat);

            answer = mySearch.boyerMoore(myPattern, mySequence);
            assertEquals(hardAnswer, answer);
        }


    }

    @Test//timeout = TIMEOUT)
    public void testRP() {
        int m = rand.nextInt(MAX_LENGTH - 1 + 1) + 1;
        String randSeq = randomSequence(m);

        for (int i = 1; i <= m; i ++) {
            String randPat = randomPattern(i);
            hardAnswer = bruteForceSearch(randPat, randSeq);
            mySequence = new SearchableString(randSeq);
            myPattern = new SearchableString(randPat);

            answer = mySearch.rabinKarp(myPattern, mySequence);
            assertEquals(hardAnswer, answer);
        }
    }

    @Test//timeout = TIMEOUT)
    public void testKMP() {
        int m = rand.nextInt(MAX_LENGTH - 1 + 1) + 1;
        String randSeq = randomSequence(m);

        for (int i = 1; i <= m; i ++) {
            String randPat = randomPattern(i);
            hardAnswer = bruteForceSearch(randPat, randSeq);
            mySequence = new SearchableString(randSeq);
            myPattern = new SearchableString(randPat);

            answer = mySearch.kmp(myPattern, mySequence);
            assertEquals(hardAnswer, answer);
        }
    }

    public String randomSequence(int length) {
        String randomSequence = "";
        for (int i = 0; i < length; i++) {
            randomSequence += (char) (rand.nextInt(126 - 32 + 1) + 32);
        }
        return randomSequence;
    }

    public String randomPattern(int length) {
        String randomPattern = "";
        for (int i = 0; i < length; i++) {
            randomPattern += (char) (rand.nextInt(126 - 32 + 1) + 32);
        }
        return randomPattern;
    }

    public List<Integer> bruteForceSearch(String pattern, String text) {

        List<Integer> result = new ArrayList<>();
        int index = text.indexOf(pattern);
        while (index >= 0) {
            result.add(index);
            index = text.indexOf(pattern, index + 1);
        }
        return result;
    }
}