package assignment3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Iterator;
import java.util.Scanner;

public class Application {

	public static final String CASE_SENSITIVE = "-i";
	public static final String DESCENDING_SORT = "-d";
	public static final String EMPTY_STRING = "";
	public static final String IS_NOT_ALPHANUMERIC = "[^a-zA-Z0-9]";
	public static final String IS_LETTER = "[a-zA-Z]";

	public static final String NO_FILES_GIVEN_EXCEPTION = "Error. No files were given.";
	public static final String READING_FILE_EXCEPTION = "There was an error while trying to read the file.";
	public static final String EXCEPTION = "%s%n";

	private PrintStream out;
	private BinarySearchTree<Identifier> tree;

	private boolean caseInsensitive;
	private boolean descendingSort;

	public Application() {
		out = new PrintStream(System.out);
		tree = new BinarySearchTree<>();

		caseInsensitive = false;
		descendingSort = false;
	}

	public static void main(String[] argv) {
		new Application().start(argv);
	}

	public void start(String[] arguments) {
		int firstFileIndex;

		try {
			firstFileIndex = readArguments(arguments);
			readFiles(arguments, firstFileIndex);
			printTree();
		} catch (APException e) {
			out.printf(EXCEPTION, e);
			System.exit(1);
		}
	}

	private int readArguments(String[] arguments) throws APException {
		for (int i = 0; i < arguments.length; i++) {
			switch (arguments[i]) {
				case CASE_SENSITIVE:
					caseInsensitive = true;
					break;
				case DESCENDING_SORT:
					descendingSort = true;
					break;
				default:
					return i;
			}
		}

		throw new APException(NO_FILES_GIVEN_EXCEPTION);
	}

	private void readFiles(String[] files, int index) throws APException {
		Scanner file;

		for (int i = index; i < files.length; i++) {
			try {
				file = new Scanner(new File(files[i]));
				readIdentifiers(file);
			} catch (FileNotFoundException e) {
				throw new APException(READING_FILE_EXCEPTION);
			}
		}
	}

	private void readIdentifiers(Scanner in) {
		in.useDelimiter(IS_NOT_ALPHANUMERIC);

		while (in.hasNext()) {
			Scanner identifierScanner = new Scanner(in.next()).useDelimiter(EMPTY_STRING);

			if (nextCharIsLetter(identifierScanner)) {
				processIdentifier(identifierScanner);
			}
		}
	}

	private boolean nextCharIsLetter(Scanner in) {
		return in.hasNext(IS_LETTER);
	}

	private void processIdentifier(Scanner identifierScanner) {
		Identifier identifier = new Identifier();
		identifier.init(readChar(identifierScanner));

		while (identifierScanner.hasNext()) {
			identifier.addChar(readChar(identifierScanner));
		}

		if (!tree.find(identifier)) {
			tree.add(identifier);
		} else {
			tree.remove(identifier);
		}
	}

	private char readChar(Scanner in) {
		if (caseInsensitive) {
			return Character.toLowerCase(in.next().charAt(0));
		} else {
			return in.next().charAt(0);
		}
	}

	private void printTree() {
		Iterator identifierIterator = descendingSort ? tree.descendingIterator() : tree.ascendingIterator();

		while (identifierIterator.hasNext()) {
			printIdentifier((Identifier) identifierIterator.next());
		}
	}

	private void printIdentifier(Identifier identifier) {
		out.println(identifier.toString());
	}

}
