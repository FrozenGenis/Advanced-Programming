package setCalculator;

import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Application {

    public static final char SPACE = ' ';
    public static final char EQUALS_SIGN = '=';
    public static final char BRACKET_OPEN = '(';
    public static final char BRACKET_CLOSE = ')';
    public static final char CURLY_BRACKET_OPEN = '{';
    public static final char CURLY_BRACKET_CLOSE = '}';
    public static final char COMMA = ',';
    public static final char ZERO = '0';
    public static final String EMPTY_STRING = "";
    public static final String END_OF_LINE_NOT_FOUND_EXCEPTION = "End of line not found.";
    public static final String IDENTIFIER_EXCEPTION = "The first character of the identifier must be a letter but was not found.";
    public static final String EQUAL_SIGN_NOT_FOUND_EXCEPTION = "'=' expected not found.";
    public static final String FACTOR_EXCEPTION = "Invalid factor.";
    public static final String POSITIVE_NUMBER_NOT_FOUND_EXCEPTION = "Positive number not found.";
    public static final String KEY_NOT_FOUND_EXCEPTION = "Map does not contain the specified key.";
    public static final String UNKNOWN_COMMAND_EXCEPTION = "The command is not available";

    private PrintStream out;

    private Map<Identifier, Set<NaturalNumber>> assignments;

    public Application() {
        out = new PrintStream(System.out);

        assignments = new Map<>();
    }

    private void start() {
        Scanner in = new Scanner(System.in).useDelimiter(EMPTY_STRING);
        String program;
        Scanner programScanner;

        //noinspection InfiniteLoopStatement
        while (true) {
            program = in.nextLine();
            programScanner = new Scanner(program).useDelimiter(EMPTY_STRING);

            try {
                readProgram(programScanner);
            } catch (APException e) {
                out.println(e);
            }
        }
    }

    private void readProgram(Scanner input) throws APException {
        while (input.hasNext()) {
            readStatement(input);
        }
    }

    private void readStatement(Scanner input) throws APException {
        trimSpaces(input);

        if (nextCharIs(input, '?')) {
            printStatement(input);
        } else if (nextCharIs(input, '/')) {
            readComment(input);
        } else if (nextCharIsLetter(input)) {
            executeAssignment(input);
        } else {
            throw new APException(UNKNOWN_COMMAND_EXCEPTION);
        }
    }

    private void printStatement(Scanner input) throws APException {
        nextChar(input);
        trimSpaces(input);

        Set<NaturalNumber> result = readExpression(input);

        trimSpaces(input);

        checkEndOfLine(input);

        out.printf("%s%n", result.toString());
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

        if (!nextCharIs(input, EQUALS_SIGN)) {
            throw new APException(EQUAL_SIGN_NOT_FOUND_EXCEPTION);
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

        while (nextCharIsMultiplicativeFactor(input)) {
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
            case '+':
                result = set1.union(set2);
                break;
            case '*':
                result = set1.intersection(set2);
                break;
            case '-':
                result = set1.complement(set2);
                break;
            case '|':
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
        } else if (nextCharIs(input, BRACKET_OPEN)) {
            result = readComplexFactor(input);
        } else if (nextCharIs(input, CURLY_BRACKET_OPEN)) {
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
        checkCharacter(input, BRACKET_OPEN);
        nextChar(input);
        trimSpaces(input);

        Set<NaturalNumber> result = readExpression(input);

        trimSpaces(input);
        checkCharacter(input, BRACKET_CLOSE);
        nextChar(input);

        return result;
    }

    private Set<NaturalNumber> readSet(Scanner input) throws APException {
        checkCharacter(input, CURLY_BRACKET_OPEN);
        nextChar(input);
        trimSpaces(input);

        Set<NaturalNumber> result = readRowNaturalNumbers(input);

        trimSpaces(input);
        checkCharacter(input, CURLY_BRACKET_CLOSE);
        nextChar(input);

        return result;
    }

    private Set<NaturalNumber> readRowNaturalNumbers(Scanner input) throws APException {
        Set<NaturalNumber> result = new Set<>();

        if (nextCharIs(input, CURLY_BRACKET_CLOSE)) {
            return result;
        }

        NaturalNumber number;
        number = readNaturalNumber(input);
        result.add(number);
        trimSpaces(input);

        while (nextCharIs(input, COMMA)) {
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
                throw new APException("Digits can't start with a 0 (eg. 05 is forbidden).");
            }
        } else {
            throw new APException("Invalid input. NaturalNumber should only contain digits.");
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
            throw new APException(String.format("\'%c\' is missing.", c));
        }
    }

    private boolean nextCharIs(Scanner input, char c) {
        return input.hasNext(Pattern.quote(String.format("%c", c)));
    }

    private char nextChar(Scanner input) {
        return input.next().charAt(0);
    }

    private boolean nextCharIsLetter(Scanner input) {
        return input.hasNext("[a-zA-Z]");
    }

    private boolean nextCharIsDigit(Scanner input) {
        return input.hasNext("[0-9]");
    }

    private boolean nextCharIsPositiveDigit(Scanner input) {
        return input.hasNext("[1-9]");
    }

    private boolean nextCharIsAdditiveOperator(Scanner input) {
        return input.hasNext("[+|-]");
    }

    private boolean nextCharIsMultiplicativeFactor(Scanner input) {
        return input.hasNext("[*]");
    }

    public static void main(String[] args) {
        new Application().start();
    }

}