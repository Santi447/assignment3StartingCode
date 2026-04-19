/**
 * @author Group sonia: Santiago Pabon, Kaley Wood, Asad Arif, Dylan Dang
 * Southern Alberta Institute of Technology: CPRG 304
 * Assignment 3
 * Created: April 19 2026
 *
 * Word -- represents a single word found during text file parsing.
 * Tracks every file and line number where the word appeared so the
 * WordTracker can report on word locations across multiple files.
 */
package implementations;

import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Comparable<Word>, Serializable {
    private static final long serialVersionUID = 1L;

    private String word;
    private ArrayList<String> fileNames;
    private ArrayList<Integer> lineNumbers;

    /**
     * Creates a new Word with its first occurrence already recorded.
     *
     * @param word       the word string (should already be lowercase, no
     *                   punctuation)
     * @param fileName   the file where this word was first found
     * @param lineNumber the line number of the first occurrence
     */
    public Word(String word, String fileName, int lineNumber) {
        this.word = word;
        this.fileNames = new ArrayList<>();
        this.lineNumbers = new ArrayList<>();

        // record the first occurrence right away
        this.fileNames.add(fileName);
        this.lineNumbers.add(lineNumber);
    }

    /**
     * Records another occurrence of this word in a file at a specific line.
     *
     * @param fileName   the file where this occurrence was found
     * @param lineNumber the line number of this occurrence
     */
    public void addOccurrence(String fileName, int lineNumber) {
        this.fileNames.add(fileName);
        this.lineNumbers.add(lineNumber);
    }

    /**
     * Returns the word string.
     *
     * @return the word this object tracks
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Returns the list of file names where this word appeared.
     * Each entry lines up with the same index in getLineNumbers().
     *
     * @return the list of file names
     */
    public ArrayList<String> getFileNames() {
        return this.fileNames;
    }

    /**
     * Returns the list of line numbers where this word appeared.
     * Each entry lines up with the same index in getFileNames().
     *
     * @return the list of line numbers
     */
    public ArrayList<Integer> getLineNumbers() {
        return this.lineNumbers;
    }

    /**
     * Returns how many times this word has been found across all files.
     *
     * @return the total number of occurrences
     */
    public int getFrequency() {
        return this.fileNames.size();
    }

    /**
     * Returns a list of unique file names where this word appeared.
     * Useful for the -pf report that only needs file names, not every occurrence.
     *
     * @return a list of distinct file names
     */
    public ArrayList<String> getUniqueFileNames() {
        ArrayList<String> uniqueFiles = new ArrayList<>();

        for (String fileName : this.fileNames) {
            if (!uniqueFiles.contains(fileName)) {
                uniqueFiles.add(fileName);
            }
        }
        return uniqueFiles;
    }

    /**
     * Compares this word to another alphabetically.
     * This is what the BST uses to decide left vs right placement.
     *
     * @param other the word to compare against
     * @return negative if this comes before other, positive if after, 0 if equal
     */
    @Override
    public int compareTo(Word other) {
        return this.word.compareTo(other.word);
    }

    /**
     * Returns the word string for easy printing.
     *
     * @return the word
     */
    @Override
    public String toString() {
        return this.word;
    }
}