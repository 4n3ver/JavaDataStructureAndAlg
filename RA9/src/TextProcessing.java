/**
 * Class containing various text processing helper functions
 *
 * @author Kush
 * @author Yoel Ivan
 */
public class TextProcessing {

    //private static final int BASE = 742617000027;
    private static final int BASE = 1332;

    /**
     * The function takes in a string and generates a last function table for
     * the given string. The string will never be null
     * <p>
     * For each possible character, the table should contain the last index of
     * that character in the string or -1
     *
     * @param needle the string to generate the table for
     * @return the table for the given string
     * @throws IllegalArgumentException if needle is empty
     */
    public static int[] buildLastTable(String needle) {
        if (needle == null || needle.length() == 0) {
            throw new IllegalArgumentException("Empty needle!");
        }
        int[] table = new int[Character.MAX_VALUE + 1];
        for (int i = 0; i < table.length; i++) {
            table[i] = needle.lastIndexOf((char) i);
        }
        return table;
    }

    /**
     * This function is a helper function for the Rabin Karp algorithm
     * <p>
     * It takes in the old hash value of some given string and returns the new
     * has based on the parameters The hash was originally calculated as
     * follows:
     * <p>
     * hash(maiden) = m*BASE^5 + a*BASE^4 + i*BASE^3 + d*BASE^2 + e*BASE^1 +
     * n*BASE^0
     * <p>
     * DO NOT USE Math.pow This function must run in O(1) time
     */
    public static int updateHash(int oldHash, char oldChar, char newChar,
                                 int length) {
        return ((oldHash - (oldChar * power(BASE, length - 1))) * BASE) +
                ((int) newChar);
    }

    private static int power(int base, int pow) {
        int res = 1;
        for (int i = 0; i < pow; i++) {
            res *= base;
        }
        return res;
    }
}
