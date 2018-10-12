package sortingAlgorithms;

/**
 * OddEvenSort is front loaded and performs the sort on construction.
 * 
 * Additional processing is used to pull data about the Sort itself.
 * 
 * @author UnfortunateCode
 */
public class OddEvenSort<T extends Comparable<T>> extends SortStructure<T>{
	
	/**
	 * Constructor that orders the provided array and sets up
	 * the data.
	 * 
	 * @param randomizedArray array to be sorted
	 */
	public OddEvenSort (T[] randomizedArray) {
		super(randomizedArray);
	}
	
	protected void sort() {
		boolean hadChanged = true;
		T tempStorage;
		
		int lastSwap = baseArray.length;
		int endPos = lastSwap;
		
		while (hadChanged) {
			hadChanged = false;
			
			for (int i = 2; i < endPos; i+=2) {
				if (comparing(baseArray[i-1],baseArray[i])) {
					++swaps;
					
					tempStorage = baseArray[i-1];
					baseArray[i-1] = baseArray[i];
					baseArray[i] = tempStorage;
					
					hadChanged = true;
					//lastSwap = i;
				}
			}
			//endPos = lastSwap;
			
			for (int i = 1; i < endPos; i+=2) {
				if (comparing(baseArray[i-1],baseArray[i])) {
					++swaps;
					
					tempStorage = baseArray[i-1];
					baseArray[i-1] = baseArray[i];
					baseArray[i] = tempStorage;
					
					hadChanged = true;
					//lastSwap = i;
				}
			}
			//endPos = lastSwap;
		}
		
		for (T key : seenTimes.keySet()) {
			if (seenTimes.get(key) == null) {
				seenTimes.put(key, 0);
			}
		}
	}
}
