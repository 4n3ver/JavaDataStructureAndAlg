import java.util.LinkedList;
import java.util.List;

/**
 * {@link StringSearching} is a collection of string pattern algorithm
 *
 * @author Yoel Ivan
 * @version 0.0a
 */
public class StringSearching implements StringSearchingInterface {

    @Override
    public List<Integer> bruteForce(CharSequence pattern, CharSequence text) {
        List<Integer> matchIndex = new LinkedList<>();
        //int count = 0;
        for (int textProbe = 0; pattern.length() + textProbe <= text.length();
             textProbe++) {
            boolean match = true;
            for (int patternProbe = 0;
                 match && patternProbe < pattern.length(); patternProbe++) {
                //count++;
                if (pattern.charAt(patternProbe) != text.charAt(
                        textProbe + patternProbe)) {
                    match = false;
                }
            }
            if (match) {
                matchIndex.add(textProbe);
                //System.out.println(pattern + " "  + count);
                //count = 0;
            }
        }
        return matchIndex;
    }

    @Override
    public List<Integer> kmp(CharSequence pattern, CharSequence text) {
        validateArgs(pattern, text);
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("0 length pattern");
        }
        List<Integer> indexFound = new LinkedList<>();
        if (text.length() >= pattern.length()) {
            int[] failTable = buildFailureTable(pattern);
            int patternProbe = 0;
            int textProbe = 0;
            while (textProbe < text.length()) {
                if (text.charAt(textProbe) == pattern.charAt(patternProbe)) {
                    if (patternProbe == pattern.length() - 1) { // ITS A MATCH!
                        indexFound.add(textProbe + 1 - pattern.length());

                        /*
                         * after found, consult failTable and continue
                         * search
                         */
                        patternProbe = failTable[patternProbe];
                    } else {
                        patternProbe++;
                    }
                    textProbe++;
                } else if (patternProbe == 0) { // nothing can be reuse
                    textProbe++;    // shift by 1 unit
                } else {
                    patternProbe = failTable[patternProbe - 1];
                }
            }
        }
        return indexFound;
    }

    @Override
    public int[] buildFailureTable(CharSequence text) {
        validateArgs(text);

        /* first element will always be 0, we need to shift at least one
        unit */
        int[] failTable = new int[text.length()];
        int prefixProbe = 0;
        int suffixProbe = 1;
        while (suffixProbe < text.length()) {
            if (text.charAt(prefixProbe) == text.charAt(suffixProbe)) {
                failTable[suffixProbe] = prefixProbe + 1;
                prefixProbe++;
                suffixProbe++;
            } else if (prefixProbe > 0) {
                prefixProbe = failTable[prefixProbe - 1];
            } else {
                suffixProbe++;
            }
        }
        return failTable;
    }

    @Override
    public List<Integer> boyerMoore(CharSequence pattern, CharSequence text) {
        validateArgs(pattern, text);
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("0 length pattern");
        }
        List<Integer> indexMatch = new LinkedList<>();
        if (text.length() >= pattern.length()) {
            int patternProbe = pattern.length() - 1;
            int textProbe = patternProbe;
            int[] last = buildLastTable(pattern);
            char textRead;
            char patternRead;
            while (textProbe < text.length()) {
                textRead = text.charAt(textProbe);
                patternRead = pattern.charAt(patternProbe);
                if (textRead != patternRead) {

                    /*  TEXT PROBE IS THE ONE THAT JUMP AROUND!

                        last[textRead] > patternProbe
                            <p> The last occurrence of the char has been </p>
                            checked, on the right side of patternProbe </p>
                            <p> Only slides the text by 1 </p>
                            textProbe += patternLength - patternProbe

                        last[textRead] < patternProbe
                            <p> The last occurrence of the char has NOT been
                            checked, on the left side of patternProbe </p>
                            <p> Slides to the text last occurrence on the
                            pattern, skip all character from/after the
                            lastOccurrence </p>
                            textProbe += patternLength - (last[textRead] + 1)

                         last[textRead] == -1
                            textProbe += patternLength - (-1 + 1)
                     */
                    textProbe += pattern.length() - Math.min(patternProbe,
                            1 + last[textRead]);
                    patternProbe = pattern.length() - 1;    // on all 3 cases
                } else if (patternProbe == 0) {  // IT's A MATCH!!!!
                    indexMatch.add(textProbe);

                    /* +patterLength since we want to check from behind */
                    textProbe += pattern.length();  // shift the text by 1 unit
                    patternProbe = pattern.length() - 1;
                } else {
                    patternProbe--;
                    textProbe--;
                }
            }
        }
        return indexMatch;
    }

    @Override
    public int[] buildLastTable(CharSequence pattern) {
        validateArgs(pattern);

        // this is implemented using lookup table

        // unicode start at /u0000 and ends at /uFFFF inclusive
        int[] lastTable = new int[Character.MAX_VALUE + 1];

        for (int i = 0; i < lastTable.length; i++) {
            lastTable[i] = -1;
        }
        for (int i = 0; i < pattern.length(); i++) {
            lastTable[pattern.charAt(i)] = i;
        }
        return lastTable;
    }

    @Override
    public int generateHash(CharSequence current, int length) {
        validateArgs(current);
        if (length < 0 || current.length() < length) {
            throw new IllegalArgumentException("invalid length");
        }
        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += current.charAt(i) * power(BASE, length - 1 - i);
        }
        return sum;
    }

    @Override
    public int updateHash(int oldHash, int length, char oldChar,
                          char newChar) {
        if (length < 0) {
            throw new IllegalArgumentException("invalid length");
        }
        return (oldHash - oldChar * power(BASE, length - 1)) * BASE + newChar;
    }

    @Override
    public List<Integer> rabinKarp(CharSequence pattern, CharSequence text) {
        validateArgs(pattern, text);
        if (pattern.length() == 0) {
            throw new IllegalArgumentException("0 length pattern");
        }
        List<Integer> indexFound = new LinkedList<>();
        if (text.length() >= pattern.length()) {
            int textProbe = 0;
            int patternHash = generateHash(pattern, pattern.length());
            int subtextHash = generateHash(text, pattern.length());
            while (textProbe + pattern.length() <= text.length()) {
                if (patternHash == subtextHash) {
                    boolean match = true;
                    for (int pP = 0; pP < pattern.length() && match; pP++) {
                        if (pattern.charAt(pP) != text.charAt(
                                textProbe + pP)) {
                            match = false;
                        }
                    }
                    if (match) {
                        indexFound.add(textProbe);
                    }
                }

                /*
                    Note when len of text == to len of pattern
                    you should not update the hash anymore, since no newchar
                    exist.
                 */
                if (textProbe + pattern.length() < text.length()) {
                    subtextHash = updateHash(subtextHash, pattern.length(),
                            text.charAt(textProbe), text.charAt(
                                    textProbe + pattern.length()));
                }
                textProbe++;
            }
        }
        return indexFound;
    }

    /**
     * Power function of integer.
     *
     * @param base base of the power
     * @param pow  th exponential part of the power
     * @return base^pow
     */
    private static int power(int base, int pow) {
        int res = 1;
        for (int i = 0; i < pow; i++) {
            res *= base;
        }
        return res;
    }

    /**
     * The usual method to make sure all the args passed are not
     * <code>null</code>.
     *
     * @param args any objects to be validated
     */
    private static void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("passing in null");
            }
        }
    }
}
