package resources;

import java.util.ArrayList;

//this is an algorithms parent class that holds various search and sort methods
public class Algorithms {
	
	
	//each method is protected so that only subclasses can access these methods
	
	//int binarysearch
	protected int binarySearch(int[]ar,int x) {
		int xindex = -1;
		
		int start = 0;
		int end = ar.length-1;
		int middle = 0;
		boolean found = false;
		
		while(start<=end && !found) {
			middle = (start+end)/2;
			if (ar[middle] == x) {
				found = true;
				xindex = middle;
			}
			else if (ar[middle]<x) {
				start = middle +1;
			}
			else {
				end = middle -1;
			}
		}

		return xindex;
	}
	
	//my own modification of binary search using string lists
	protected int binaryStringSearch(ArrayList<String> list,String x) {
		int xindex = -1;
		
		int start = 0;
		int end = list.size()-1;
		int middle = 0;
		boolean found = false;
		
		while(start<=end && !found) {
			middle = (start+end)/2;
			if (list.get(middle).compareTo(x)==0) {
				found = true;
				xindex = middle;
			}
			else if (list.get(middle).compareTo(x)<0) {
				start = middle +1;
			}
			else {
				end = middle -1;
			}
		}

		return xindex;
	}
	
	
	//Isertion sort using integer array
	protected void IntegerArrayInsertionSort(int[]ar) {
		//controls the pointer for the sorted left half of the array
		for (int end = 1; end<ar.length;end++) {
			int item = ar[end];
			int i = end;
			while(i>0&& item <(ar[i-1])) {
				ar[i]=ar[i-1]; //moves larger value to the right
				i--;
			}
			//move smaller value to the left hand side
			ar[i]=item;
		}
	}
	
	//insertion sore using integer list
	protected void IntegerListInsertionSort(ArrayList<Integer>list) {
		//controls the pointer for the sorted left half of the array
		for (int end = 1; end<list.size();end++) {
			int item =list.get(end);
			int i = end;
			while(i>0&& item <(list.get(i-1))) { //moves larger value to the right
				list.set(i, list.get(i-1));
				i--;
			}
			//move smaller value to the left hand side
			list.set(i, item);
		}
	}
	


	

}
