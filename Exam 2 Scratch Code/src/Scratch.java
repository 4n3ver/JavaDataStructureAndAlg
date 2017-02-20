import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by Yoel Ivan on 3/7/2015.
 */
public class Scratch {
    public static void main(String[] args) {
        Integer[] a = new Integer[6];
        Random r = new Random();
        for (int i = 0; i < a.length - 1; i++) {
            a[i] = Math.abs(r.nextInt() % 256);
        }
        System.out.println(Arrays.toString(a));
        MaxHeap<Integer> test = new MaxHeap<>(a, a.length - 1);
        test.sort();
        System.out.println(Arrays.toString(a));

        List<Boo> b = new ArrayList<>();
        List<Foo> f = new ArrayList<>();
        processElements(b);

    }
    public static void processElements(List<? extends Comparable> elements){
        for(Comparable o : elements){
            System.out.println(o);
        }
    }

    public static class Boo implements Comparable<Boo> {

        @Override
        public int compareTo(Boo o) {
            return 0;
        }
    }

    public static class Foo implements Comparable {

        @Override
        public int compareTo(Object o) {
            return 0;
        }
    }


}
