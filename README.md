# Assignment 3 — Binary Search Tree & Word Tracker

CPRG-304 group assignment. Group **sonia**: Santiago Pabon, Kaley Wood, Asad Arif, Dylan Dang.

Two deliverables:
1. A generic **Binary Search Tree** implementing the provided `BSTreeADT` interface.
2. A **Word Tracker** CLI that reads text files, tracks every word's filename + line number in the BST, and persists the tree to `repository.ser` between runs.

## Requirements

- **Java 8+** (project targets `JavaSE-1.8` per [.classpath](.classpath))
- **JUnit 4** (already wired via the Eclipse classpath container)
- **Eclipse** *or* **VS Code** with the [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack)

> Submission must compile and run with Eclipse + Java 8 + JUnit 4. Don't use newer language features.

## Project Structure

```
src/
  implementations/   BSTree, BSTreeNode  (our implementations)
  utilities/         BSTreeADT, Iterator (provided — DO NOT MODIFY)
test/
  unitTests/         BSTreeTest          (provided — DO NOT MODIFY)
res/                 test1.txt, test2.txt, test3.txt
```

## Getting Started

**Eclipse:** `File → Import → Existing Projects into Workspace` → select this folder.

**VS Code:** open the folder; the Java extension auto-imports the Eclipse `.classpath`/`.project`. Tests appear in the Testing sidebar.

## Running the Word Tracker

Once built into a JAR:

```
java -jar WordTracker.jar <input.txt> -pf|-pl|-po [-f<output.txt>]
```

| Flag | Output |
|------|--------|
| `-pf` | words + files they appear in |
| `-pl` | words + files + line numbers |
| `-po` | words + files + line numbers + frequency |
| `-f<file>` | redirect output to file (optional) |

Example: `java -jar WordTracker.jar res/test1.txt -po -fout.txt`

## Work Split

| Person | Owns | Status |
|--------|------|--------|
| 1 — Santiago | `BSTreeNode`, BSTree foundation (`getRoot`, `getHeight`, `size`, `isEmpty`, `clear`) | Done |
| 2 — Asad | `contains`, `search`, `add`, `removeMin`, `removeMax` | TODO |
| 3 — Kaley | Iterators (in/pre/post-order), `Word.java`, `WordTracker` core + serialization | TODO |
| 4 — Dylan | `WordTracker` CLI parsing + print reports | TODO |

Order: **1 → (2 & 3 in parallel) → 4**.

## Notes for Contributors

- Compiled `.class` files in `bin/` are git-ignored — don't commit them.
- Run `BSTreeTest` (JUnit 4) before pushing changes to `BSTree`.
- `Word` and `BSTree` must implement `Serializable`; `Word` needs a `serialVersionUID`.
- Normalize words to lowercase and strip punctuation when parsing.

## Submission

Zip as `A3sonia.zip` containing:
1. `WordTracker.jar`
2. The Eclipse project folder
3. Completed `MarkingCriteria_Assignment3.docx`
