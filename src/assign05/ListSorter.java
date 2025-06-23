package assign05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Class for sorting Java class Lists; contains implementations for mergesort as well as
 * quick sort and their associated driver methods
 * @author Brian Keller & Wyatt Young
 * @version June 9, 2024
 */
public class ListSorter
{
    // threshold value for merge sort switching to insertion sort
    private static int insertionThreshold = 5;
    private static String pivotStrategy = "first"; // Default pivot strategy

    /**
     * Driver method for mergesort; when called performs a mergesort on the List of
     * generic items
     * @param arr: List to be merge sorted
     */
    public static <T extends Comparable<? super T>>void mergesort(List<T> arr)
    {
        // array validity checks; cant be null or size less than 1
        if(arr == null || arr.size() <= 1){return;}
        // temporary allocated list
        ArrayList<T> tempArr = new ArrayList<>(Collections.nCopies(arr.size(), null));
        // call recursive method
        mergeSortImp(arr, tempArr, 0, arr.size()-1);
    }

    /**
     * Private implementation method for mergesort, called by driver
     * Switches over to insertion sort when size of sublist to be sorted meets
     * a certain threshold
     * @param arr List to be merge sorted
     */
    private static <T extends Comparable<? super T>> void mergeSortImp(List<T> arr, List<T> tempList,int left, int right)
    {
        //Base Case
        if(right-left <= insertionThreshold) {
            insertionSort(arr, left, right);
            return;
        }
        //Recursive Step
        int mid = left + (right - left) / 2;
        mergeSortImp(arr, tempList, left, mid);
        mergeSortImp(arr, tempList, mid+1, right);
        //Merge
        merge(arr, tempList, left, mid, right);
    }

    /**
     * Private helper method for merge part of mergesort
     * @param arr being sorted
     * @param temp arr
     * @param left index
     * @param mid index
     * @param right index
     */
    private static <T extends Comparable<? super T>> void merge(List<T> arr, List<T> temp, int left, int mid, int right)
    {
        // Initialize 3 pointers; one for left sublist, right sublist, and merged list
        int i = left;
        int j = mid+1;
        int k = left;
        // While there are elements in each subarray
        while(i <= mid && j <= right){
            if (arr.get(i).compareTo(arr.get(j)) <= 0)
            {
                temp.set(k, arr.get(i));
                i++;
            }
            else{
                temp.set(k, arr.get(j));
                j++;
            }
            k++;
        }
        while(i <= mid){
            temp.set(k, arr.get(i));
            i++;
            k++;
        }
        while(j <= right){
            temp.set(k, arr.get(j));
            j++;
            k++;
        }
        for(int l = left; l <= right; l++){
            arr.set(l, temp.get(l));
        }
        // Compare current elements of left sublist with current in right sublist
        // if left element is less than right element, place in original list at index k
        // and increment i and k.
        // O/W right element is placed in original list at k and j and k are incremented
        // copy remaining elements in left sublist to original list
    }

    /**
     * Private helper method for implementing insertion sort for use in mergesort
     * cases
     * @param arr subarray to be sorted, must be less than threshold
     * @param left index
     * @param right index
     */
    private static <T extends Comparable<? super T>> void insertionSort(List<T> arr, int left, int right)
    {
        // start at left index to right
        for(int i = left + 1; i <= right; i++)
        {
            T element = arr.get(i);
            int j;
            for(j = i - 1; j >= left && arr.get(j).compareTo(element) > 0; j--)
            {
                arr.set(j + 1, arr.get(j));
            }
            arr.set(j + 1, element);
        }

    }
    /**
     * Driver method for quicksort; when called performs a quicksort on the List of
     * generic items
     * @param arr: List to be quick sorted
     */
    public static <T extends Comparable<? super T>>void quicksort(List<T> arr)
    {
        // check for null or size 1 arrays
        if(arr == null || arr.size() <= 1){return;}

        // call recursive method
        quickSortImp(arr,0,arr.size() - 1);
    }

    /**
     * Private implementation method for quicksort, called by driver
     * Modular w/ 3 different ways of determining pivot value
     * @param arr List to be sorted
     */
    private static <T extends Comparable<? super T>> void quickSortImp(List<T> arr, int low, int high)
    {
        //Base Case
        if (low>=high)
            return;
        //Choose Pivot
        int pivotIndex = choosePivot(arr, low, high);
        //Partition
        int partitionIndex = partition(arr, low, high, pivotIndex);
        //Sort Sub-arrays
        quickSortImp(arr, low, partitionIndex-1);
        quickSortImp(arr, partitionIndex+1, high);
    }

    /**
     * Private helper method to choose pivot and partition according to pivot value
     * @param arr to be sorted
     * @param left index
     * @param right index
     * @return final pivot index after moving items less than to left and greater than to right
     */
    private static <T extends Comparable<? super T>> int partition(List<T> arr, int left, int right, int pivotIndex)
    {
        T pivotVal = arr.get(pivotIndex);
        swap(arr, pivotIndex, right);
        // initialize points; 1 for one less than pivot index i.e left - 1
        int i = left;
        for( int j = left; j < right; j++){
            //compare the current element to the pivot
            if(arr.get(j).compareTo(pivotVal) < 0){
                swap(arr, i, j);
                i++;
            }
        }
        // place pivot value; swap with element at i and return its position
        swap(arr, i, right);
        return i;
    }

    /**
     * Helper method to choose pivot based on the selected strategy
     * @param arr List to be sorted
     * @param low Lower bound of the subarray
     * @param high Upper bound of the subarray
     * @return Index of the chosen pivot
     */
    private static <T extends Comparable<? super T>> int choosePivot(List<T> arr, int low, int high)
    {
        switch (pivotStrategy) {
            case "random":
                return randomItemPivot(arr, low, high);
            case "middle":
                return middleItemPivot(arr, low, high);
            default:
                return medianPivot(arr, low, high);
        }
    }

    /**
     * 1 of 3 private helper methods for determining quick sort pivot
     * Uses the median of 3 selected values in List
     * @param subArray to find pivot for
     * @return index of pivot point
     */
    private static <T extends Comparable<? super T>> int medianPivot(List<T> subArray, int low, int high)
    {
        // get 3 random indices
        Random random = new Random();
        int[] randInd = new int[3];
        for(int i = 0;i < 3;i++)
        {
            randInd[i] = low + random.nextInt(high - low + 1);
        }
        // find median
        T a = subArray.get(randInd[0]);
        T b = subArray.get(randInd[1]);
        T c = subArray.get(randInd[2]);
        if ((a.compareTo(b) >= 0 && a.compareTo(c) <= 0) || (a.compareTo(b) <= 0 && a.compareTo(c) >= 0)) {
            return randInd[0];
        } else if ((b.compareTo(a) >= 0 && b.compareTo(c) <= 0) || (b.compareTo(a) <= 0 && b.compareTo(c) >= 0)) {
            return randInd[1];
        } else {
            return randInd[2];
        }
    }
    /**
     * 1 of 3 private helper methods for determining quick sort pivot
     * Uses a random item in List as pivot
     * @param subArray to find pivot for
     * @return index of pivot point
     */
    private static <T> int randomItemPivot(List<T> subArray, int low, int high)
    {
        Random rand = new Random();
        return low + rand.nextInt(high - low + 1);
    }
    /**
     * 1 of 3 private helper methods for determining quick sort pivot
     * Uses the middle index of List for pivot
     * @param subArray to find pivot for
     * @return index of pivot point
     */
    private static <T> int middleItemPivot(List<T> subArray, int low, int high)
    {
        return (low + high) / 2;
    }

    /**
     * Generates and returns a List of integers 1 to size in ascending order.
     * @param size of array to be returned
     * @return ArrayList instance of the List interface
     */
    public static List<Integer> generateAscending(int size)
    {
        ArrayList<Integer> list = new ArrayList<>(size);
        for(int i = 1; i < size+1; i++)
        {
            list.add(i);
        }
        return list;
    }

    /**
     * Generates and returns a List of integers 1 to size in a random order
     * Makes use of Javas Collections.shuffle method
     * @param size of array to be returned
     * @return ArrayList instance of List
     */
    public static List<Integer> generatePermuted(int size)
    {
        ArrayList<Integer> list = new ArrayList<>(size);
        for(int i = 1; i < size+1; i++)
        {
            list.add(i);
        }
        Collections.shuffle(list);
        return list;
    }
    /**
     * Generates and returns a List of integers 1 to size in descending order
     * @param size of array to be returned
     * @return ArrayList instance of List
     */
    public static List<Integer> generateDescending(int size)
    {
        ArrayList<Integer> list = new ArrayList<>(size);
        for(int i = size; i > 0; i--)
        {
            list.add(i);
        }
        return list;
    }

    private static <T> void swap(List<T> arr, int i, int j) {
        T temp = arr.get(i);
        arr.set(i, arr.get(j));
        arr.set(j, temp);
    }

    /**
     * Sets the insertion sort threshold for merge sort
     * @param threshold Threshold value
     */
    public static void setThreshold(int threshold)
    {
        insertionThreshold = threshold;
    }

    /**
     * Sets the pivot selection strategy for quicksort
     * @param strategy Pivot strategy ("first", "random", "median")
     */
    public static void setPivotStrategy(String strategy)
    {
        pivotStrategy = strategy;
    }
}
