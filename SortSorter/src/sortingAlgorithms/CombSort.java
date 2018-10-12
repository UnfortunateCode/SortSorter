package sortingAlgorithms;


/**
 * CombSort is front loaded and performs the sort on construction.
 * 
 * Additional processing is used to pull data about the Sort itself.
 * 
 * @author UnfortunateCode
 */
public class CombSort<T extends Comparable<T>> extends SortStructure<T>{
	
	/**
	 * Constructor that orders the provided array and sets up
	 * the data.
	 * 
	 * @param randomizedArray array to be sorted
	 */
	public CombSort (T[] randomizedArray) {
		super(randomizedArray);
	}
	
	protected void sort() {
		boolean hadChanged = true;
		T tempStorage;
		
		int gap = baseArray.length;
		float shrink = 1.3f;
		
		
		while (hadChanged) {
			gap = Math.max((int)Math.floor((double)gap / shrink), 1);
			if (gap > 1) {
				hadChanged = true;
			} else {
				hadChanged = false;
			}
			
			for (int i = 0; i + gap < baseArray.length; ++i) {
				if (comparing(baseArray[i],baseArray[i + gap])) {
					++swaps;
					
					tempStorage = baseArray[i];
					baseArray[i] = baseArray[i + gap];
					baseArray[i + gap] = tempStorage;
					
					hadChanged = true;
				}
			}
		}
		
		for (T key : seenTimes.keySet()) {
			if (seenTimes.get(key) == null) {
				seenTimes.put(key, 0);
			}
		}
	}
}
