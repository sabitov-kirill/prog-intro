import java.io.*;

public abstract class WordStat {
    protected abstract void performWrite(BufferedWriter writer) throws IOException;
    protected abstract void tokenConsum(String token);

    public void read(String inputFileName)
            throws FileNotFoundException, UnsupportedEncodingException, IOException {
        Scanner in = new Scanner(inputFileName);
        try {
            while (in.hasNextWord()) {
                String word = in.nextWord();
                tokenConsum(word);
            }
        } finally {
            in.close();
        }
    }

    public void write(String outputFileName)
            throws FileNotFoundException, UnsupportedEncodingException, IOException {
        BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(outputFileName),
                        "utf8"
                )
        );

        try {
            performWrite(out);
        } finally {
            out.close();
        }
    }
    
    public void perform(String[] args) {
        String inFileName = null, outFileName = null;
        if (args.length < 2) {
            System.err.println("Input and/or output files isn't provided.");
            return;
        }
        inFileName = args[0];
        outFileName = args[1];

        try {
            read(inFileName);
            write(outFileName);
        } catch (FileNotFoundException e) {
            System.err.println("Specified input file not found: " + e.getMessage());
        } catch (UnsupportedEncodingException e) {
            System.err.println("Utf8 encoding isn't supported on your platform: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error occured while reading/writing files: " + e.getMessage());
        }
    }
}
