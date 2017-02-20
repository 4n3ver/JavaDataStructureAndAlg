/**
  * Class that provides a static selection sort method
 *
 *  @author Yoel Ivan (yivan3@gatech.edu)
  */
public class SelectionSort {

	/**
     * Sorts the supplied array of integers using the selection
     * sort algorithm.  The function should run in O(n^2)
     *
     * @param arr The integer array to sort     *
     */
	public static void selectionSort(Comparable[] arr){
        for (int i = 1; i < arr.length; i++) {
            int minIndex = i - 1;
            for (int j = i; j < arr.length; j++) {
                if (arr[minIndex].compareTo(arr[j]) > 0) {
                    minIndex = j;
                }
            }
            Comparable temp = arr[minIndex];
            arr[minIndex] = arr[i - 1];
            arr[i - 1] = temp;
        }
	}
}