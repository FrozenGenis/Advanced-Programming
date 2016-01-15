package setCalculator;

import java.util.Scanner;
import java.io.PrintStream;
import java.util.regex.Pattern;

public class Application {

    public static final String REQUEST_FOR_FIRST_COLLECTION = "Give the first collection: ";
    public static final String REQUEST_FOR_SECOND_COLLECTION = "Give the second collection: ";
    public static final String BASE_ANSWER_DIFFERENCE = "difference\t\t=";
    public static final String BASE_ANSWER_INTERSECTION = "intersection\t=";
    public static final String BASE_ANSWER_UNION = "union\t\t\t=";
    public static final String BASE_ANSWER_SYM_DIFF = "sym. diff\t\t=";
    public static final String EMPTY_STRING = "";
    public static final String SPACE = " ";
    public static final char COLLECTION_START = '{';
    public static final char COLLECTION_END = '}';
	public static final String INVALID_IDENTIFIER_MESSAGE = "The input contains an invalid identifier. The first letter of an identifier must be a letter. Please try again.";
    public static final String INVALID_COLLECTION_MESSAGE = "Invalid collection. The collection should not exceed more than 10 identifiers.";
    public static final String END_OF_LINE_NOT_FOUND_MESSAGE = "End of line not found. No more input allowed after '}'.";

	private PrintStream out;

	public Application() {
		out = new PrintStream(System.out);
	}

    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {
        Scanner in = new Scanner(System.in).useDelimiter(EMPTY_STRING);

        Collection firstCollection;
        Collection secondCollection;

        //noinspection InfiniteLoopStatement
        while (true) {
            firstCollection = readInput(REQUEST_FOR_FIRST_COLLECTION, in);
            secondCollection = readInput(REQUEST_FOR_SECOND_COLLECTION, in);

            processOutput(firstCollection, secondCollection);
        }
    }

    private Collection readInput(String request, Scanner input) {
        while (true) {
            try {
                out.print(request);
                checkIfTerminated(input);

                String line = input.nextLine();
                if (checkIfEmpty(line)) continue;

                Scanner collectionScanner = new Scanner(line).useDelimiter(EMPTY_STRING);
                return readCollection(collectionScanner);
            } catch(Exception e) {
                out.println(e.getMessage());
            }
        }
    }

    private void checkIfTerminated(Scanner input) {
        if (!input.hasNext()) {
            System.exit(0);
        }
    }

    private boolean checkIfEmpty(String input) {
        input = input.trim();
        return input.isEmpty();
    }

	private Collection readCollection(Scanner input) throws Exception {
        trimSpaces(input);

		checkCharacter(input, COLLECTION_START);
		nextChar(input);

	    Collection result = readIdentifiers(input);

	    checkCharacter(input, COLLECTION_END);
		nextChar(input);

	    checkEndOfLine(input);

	    return result;
	}

	private void checkCharacter(Scanner input, char c) throws Exception {
	    if (!nextCharIs(input, c)) {
            throw new Exception(String.format("\'%c\' is missing.", c));
        }
	}

	private boolean nextCharIs(Scanner in, char c) {
		return in.hasNext(Pattern.quote(String.format("%c", c)));
	}

	private char nextChar(Scanner in) {
		return in.next().charAt(0);
	}

	private Collection readIdentifiers(Scanner input) throws Exception {
		Collection result = new Collection();
		Identifier identifier;

		while (!nextCharIs(input, COLLECTION_END)) {
            trimSpaces(input);

            if (!input.hasNext()) {
                throw new Exception(String.format("\'%c\' %s", COLLECTION_END, "is missing."));
            }

            if (!nextCharIsLetter(input)) {
				throw new Exception(INVALID_IDENTIFIER_MESSAGE);
			}

			identifier = new Identifier();
            identifier.init(nextChar(input));

			while (nextCharIsLetter(input) || nextCharIsDigit(input)) {
				identifier.addChar(nextChar(input));
			}

            result.add(identifier);

            if (result.size() > 10) {
                throw new Exception(INVALID_COLLECTION_MESSAGE);
            }

            trimSpaces(input);
		}

		return result;
	}

    private void trimSpaces(Scanner input) {
        while (input.hasNext(SPACE)) {
            nextChar(input);
        }
    }

    private boolean nextCharIsLetter(Scanner in) {
		return in.hasNext("[a-zA-Z]");
	}

    private boolean nextCharIsDigit(Scanner in) {
        return in.hasNext("[0-9]");
    }

	private void checkEndOfLine(Scanner input) throws Exception {
        if (input.hasNext()) {
            throw new Exception(END_OF_LINE_NOT_FOUND_MESSAGE);
        }
    }

	private void processOutput(Collection firstCollection, Collection secondCollection) {
        try {
            out.printf("%s %s\n", BASE_ANSWER_DIFFERENCE, firstCollection.calculateDifference(secondCollection).toString());
            out.printf("%s %s\n", BASE_ANSWER_INTERSECTION, firstCollection.calculateIntersection(secondCollection).toString());
            out.printf("%s %s\n", BASE_ANSWER_UNION, firstCollection.calculateUnion(secondCollection).toString());
            out.printf("%s %s\n", BASE_ANSWER_SYM_DIFF, firstCollection.calculateSymDiff(secondCollection).toString());
        } catch (Exception e) {
            out.println(e.getMessage());
        }
	}

}