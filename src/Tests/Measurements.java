package Tests;

import Heap.FibonacciHeap;
import Heap.FibonacciHeap.HeapNode;

public class Measurements {

	public static void main(String[] args) {
		runSequence1();
		runSequence2();	
	}
	
	public static void runSequence1() {
		System.out.println("---------------- Measurements Part 1 ---------------------");
		sequence1(1024, 10);
		sequence1(2048, 11);
		sequence1(4096, 12);
		System.out.println();
	}
	
	public static void runSequence2() {
		System.out.println("---------------- Measurements Part 2 ---------------------");
		sequence2(1000);
		sequence2(2000);
		sequence2(3000);
		System.out.println();
	}
	
	public static void sequence1(int m, int log) {
		long start = System.currentTimeMillis();

		FibonacciHeap heap = new FibonacciHeap();
		HeapNode[] nodes = new HeapNode[m+1];
		for(int i = m; i >=0; i--) {
			nodes[i] = heap.insert(i);
		}
		heap.deleteMin();
		for (int i = 0; i < log; i++) {
			double keytoDec = 0;
			for (int k = 1; k <= i; k++) {
				keytoDec += Math.pow(0.5, k);
			}
			keytoDec *= m;
			keytoDec += 2;
			heap.decreaseKey(nodes[(int)keytoDec], m+1);
		}
		heap.decreaseKey(nodes[m-1], m+1);
		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		printResults(m, timeElapsed, heap);
		FibonacciHeap.resetStaticVars();
	}
	
	public static void sequence2(int m) {
		long start = System.currentTimeMillis();

		FibonacciHeap heap = new FibonacciHeap();
		for(int i = m; i >0; i--) {
			heap.insert(i);
		}
		for (int i = 0; i < m/2; i++) {
			heap.deleteMin();
		}

		long finish = System.currentTimeMillis();
		long timeElapsed = finish - start;

		printResults(m, timeElapsed, heap);
		FibonacciHeap.resetStaticVars();
	}
	
	public static void printResults(int m, long time, FibonacciHeap h) {
		String result ="m = " + m + " \t";
		result += "Run Time = " + time + " \t";
		result += "Total Links = " + FibonacciHeap.totalLinks() + " \t";
		result += "Total Cuts = " + FibonacciHeap.totalCuts() + " \t";
		result += "Potential = " + h.potential() + " \t";
		System.out.println(result);
	}

}
