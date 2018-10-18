package sortEvolver;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

/**
 * SortEvolver genetically breeds sorting algorithms to 
 * minimize the number of times the max seen element is
 * compared to other elements. Unlike computationally
 * optimized sorting algorithms, the result of this 
 * process is built for human-based sorting, where the
 * human does not want to continuously see previously
 * sorted items.
 * 
 * The maximum allowable seen-count equals the number 
 * of elements being inserted -1. This is due to a Binary
 * Insertion on a balanced tree requiring every other 
 * element to compare to the first inserted element in
 * the worst case where the first element is consistently
 * the middle element. If no algorithm can beat the Binary
 * Insertion, then the Binary Insertion is the result.
 * 
 * The second mode (TODO) is to additionally minimize 
 * seen density on already sorted elements. It is expected
 * to focus on one element while inserting it into its 
 * place, however, once sorted, consistently seeing it
 * over and over again causes frustration. In this mode,
 * the Binary Insertion (with the first element being
 * the middle element) is the worst case, as the first
 * element is seen for EVERY other insertion.
 * 
 *
 * @author UnfortunateCode
 */
public class SortEvolver {
	public static Random rand = new Random();
	
	LinkedList<LinkedList<Integer>> randLists;
	SortSpecies[] speciesList;
	SortSpecies[] nextSpeciesList;
	SortSpecies[] generationBests;
	
	int generationSize;
	int testSize;
	int testCases;
	int numGenerations;
	
	double generationTotalScore;
	double generationBestScore;
	double[] cumulScore;
	double randScore;
		
	int firstElement;
	int middleElement;
	int lastElement;
	
	
	public SortEvolver(int generationSize, int testSize, int testCases, int numGenerations) {
		
		this.generationSize = generationSize;
		this.testSize = testSize;
		this.testCases = testCases;
		this.numGenerations = numGenerations;
		
		buildLists();
		buildInputs();
	}
	
	public SortEvolver() {
		generationSize = 1000;
		testSize = 900;
		testCases = 5;
		numGenerations = 1000;
		
		buildLists();
		buildInputs();
	}
	
	public void buildLists() {
		speciesList = new SortSpecies[generationSize];
		generationBests = new SortSpecies[numGenerations];
	}

	public void buildInputs() {
		
		randLists = new LinkedList<>();
		LinkedList<Integer> tempList;
		
		for (int testIndex = 0; testIndex < testCases; ++testIndex) {
			tempList = new LinkedList<>();
			
			for (int valueIndex = 0; valueIndex < testSize; ++valueIndex) {
				tempList.add(valueIndex);
			}

			Collections.shuffle(tempList);
			
			randLists.add(tempList);
		}

	}
	
	private SortSpecies getRandomParent() {
		if (speciesList == null || cumulScore == null) {
			return null;
		}
		
		firstElement = 0;
		
		for (lastElement = cumulScore.length - 1; lastElement > 0 && cumulScore[lastElement] <= cumulScore[lastElement - 1]; --lastElement) {
			
		}
		
		if (lastElement == 0) {
			return speciesList[0];
		}
		
		randScore = rand.nextDouble() * cumulScore[lastElement];
		
		while (lastElement > firstElement) {
			middleElement = (lastElement + firstElement) / 2;
			
			if (randScore >= cumulScore[middleElement]) {
				firstElement = middleElement + 1;
			} else { 
				lastElement = middleElement;
			}
		}
		
		return speciesList[firstElement];
	}

	
	public void performTests() {
		boolean isSorted;
		int testNum;
		SortSpecies parent;
		
		for (int speciesIndex = 0; speciesIndex < speciesList.length; ++speciesIndex) {
			speciesList[speciesIndex] = new SortSpecies(testSize, testCases);
		}
		
		
		for (int generation = 0; generation < numGenerations; ++generation) {
			
			generationTotalScore = 0;
			generationBestScore = 0;
			cumulScore = new double[speciesList.length];
			
			for (int speciesIndex = 0; speciesIndex < speciesList.length; ++speciesIndex) {
				
				testNum = 0;
				
				for (LinkedList<Integer> test : randLists) {
					isSorted = true;

					for (Integer element : test) {

						isSorted = speciesList[speciesIndex].insert(element, 0, speciesList[speciesIndex].size(testNum) - 1, testNum);

						if (!isSorted) {
							break;
						}
					}
					
					
					++testNum;
				}
				
				generationTotalScore += speciesList[speciesIndex].getScore();
				cumulScore[speciesIndex] = generationTotalScore;
				
				if (generationBestScore > speciesList[speciesIndex].getScore()) {
					generationBestScore = speciesList[speciesIndex].getScore();
					generationBests[generation] = speciesList[speciesIndex];
				}
				
			}
			
			nextSpeciesList = new SortSpecies[generationSize];
			
			for (int speciesIndex = 0; speciesIndex < nextSpeciesList.length; ++speciesIndex) {
				
				assert speciesList != null;
				assert speciesList[0] != null;
				parent = getRandomParent();
				
			}
		}
		
	}
	
	
	public static void main(String[] args) {

		SortEvolver se = new SortEvolver();
		
		se.performTests();
	}

}
