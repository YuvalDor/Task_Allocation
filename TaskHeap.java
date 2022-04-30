
/**
 * A heap, implemented as an array.
 * The elements in the heap are instances of the class TaskElement,
 * and the heap is ordered according to the Task instances wrapped by those objects.
 *
 * IMPORTANT: Except the percolation (private) functions and the constructors, no single function may loop/recurse through all elements in the heap.
 *
 *
 *
 */
public class TaskHeap {

    public static int capacity = 200; // the maximum number of elements in the heap
    /*
     * The array in which the elements are kept according to the heap order.
     * The following must always hold true:
     * 			if i < size then heap[i].heapIndex == i
     */
    TaskElement[] heap;
    int size; // the number of elements in the heap, it is required that size <= heap.length

    /**
     * Creates an empty heap which can contain 'capacity' elements.
     */
    public TaskHeap() {
        this.heap = new TaskElement[capacity + 1];
        this.size = 0;
    }

    /**
     * Constructs a heap that may contain 'capacity' many elements, from a given array of TaskElements, of size at most 'capacity'.
     * This should be done according to the "build-heap" function studied in class.
     * NOTE: the heapIndex field of each TaskElement might be -1 (or incorrect).
     * You may NOT use the insert function of heap.
     * In this function you may use loops.
     */
    public TaskHeap(TaskElement[] arr) {
        this();
        for (int i = 0; i < arr.length; i++) {
            size++;
            heap[i + 1] = arr[i];
            heap[i + 1].heapIndex = i+1;
        } /////////////////////// Sort it as Max heap
        for (int i = size / 2; i > 0; i--) {
            percolateDown(i);
        }
    }

    /**
     * Returns the size of the heap.
     *
     * @return the size of the heap
     */
    public int size() {return size;}

    /**
     * Inserts a given element into the heap.
     *
     * @param e - the element to be inserted.
     */
    public void insert(TaskElement e) {
        if (this.size == this.heap.length - 1) resize();
        ///////////////////// Insertion to leaf index and PercolateUp
        heap[size + 1] = e;
        size++;
        percolateUp(size);
    }


    private void resize() {
        TaskElement[] heap = new TaskElement[this.heap.length * 2];
        //////////////// keep the indexes as before - Manual copy
        for (int i = 0; i < this.heap.length; i++) {
            heap[i] = this.heap[i];
        }
        this.heap = heap;
    }

    /**
     * Returns and does not remove the element which wraps the task with maximal priority.
     *
     * @return the element which wraps the task with maximal priority.
     */
    public TaskElement findMax() {
        return heap[1]; ///getRoot()
    }


    /**
     * Returns and removes the element which wraps the task with maximal priority.
     *
     * @return the element which wraps the task with maximal priority.
     */
    public TaskElement extractMax() {
        if (this.size==0) return null;
        TaskElement max = findMax();
        remove(1);
        return max;
    }

    /**
     * Removes the element located at the given index.
     *
     * Note: this function is not part of the standard heap API.
     *		 Make sure you understand how to implement it, and why it is required.
     *       There are several ways this function could be implemented.
     * 		 No matter how you choose to implement it, you need to consider the different possible edge cases.
     * @param index
     */
    public void remove(int index){
        heap[index] = heap[size];
        heap[size] = null;
        if (index > size) return;   //edge cases
        if (index == size) {
            size--;
        }
        ///////////////////////////  Leaf removal
        else if (2 * index > size) {
            size--;
            percolateUp(index);
        }
        //////////////////////////// root removal
         else if (index == 1) {
            size--;
            percolateDown(index);
        }
        //////////////////////////// Parent removal
         else if (2 * index < size) {
            size--;
            if (heap[index].t.compareTo(heap[index / 2].t) > 0) {
                percolateUp(index);
            } else
                percolateDown(index);
        }
    }

    private void percolateUp(int index){
        if (index == 1) return;
        TaskElement child = heap[index];
        TaskElement parent = heap[index/2];
        if (parent.t.compareTo(child.t) > 0) return;
         swap(index, index / 2); // help func
         percolateUp(index / 2);
    }

    private void percolateDown(int index){
        if(2*index > size) return;
        TaskElement parent = heap [index];
        TaskElement lChild = heap[2*index];
        TaskElement rChild = heap[(2*index)+1];
        //////////////////////////// After operation on a Leaf
        if (2 * index > size) { heap[index].heapIndex = index; }
        ////////////////////////////  After operation on a Node with Single child
        if (2 * index == size) {
            if (lChild.t.compareTo(parent.t) > 0){
                swap(index, 2 * index);
            }
        }
        ///////////////////////////  After operation on a Node with 2 children
        if (2 * index < size){
            int childIndex = 2 * index; // left child
            if (lChild.t.compareTo(rChild.t) < 0){ // change if right bigger than left
                childIndex = 2 * index + 1; //right child
            }
            if (parent.t.compareTo(heap[childIndex].t) < 0){ // need to switch between parent child ???
                swap(index, childIndex); // help func
                percolateDown(childIndex);
            }
        }
    }

    private void swap(int i, int j) { // help function
        TaskElement child = heap[i];
        TaskElement parent = heap[j];
        heap[i] = parent;
        heap[j] = child;
    }
    public static void main (String[] args){

        /*
         * A basic test for the heap.
         * You should be able to run this before implementing the queue.
         *
         * Expected outcome:
         * 	task: Add a new feature, priority: 10
         *	task: Solve a problem in production, priority: 100
         *	task: Solve a problem in production, priority: 100
         *	task: Develop a new feature, priority: 10
         *	task: Code Review, priority: 3
         *	task: Move to the new Kafka server, priority: 2
         *
         */

        Task a = new Task(10, "Add a new feature");
        Task b = new Task(3, "Code Review");
        Task c = new Task(2, "Move to the new Kafka server");
        TaskElement [] arr = {new TaskElement(a), new TaskElement(b), new TaskElement(c)};
        TaskHeap heap = new TaskHeap(arr);
        System.out.println(heap.findMax());

        Task d = new Task(100, "Solve a problem in production");
        heap.insert(new TaskElement(d));

        System.out.println(heap.findMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());
        System.out.println(heap.extractMax());

    }
}
