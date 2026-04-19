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
import java.util.Scanner;

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
		// TODO: CLI parsing and report output go here
		// 
		// Here's the flow for the core logic you'll call:
		//   BSTree<Word> wordTree = loadTree();
		//   scanFile(wordTree, inputFilePath);
		//   saveTree(wordTree);
		//   printReport(wordTree, printFlag, output);
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
					String cleaned = token.replaceAll("[^a-zA-Z']", "").toLowerCase();
					
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
	// TODO: public static void printReport(BSTree<Word> wordTree, String printFlag, PrintStream output

}