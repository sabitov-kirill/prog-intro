import java.io.*;
import java.util.InputMismatchException;

public class Scanner implements Closeable {
    // Flag, showing wether scanner is opened and ready to use
    private boolean isOpened;
    // Flag, showing wether source reader is opened and its sream end is not reached
    private boolean isSourceOpened;
    // Scanner input source reader
    private Reader source;

    // Default size of input buffer
    private final int BUFFER_DEFAULT_SIZE = 128;
    // Input chars buffer
    private char[] buffer = new char[BUFFER_DEFAULT_SIZE];
    // Current possition of last read char in buffer (from source reader)
    private int bufferPossition = -1;
    // Current offset index of input buffer
    private int bufferOffset;
    // Saved offset index of input buffer (used while caching and parsing specific token)
    private int bufferSavedOffset;

    // Last cached token object
    private Object cachedNextToken;
    // Last cached line object
    private String cachedNextLine;
    // Last cached word object
    private String cachedNextWord;

    /**
     * Scanner constructor by file name.
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
                "utf8"
        );
        isSourceOpened = true;
        isOpened = true;
    }

    /**
     * Scanner constructor by input stream.
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
        if (source instanceof Closeable) {
            source.close();
        }
        isOpened = false;
        isSourceOpened = false;
        source = null;
    }

    /**
     * Read next token from InputStream/file.
     * 
     * @param tokenSeparatorTester predicate to check if charater is token separator.
     * @return readed token.
     * @throws IOException
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    private String next(TokenPartTester tokenSeparatorTester) throws IOException {
        String token = "";

        while (true) {
            // Read token (or reach possition of last char in buffer)
            int tokenStartIndex = -1, tokenEndIndex = -1;
            TokenPartTester.Result prevCharTestResult = TokenPartTester.Result.NONE;
            while (bufferOffset <= bufferPossition) {
                var charTestResult = tokenSeparatorTester.test(buffer[bufferOffset], prevCharTestResult);
                prevCharTestResult = charTestResult;

                if (charTestResult == TokenPartTester.Result.START) {
                    tokenStartIndex = bufferOffset;
                } else if (charTestResult == TokenPartTester.Result.END) {
                    tokenEndIndex = bufferOffset;
                    break;
                }
                bufferOffset++;
            }

            // Add either part of token (if last char in buffer reached), or whole token
            if (tokenStartIndex != -1 && tokenEndIndex != -1) {
                token += new String(buffer, tokenStartIndex, tokenEndIndex - tokenStartIndex);
                break;
            } else if (tokenStartIndex != -1) {
                token += new String(buffer, tokenStartIndex, bufferPossition - tokenStartIndex);
            }

            // If end of token not reached - fill input buffer by reading source
            read();
            if (!isSourceOpened) {
                break;
            }
        }
        return token != "" ? token : null;
    }

    /**
     * Read next token from InputStream/file.
     * 
     * @return next token read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public String next() throws IOException {
        if (cachedNextToken instanceof String) {
            bufferOffset = bufferSavedOffset;
            String val = (String) cachedNextToken;
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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public String nextLine() throws IOException {
        if (cachedNextLine != null) {
            bufferOffset = bufferSavedOffset;
            String val = cachedNextLine;
            cachedNextLine = null;
            return val;
        }

        String line = next(Scanner.TokenPartTester::lineTester);
        return line != null && line.length() != 0 ? line.substring(0, line.length() - 1) : null;
    }

    /**
     * Read next word from InputStream/file.
     * 
     * @return next word read from InputStream/file.
     * @throws IOException
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public String nextWord() throws IOException {
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
     *          thrown in case of I/O error occured while reading to internal buffer.
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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public int nextInt(int radix) throws IOException, InputMismatchException {
        if (cachedNextToken instanceof Integer) {
            bufferOffset = bufferSavedOffset;
            int val = ((Integer) cachedNextToken).intValue();
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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public long nextLong() throws IOException, InputMismatchException {
        if (cachedNextToken instanceof Long) {
            bufferOffset = bufferSavedOffset;
            long val = ((Long) cachedNextToken);
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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public float nextFloat() throws IOException, InputMismatchException {
        if (cachedNextToken instanceof Float) {
            bufferOffset = bufferSavedOffset;
            float val = ((Float) cachedNextToken);
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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public double nextDouble() throws IOException, InputMismatchException {
        if (cachedNextToken instanceof Double) {
            bufferOffset = bufferSavedOffset;
            double val = ((Double) cachedNextToken);
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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNext() throws IOException {
        // Cache next token
        bufferSavedOffset = bufferOffset;
        cachedNextToken = next();

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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNextLine() throws IOException {
        // Cache next line
        bufferSavedOffset = bufferOffset;
        cachedNextLine = nextLine();

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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNextWord() throws IOException {
        // Cache next line
        bufferSavedOffset = bufferOffset;
        cachedNextWord = nextWord();

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
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNextInt() throws IOException, InputMismatchException {
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

        return cachedNextToken != null;
    }

    /**
     * Check if next long integer can be read from InputStream/file.
     * 
     * @return whether next long integer can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNextLong() throws IOException, InputMismatchException {
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

        return cachedNextToken != null;
    }

    /**
     * Check if next token can be read from InputStream/file.
     * 
     * @return whether next float number can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNextFloat() throws IOException, InputMismatchException {
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

        return cachedNextToken != null;
    }

    /**
     * Check if next double precision float number can be read from InputStream/file.
     * 
     * @return whether next double precision float number can be read from InputStream/file or not.
     * @throws IOException
     *          thrown in case of I/O error occured while reading to internal buffer.
     */
    public boolean hasNextDouble() throws IOException, InputMismatchException {
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

        return cachedNextToken != null;
    }

    /**
     * Fill input buffer, by reading source reader.
     * Automaticly increases buffer size if its already full.
     * 
     * @throws IOException
     *          thrown in case I/O error occures while read.
     */
    private void read() throws IOException {
        if (!isOpened || !isSourceOpened) {
            return;
        }

        if (bufferPossition + 1 >= buffer.length && bufferOffset != bufferPossition) {
            bufferIncreaseSize();
        }

        int n = -1;
        try {
            n = source.read(buffer, bufferPossition + 1, buffer.length - (bufferPossition + 1));
        } finally {
            if (n == -1) {
                isSourceOpened = false;
            }
        }
        if (n > 0) {
            bufferPossition += n;
        }
    }

    /**
     * Input buffer increase size function.
     * Allocates memory twice as much as before and copies not offseted data.
     */
    private void bufferIncreaseSize() {
        char[] resizedBuffer = new char[buffer.length * 2];
        System.arraycopy(buffer, bufferOffset - 1, resizedBuffer, 0, bufferPossition - (bufferOffset - 1) + 1);
        buffer = resizedBuffer;

        bufferPossition -= bufferOffset - 1;
        bufferOffset = 0;
    }

    /**
     * Functional interface for testing whether char is part of specific type of token,
     * depending on this char and previous one.
     */
    @FunctionalInterface
    private interface TokenPartTester {
        /**
         * Enum, resposible for showing result of char test.
         */
        public enum Result {
            START, PART, END, NONE
        }

        /**
         * Test char to beloning to specific type of token.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        public Result test(char ch, Result prevResult);

        /**
         * Test char to beloning to any token, separated by whitespaces.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        public static Result tokenTester(char ch, Result prevResult) {
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
         * Test char to beloning to line.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        public static Result lineTester(char ch, Result prevResult) {
            if (prevResult == Result.NONE) {
                return Result.START;
            }
            return ch == '\n' ? Result.END : Result.PART;
        }

        /**
         * Test char to beloning to word.
         * 
         * @param ch - testing character.
         * @param prevResult - result of testing previous character.
         * @return result of character testing.
         */
        public static Result wordTester(char ch, Result prevResult) {
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
