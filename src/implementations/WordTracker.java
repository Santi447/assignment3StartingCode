/**
 * @author Group sonia: Santiago Pabon, Kaley Wood, Asad Arif, Dylan Dang
 * Southern Alberta Institute of Technology: CPRG 304
 * Assignment 3
 * Created: April 19 2026
 *
 * WordTracker -- reads a text file, breaks it into words, and tracks each
 * word's file name and line number inside a BST. The tree persists between
 * runs via repository.ser so data accumulates across multiple input files.
 *
 * Kaley: loadTree(), saveTree(), scanFile()
 * Dylan: main() CLI parsing, printReport(), output stream handling
 */
package implementations;

import java.io.*;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.util.ArrayList;

public class WordTracker
{
	private static final String REPO_FILE = "repository.ser";
	
	/**
	 * Main entry point.
	 *
	 * Usage: java -jar WordTracker.jar <input.txt> -pf|-pl|-po [-f<output.txt>]
	 *
	 * Dylan -- this is your section. You need to:
	 * 1. Parse args[0] as the input file path
	 * 2. Parse args[1] as the print flag (-pf, -pl, or -po)
	 * 3. Parse optional args[2] for output file redirect (-f<filename>)
	 * 4. Validate arguments and print helpful errors if wrong
	 * 5. Call loadTree(), scanFile(), saveTree() (Kaley's methods)
	 * 6. Set up output stream (System.out or a file)
	 * 7. Call your printReport() method with the tree, flag, and output
	 *
	 * @param args command-line arguments
	 */
	public static void main(String[] args) {
		// Validate minimum argument count
		if (args.length < 2) {
			System.err.println("Usage: java -jar WordTracker.jar <input.txt> -pf|-pl|-po [-f<output.txt>]");
			System.exit(1);
		}

		String inputFilePath = args[0];
		String printFlag     = args[1];

		// Validate print flag
		if (!printFlag.equals("-pf") && !printFlag.equals("-pl") && !printFlag.equals("-po")) {
			System.err.println("Error: print flag must be -pf, -pl, or -po.");
			System.exit(1);
		}

		// Check for optional output file argument (-f<filename>)
		String outputFilePath = null;
		if (args.length >= 3 && args[2].startsWith("-f")) {
			outputFilePath = args[2].substring(2); // strip the "-f" prefix
			if (!outputFilePath.endsWith(".txt")) {
				outputFilePath = outputFilePath + ".txt";
			}
		}

		// Core workflow
		BSTree<Word> wordTree = loadTree();
		scanFile(wordTree, inputFilePath);
		saveTree(wordTree);

		// Set up output stream (console or file)
		PrintStream output = System.out;
		if (outputFilePath != null) {
			try {
				output = new PrintStream(new FileOutputStream(outputFilePath));
				System.out.println("Exporting report to: " + outputFilePath);
			} catch (FileNotFoundException e) {
				System.err.println("Error: could not open output file - " + outputFilePath);
				System.exit(1);
			}
		}

		printReport(wordTree, printFlag, output);

		// if writing to a file, also print to console so the user can see output
		if (outputFilePath != null) {
			printReport(wordTree, printFlag, System.out);
		} else {
			System.out.println("Not exporting file.");
		}
	}
	
	// ======================== Kaley's Section ========================
	
	/**
	 * Tries to load an existing BST from repository.ser.
	 * If the file doesn't exist or can't be read, returns a fresh empty tree.
	 *
	 * @return the deserialized tree, or a new empty tree
	 */
	@SuppressWarnings("unchecked")
	public static BSTree<Word> loadTree() {
		File repoFile = new File(REPO_FILE);
		
		if (repoFile.exists()) {
			try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(repoFile))) {
				BSTree<Word> loadedTree = (BSTree<Word>) in.readObject();
				System.out.println("Loaded existing word tree from " + REPO_FILE);
				return loadedTree;
			} catch (IOException | ClassNotFoundException e) {
				System.err.println("Warning: could not load " + REPO_FILE + ", starting fresh.");
			}
		}
		
		return new BSTree<>();
	}
	
	/**
	 * Serializes the BST out to repository.ser so it persists between runs.
	 *
	 * @param wordTree the tree to save
	 */
	public static void saveTree(BSTree<Word> wordTree) {
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(REPO_FILE))) {
			out.writeObject(wordTree);
		} catch (IOException e) {
			System.err.println("Error: could not save tree to " + REPO_FILE);
			e.printStackTrace();
		}
	}
	
	/**
	 * Reads the input file line by line, splits each line into words,
	 * strips punctuation, converts to lowercase, and either updates an
	 * existing Word node in the tree or inserts a new one.
	 *
	 * @param wordTree      the BST to insert words into
	 * @param inputFilePath path to the text file to scan
	 */
	public static void scanFile(BSTree<Word> wordTree, String inputFilePath) {
		File inputFile = new File(inputFilePath);
		
		if (!inputFile.exists()) {
			System.err.println("Error: input file not found - " + inputFilePath);
			return;
		}
		
		try (Scanner fileReader = new Scanner(inputFile)) {
			int lineNumber = 1;
			
			while (fileReader.hasNextLine()) {
				String line = fileReader.nextLine();
				
				// split on whitespace to get individual tokens
				String[] tokens = line.split("\\s+");
				
				for (String token : tokens) {
					// strip punctuation and convert to lowercase
					String cleaned = token.replaceAll("[^a-zA-Z]", "").toLowerCase();
					
					// skip empty strings that result from punctuation-only tokens
					if (cleaned.isEmpty()) {
						continue;
					}
					
					// create a temporary Word to use as a search key
					Word searchKey = new Word(cleaned, inputFilePath, lineNumber);
					
					// check if this word already exists in the tree
					BSTreeNode<Word> existingNode = wordTree.search(searchKey);
					
					if (existingNode != null) {
						// word already exists -- add this new occurrence
						existingNode.getElement().addOccurrence(inputFilePath, lineNumber);
					} else {
						// brand new word -- insert it into the tree
						wordTree.add(searchKey);
					}
				}
				
				lineNumber++;
			}
		} catch (FileNotFoundException e) {
			System.err.println("Error: could not read file - " + inputFilePath);
		}
	}
	
	// ======================== Dylan's Section ========================
	
	/**
	 * print report method goes here.
	 *
	 * Iterate the BST in-order (alphabetical) and print based on the flag:
	 *   -pf: word + list of files it appears in
	 *   -pl: word + files + line numbers
	 *   -po: word + files + line numbers + frequency
	 *
	 * Use wordTree.inorderIterator() to get the alphabetical traversal.
	 * Word has these getters you'll need:
	 *   getWord(), getFileNames(), getLineNumbers(),
	 *   getFrequency(), getUniqueFileNames()
	 *
	 * @param wordTree  the BST containing all tracked words
	 * @param printFlag the report type (-pf, -pl, or -po)
	 * @param output    where to write (System.out or a file stream)
	 */

	public static void printReport(BSTree<Word> wordTree, String printFlag, PrintStream output) {
		switch (printFlag) {
			case "-pf": output.println("Displaying -pf format"); break;
			case "-pl": output.println("Displaying -pl format"); break;
			case "-po": output.println("Displaying -po format"); break;
		}

		utilities.Iterator<Word> it = wordTree.inorderIterator();

		while (it.hasNext()) {
			Word word = it.next();
			StringBuilder sb = new StringBuilder();
			sb.append("Key : ===").append(word.getWord()).append("===  ");

			switch (printFlag) {

				case "-pf": {
					// unique files only
					ArrayList<String> uniqueFiles = word.getUniqueFileNames();
					for (String file : uniqueFiles) {
						sb.append("found in file: ").append(file).append(", ");
					}
					break;
				}

				case "-pl": {
					// group line numbers per file
					sb.append(buildFileLineString(word));
					break;
				}

				case "-po": {
					// file + lines + frequency
					sb.append("number of entries: ").append(word.getFrequency()).append(" ");
					sb.append(buildFileLineString(word));
					break;
				}
			}

			output.println(sb.toString());
		}
	}

	/**
	 * Builds a "found in file: X on lines: 1,2,3, found in file: Y on lines: 4,5,"
	 * string by grouping occurrences per file in the order they were encountered.
	 *
	 * @param word the Word whose occurrences to format
	 * @return formatted file-and-lines string
	 */
	private static String buildFileLineString(Word word) {
		ArrayList<String> fileNames   = word.getFileNames();
		ArrayList<Integer> lineNumbers = word.getLineNumbers();

		StringBuilder sb = new StringBuilder();

		// collect unique file names preserving order
		ArrayList<String> seen = new ArrayList<>();
		for (String f : fileNames) {
			if (!seen.contains(f)) seen.add(f);
		}

		for (String file : seen) {
			sb.append("found in file: ").append(file).append(" on lines: ");
			for (int i = 0; i < fileNames.size(); i++) {
				if (fileNames.get(i).equals(file)) {
					sb.append(lineNumbers.get(i)).append(",");
				}
			}
			sb.append(" ");
		}
		return sb.toString();
	}

}