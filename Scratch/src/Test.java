import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by Yoel Ivan on 4/1/2015.
 */
public class Test {
    private static Random            rand;
    private static ArrayList<String> expected;
    private static final int TEST_SIZE = 10;

    public static void main(String[] args) {
        /*int a = 100;
        while ((a /= 2) > 0) {
            System.out.println(a);
        }*/
    /*
        int[] failureTable = {0, 0, 0, 1, 0, 0, 1, 2, 3, 4, 2, 1, 2, 3, 4};
        System.out.println(Arrays.toString(failureTable));
        System.out.println(Arrays.toString(prefixSuffix("lovlvolovlolovl")));
        System.out.println(Arrays.toString(prefixSuffix("lecture")));*/
        int[] l = {5, 6, 8, 10, 22};
        int[] r = {9, 25 ,41 ,56 , 68};
        System.out.println(Arrays.toString(merge(l, r)));
    }

    public static List<String> randomStringGenerator() {
        rand = new Random(System.currentTimeMillis());
        List<String> randWord = new LinkedList<>();
        for (int count = 0; count < TEST_SIZE; count++) {
            String randString = "";
            for (int i = 0, len = 5 + rand.nextInt(20); i < len; i++) {
                randString += (char) (97 + rand.nextInt(3));
            }
            randWord.add(randString);
        }
        return randWord;
    }

    public static int[] merge(int[] arr1, int[] arr2) {
        int[] res = new int[arr1.length + arr2.length];
        for (int i = 0, j = 0; i + j < res.length;) {
            if (i < arr1.length && arr1[i] <= arr2[j]) {
                res[i + j] = arr1[i++];
            } else {
                res[i + j] = arr2[j++];
            }
        }
        return res;
    }

    public static int[] prefixSuffix(String word) {
        int[] failtable = new int[word.length()];
        for (int i = word.length() - 1; i > 0; i--) {
            String pre = word.substring(0, i);
            for (int j = 1; j + pre.length() <= word.length(); j++) {
                if (word.startsWith(pre, j)) {
                    failtable[j + pre.length() - 1] = Math.max(failtable[j + pre.length() - 1], pre.length());
                }
            }
        }
        return failtable;
    }


}
