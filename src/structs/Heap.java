package structs;
**
 * Heap.java
 * @author Siyu Li
 * CIS 22C, Lab 18
 */
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

public class Heap<T> {
    private int heapSize;
    private ArrayList<T> heap;
    private Comparator<T> cmp;

    /**Constructors/

    /**
     * Constructor for the Heap class.
     * Sets heapSize to data size, stores parameters, inserts null at heap
     * element 0, and calls buildHeap().
     * @param data an unordered ArrayList, where element 0 is not used.
     * @param comparator that determines organization of heap
     * based on priority.
     */
    public Heap(ArrayList<T> data, Comparator<T> cmp) {
        if (data == null){
            throw new IllegalArgumentException("Data list cannot be null");
        }
        this.cmp = cmp;
        // Create a new ArrayList and insert null at index 0.
        heap = new ArrayList<>(data.size() + 1);
        heap.add(null);  // index 0 is not used
        // Add the data elements in order (data list is assumed unordered)
        for (T item : data) {
            heap.add(item);
        }
        heapSize = data.size(); // size:0
        buildHeap();
    }

    /**Mutators*/

    /**
     * Converts an ArrayList into a valid max heap. Called by constructor.
     * Calls helper method heapify.
     */
    public void buildHeap() {
        // From floor(heapSize/2) downto 1, call heapify.
        for (int i = heapSize / 2; i >= 1; i--) {
            heapify(i);
        }
    }

    /**
     * Helper method to buildHeap, remove, and sort.
     * Bubbles an element down to its proper location within the heap.
     * @param index an index in the heap
     */
    private void heapify(int index) {
        int largest = index;

        System.out.println("2222");
        int left = getLeft(index);
        System.out.println("3333");
        int right = getRight(index);
        System.out.println("4444");
    
        // Check left child
        if (left > 0 && left <= heapSize && cmp.compare(heap.get(left), heap.get(largest)) > 0) {
            largest = left;
        }
        // Check right child
        if (right > 0 && right <= heapSize && cmp.compare(heap.get(right), heap.get(largest)) > 0) {
            largest = right;
        }
        // If either child is greater, swap inline and heapify recursively
        if (largest != index) {
            T temp = heap.get(index);
            heap.set(index, heap.get(largest));
            heap.set(largest, temp);
            heapify(largest);
        }
    }

    /**
     * Inserts the given data into heap.
     * Calls helper method heapIncreaseKey.
     * @param key the data to insert
     */
    public void insert(T key) {
        heapSize++;
        heap.add(key);
        heapIncreaseKey(heapSize, key);
    }

    /**
     * Helper method for insert.
     * Bubbles an element up to its proper location
     * @param index the current index of the key
     * @param key the data
     */
    private void heapIncreaseKey(int index, T key) {
        while (index > 1 && cmp.compare(heap.get(getParent(index)), key) < 0) {
            heap.set(index, heap.get(getParent(index)));
            index = getParent(index);
        }
        heap.set(index, key);
    }

    /**
     * Removes the element at the specified index.
     * Calls helper method heapify
     * @param index the index of the element to remove
     */
    public void remove(int index) {
        if (index < 1 || index > heapSize) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        heap.set(index, heap.get(heapSize));
        heapSize--;
        heapify(index);
    }

    /**Accessors*/

    /**
     * Returns the heap size (current number of elements)
     * @return the size of the heap
     */
    public int getHeapSize() {
        return heapSize;
    }

    /**
     * Returns the location (index) of the
     * left child of the element stored at index.
     * @param index the current index
     * @return the index of the left child.
     * @precondition 0 < index <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getLeft(int index) throws IndexOutOfBoundsException {
        System.out.println("getLeft fn index:  "+index + 1);
        
        if (index < 1 || index > heapSize + 1){
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }

        return 2 * index;

    // If left child is out of bounds, return -1 instead of null
   
    }

    /**
     * Returns the location (index) of the right child of the element
     * stored at index.
     * @param index the current index
     * @return the index of the right child
     * @precondition 0 < i <= heap_size 
     * @throws IndexOutOfBoundsException when precondition is violated.
     */


    public int getRight(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > heapSize + 1)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);

        return 2 * index + 1;        
    }

    /**
     * Returns the location (index) of the
     * parent of the element stored at index.
     * @param index the current index
     * @return the index of the parent
     * @precondition 1 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public int getParent(int index) throws IndexOutOfBoundsException {
        if (index <= 1 || index > heapSize)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return index / 2;
    }
    /**
     * Returns the maximum element (highest priority)
     * @return the max value
     */
    public T getMax() {
        if (heapSize < 1)
            throw new NoSuchElementException("Heap is empty");
        return heap.get(1);
    }

    /**
     * Returns the element at a specific index.
     * @param index an index in the heap.
     * @return the data at the index.
     * @precondition 0 < i <= heap_size
     * @throws IndexOutOfBoundsException when precondition is violated.
     */
    public T getElement(int index) throws IndexOutOfBoundsException {
        if (index < 1 || index > heapSize)
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        return heap.get(index);
    }

    /**Additional Operations*/

    /**
     * Creates a String of all elements in the heap, separated by ", ".
     * @return a String of all elements in the heap, separated by ", ".
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= heapSize; i++) {
            sb.append(heap.get(i));
            if (i < heapSize)
                sb.append(", ");
        }
        return sb.toString();
    }

    /**
     * Uses the heap sort algorithm to sort the heap into ascending order.
     * Calls helper method heapify.
     * @return an ArrayList of sorted elements
     * @postcondition heap remains a valid heap
     */
    public ArrayList<T> sort() {
        ArrayList<T> sorted = new ArrayList<>();
        ArrayList<T> originalHeap = new ArrayList<>(heap); // Create a copy of the heap
        int originalSize = heapSize; 
    
        for (int i = originalSize; i >= 2; i--) {
            T temp = heap.get(1);
            heap.set(1, heap.get(i));
            heap.set(i, temp);
            sorted.add(0, temp); // Add to the beginning for ascending order
            heapSize--;
            heapify(1);
        }
        sorted.add(0, heap.get(1)); // Add last element
    
        // Restore original heap state
        heap = originalHeap;
        heapSize = originalSize;
    
        return sorted;
    }
}
