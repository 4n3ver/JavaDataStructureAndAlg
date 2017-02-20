import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Sorting algorithm assortment.
 *
 * @author Yoel Ivan
 * @version 0.0a
 */
public class Sorting {

    /**
     * Its mythical its BOGO!sort...
     *
     * @param arr        the array to be sorted
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param <T>        data type to sorts
     */
    public static <T> void bogosort(T[] arr, Comparator<T> comparator) {
        validateArgs(arr.clone(), comparator);
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 1; i < arr.length; i++) {
                if (comparator.compare(arr[i], arr[i - 1]) < 0) {
                    isSorted = false;
                }
            }
            if (!isSorted) {
                for (int i = 0; i < arr.length; i++) {
                    T temp = arr[i];
                    int rand = new Random().nextInt(arr.length);
                    arr[i] = arr[rand];
                    arr[rand] = temp;
                }
            }
        }
    }

    /**
     * The out-of-place stable variant of the legendary quicksort...
     *
     * @param arr        the array to be sorted
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param <T>        data type to sorts
     */
    public static <T> void stablequicksort(T[] arr, Comparator<T> comparator) {
        validateArgs(arr, comparator);
        if (arr.length > 1) {
            ArrayList<T> less = new ArrayList<>(arr.length);
            Queue<T> equal = new LinkedList<>();
            ArrayList<T> greater = new ArrayList<>(arr.length);
            T pivot = arr[new Random().nextInt(arr.length)];
            for (int i = 0; i < arr.length; i++) {
                if (comparator.compare(arr[i], pivot) < 0) {
                    less.add(arr[i]);
                } else if (comparator.compare(arr[i], pivot) > 0) {
                    greater.add(arr[i]);
                } else {
                    equal.add(arr[i]);
                }
            }
            T[] tempL = (T[]) less.toArray();
            stablequicksort(tempL, comparator);
            T[] tempR = (T[]) greater.toArray();
            stablequicksort(tempR, comparator);
            int i = 0;
            for (int l = 0; l < tempL.length; l++) {
                arr[i++] = tempL[l];
            }
            while (!equal.isEmpty()) {
                arr[i++] = equal.remove();
            }
            for (int g = 0; g < tempR.length; g++) {
                arr[i++] = tempR[g];
            }
        }
    }

    /**
     * Implement bubble sort. It should be: in-place stable Have a worst case
     * running time of: O(n^2) And a best case running time of: O(n) Any
     * duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void bubblesort(T[] arr, Comparator<T> comparator) {
        validateArgs(arr, comparator);
        boolean isSorted = false;
        for (int i = 1; !isSorted && i < arr.length; i++) {
            isSorted = true;
            for (int j = 1; j <= arr.length - i; j++) {
                if (comparator.compare(arr[j - 1], arr[j]) > 0) {
                    swap(arr, j - 1, j);
                    isSorted = false;
                }
            }
        }
    }

    /**
     * Validate any object to make sure it is not <code>null</code>.
     *
     * @param args any object to be validated.
     * @throws IllegalArgumentException If any of the <code>args</code> passed
     *                                  is <code>null</code>.
     */
    private static void validateArgs(Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new IllegalArgumentException("NULL HERE!!!");
            }
        }
    }

    /**
     * Swap two elements in an array.
     *
     * @param arr array which elements will be swapped
     * @param a   index of the first element
     * @param b   index of the second element
     * @param <T> type of element in the array
     */
    private static <T> void swap(T[] arr, int a, int b) {
        T temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    /**
     * Implement insertion sort. It should be: in-place stable Have a worst
     * case running time of: O(n^2) And a best case running time of: O(n) Any
     * duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void insertionsort(T[] arr, Comparator<T> comparator) {
        validateArgs(arr, comparator);
        generalizedinsertionsort(arr, comparator, 1);
    }

    /**
     * Generalization of insertion sort with <code>gap</code> variable.
     *
     * @param arr        array to be sorted
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param gap        custom gap
     * @param <T>        type of element in the array
     */
    private static <T> void generalizedinsertionsort(T[] arr,
                                                     Comparator<T> comparator,
                                                     int gap) {
        for (int i = gap; i < arr.length; i++) {
            boolean isSorted = false;
            for (int j = i; !isSorted && j >= gap; j -= gap) {
                isSorted = true;
                if (comparator.compare(arr[j], arr[j - gap]) < 0) {
                    swap(arr, j, j - gap);
                    isSorted = false;
                }
            }
        }
    }

    /**
     * Implement shell sort. It should be: in-place Have a worst case running
     * time of: O(n^2) And a best case running time of: O(n log(n)) Note that
     * there may be duplicates in the array.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void shellsort(T[] arr, Comparator<T> comparator) {
        validateArgs(arr, comparator);
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            generalizedinsertionsort(arr, comparator, gap);
        }
    }

    /**
     * Implement quick sort. Use the provided random object to select your
     * pivots. For example if you need a pivot between a (inclusive) and b
     * (exclusive) where b > a, use the following code: int pivotIndex =
     * r.nextInt(b - a) + a; It should be: in-place Have a worst case running
     * time of: O(n^2) And a best case running time of: O(n log n) Note that
     * there may be duplicates in the array.
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param rand       the Random object used to select pivots
     * @throws IllegalArgumentException if the array or comparator or rand is
     *                                  null
     */
    public static <T> void quicksort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        validateArgs(arr, comparator, rand);
        quicksortHelper(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Helper method of quicksort which does the recursive part of the
     * algorithm.
     *
     * @param arr        array to be sorted
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param rand       {@link Random} object to determined the pivot
     * @param beginIndex beginning index of the sub array to be sorted
     *                   (inclusive)
     * @param lastIndex  last index of the sub array to be sorted (inclusive)
     * @param <T>        type of the element in the array
     */
    private static <T> void quicksortHelper(T[] arr, Comparator<T> comparator,
                                            Random rand, int beginIndex,
                                            int lastIndex) {
        if (lastIndex > beginIndex) {
            int pivot = beginIndex + rand.nextInt(lastIndex - beginIndex + 1);
            int insertionIndex = beginIndex + 1;
            swap(arr, beginIndex, pivot);
            for (int i = beginIndex + 1; i <= lastIndex; i++) {
                if (comparator.compare(arr[i], arr[beginIndex]) < 0) {
                    swap(arr, insertionIndex++, i);
                }
            }
            swap(arr, beginIndex, insertionIndex - 1);
            quicksortHelper(arr, comparator, rand, beginIndex,
                    insertionIndex - 2);
            quicksortHelper(arr, comparator, rand, insertionIndex, lastIndex);
        }
    }

    /**
     * Implement merge sort. It should be: stable Have a worst case running
     * time of: O(n log n) And a best case running time of: O(n log n) You can
     * create more arrays to run mergesort, but at the end, everything should
     * be merged back into the original T[] which was passed in.
     * ********************* IMPORTANT ************************ FAILURE TO DO
     * SO MAY CAUSE ClassCastException AND CAUSE YOUR METHOD TO FAIL ALL THE
     * TESTS FOR MERGE SORT
     * ********************************************************
     * Any duplicates in the array should be in the same relative position
     * after sorting as they were before sorting.
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @throws IllegalArgumentException if the array or comparator is null
     */
    public static <T> void mergesort(T[] arr, Comparator<T> comparator) {
        validateArgs(arr, comparator);
        T[] sorted = divide(arr, comparator);
        for (int i = 0; i < arr.length; i++) {
            arr[i] = sorted[i];
        }
    }

    /**
     * Recursive helper method for merge sort that does the division step.
     *
     * @param arr        array to be sorted
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param <T>        type of the elements in the array
     * @return sorted copy of the original array
     */
    private static <T> T[] divide(T[] arr, Comparator<T> comparator) {
        int half = arr.length / 2;
        if (half < 1) {
            return arr;
        } else {
            T[] left = (T[]) new Object[half];
            T[] right = (T[]) new Object[arr.length - half];
            for (int i = 0, otherHalf = arr.length - half; i < otherHalf;
                 i++) {
                if (i < half) {
                    left[i] = arr[i];
                }
                right[i] = arr[half + i];
            }
            return merge(divide(left, comparator), divide(right, comparator),
                    comparator);
        }
    }

    /**
     * Recursive helper method for merge sort that does the merging step.
     *
     * @param left       first sub array to be merged
     * @param right      second sub array to be merged
     * @param comparator {@link Comparator} object to compare elements in the
     *                   <code>arr</code>
     * @param <T>        type of the elements in the array
     * @return sorted copy of merged array
     */
    private static <T> T[] merge(T[] left, T[] right,
                                 Comparator<T> comparator) {
        T[] dest = (T[]) new Object[left.length + right.length];
        for (int i = 0, a = 0, b = 0; i < dest.length; i++) {
            if (a < left.length && b < right.length && comparator.compare(
                    left[a], right[b]) > 0 || a >= left.length) {
                dest[i] = right[b++];
            } else {
                dest[i] = left[a++];
            }
        }
        return dest;
    }

    /**
     * Implement radix sort. Remember you CANNOT convert the ints to strings.
     * It should be: stable Have a worst case running time of: O(kn) And a best
     * case running time of: O(kn) Any duplicates in the array should be in the
     * same relative position after sorting as they were before sorting. You
     * may use an ArrayList or LinkedList if you wish, but it may only be used
     * inside radixsort and any radix sort helpers Do NOT use these classes
     * with other sorts.
     *
     * @param arr the array to be sorted
     * @return the sorted array
     * @throws IllegalArgumentException if the array is null
     */
    public static int[] radixsort(int[] arr) {
        validateArgs(arr);
        int[] bucket = new int[10];
        int[] temp = new int[arr.length];
        int maxVal = 0;
        int maxDigit = 1;
        for (int div = 1, digit = 0; digit < maxDigit; div *= 10, digit++) {
            for (int i = 0; i < arr.length; i++) {
                int abs = Math.abs(arr[i]);
                if (maxVal < abs) {
                    maxVal = abs;
                    maxDigit = digitOf(maxVal);
                }
                bucket[abs / div % 10]++;
            }
            for (int i = 1; i < 10; i++) {
                bucket[i] += bucket[i - 1];
            }
            for (int i = arr.length - 1; i >= 0; i--) {
                int abs = Math.abs(arr[i]);
                temp[--bucket[abs / div % 10]] = arr[i];
            }
            int[] swap = arr;
            arr = temp;
            temp = swap;
            bucket = new int[10];
        }
        return signsort(arr);
    }

    /**
     * Divide and Conquer method to find #of digit of an integer which seems
     * crazy but apparently faster than using log or strlen.
     *
     * @param n <code>int</code> which digit is to be count
     * @return # of digit
     */
    private static int digitOf(int n) {
        return (n < 100000) ? (n < 100) ? (n < 10) ? 1 : 2
                : (n < 1000) ? 3 : (n < 10000) ? 4 : 5
                : (n < 10000000) ? (n < 1000000) ? 6 : 7
                        : (n < 100000000) ? 8 : (n < 1000000000) ? 9 : 10;
    }

    /**
     * Stable bucket sort based on sign of the <code>int</code>.
     *
     * @param arr array to be sorted
     * @return sorted array
     */
    private static int[] signsort(int[] arr) {
        int[] bucket = new int[2];
        int[] temp = new int[arr.length];
        for (int n = 0; n < arr.length; n++) {
            bucket[arr[n] < 0 ? 0 : 1]++;
        }
        bucket[1] += bucket[0];
        for (int n = arr.length - 1; n >= 0; n--) {
            if (arr[n] >= 0) {
                temp[--bucket[1]] = arr[n];
            }
        }
        for (int n = 0; n < arr.length; n++) {
            if (arr[n] < 0) {
                temp[--bucket[0]] = arr[n];
            }
        }
        return temp;
    }
}