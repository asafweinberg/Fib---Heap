package Heap;

/**
 * FibonacciHeap
 *
 * An implementation of fibonacci heap over integers.
 */
public class FibonacciHeap
{
	public static int cutCounter = 0; //static field - counts the number of cuts performed in this program
	public static int linkCounter = 0; //static field - counts the number of links performed in this program
	
	private HeapNode first;
	private HeapNode min;
	private int size;
	private int marked;
	private int treesNumber;

	/**
    * FibonacciHeap():
    * empty constructor - sets properties to default values
    */
	public FibonacciHeap() {
		this.size = 0;
		this.marked = 0;
		this.treesNumber = 0;
		this.min = null;
		this.first = null;
	}
	
	/**
	    * HeapNode getFirst():
	    * returns the first node of the heap
	    */
	public HeapNode getFirst() {
		return this.first;
	}
	
	/**
	    * int getMarkedCount():
	    * returns the number of marked nodes in the heap
	    */
	public int getMarkedCount() {
		return this.marked;
	}
	
	/**
	    * int getTreesNumber():
	    * returns the number of trees in the heap
	    */
	public int getTreesNumber() {
		return this.treesNumber;
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
    	return this.insertNode(node,true);
    }
    
	/**
	    * HeapNode insert(int key, HeapNode info):
	    * private method - inserts a new node to a given heap.
	    * saves in the node a pointer to a different HeapNode.
	    */
    private HeapNode insert(int key, HeapNode info)
    {    
    	HeapNode node = new HeapNode(key,info);
    	return this.insertNode(node,true);
    }
   
    /**
    * public void deleteMin()
    *
    * Delete the node containing the minimum key.
    *
    */
    public void deleteMin()
    {
     	HeapNode child = this.min.child;
     	HeapNode minPrev = this.min.getPrev();
     	
     	if (this.min != null && this.min != minPrev)
     	{
     		this.min.deleteFromTree();
     		if (child != null) {
         		minPrev.insertAfter(child);
         		child.setAllSiblingsParent(null);
         	}
     		if(this.min == this.first) {
     			if( child != null) {
     				this.first = child;
     			} else {
     				this.first = minPrev.getNext();
     			}
         	}
     	}
     	else {
     		this.first = child;
     		if (child != null) {
         		child.setAllSiblingsParent(null);
         	}
     	}
     	
     	this.preformSuccessiveLinking();
     	
     	this.size--;
    }
    
    /**
    * private void preformSuccessiveLinking()
    *
    * performing the consolidating algorithm over the current heap.
    *
    */
    private void preformSuccessiveLinking() {
        HeapNode[] buckets = this.toBuckets();
        this.fromBuckets(buckets);
    }
    
    /**
    * private HeapNode[] toBuckets()
    *
    * First part of  the consolidating algorithm.
    * linking trees with same rank until we get one or less tree with the same rank.
    */
    private HeapNode[] toBuckets() {
    	int bNumber = (int)(Math.floor(Math.log(this.size)/Math.log(2)) + 1);
    	HeapNode[] buckets = new HeapNode[bNumber];
    	if(this.first == null) {
    		return buckets;
    	}
    	this.first.getPrev().setNext(null);
    	HeapNode node = this.first;
    	HeapNode current;
    	
    	while(node != null) {
    		current = node;
    		node = node.getNext();
    		current.isolateNode();
    		while(buckets[current.rank] != null) {
    			current = this.linkTwoTrees(current, buckets[current.rank]);
    			buckets[current.rank - 1] = null;
    		}
    		buckets[current.rank] = current;
    	}
    	   	
    	return buckets;
    }
    
    /**
     * private void fromBuckets(HeapNode[] buckets)
     *
     * Second part of  the consolidating algorithm.
     * link all the different rank trees and insert into the heap, replacing the old heap.
     */
    private void fromBuckets(HeapNode[] buckets) {
    	HeapNode newFirst = null, newMin = null;
    	int trees = 0;
    	
    	for (int i = 0; i < buckets.length; i++) {
    		if(buckets[i] != null) {
    			if(newFirst == null) {
    				newFirst = buckets[i];
    				newMin = newFirst;
//    				newFirst.isolateNode();
    			}
    			else {
    				HeapNode current = buckets[i];
//    				current.isolateNode();
    				newFirst.insertBefore(current);
    				if (current.getKey() < newMin.getKey())
    					newMin = current;
    			}
    			trees++;
    		}
		}
    	
    	this.first = newFirst;
    	this.min = newMin;
    	this.treesNumber = trees;
    }
    
    /**
     * private HeapNode linkTwoTrees(HeapNode a, HeapNode b)
     * getting to nodes, linking their trees into a legal heap-tree.
     */
    private HeapNode linkTwoTrees(HeapNode a, HeapNode b) {
    	HeapNode root, son;
    	if(a.getKey() < b.getKey()) {
    		root = a;
    		son = b;
    	}
    	else {
    		root = b;
    		son = a;
    	}
    	
    	root.addChild(son);
    	FibonacciHeap.linkCounter++;
    	return root;
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
    		  this.first.insertBefore(heap2.getFirst());
    	  }
    	  this.size += heap2.size;
    	  this.marked += heap2.marked;
    	  this.treesNumber += heap2.treesNumber;
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
    	int h=(int)(Math.log(this.size) / Math.log(2));
    	int[] arr = new int[h];
    	HeapNode node=this.first;
    	int rank;
    	do {
    		rank=node.getRank();
    		arr[rank]++;
    		node=node.getNext();
    	}while(node!=this.first);
    	
    	int lastFull=h-1;
    	while(lastFull>=0 && arr[lastFull]==0) {
    		lastFull--;
    	}
    	int[] arrRet = new int[lastFull+1];
    	
    	for(int i=0 ; i<=arrRet.length ;i++) {
    		arrRet[i]=arr[i];
    	}
    	
        return arrRet; 
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
    	int newKey=x.getKey()-delta;
    	x.setKey(newKey);
    	HeapNode xParent= x.getParent();
    	if (xParent==null) { //x is a root and a root can be decreased
    		if(newKey<this.min.getKey()) {
    			this.min=x;
    		}
    		return;
    	}
    	int parentKey= x.getParent().getKey();
    	
    	if (newKey>parentKey) { //the new Key is still greater
    		return;
    	}
    	
    	if(newKey<this.min.getKey()) {
			this.min=x;
		}
    	
    	this.cascadingCut(x,xParent); 
    	
    }
    
    /**
     * private HeapNode insertNode(HeapNode node, boolean isNew)
     * inserting a node to the tree.
     * the method handles all the heap properties.
     * isNew - if true increasing heap size
     */
    private HeapNode insertNode(HeapNode node, boolean isNew) {
       	
    	if(this.isEmpty()) {
    		this.min = node;
    	}
    	else {
        	this.first.insertBefore(node); //to check
        	
        	if(this.min.getKey() > node.getKey()) {
        		this.min = node;
        	}
    	}
    	this.first = node;
    	if(isNew) {
    		this.size++;
    	}
    	this.treesNumber++;
    	return node;
    }
    
    private void cascadingCut(HeapNode x,HeapNode xParent){
    	x = cut(x,xParent);
    	x.isolateNode();
    	this.insertNode(x,false);
    	
    	if (xParent.getParent()!=null) { //if the parent is not a root
    		if (xParent.getMark()==false) { //if the parent is not marked already
    			xParent.setMark(true);
    			this.marked++;
    		}
    		else
    			cascadingCut(xParent,xParent.getParent());
    	}
    	
    	
    }
    private HeapNode cut(HeapNode x, HeapNode xParent) {
    	FibonacciHeap.cutCounter++;
    	
    	if (x.getMark()==true) { //TODO CHECK WITH CARINA - IS IT RIGHT?
    		this.marked--;
    		x.setMark(false); //TODO CHECK WITH CARINA - IS IT RIGHT?
    	}
    	
    	
    	if (x.getNext()==x) { // x is only child
    		xParent.setChild(null);
    		xParent.setRank(xParent.getRank()-1);
    	}
    	else {
    		x=x.deleteFromTree();
    	}
    	x.setParent(null);
    	return x;
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
    	return this.treesNumber + 2 * this.marked;
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
     * public static void resetStaticVars()
     * reseting the static count fields - used only for the measurements section in the assignment
     */
    public static void resetStaticVars() {
    	FibonacciHeap.cutCounter = 0;
    	FibonacciHeap.linkCounter = 0;
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
        int[] arr = new int[k];
        FibonacciHeap tempHeap = new FibonacciHeap();
        HeapNode minInserted = H.first;
        
        for (int i=0 ; i< k; i++) {
        	arr[i]=minInserted.getKey();  //insert the minimum from tree
        	HeapNode childIter =minInserted.getChild();
        	if(childIter!=null) {
	        	do{ 
	 //       		HeapNode child= new HeapNode(); //insert to temp tree the children of the minimum. //key,pointer in original tree
	        		tempHeap.insert(childIter.getKey(),childIter);
	        		childIter=childIter.getNext();
	        	}	while(childIter!=minInserted.getChild());
        	}	
        	minInserted=tempHeap.min; //key, pointer to node
        	tempHeap.deleteMin();
        	minInserted=minInserted.getInfo();
        }
        return arr;
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

		public int key; // the node's key
		private int rank; // the number of children of the node
		private boolean mark; // contains the mark'd state of the heap
		private HeapNode next; // a pointer to the next node in the siblings list
		private HeapNode prev; // a pointer to the previous node in the siblings list
		private HeapNode child; // a pointer to the first child in the children list
		private HeapNode parent; // a pointer to the node's parent
		
		private HeapNode info; // contains a pointer to an original node from a different heap. used in kMin algorithm
		
		/**
	     * public HeapNode(int key)
	     * creates a single node with the given key.
	     * the single node is a nodes double-linked-list with one node
	     */
	  	public HeapNode(int key) {
		    this.key = key;
		    this.rank = 0;
		    this.mark = false;
		    this.next = this;
		    this.prev = this;
		    this.child = null;
		    this.parent = null;
	      }
	  	
	  	/**
	     * public HeapNode(int key)
	     * creates a single node with the given key and info.
	     * the single node is a nodes double-linked-list with one node
	     */
	  	public HeapNode(int key, HeapNode info) {
		    this(key);
		    this.info=info;
	      }
	  	
	  	/**
	     * public HeapNode getInfo()
	     * returns the node's info field info.
	     */
	  	public HeapNode getInfo() {
		    return this.info;
	    }
	  	
	  	/**
	     * public int getKey()
	     * returns the node's key.
	     */
	  	public int getKey() {
		    return this.key;
	    }
	  	/**
	     * public void setKey(int key)
	     * Sets the key of the node.
	     */
	  	public void setKey(int key) {
		    this.key = key;
	    }
	  	
	  	/**
	     * public int getRank()
	     * returns the node's rank - the number of children.
	     */
	  	public int getRank() {
		    return this.rank;
	    }
	  	/**
	     * public void setRank(int rank)
	     * Sets the rank of the node - the number of children he has.
	     */	  	
	  	public void setRank(int rank) {
		    this.rank = rank;
	    }
	  	
	  	/**
	     * public boolean getMark()
	     * returns if the node is marked.
	     */
	  	public boolean getMark() {
		    return this.mark;
	    }
	  	/**
	     * public void setMark(boolean mark)
	     * Sets the mark flag of the node.
	     */
	  	public void setMark(boolean mark) {
		    this.mark = mark;
	    }
	  	
	  	/**
	     * public HeapNode getNext()
	     * returns the next node in the siblings list.
	     */
	  	public HeapNode getNext() {
		    return this.next;
	    }
	  	/**
	     * public void setNext(HeapNode newNext)
	     * Sets the next pointer of the node.
	     */
	  	public void setNext(HeapNode newNext) {
	  		this.next = newNext;
	    }
	  	
	  	/**
	     * public HeapNode getPrev()
	     * returns the previous node in the siblings list.
	     */
	  	public HeapNode getPrev() {
		    return this.prev;
	    }
	  	/**
	     * public void setPrev(HeapNode newPrev)
	     * Sets the previous pointer of the node.
	     */
	  	public void setPrev(HeapNode newPrev) {
	  		this.prev = newPrev;
	    }
	  	
	  	/**
	     * public HeapNode getParent()
	     * returns the node's parent in the tree.
	     */
	  	public HeapNode getParent() {
		    return this.parent;
	    }
	  	/**
	     * public void setParent(HeapNode p)
	     * Sets the node's parent.
	     */
	  	public void setParent(HeapNode p) {
	  		this.parent = p;
	    }
	  	
	  	/**
	     * public HeapNode getChild()
	     * returns the first node in the children list.
	     */
	  	public HeapNode getChild() {
		    return this.child;
	    }
	  	/**
	     * public void setChild(HeapNode c)
	     * Sets the node's children list pointer.
	     */
	  	public void setChild(HeapNode c) {
	  		this.child = c;
	    }
	  	
	  	/**
	     * public void insertAfter(HeapNode newNext)
	     * Insert a node to the list after this node.
	     * @pre newNext!=null
	     */
	  	public void insertAfter(HeapNode newNext) {
	  		HeapNode newListLast = newNext.prev;
	  		newListLast.next = this.next;
	  		this.next.prev = newListLast;
	  		
		    this.next = newNext;
		    newNext.prev = this;
	  	}

	  	/**
	     * public void insertBefore(HeapNode newPrev)
	     * Insert a node to the list before this node.
	     * @pre newPrev!=null
	     */
	  	public void insertBefore(HeapNode newPrev) {
	  		HeapNode p = newPrev.prev;
	  		p.insertAfter(this);
	  	}
	  	
	  	/**
	     * public void addChild(HeapNode c)
	     * Insert a node to this node children list. the new child will be located at the beginning of the list
	     * @pre c has no siblings - c is a single-node list
	     */
	  	public void addChild(HeapNode c) {
	  		if (this.child != null) {
		  		this.child.insertBefore(c);
	  		}
	  		this.child = c;

	  		c.setParent(this);
	  		this.rank++; //Assuming c is a single node.
	  	}
	  	
	  	/**
	     * public void setAllSiblingsParent(HeapNode newParent)
	     * change the parent of every node in the list
	     */
	  	public void setAllSiblingsParent(HeapNode newParent) {
	  		HeapNode first = this;
	  		while (first.getNext() != this) {
				first = first.getNext();
				first.setParent(newParent);
			}
	  		this.setParent(newParent);
	  	}
	  	
	  	/**
	     * public void isolateNode()
	     * make turn this node into a single-node list
	     */
	  	public void isolateNode() {
	  		this.next = this;
	  		this.prev = this;
	  	}

	  	/**
	     * HeapNode deleteFromTree()
	     * removes a node from the list. 
	     * disconnect the node from his parent.
	     * decreases the parent's rank.
	     */
	  	public HeapNode deleteFromTree() {
	  		this.getPrev().setNext(this.getNext());
	  		this.getNext().setPrev(this.getPrev());
	  		if (this.getParent() != null) { 
	  			if(this.getParent().getChild() == this) { //the node is first
	  				this.getParent().setChild(this.getNext());
	  			}
	  			this.getParent().rank--;
	  		}
	  		return this;
	  	}

    }
}
