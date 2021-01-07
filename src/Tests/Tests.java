package Tests;

import java.util.Arrays;

import Heap.FibonacciHeap;
import Heap.FibonacciHeap.HeapNode;

public class Tests {

	public static void main(String[] args) {
		FibonacciHeap h = new FibonacciHeap();
//		int [] arr= {2,6,7,4,1,9,3,5,10,12,13,14};
		int [] arr= {10,9,8,7,6,4,5,3,2,1,11,12,14,13,16,15,17};
		HeapNode [] arrNodes =arrayNodes(arr,h);
		h.deleteMin();
		printHeap.printHeapFib(h);
		int [] arr2 = h.kMin(h, 7);
		System.out.println(Arrays.toString(arr2));
//		printHeap.printHeapFib(h);
//		h.deleteMin();
//		printHeap.printHeapFib(h);
//		h.decreaseKey(arrNodes[8], 9); //10
//		printHeap.printHeapFib(h);

//		h.decreaseKey(arrNodes[10], 12); //13 ->1
//		h.decreaseKey(arrNodes[9], 12); //12 ->0
//		printHeap.printHeapFib(h);
		

		System.out.print("done");

	}
	
	public static HeapNode [] arrayNodes (int [] arr , FibonacciHeap he) {
		HeapNode [] myArrayNodes=new HeapNode[arr.length];
		
		for (int i=0 ; i< arr.length ; i++) {
			myArrayNodes[i]=he.insert(arr[i]);
		}
		return myArrayNodes;
	}
	
	public static void insertKeys(FibonacciHeap h, int [] arr) {
		for(int i=0; i< arr.length; i++) {
			h.insert(arr[i]);
		}
	}
	public static void insertKeys(FibonacciHeap h, int from, int to) {
		for(int i=from; i< to; i++) {
			h.insert(i);
		}
	}
	
	public static void testDeleteMin() {
		
	}
}
