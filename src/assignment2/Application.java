package assignment2;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application {

	/**
	 * Current issues (3):
	 *
	 * Nasty check: Charlie
	 *
	 * / The next line contains two assignments, and is not a statement according to the syntax
	 * Alfa = {1, 10, 100, 1000}          Delta = {3, 4}
	 *
	 * Delta = Alfa        + Alfa + {     }*Bravo*(Alfa + Bravo)
	 * / Delta is now equal to Alfa
	 */

	public static final String EMPTY_STRING = "";
	public static final char SPACE = ' ';
	public static final char ZERO = '0';
	public static final String LETTER = "[a-zA-Z]";
	public static final String DIGIT = "[0-9]";
	public static final String POSITIVE_DIGIT = "[1-9]";
	public static final String ADDITIVE_OPERATOR = "[+|-]";
	public static final String MULTIPLICATIVE_OPERATOR = "[*]";

	public static final char START_STATEMENT = '?';
	public static final String STATEMENT = "%s%n";
	public static final char START_COMMENT = '/';
	public static final char ASSIGNMENT = '=';
	public static final char START_SET = '{';
	public static final char END_SET = '}';
	public static final char START_COMPLEX_FACTOR = '(';
	public static final char END_COMPLEX_FACTOR = ')';
	public static final char NATURAL_NUMBERS_SEPARATOR = ',';

	public static final char UNION = '+';
	public static final char INTERSECTION = '*';
	public static final char COMPLEMENT = '-';
	public static final char SYMMETRIC_DIFFERENCE = '|';

	public static final String END_OF_LINE_NOT_FOUND_EXCEPTION = "End of line not found.";
	public static final String IDENTIFIER_EXCEPTION = "The first character of the identifier must be a letter but was not found.";
	public static final String ASSIGNMENT_NOT_FOUND_EXCEPTION = "'=' expected but not found.";
	public static final String FACTOR_EXCEPTION = "Invalid factor.";
	public static final String POSITIVE_NUMBER_NOT_FOUND_EXCEPTION = "Positive number not found.";
	public static final String KEY_NOT_FOUND_EXCEPTION = "Map does not contain the specified key.";
	public static final String UNKNOWN_COMMAND_EXCEPTION = "The command [%c] is not available.";
	public static final String INVALID_INPUT_EXCEPTION = "Invalid input. NaturalNumber should only contain digits.";
	public static final String NUMBER_STARTS_WITH_ZERO_EXCEPTION = "Number can't start with a 0 (eg. 05 is forbidden).";
	public static final String CHAR_MISSING_EXCEPTION = "\'%c\' is missing.";

	private PrintStream out;

	private Map<Identifier, Set<NaturalNumber>> assignments;

	public Application() {
		out = new PrintStream(System.out);

		assignments = new Map<>();
	}

	public static void main(String[] args) {
		new Application().start();
	}

	private void start() {
		Scanner in = new Scanner(System.in).useDelimiter(EMPTY_STRING);
		String program;
		Scanner programScanner;

		//noinspection InfiniteLoopStatement
		while (true) {
			checkIfTerminated(in);

			trimSpaces(in);
			program = in.nextLine();
			if (program.isEmpty()) continue;

			try {
				programScanner = new Scanner(program).useDelimiter(EMPTY_STRING);
				readProgram(programScanner);
			} catch (APException e) {
				out.println(e);
			}
		}
	}

	private void checkIfTerminated(Scanner input) {
		if (!input.hasNext()) {
			System.exit(0);
		}
	}

	private void readProgram(Scanner input) throws APException {
		while (input.hasNext()) {
			readStatement(input);
		}
	}

	private void readStatement(Scanner input) throws APException {
		trimSpaces(input);

		if (nextCharIs(input, START_STATEMENT)) {
			printStatement(input);
		} else if (nextCharIs(input, START_COMMENT)) {
			readComment(input);
		} else if (nextCharIsLetter(input)) {
			executeAssignment(input);
		} else {
			throw new APException(String.format(UNKNOWN_COMMAND_EXCEPTION, nextChar(input)));
		}
	}

	private void printStatement(Scanner input) throws APException {
		nextChar(input);
		trimSpaces(input);

		Set<NaturalNumber> result = readExpression(input);

		trimSpaces(input);
		checkEndOfLine(input);

		out.printf(STATEMENT, result.toString());
	}

	private void checkEndOfLine(Scanner lineScanner) throws APException {
		if (lineScanner.hasNext()) {
			throw new APException(END_OF_LINE_NOT_FOUND_EXCEPTION);
		}
	}

	private void readComment(Scanner input) throws APException {
		input.nextLine();
	}

	private void executeAssignment(Scanner input) throws APException {
		Identifier identifier = readIdentifier(input);
		trimSpaces(input);

		if (!nextCharIs(input, ASSIGNMENT)) {
			throw new APException(ASSIGNMENT_NOT_FOUND_EXCEPTION);
		}

		nextChar(input);
		trimSpaces(input);

		Set<NaturalNumber> expression = readExpression(input);

		assignments.put(identifier, expression);
	}

	private Identifier readIdentifier(Scanner input) throws APException {
		if (!nextCharIsLetter(input)) {
			throw new APException(IDENTIFIER_EXCEPTION);
		}

		Identifier result = new Identifier();
		result.init(nextChar(input));

		while (nextCharIsLetter(input) || nextCharIsDigit(input)) {
			result.addChar(nextChar(input));
		}

		return result;
	}

	private Set<NaturalNumber> readExpression(Scanner input) throws APException {
		Set<NaturalNumber> result = readTerm(input);
		trimSpaces(input);

		while (nextCharIsAdditiveOperator(input)) {
			char operator = nextChar(input);
			trimSpaces(input);
			result = calculate(result, operator, readTerm(input));
			trimSpaces(input);
		}

		return result;
	}

	private Set<NaturalNumber> readTerm(Scanner input) throws APException {
		Set<NaturalNumber> result = readFactor(input);
		trimSpaces(input);

		while (nextCharIsMultiplicativeOperator(input)) {
			char operator = nextChar(input);
			trimSpaces(input);
			result = calculate(result, operator, readFactor(input));
			trimSpaces(input);
		}

		return result;
	}

	private Set<NaturalNumber> calculate(Set<NaturalNumber> set1, char operator, Set<NaturalNumber> set2) {
		Set<NaturalNumber> result = new Set<>();

		switch (operator) {
			case UNION:
				result = set1.union(set2);
				break;
			case INTERSECTION:
				result = set1.intersection(set2);
				break;
			case COMPLEMENT:
				result = set1.complement(set2);
				break;
			case SYMMETRIC_DIFFERENCE:
				result = set1.symmetricDifference(set2);
				break;
			default:
				// should never be executed
		}

		return result;
	}

	private Set<NaturalNumber> readFactor(Scanner input) throws APException {
		Set<NaturalNumber> result;

		if (nextCharIsLetter(input)) {
			result = getSetByIdentifier(input);
		} else if (nextCharIs(input, START_COMPLEX_FACTOR)) {
			result = readComplexFactor(input);
		} else if (nextCharIs(input, START_SET)) {
			result = readSet(input);
		} else { // invalid syntax
			throw new APException(FACTOR_EXCEPTION);
		}

		return result;
	}

	private Set<NaturalNumber> getSetByIdentifier(Scanner input) throws APException {
		Identifier identifier = readIdentifier(input);

		if (!assignments.contains(identifier)) {
			throw new APException(KEY_NOT_FOUND_EXCEPTION);
		}

		return assignments.get(identifier);
	}

	private Set<NaturalNumber> readComplexFactor(Scanner input) throws APException {
		checkCharacter(input, START_COMPLEX_FACTOR);
		nextChar(input);
		trimSpaces(input);

		Set<NaturalNumber> result = readExpression(input);

		trimSpaces(input);
		checkCharacter(input, END_COMPLEX_FACTOR);
		nextChar(input);

		return result;
	}

	private Set<NaturalNumber> readSet(Scanner input) throws APException {
		checkCharacter(input, START_SET);
		nextChar(input);
		trimSpaces(input);

		Set<NaturalNumber> result = readRowNaturalNumbers(input);

		trimSpaces(input);
		checkCharacter(input, END_SET);
		nextChar(input);

		return result;
	}

	private Set<NaturalNumber> readRowNaturalNumbers(Scanner input) throws APException {
		Set<NaturalNumber> result = new Set<>();

		if (nextCharIs(input, END_SET)) {
			return result;
		}

		NaturalNumber number;
		number = readNaturalNumber(input);
		result.add(number);
		trimSpaces(input);

		while (nextCharIs(input, NATURAL_NUMBERS_SEPARATOR)) {
			nextChar(input);
			trimSpaces(input);

			number = readNaturalNumber(input);
			result.add(number);

			trimSpaces(input);
		}

		return result;
	}

	private NaturalNumber readNaturalNumber(Scanner input) throws APException {
		NaturalNumber result;

		if (nextCharIsPositiveDigit(input)) {
			result = readPositiveNumber(input);
		} else if (nextCharIs(input, ZERO)) {
			result = new NaturalNumber();
			result.init(readZero(input));

			if (nextCharIsDigit(input)) {
				throw new APException(NUMBER_STARTS_WITH_ZERO_EXCEPTION);
			}
		} else {
			throw new APException(INVALID_INPUT_EXCEPTION);
		}

		return result;
	}

	private NaturalNumber readPositiveNumber(Scanner input) throws APException {
		NaturalNumber result = new NaturalNumber();
		result.init(readNotZero(input));

		while (nextCharIsDigit(input)) {
			result.addDigit(readNumber(input));
		}

		return result;
	}

	private char readNumber(Scanner input) throws APException {
		char result;

		if (nextCharIs(input, ZERO)) {
			result = readZero(input);
		} else { // next char is [1-9]
			result = readNotZero(input);
		}

		return result;
	}

	private char readZero(Scanner input) throws APException {
		checkCharacter(input, ZERO);
		nextChar(input);
		return ZERO;
	}

	private char readNotZero(Scanner input) throws APException {
		if (!nextCharIsPositiveDigit(input)) {
			throw new APException(POSITIVE_NUMBER_NOT_FOUND_EXCEPTION);
		}

		return nextChar(input);
	}

	private void trimSpaces(Scanner input) {
		while (nextCharIs(input, SPACE)) {
			nextChar(input);
		}
	}

	private void checkCharacter(Scanner input, char c) throws APException {
		if (!nextCharIs(input, c)) {
			throw new APException(String.format(CHAR_MISSING_EXCEPTION, c));
		}
	}

	private boolean nextCharIs(Scanner input, char c) {
		return input.hasNext(Pattern.quote(String.format("%c", c)));
	}

	private char nextChar(Scanner input) {
		return input.next().charAt(0);
	}

	private boolean nextCharIsLetter(Scanner input) {
		return input.hasNext(LETTER);
	}

	private boolean nextCharIsDigit(Scanner input) {
		return input.hasNext(DIGIT);
	}

	private boolean nextCharIsPositiveDigit(Scanner input) {
		return input.hasNext(POSITIVE_DIGIT);
	}

	private boolean nextCharIsAdditiveOperator(Scanner input) {
		return input.hasNext(ADDITIVE_OPERATOR);
	}

	private boolean nextCharIsMultiplicativeOperator(Scanner input) {
		return input.hasNext(MULTIPLICATIVE_OPERATOR);
	}

}