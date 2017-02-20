import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

/**
 * Created by Yoel Ivan on 1/31/2015.
 */
public class Spans {


    public static void main(String[] args) {
        int[] X = {6, 5, 4, 3, 2, 1};

        System.out.println(Arrays.toString(span(X)));
    }

    public static int[] span(int[] x ) {
        int[] s = new int[x.length];
        Stack<Integer> buffer = new Stack<>();
        for (int i = 0; i < x.length; i++) {
            int buffpeek = -1;
            if (!buffer.isEmpty()) buffpeek = x[buffer.peek()];
            while (!buffer.isEmpty() && x[buffer.peek()] <= x[i]) { // comparing current value with previous value
                buffer.pop();   // removing value less than current value from the buffer
            }
            if (buffer.isEmpty()){
                s[i] = i + 1;   // only run when you found new highest value (current index) + 1
            } else {
                s[i] = i - buffer.peek();   // range of current index from the index of highest value found so far
            }
            System.out.println("s: " + Arrays.toString(s));
            System.out.println("b: " + buffer);
            System.out.println("i: " + i);
            System.out.println("x[buffer.peak()]: " + buffpeek);
            System.out.println("x[i]: " + x[i]);
            System.out.println();
            buffer.push(i);
        }
        return s;
    }

    public boolean isHTMLMatch(String html) {
        Deque<Character> buffer = new LinkedList<>();
        for (int i = 0; i < html.length(); i++) {
            if (html.charAt(i) == '<') {
                buffer.push('<');
            } else if (html.charAt(i) == '>') {
                if (buffer.isEmpty()) {
                    return false; // nothing to match with
                }
                buffer.pop();
            }
        }
        return buffer.isEmpty();
    }


}
