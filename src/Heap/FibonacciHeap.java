package Heap;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
	public static int cutCounter = 0;
	public static int linkCounter = 0;
	
	private HeapNode first;
	private HeapNode min;
	private int size;
	private int marked;

	public FibonacciHeap() {
		this.size = 0;
		this.marked = 0;
		this.min = null;
		this.first = null;
	}
	
	public HeapNode getFirst() {
		return this.first;
	}
	
   /**
    * public boolean isEmpty()
    *
    * precondition: none
    * 
    * The method returns true if and only if the heap
    * is empty.
    *   
    */
    public boolean isEmpty()
    {
    	return first == null;
    }
		
   /**
    * public HeapNode insert(int key)
    *
    * Creates a node (of type HeapNode) which contains the given key, and inserts it into the heap.
    * 
    * Returns the new node created. 
    */
    public HeapNode insert(int key)
    {    
    	HeapNode node = new HeapNode(key);
    	if(this.isEmpty()) {
    		this.first = node;
    		this.min = node;
    	}
    	else {
        	this.first.setPrev(node);
        	if(this.min.getKey() > key) {
        		this.min = node;
        	}
    	}
    	
    	this.size++;
    	return node;
    }

   /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	return; // should be replaced by student code
     	
    }

   /**
    * public HeapNode findMin()
    *
    * Return the node of the heap whose key is minimal. 
    *
    */
    public HeapNode findMin()
    {
    	return this.min;
    } 
    
   /**
    * public void meld (FibonacciHeap heap2)
    *
    * Meld the heap with heap2
    *
    */
    public void meld (FibonacciHeap heap2)
    {
    	  if(this.isEmpty())
    	  {
    		  this.first = heap2.getFirst();
    	  }
    	  else {
    		  this.first.setPrev(heap2.getFirst());
    	  }
    	  this.size += heap2.size;
    	  this.marked += heap2.marked;
    }

   /**
    * public int size()
    *
    * Return the number of elements in the heap
    *   
    */
    public int size()
    {
    	return this.size;
    }
    	
    /**
    * public int[] countersRep()
    *
    * Return a counters array, where the value of the i-th entry is the number of trees of order i in the heap. 
    * 
    */
    public int[] countersRep()
    {
	int[] arr = new int[42];
        return arr; //	 to be replaced by student code
    }
	
   /**
    * public void delete(HeapNode x)
    *
    * Deletes the node x from the heap. 
    *
    */
    public void delete(HeapNode x) 
    {    
    	this.decreaseKey(x, x.getKey() - Integer.MIN_VALUE);
    	this.deleteMin();
    }

   /**
    * public void decreaseKey(HeapNode x, int delta)
    *
    * The function decreases the key of the node x by delta. The structure of the heap should be updated
    * to reflect this chage (for example, the cascading cuts procedure should be applied if needed).
    */
    public void decreaseKey(HeapNode x, int delta)
    {    
    	return; // should be replaced by student code
    }

   /**
    * public int potential() 
    *
    * This function returns the current potential of the heap, which is:
    * Potential = #trees + 2*#marked
    * The potential equals to the number of trees in the heap plus twice the number of marked nodes in the heap. 
    */
    public int potential() 
    {    
    	return this.size + 2 * this.marked;
    }

   /**
    * public static int totalLinks() 
    *
    * This static function returns the total number of link operations made during the run-time of the program.
    * A link operation is the operation which gets as input two trees of the same rank, and generates a tree of 
    * rank bigger by one, by hanging the tree which has larger value in its root on the tree which has smaller value 
    * in its root.
    */
    public static int totalLinks()
    {    
    	return FibonacciHeap.linkCounter;
    }

   /**
    * public static int totalCuts() 
    *
    * This static function returns the total number of cut operations made during the run-time of the program.
    * A cut operation is the operation which diconnects a subtree from its parent (during decreaseKey/delete methods). 
    */
    public static int totalCuts()
    {    
    	return FibonacciHeap.cutCounter;
    }

     /**
    * public static int[] kMin(FibonacciHeap H, int k) 
    *
    * This static function returns the k minimal elements in a binomial tree H.
    * The function should run in O(k*deg(H)). 
    * You are not allowed to change H.
    */
    public static int[] kMin(FibonacciHeap H, int k)
    {    
        int[] arr = new int[42];
        return arr; // should be replaced by student code
    }
    
   /**
    * public class HeapNode
    * 
    * If you wish to implement classes other than FibonacciHeap
    * (for example HeapNode), do it in this file, not in 
    * another file 
    *  
    */
    public class HeapNode{

		public int key;
		private int rank;
		private boolean mark;
		private HeapNode next;
		private HeapNode prev;
		private HeapNode child;
		private HeapNode parent;
		
	
	  	public HeapNode(int key) {
		    this.key = key;
		    this.rank = 0;
		    this.mark = false;
		    this.next = this;
		    this.prev = this;
		    this.child = null;
		    this.parent = null;
	      }
	
	  	public int getKey() {
		    return this.key;
	    }
	  	public void setKey(int key) {
		    this.key = key;
	    }
	  	
	  	public int getRank() {
		    return this.rank;
	    }
	  	public void setRank(int rank) {
		    this.rank = rank;
	    }
	  	
	  	public boolean getMark() {
		    return this.mark;
	    }
	  	public void setMark(boolean mark) {
		    this.mark = mark;
	    }
	  	
	  	public HeapNode getNext() {
		    return this.next;
	    }
	  	public void setNext(HeapNode newNext) {
	  		HeapNode newListLast = newNext.prev;
	  		newListLast.next = this.next;
	  		this.next.prev = newListLast;
	  		
		    this.next = newNext;
		    newNext.prev = this;
	    }
	  	
	  	public HeapNode getPrev() {
		    return this.prev;
	    }
	  	public void setPrev(HeapNode newPrev) {
	  		HeapNode p = newPrev.prev;
	  		p.setNext(newPrev);
	    }
	  	
	  	public HeapNode getParent() {
		    return this.parent;
	    }
	  	public void setParent(HeapNode p) {
	  		this.parent = p;
	    }
	  	
	  	public HeapNode getChild() {
		    return this.child;
	    }
	  	public void setChild(HeapNode c) {
	  		if (this.child != null) {
		  		this.child.setPrev(c);
	  		}
	  		else {
	  			this.child = c;
	  		}
	  		c.setParent(this);
	  		this.rank++; //Assuming c is a single node.
	    }
	  	

    }
}
