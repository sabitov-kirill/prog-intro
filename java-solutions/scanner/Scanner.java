package scanner;

import java.io.*;
import java.util.Arrays;
import java.util.InputMismatchException;

public class Scanner implements Closeable {
    public static final String DEFAULT_ENCODING = "utf8";
    // Flag, showing whether scanner is opened and ready to use
    private boolean isOpened;
    // Flag, showing whether source reader is opened and its stream end is not reached
    private boolean isSourceOpened;
    // Scanner.Scanner input source reader
    private Reader source;

    // Default size of input buffer
    private final int BUFFER_DEFAULT_SIZE = 8192;
    // Input chars buffer
    private char[] buffer = new char[BUFFER_DEFAULT_SIZE];
    // Current position of last read char in buffer (from source reader)
    private int bufferPosition = -1;
    // Current offset index of input buffer
    private int bufferOffset;
    // Saved offset index of input buffer (used while caching and parsing specific token)
    private int bufferSavedOffset;
    // Flag, showing whether to skip next char (part of EOL sequence) or not
    private boolean skipEOL;

    // Last cached token object
    private Object cachedNextToken;
    // Last cached line object
    private String cachedNextLine;
    // Last cached word object
    private String cachedNextWord;

    /**
     * Scanner.Scanner constructor by file name.
     * 
     * @param inFileName name of input file, to create reader from.
     * @throws FileNotFoundException
     *          thrown in case input file is not found.
     * @throws UnsupportedEncodingException
     *          thrown in case of "utf8" encoding is not supported.
     */
    public Scanner(String inFileName)
            throws FileNotFoundException, UnsupportedEncodingException {
        source = new InputStreamReader(
                new FileInputStream(inFileName),
                DEFAULT_ENCODING
        );
        isSourceOpened = true;
        isOpened = true;
    }

    /**
     * Scanner.Scanner constructor by input stream.
     * 
     * @param in input stream to create reader from (ex. System.in).
     */
    public Scanner(InputStream in) {
        source = new InputStreamReader(in);
        isSourceOpened = true;
        isOpened = true;
    }

    /**
     * Close scanner function.
     * Also closes its internal reader.
     */
    @Override
    public void close() throws IOException {
        if (!isOpened) {
            return;
        }
        source.close();
        isOpened = false;
        source = null;
    }

    /**
     * Check if scanner is opened function.
     */
    private void ensureOpen() {
        if (!isOpened) {
            throw new IllegalStateException("Scanner.Scanner closed");
        }
    }

    /**
     * Read next token from InputStream/file.
     * 
     * @param tokenSeparatorTester predicate to check if character is token separator.
     * @return read token.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    private String next(TokenPartTester tokenSeparatorTester)
            throws IOException, InputMismatchException {
        ensureOpen();

        TokenPartTester.Result charTestResult;
        TokenPartTester.Result prevCharTestResult = TokenPartTester.Result.NONE;
        int tokenStartIndex = -1, tokenEndIndex = -1;
        StringBuilder sb = new StringBuilder();

        do {
            if (skipEOL && bufferOffset <= bufferPosition && buffer[bufferOffset] == '\n') {
                bufferOffset++;
                skipEOL = false;
            }

            // Read token (or reach position of last char in buffer)
            while (bufferOffset <= bufferPosition && prevCharTestResult != TokenPartTester.Result.END) {
                charTestResult = tokenSeparatorTester.test(buffer[bufferOffset], prevCharTestResult);

                if (charTestResult == TokenPartTester.Result.START) {
                    tokenStartIndex = bufferOffset;
                }
                if (charTestResult == TokenPartTester.Result.END ||
                        (charTestResult = tokenSeparatorTester.test(buffer[bufferOffset],
                                charTestResult)) == TokenPartTester.Result.END) {
                    tokenEndIndex = bufferOffset;
                    if (buffer[bufferOffset] == '\r') {
                        skipEOL = true;
                    }
                }
                prevCharTestResult = charTestResult;
                bufferOffset++;
            }

            // Add either part of token (if last char in buffer reached), or whole token
            if (tokenStartIndex != -1 && tokenEndIndex != -1) {
                sb.append(buffer, tokenStartIndex, tokenEndIndex - tokenStartIndex);
                return sb.toString();
            } else if (tokenStartIndex != -1) {
                sb.append(buffer, tokenStartIndex, bufferOffset - tokenStartIndex);
            }

            // If end of token not reached - fill input buffer by reading source
            read();
            if (tokenStartIndex != -1) {
                tokenStartIndex = 0;
            }
        } while (isSourceOpened);
        
        if (sb.isEmpty()) {
            throw new InputMismatchException();
        } else {
            return sb.toString();
        }
    }

    /**
     * Read next token from InputStream/file.
     * 
     * @return next token read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public String next() throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextToken instanceof String val) {
            bufferOffset = bufferSavedOffset;
            cachedNextToken = null;
            return val;
        }

        return next(Scanner.TokenPartTester::tokenTester);
    }

    /**
     * Read next line from InputStream/file.
     * 
     * @return next line read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public String nextLine() throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextLine != null) {
            bufferOffset = bufferSavedOffset;
            String val = cachedNextLine;
            cachedNextLine = null;
            return val;
        }

        return next(Scanner.TokenPartTester::lineTester);
    }

    /**
     * Read next word from InputStream/file.
     * 
     * @return next word read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public String nextWord() throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextWord != null) {
            bufferOffset = bufferSavedOffset;
            String val = cachedNextWord;
            cachedNextWord = null;
            return val;
        }

        return next(Scanner.TokenPartTester::wordTester);
    }

    /**
     * Read next integer from InputStream/file.
     * 
     * @return next integer read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public int nextInt() throws IOException, InputMismatchException {
        return nextInt(10);
    }

    /**
     * Read next integer from InputStream/file.
     * 
     * @param radix radix of parsing integer.
     * @return next integer read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public int nextInt(int radix) throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextToken instanceof Integer val) {
            bufferOffset = bufferSavedOffset;
            cachedNextToken = null;
            return val;
        }

        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            return Integer.parseInt(token, radix);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            throw new InputMismatchException(e.getMessage());
        }
    }

    /**
     * Read next long integer from InputStream/file.
     * 
     * @return next long integer read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public long nextLong() throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextToken instanceof Long val) {
            bufferOffset = bufferSavedOffset;
            cachedNextToken = null;
            return val;
        }

        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            return Long.parseLong(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            throw new InputMismatchException(e.getMessage());
        }
    }

    /**
     * Read next float number from InputStream/file.
     * 
     * @return next float number read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public float nextFloat() throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextToken instanceof Float val) {
            bufferOffset = bufferSavedOffset;
            cachedNextToken = null;
            return val;
        }

        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            return Float.parseFloat(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            throw new InputMismatchException(e.getMessage());
        }
    }

    /**
     * Read next double precision float number from InputStream/file.
     * 
     * @return next double precision float number read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     * @throws InputMismatchException
     *          throws in case of trying to get not existing token/token of different type.
     */
    public double nextDouble() throws IOException, InputMismatchException {
        ensureOpen();

        if (cachedNextToken instanceof Double val) {
            bufferOffset = bufferSavedOffset;
            cachedNextToken = null;
            return val;
        }

        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            return Double.parseDouble(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            throw new InputMismatchException(e.getMessage());
        }
    }

    /**
     * Check if next token can be read from InputStream/file.
     * 
     * @return whether next token can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNext() throws IOException {
        ensureOpen();

        // Cache next token
        bufferSavedOffset = bufferOffset;
        try {
            cachedNextToken = next();
        } catch (InputMismatchException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return cachedNextToken != null;
    }

    /**
     * Check if next line can be read from InputStream/file.
     * 
     * @return whether next line can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNextLine() throws IOException {
        ensureOpen();

        // Cache next line
        bufferSavedOffset = bufferOffset;
        try {
            cachedNextLine = nextLine();
        } catch (InputMismatchException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return cachedNextLine != null;
    }

    /**
     * Check if next word can be read from InputStream/file.
     * 
     * @return whether next word can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNextWord() throws IOException {
        ensureOpen();

        // Cache next line
        bufferSavedOffset = bufferOffset;
        try {
            cachedNextWord = nextWord();
        } catch (InputMismatchException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return cachedNextWord != null;
    }

    /**
     * Check if next integer can be read from InputStream/file.
     * 
     * @return whether next integer can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNextInt() throws IOException, InputMismatchException {
        ensureOpen();

        // Cache nest token
        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            cachedNextToken = Integer.parseInt(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return true;
    }

    /**
     * Check if next long integer can be read from InputStream/file.
     * 
     * @return whether next long integer can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNextLong() throws IOException, InputMismatchException {
        ensureOpen();

        // Cache nest token
        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            cachedNextToken = Long.parseLong(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return true;
    }

    /**
     * Check if next token can be read from InputStream/file.
     * 
     * @return whether next float number can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNextFloat() throws IOException, InputMismatchException {
        ensureOpen();

        // Cache nest token
        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            cachedNextToken = Float.parseFloat(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return true;
    }

    /**
     * Check if next double precision float number can be read from InputStream/file.
     * 
     * @return whether next double precision float number can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occurred while reading to internal buffer.
     */
    public boolean hasNextDouble() throws IOException, InputMismatchException {
        ensureOpen();

        // Cache nest token
        bufferSavedOffset = bufferOffset;
        String token = next();
        try {
            cachedNextToken = Double.parseDouble(token);
        } catch (NumberFormatException e) {
            bufferOffset = bufferSavedOffset;
            return false;
        }

        // Swap offsets for caching
        int tempOffset = bufferSavedOffset;
        bufferSavedOffset = bufferOffset;
        bufferOffset = tempOffset;

        return true;
    }

    /**
     * Fill input buffer, by reading source reader.
     * Automatically increases buffer size if it's already full.
     * 
     * @throws IOException
     *          thrown in case I/O error occurs while read.
     */
    private void read() throws IOException {
        if (!isOpened || !isSourceOpened) {
            return;
        }

        if (bufferOffset + 1 >= buffer.length) {
            bufferIncreaseSize();
        }

        int n = -1;
        try {
            n = source.read(buffer, 0, buffer.length);
        } finally {
            if (n == -1) {
                isSourceOpened = false;
            }
        }
        if (n > 0) {
            bufferPosition = n - 1;
            bufferOffset = 0;
        }
    }

    /**
     * Input buffer increase size function.
     * Allocates memory twice as much as before and copies not offset data.
     */
    private void bufferIncreaseSize() {
        buffer = Arrays.copyOf(buffer, buffer.length * 2);
    }

    /**
     * Functional interface for testing whether char is part of specific type of token,
     * depending on this char and previous one.
     */
    @FunctionalInterface
    private interface TokenPartTester {
        /**
         * Enum, responsible for showing result of char test.
         */
        enum Result {
            NONE, START, PART, END
        }

        /**
         * Test char to belonging to specific type of token.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        Result test(char ch, Result prevResult);

        /**
         * Test char to belonging to any token, separated by whitespaces.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        static Result tokenTester(char ch, Result prevResult) {
            boolean chWhitespace = Character.isWhitespace(ch);

            if (!chWhitespace && prevResult == Result.NONE) {
                return Result.START;
            }
            if (chWhitespace && prevResult == Result.PART) {
                return Result.END;
            }
            return prevResult == Result.START || prevResult == Result.PART ? Result.PART : Result.NONE;
        }

        /**
         * Test char to belonging to line.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        static Result lineTester(char ch, Result prevResult) {
            if (prevResult == Result.NONE || prevResult == Result.END) {
                return Result.START;
            }
            return (ch == '\n' || ch == '\r') ? Result.END : Result.PART;
        }

        /**
         * Test char to belonging to word.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        static Result wordTester(char ch, Result prevResult) {
            boolean chWordPart = isCharWordPart(ch);

            if (chWordPart && prevResult == Result.NONE) {
                return Result.START;
            }
            if (!chWordPart && (prevResult == Result.PART || prevResult == Result.START)) {
                return Result.END;
            }
            return prevResult == Result.START || prevResult == Result.PART ? Result.PART : Result.NONE;
        }

        /**
         * Check if single char is word part function.
         * 
         * @param ch testing character.
         * @return whether single char is word part.
         */
        private static boolean isCharWordPart(char ch) {
            return ch == '\''
                    || Character.isLetter(ch)
                    || Character.getType(ch) == Character.DASH_PUNCTUATION;
        }
    }
}
