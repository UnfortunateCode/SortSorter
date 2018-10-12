package sortSorter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sortingAlgorithms.*;

/**
 * Program runs a number of sort algorithms to determine
 * experimentally various performance statistics. Note,
 * speed is not determined, as each algorithm is bloated 
 * with additional data creation.
 * 
 * The reason for this program is to find good sorting 
 * algorithms for humans to perform manually. Thus, a 
 * primary concern is to minimize the number of times
 * a certain value has to be considered. For example,
 * when rating Pokemon, there's not much more to say
 * about Bulbasaur if it has already been seen 800 times
 * previously (i.e., using a naive sort that starts at the
 * beginning for each new data point).
 * 
 *
 * @author UnfortunateCode
 */
public class SortSorter {

	public static void main(String[] args) {
		// create basic structures for reporting
		int arraySize = 900;
		int numTests = 2;
		
		Integer[] randomArray = new Integer[arraySize];
		List<Integer> randomList;
		
		Class<SortStructure>[] classes = new Class[] { BubbleSort.class,
													   CocktailShakerSort.class,
													   OddEvenSort.class,
													   CombSort.class,
													   GnomeSort.class
										};
		SortStructure<Integer>[][] allSorts = new SortStructure[classes.length][numTests];
		
		DecimalFormat format = new DecimalFormat("#0.00");
		
		Object[] params = new Object[1];
		// generate randomization and run each sort algorithm
		for (int testNum = 0; testNum < allSorts[0].length; ++testNum) {
			for (Integer i = 0; i < 900; ++i) {
				randomArray[i] = i;
			}
			
			randomList = Arrays.asList(randomArray);
			Collections.shuffle(randomList);
			randomArray = randomList.toArray(new Integer[0]);
			randomList = null;
			params[0] = randomArray;
			
			for (int sortType = 0; sortType < allSorts.length; ++sortType) {
				
				try {
					System.out.println("Starting " + classes[sortType].getName() + " " + testNum);
					allSorts[sortType][testNum] = (SortStructure<Integer>)(classes[sortType].getConstructor(Comparable[].class).newInstance(params));
					System.out.println("Completed " + classes[sortType].getName() + " " + testNum);
					
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					allSorts[sortType][testNum] = null;
					e.printStackTrace();
				}
			}
		}
		
		
		
		// display results
		for (int sortType = 0; sortType < allSorts.length; ++sortType) {
			System.out.println(classes[sortType].getName());
			double averageAverages = 0;
			double averageStdDev = 0;
			
			for (int testNum = 0; testNum < allSorts[0].length; ++testNum) {
				if (allSorts[sortType][testNum] == null) {
					System.out.print("[" + format.format(averageAverages) + ", " + format.format(averageAverages) + "] ");
				} else {
					System.out.print("[" + format.format(allSorts[sortType][testNum].getAverageSeenTime()) + ", " +
							format.format(allSorts[sortType][testNum].getSeenStandardDeviation()) + "] ");

					averageAverages += allSorts[sortType][testNum].getAverageSeenTime();
					averageStdDev += allSorts[sortType][testNum].getSeenStandardDeviation();
				}
			}
			
			System.out.println();
			System.out.println("[" + (averageAverages / allSorts[sortType].length) +
							   ", " + (averageStdDev / allSorts[sortType].length) + "]");
			
			for (int testNum = 0; testNum < allSorts[0].length; ++testNum) {
				System.out.print("[ ");
				for (int element : allSorts[sortType][testNum].getSortedArray()) {
					System.out.print(element + " ");
				}
				System.out.println();
			}
			System.out.println();
		}
	}

}
