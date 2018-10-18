package sortEvolver;

public class SortSpecies {
	private int[] score;
	private Integer[][] sortedTests;
	private int[] numFilled;
	
	private TreeArithmatic comparisonPicker;
	private TreeArithmatic largerValuePicker;
	private TreeArithmatic smallerValuePicker;
	
	private int pickedNum;
	private Integer tempValue;
	private boolean isSorted = true;
	
	/**
	 * 
	 * 
	 * Score is based on 2 times the number of values that should be inserted (N).
	 * The score, number of times the most seen element is seen, is guaranteed to 
	 * be at least log2(N). In a straight Binary Sort, the first element is seen
	 * N-1 times, meaning a better algorithm should see the max seen element less than
	 * N times. The second set of N scores is for algorithms that fail to properly sort.
	 * An algorithm that fails to sort on the 3rd element (score 2N-3) is less fit than
	 * an algorithm that fails to sort on the 50th element (score 2N-50). No failed 
	 * sort will have a score of less than N. Likewise, the Binary Sort has a score
	 * of N-1. Note, the rebalancing Binary Sort can have a score of N-1 if the first
	 * element entered is the midpoint, and all other elements alternate which side
	 * they fall on.
	 * 
	 * Thus, the genetic program should find the Binary Sort in the worst case:
	 * comparisonPicker(f,l,0) -> (f+l)/2
	 * largerValuePicker(f,l,p) -> p
	 * smallerValuePicker(f,l,p) -> p+1
	 * 
	 * If a better algorithm can be found, then the next scoring method would be based on
	 * worst case density of views. That is, if an element is tested against in many
	 * insertions in a row, that would be worse than if it is seen the same number of times,
	 * but more spread out in the insertions.
	 * 
	 * @param testSize
	 * @param testCases
	 */
	public SortSpecies (int testSize, int testCases) {
		
		score = new int[testCases];
		numFilled = new int[testCases];
		
		sortedTests = new Integer[testCases][testSize];
		
		for (int testNum = 0; testNum < score.length; ++testNum) {
			score[testNum] = 2*testSize - (int)Math.floor(Math.log(testSize) / Math.log(2));
			numFilled[testNum] = 0;
		}
		
		
	}
	
	public SortSpecies(SortSpecies ss) {
		score = new int[ss.score.length];
		numFilled = new int[ss.score.length];
		
		sortedTests = new Integer[ss.sortedTests.length][ss.sortedTests[0].length];
		
		for (int testNum = 0; testNum < score.length; ++testNum) {
			score[testNum] = 2*sortedTests[0].length;
			numFilled[testNum] = 0;
		}
		
		comparisonPicker = ss.comparisonPicker.clone();
		largerValuePicker = ss.largerValuePicker.clone();
		smallerValuePicker = ss.smallerValuePicker.clone();
	}

	public int size(int testNum) {
		return numFilled[testNum];
	}

	public boolean insert(int element, int firstAvail, int lastAvail, int testNum) {
		if (numFilled[testNum] == 0) { 
			sortedTests[testNum][0] = element;
			return true;
		}
		
		if (numFilled[testNum] >= sortedTests[0].length) {
			return false;
		}
		
		if (lastAvail < firstAvail) {
			if (firstAvail < 0 || firstAvail > sortedTests[testNum].length - 1) {
				return false;
			}
			
			boolean isSorted = true;
			++numFilled[testNum];
			for (int valueIndex = firstAvail; valueIndex < numFilled[testNum]; ++valueIndex) {
				tempValue = sortedTests[testNum][valueIndex];
				sortedTests[testNum][valueIndex] = element;
				element = tempValue;
				
				if (sortedTests[testNum][valueIndex] > element) {
					isSorted = false;
				}
			}
			
			if (!isSorted) {
				score[testNum] -= numFilled[testNum];
			}
			
			return isSorted;			
		}
		
		pickedNum = comparisonPicker.solveFor(firstAvail, lastAvail, 0);
		
		if (element < sortedTests[testNum][pickedNum]) {
			return insert(element, firstAvail, Math.min(lastAvail - 1, Math.max(firstAvail, largerValuePicker.solveFor(firstAvail, lastAvail, pickedNum))), testNum);
		} else {
			return insert(element, Math.min(lastAvail + 1, Math.max(firstAvail + 1, smallerValuePicker.solveFor(firstAvail, lastAvail, pickedNum))), lastAvail, testNum);
		}
	}

	public double getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
}
