import java.util.Random;

/**
 * Created by Yoel Ivan on 2/13/2015.
 */
public class randomArrayfor2110Bubble {
    public static void main(String[] args) {
        Random r = new Random();
        System.out.println(".orig x7000");
        for (int i = 0; i < 100; i++) {
            System.out.println(".fill " + (short)(r.nextInt(Short.MAX_VALUE) - r.nextInt(Short.MAX_VALUE)) / 2);
        }
        System.out.println(".end");
    }

}
