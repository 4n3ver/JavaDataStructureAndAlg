import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a class that will allow you to iterate through the first n
 * Fibonacci elements
 *
 * @author Yoel Ivan (yivan3@gatech.edu) *
 */
public class FibonacciIterator implements Iterator<Integer> {

	// Do not add any instance variables, you may not need to use all of them though
	private Integer n;
	private Integer current;
	private Integer runningValue = 1;
    private Integer previousValue = 0;
	
	public FibonacciIterator(Integer n) {
        this.n = n;
        current = 0;
        previousValue = 1;
	}
	
	@Override
	public boolean hasNext() {
        return n > 0;
	}

	@Override
	public Integer next() {
        if (!hasNext()) {
            throw new NoSuchElementException("no element to iterate through");
        }
        n--;
        int val = current;
        current += previousValue;
        previousValue = val;
        return val;
	}
}
