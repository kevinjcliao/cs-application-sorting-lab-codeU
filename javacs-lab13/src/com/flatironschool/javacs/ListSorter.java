/**
 * 
 */
package com.flatironschool.javacs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Provides sorting algorithms.
 *
 */
public class ListSorter<T> {

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void insertionSort(List<T> list, Comparator<T> comparator) {
	
		for (int i=1; i < list.size(); i++) {
			T elt_i = list.get(i);
			int j = i;
			while (j > 0) {
				T elt_j = list.get(j-1);
				if (comparator.compare(elt_i, elt_j) >= 0) {
					break;
				}
				list.set(j, elt_j);
				j--;
			}
			list.set(j, elt_i);
		}
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void mergeSortInPlace(List<T> list, Comparator<T> comparator) {
		List<T> sorted = mergeSort(list, comparator);
		list.clear();
		list.addAll(sorted);
	}

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * Returns a list that might be new.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public List<T> mergeSort(List<T> list, Comparator<T> comparator) {
        // FILL THIS IN!
      System.out.println("list: " + list);
      List<T> half1 = list.subList(0, list.size()/2);
      List<T> half2 = list.subList(list.size()/2, list.size());
      System.out.println("Half1: " + half1);
      System.out.println("Half2: " + half2);
      List<T> merged = this.merge(half1, half2, comparator);
      System.out.println("merged: " + merged);
      return this.msHelper(list, comparator);
	}

    private List<T> msHelper(List<T> list, Comparator<T> comparator) {
        if(list.size() == 1) {
            return list;
        } else {
            return merge(msHelper(list.subList(0, list.size()/2),
                                  comparator),
                         msHelper(list.subList(list.size()/2, list.size()),
                                  comparator),
                         comparator);
        }
    }

    public List<T> merge(List<T> list1, List<T> list2, Comparator<T> comparator) {
        System.out.println("Merge called for: " + list1 + " and " + list2);
        return mergeHelper(list1, list2, new ArrayList<T>(), comparator);
    }

    private List<T> mergeHelper(List<T> list1, List<T> list2, List<T> currentList, Comparator<T> comparator) {
        if(list1.size() == 0) {
            System.out.println("Hit base case 1. List 1: " + list1 + " list 2: " + list2);
            currentList.addAll(list2);
            System.out.println("Returning list: " + currentList);
            return currentList;

        } else if (list2.size() == 0) {
            System.out.println("Hit base case 2. List 1: " + list1 + " list 2: " + list2);
            currentList.addAll(list1);
            System.out.println("Returning list: " + currentList);
            return currentList;
        }
        int flag = comparator.compare(list1.get(0), list2.get(0));
        if (flag <= 0) {
            currentList.add(list1.get(0));
            return mergeHelper(list1.subList(1, list1.size()), list2, currentList, comparator);
        } else {
            currentList.add(list2.get(0));
            return mergeHelper(list1, list2.subList(1, list2.size()), currentList, comparator);
        }
    }

	/**
	 * Sorts a list using a Comparator object.
	 * 
	 * @param list
	 * @param comparator
	 * @return
	 */
	public void heapSort(List<T> list, Comparator<T> comparator) {
      PriorityQueue<T> queue = new PriorityQueue<T>(list.size(), comparator);
      queue.addAll(list);
      list.clear();
      while(!queue.isEmpty()) {
          list.add(queue.poll());
      }
      return;
	}

	
	/**
	 * Returns the largest `k` elements in `list` in ascending order.
	 * 
	 * @param k
	 * @param list
	 * @param comparator
	 * @return 
	 * @return
	 */
	public List<T> topK(int k, List<T> list, Comparator<T> comparator) {
      PriorityQueue<T> queue = new PriorityQueue<T>(k, comparator);
      while(!list.isEmpty()) {
          if(queue.size() < k) {
              queue.offer(list.remove(0));
          } else if(comparator.compare(list.get(0), queue.peek()) > 0) {
              queue.poll();
              queue.offer(list.remove(0));
          } else {
              list.remove(0);
          }
      }
      while(!queue.isEmpty()) {
          list.add(queue.poll());
      }
        return list;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<Integer> list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		
		Comparator<Integer> comparator = new Comparator<Integer>() {
			@Override
			public int compare(Integer n, Integer m) {
				return n.compareTo(m);
			}
		};
		
		ListSorter<Integer> sorter = new ListSorter<Integer>();
		sorter.insertionSort(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.mergeSortInPlace(list, comparator);
		System.out.println(list);

		list = new ArrayList<Integer>(Arrays.asList(3, 5, 1, 4, 2));
		sorter.heapSort(list, comparator);
		System.out.println(list);
	
		list = new ArrayList<Integer>(Arrays.asList(6, 3, 5, 8, 1, 4, 2, 7));
		List<Integer> queue = sorter.topK(4, list, comparator);
		System.out.println(queue);
	}
}
