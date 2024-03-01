package cs310;

import java.util.*;
import java.io.*;


public class SpellChecker {

	/******* PRINTING FOR TESTING ******/
	private void printDictionary() {
		Iterator<String> dictIterator = dictionary.iterator();
		while (dictIterator.hasNext()) {
			System.out.println(dictIterator.next());
		}
	}

	private void printMissSpelled() {
		Set<String> words = missSpelledWords.keySet();
		Iterator<String> wordsIterator = words.iterator();
		while (wordsIterator.hasNext()) {
			String word = wordsIterator.next();
			System.out.print(word + " ");
			List<Integer> lineNum = missSpelledWords.get(word);
			Iterator<Integer> lineNumIterator = lineNum.iterator();
			String num;
			while (lineNumIterator.hasNext()) {
				num = Integer.toString(lineNumIterator.next());
				System.out.printf("%s ", num);
			}
			System.out.println();
		}
	}

	private void printAddOne() {
		Set<String> s = addOne.keySet();
		for (String x : s) {
			Set<String> y = addOne.get(x);
			for (String z : y) {
				System.out.println(z);
			}
		}
	}

	private void printStringSet(Set<String> set) {									// O(1)
		for (String s : set) {
			System.out.println(s);
		}
	}

	/*private void printAddOneChar() {
		Set<String> aoSet = addOne.keySet();
		for (String word : aoSet) {
			List<String> l = addOne.get(word);
			Iterator<String> aoIter = aoSet.iterator();
			while (aoIter.hasNext()) {
				System.out.print(aoIter.next() + " ");
			}
			System.out.println();
		}
	}*/



	// miss spelled words
	private Map<String, List<Integer>> missSpelledWords;
	// dictionary
	private Set<String> dictionary;

	private Map<String, Set<String>> addOne;

	public SpellChecker(String fileName) throws FileNotFoundException {					// O(N)		
		dictionary = new TreeSet<String>();
		// fill in dictionary
		loadData(fileName);
	}

	// puts correct words into dictionary from a file
	private void loadData(String fileName) throws FileNotFoundException {				// O(N)
		Scanner fScanner = new Scanner(new File(fileName));
		while (fScanner.hasNextLine()) {
			String word = fScanner.nextLine().replaceAll("\\s+", "");
			dictionary.add(word);
		}
		fScanner.close();
		// printDictionary();
	}


	// gets miss spelled words from a file
	public void checkSpelling(String fileName) throws FileNotFoundException {			//  2 * O(N) + 3 * O(1)
		// go through file
		int lineNum = 0;
		missSpelledWords = new TreeMap<String, List<Integer>>();
		Scanner fScanner = new Scanner(new File(fileName));
		while (fScanner.hasNextLine()) {
			String line = fScanner.nextLine();
			String[] splitLine = line.split(" ");
			// go through all the words in file and compare with dictionary
			for (int i = 0; i < splitLine.length; i++) {
				if (!dictionary.contains(splitLine[i])) {
					// insert the word and intialize it's line num list
					missSpelledWords.put(splitLine[i], new LinkedList<Integer>());
				}
			}
		}
		fScanner.close();
		lineNumbers(fileName);
		// gives related dictionary words for miss spelled ones
		checkModified();
		printMissSpelled();
	}

	private void lineNumbers(String fileName) throws FileNotFoundException {			// O(N)
		int lineNum = 0;
		Scanner fScanner = new Scanner(new File(fileName));
		while (fScanner.hasNextLine()) {
			String line = fScanner.nextLine();
			lineNum++;
			String[] splitLine = line.split(" ");
			// go through all the words in file and put their line numbers
			for (int i = 0; i < splitLine.length; i++) {
				if (!dictionary.contains(splitLine[i])) {
					List<Integer> list = missSpelledWords.get(splitLine[i]);
					list.add(lineNum);
				}
			}
		}
		fScanner.close();
	}

	/* adds one char, removes one char, exchange adjacent and then adds 
	   real words next to miss spelled ones */
	private void checkModified() {														// O(1)
		Set<String> mswSet = missSpelledWords.keySet();
		Set<String> addOneToWords = new TreeSet<String>();
		for (String word : mswSet) {
			addOneToWords.add(addOneChar(word, word.length()));
		}
		/*printStringSet(addOneToWords);*/
		/*printMissSpelled();*/
	}

	private String addOneChar(String word, int len) {									// O(1)
		StringBuilder strBuilder = new StringBuilder();
		StringBuilder updatedWord = new StringBuilder();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			for (int i = 0; i < len + 1; i++) {
				strBuilder.append(word);
				strBuilder.insert(i, ch);
				if (dictionary.contains(strBuilder.toString())) {
					updatedWord.append(strBuilder.toString());
					updatedWord.append(" ");
				}
				strBuilder = new StringBuilder();
			}
		}
		return updatedWord.toString();
	}

	
	public static void main( String [ ] args ) throws FileNotFoundException {
		SpellChecker spellChecker = new SpellChecker(args[1]);
		spellChecker.checkSpelling(args[0]);
	}
}