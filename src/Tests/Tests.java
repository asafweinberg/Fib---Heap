package Tests;

import Heap.FibonacciHeap;

public class Tests {

	public static void main(String[] args) {
		FibonacciHeap h = new FibonacciHeap();
		insertKeys(h, 0, 8);
		h.deleteMin();

		printHeap.printHeapFib(h);
		insertKeys(h, 40, 44);
		h.deleteMin();
		printHeap.printHeapFib(h);
		
		
		int j=0;
		j++;
		System.out.print("done - size = " + h.size());

	}

	public static void insertKeys(FibonacciHeap h, int from, int to) {
		for(int i=from; i< to; i++) {
			h.insert(i);
		}
	}
	
	public static void testDeleteMin() {
		
	}
}
