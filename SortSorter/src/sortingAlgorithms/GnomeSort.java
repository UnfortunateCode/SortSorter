package sortingAlgorithms;


/**
 * GnomeSort is front loaded and performs the sort on construction.
 * 
 * Additional processing is used to pull data about the Sort itself.
 * 
 * @author UnfortunateCode
 */
public class GnomeSort<T extends Comparable<T>> extends SortStructure<T>{
	
	/**
	 * Constructor that orders the provided array and sets up
	 * the data.
	 * 
	 * @param randomizedArray array to be sorted
	 */
	public GnomeSort (T[] randomizedArray) {
		super(randomizedArray);
	}
	
	protected void sort() {
		T tempStorage;
		
		for (int upperBound = 1; upperBound < baseArray.length; ++upperBound) {
			for (int i = upperBound; i > 0 && comparing(baseArray[i-1],baseArray[i]); --i) {
				++swaps;
				
				tempStorage = baseArray[i-1];
				baseArray[i-1] = baseArray[i];
				baseArray[i] = tempStorage;
			}
		}
		
		for (T key : seenTimes.keySet()) {
			if (seenTimes.get(key) == null) {
				seenTimes.put(key, 0);
			}
		}
	}
}
