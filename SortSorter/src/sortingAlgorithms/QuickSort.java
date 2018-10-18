package sortingAlgorithms;


/**
 * QuickSort is front loaded and performs the sort on construction.
 * 
 * Additional processing is used to pull data about the Sort itself.
 * 
 * @author UnfortunateCode
 */
public class QuickSort<T extends Comparable<T>> extends SortStructure<T>{
	private T tempStorage;
	
	/**
	 * Constructor that orders the provided array and sets up
	 * the data.
	 * 
	 * @param randomizedArray array to be sorted
	 */
	public QuickSort (T[] randomizedArray) {
		super(randomizedArray);
	}
	
	protected void sort() {
		
		quicksort(0, baseArray.length - 1);
		
	}
	
	private Object quicksort(int first, int last) {
		if (last <= first) { // one or less element
			return null;
		} else if (last - first == 1) { // two elements
			if (comparing(baseArray[first],baseArray[last])) {
				++swaps;
				
				tempStorage = baseArray[first];
				baseArray[first] = baseArray[last];
				baseArray[last] = tempStorage;
			}
			return null;
		} else { // three-plus elements
			int mid = first + (last - first)/2;

			if (comparing(baseArray[first],baseArray[mid])) {
				++swaps;
				
				tempStorage = baseArray[first];
				baseArray[first] = baseArray[mid];
				baseArray[mid] = tempStorage;
			}
			if (comparing(baseArray[first],baseArray[last])) {
				++swaps;
				
				tempStorage = baseArray[first];
				baseArray[first] = baseArray[last];
				baseArray[last] = tempStorage;
			}
			if (last - first == 2) { // exactly three elements
				if (comparing(baseArray[last-1],baseArray[last])) {
					++swaps;

					tempStorage = baseArray[mid];
					baseArray[mid] = baseArray[last];
					baseArray[last] = tempStorage;
				}
				return null;
			} else { // more than three elements - put middle(first, mid, last) to last index
				if (comparing(baseArray[last],baseArray[mid])) {
					++swaps;

					tempStorage = baseArray[mid];
					baseArray[mid] = baseArray[last];
					baseArray[last] = tempStorage;
				}
			}
			
			// Either the section is sorted, and we've already returned,
			// or the pivot point is at the last index
			
			int firstHigher = first, lastLower = last - 1;
			
			while (lastLower > firstHigher) {

				for (; firstHigher < last && !comparing(baseArray[firstHigher],baseArray[last]); ++firstHigher) {
				}

				for (; lastLower >= first && !comparing(baseArray[last],baseArray[lastLower]); --lastLower) {
				}


				if (lastLower > firstHigher) {
					++swaps;
					
					tempStorage = baseArray[firstHigher];
					baseArray[firstHigher] = baseArray[lastLower];
					baseArray[lastLower] = tempStorage;
				}
			}
			
			++swaps;
			
			tempStorage = baseArray[last];
			baseArray[last] = baseArray[lastLower + 1];
			baseArray[lastLower + 1] = tempStorage;
			
			if (lastLower - first < last - (lastLower + 2)) {
				quicksort(first, lastLower);
				return quicksort(lastLower+2, last);
			} else {
				quicksort(lastLower+2, last);
				return quicksort(first, lastLower);
			}
		}
		
		
	}
}
