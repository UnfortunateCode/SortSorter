package sortingAlgorithms;


/**
 * CocktailShakerSort is front loaded and performs the sort on construction.
 * 
 * Additional processing is used to pull data about the Sort itself.
 * 
 * @author UnfortunateCode
 */
public class CocktailShakerSort<T extends Comparable<T>> extends SortStructure<T>{
	
	/**
	 * Constructor that orders the provided array and sets up
	 * the data.
	 * 
	 * @param randomizedArray array to be sorted
	 */
	public CocktailShakerSort (T[] randomizedArray) {
		super(randomizedArray);
	}
	
	protected void sort() {
		boolean hadChanged = true;
		T tempStorage;
		
		int firstSwap = 1;
		int lastSwap = baseArray.length;
		int startPos = firstSwap - 1;
		int endPos = lastSwap;
		
		while (hadChanged) {
			hadChanged = false;
			
			for (int i = startPos + 1; i < endPos; ++i) {
				if (comparing(baseArray[i-1],baseArray[i])) {
					++swaps;
					
					tempStorage = baseArray[i-1];
					baseArray[i-1] = baseArray[i];
					baseArray[i] = tempStorage;
					
					hadChanged = true;
					lastSwap = i;
				}
			}
			
			if (!hadChanged) {
				break;
			}
			
			endPos = lastSwap;
			
			hadChanged = false;
			
			for (int i = endPos - 1; i > startPos; --i) {
				++comparisons;
				if (comparing(baseArray[i-1],baseArray[i])) {
					++swaps;
					
					tempStorage = baseArray[i-1];
					baseArray[i-1] = baseArray[i];
					baseArray[i] = tempStorage;
					
					hadChanged = true;
					firstSwap = i;
				}
			}
			
			startPos = firstSwap;
			
		}
		
		for (T key : seenTimes.keySet()) {
			if (seenTimes.get(key) == null) {
				seenTimes.put(key, 0);
			}
		}
	}
}
