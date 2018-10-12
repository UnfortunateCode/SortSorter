package sortingAlgorithms;

import java.util.Hashtable;
import java.util.Vector;

public abstract class SortStructure<T extends Comparable<T>> {
	protected Hashtable<Vector<T>,Integer> seenList = new Hashtable<>();
	protected Hashtable<T,Integer> seenTimes = new Hashtable<>();
	protected T[] baseArray = null;
	protected int comparisons = 0;
	protected int swaps = 0;
	protected double avgSeenTime = 0;;
	protected double seenStdDev = 0;
	protected int minSeen = 0;
	protected int maxSeen = 0;
	
	/**
	 * Constructor that orders the provided array and sets up
	 * the data.
	 * 
	 * @param randomizedArray array to be sorted
	 */
	public SortStructure(T[] randomizedArray) {
		baseArray = randomizedArray.clone();
		
		sort();
		setAvgMinMaxSeenTime();
		setSeenStdDev();
	}
	
	protected abstract void sort();
	
	/*
	 * Checks to see if the pair has been seen before,
	 * if so, then old decision can be remembered, otherwise
	 * each of the pair is seen another time.
	 */
	protected boolean comparing (T first, T second) {
		++comparisons;
		
		Vector<T> pairing = new Vector<>();
		Integer value = null;
		
		pairing.add(first);
		pairing.add(second);
		
		value = seenList.get(pairing);
		
		if (value == null) {
			value = first.compareTo(second);
			
			seenList.put(pairing, value);
			
			pairing = new Vector<>();
			pairing.add(second);
			pairing.add(first);
			
			seenList.put(pairing, -value);
			
			Integer count = seenTimes.get(first);
			if (count == null) {
				seenTimes.put(first, 1);
			} else {
				seenTimes.replace(first, count + 1);
			}
			
			count = seenTimes.get(second);
			if (count == null) {
				seenTimes.put(second, 1);
			} else {
				seenTimes.replace(second, count + 1);
			}		
		}
		
		return value > 0; // TODO Verify this is correct action when value = 0
						  //      Valid for BubbleSort
	}
		
	/*
	 * Sets the average times data has been seen with 
	 * the min and the max times.
	 */
	private void setAvgMinMaxSeenTime() {
		minSeen = Integer.MAX_VALUE;
		maxSeen = Integer.MIN_VALUE;
		double sum = 0;
		
		for (Integer times : seenTimes.values()) {
			sum += times;
			
			if (times < minSeen) {
				minSeen = times;
			}
			
			if (times > maxSeen) {
				maxSeen = times;
			}
		}
		
		avgSeenTime = sum / seenTimes.size();
	}
	
	/*
	 * Sets the standard deviation of the times seen
	 */
	private void setSeenStdDev() {
		double deviation = 0;
				
		for (Integer times : seenTimes.values()) {
			deviation += Math.pow(times - avgSeenTime,2);
		}
		
		seenStdDev = Math.sqrt(deviation / seenTimes.size());
	}
	
	/**
	 * @return the average times data has been seen
	 */
	public double getAverageSeenTime() {
		return avgSeenTime;
	}
	
	/**
	 * @return the minimum times a piece of data was seen
	 */
	public int getMinSeenTimes() {
		return minSeen;
	}
	
	/**
	 * @return the maximum times a piece of data was seen
	 */
	public int getMaxSeenTimes() {
		return maxSeen;
	}
	
	/**
	 * The standard deviation for the number of times
	 * data are seen.
	 * 
	 * @return the standard deviation of seen times
	 */
	public double getSeenStandardDeviation() {
		return seenStdDev;
	}
	
	
	public T[] getSortedArray() {
		return baseArray.clone();
	}
	
}
