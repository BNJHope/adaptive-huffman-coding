package AdaptiveHuffmanCoding;

import java.io.*;
import java.nio.charset.Charset;

public class AdaptiveHuffmanEncoder {


    /**
     * Compresses the given file using Adaptive Huffman Encoding.
     * @param filename The name of the file to be compressed.
     */
    public void encode(String filename){

        //The character that was read from the input stream.
        int nextCharInFile;

        //The character representation of what was read from the file.
        char charToBeAdded;

        //The reader of the file given to this function.
        BufferedReader fileReader = null;

        //The value that is read from the reader when it has reached the end of file.
        int EOFConst = -1;

        //Try to initialise the file reader with the given file name.
        //If there are any problems then exit the program.
        try {
            fileReader = this.getFileReader(filename);
        } catch(FileNotFoundException e) {
            System.out.println("File " + filename + " not found. Exiting.");
            System.exit(0);
        }

        //Tries to read the file character by character up to the end of the file.
        //If there are any problems then exit the program.
        try {
            while((nextCharInFile = fileReader.read()) != EOFConst) {
                charToBeAdded = (char) nextCharInFile;
                updateTree(charToBeAdded);
            }
        } catch (IOException e) {
            System.out.println("Error reading file : exiting.");
            System.exit(0);
        }
    }

    private BufferedReader getFileReader(String filename) throws FileNotFoundException {

        //Encoding needed for an input stream reader - using system default as default.
        Charset encoding = Charset.defaultCharset();

        // /The file to read.
        File fileToReturn = new File(filename);

        //The InputStream from the file.
        InputStream fileStream = new FileInputStream(fileToReturn);

        //The buffered reader for the file.
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream, encoding));

        return fileReader;
    }

    private void updateTree(char charToBeAdded) {
        System.out.println("char : " + charToBeAdded);
    }

    private char convertBinaryStringToChar(String strToConvert){
        //converts the binary string handed to the function into its base 10 representation.
        int integerRepresentation = Integer.parseInt(strToConvert, 2);

        //converts the integer into its coresponding ASCII character and returns it.
        return (char) integerRepresentation;
    }
}