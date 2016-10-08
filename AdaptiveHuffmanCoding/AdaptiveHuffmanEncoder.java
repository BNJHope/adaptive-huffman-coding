package AdaptiveHuffmanCoding;

import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.AdaptiveHuffmanTree;

import java.io.*;
import java.nio.charset.Charset;

public class AdaptiveHuffmanEncoder {

    TreePrinter tp = new TreePrinter();

    /**
     * The number of bits from a character to use when handing values to the tree to encode.
     * Must be either 2, 4 or 8.
     */
    private int numberOfBitsToUse;

    /**
     * The tree to be used whilst encoding the Huffman node
     */
    AdaptiveHuffmanTree tree;

    public AdaptiveHuffmanEncoder(int numberOfBitsToUse) {
        if(numberOfBitsToUse !=8 && numberOfBitsToUse != 4 && numberOfBitsToUse != 2){
            System.out.println("Number of bits to use value is not either 2, 4 or 8. Set to 8 by default.");
            this.numberOfBitsToUse = 8;
        } else {
            this.numberOfBitsToUse = numberOfBitsToUse;
        }

        this.tree = new AdaptiveHuffmanTree();
    }

    /**
     * Compresses the given file using Adaptive Huffman Encoding.
     * @param filename The name of the file to be compressed.
     */
    public void encode(String filename){

        //the current characters waiting to be read to the file and the string to add to the file
        String currString = "", stringToAdd = "";

        //The value that was read from the input stream.
        int nextCharInFile;

        //The reader of the file given to this function.
        FileInputStream fileReader = null;

        //The value that is read from the reader when it has reached the end of file.
        final int EOFConst = -1;

        //the array of the character split into individual bit segments
        String[] bitSegments;

        //Try to initialise the file reader with the given file name.
        //If there are any problems then exit the program.
        try {
            fileReader = this.getFileReader(filename);
        } catch(FileNotFoundException e) {
            System.out.println("File " + filename + " not found. Exiting.");
            System.exit(0);
        }

        //the file output stream for the given file
        FileOutputStream  fout = this.setupOutputFile(filename);

        //Tries to read the file character by character up to the end of the file.
        //If there are any problems then exit the program.
        try {
            while((nextCharInFile = fileReader.read()) != EOFConst) {
                //the character split into its bit segments specified by the value
                //given in the class
                bitSegments = this.getBits(nextCharInFile);

                for(String s : bitSegments) {
                    //get the combination of bits from the encoding of this character from the Huffman tree
                    //and add it to the collection of bits that need to be compressed and added to the output file.
                    currString += getHuffmanCode(s);

                    //if the collection of bits from the encoding is longer than or equal to 8
                    //then remove the first 8, compress them into and add them to the file
                    if(currString.length() >= 8) {
                        stringToAdd = currString.substring(0, 8);
                        currString = currString.substring(8);
                        outputHuffmanCode(stringToAdd, fout);
                    }
                }

            }
        } catch (IOException e) {
            System.out.println("Error reading file : exiting.");
            System.exit(0);
        }

        //if the last byte is not yet complete, then fill it with an NYT buffer and write it to the stream before closing the file
        if(currString.length() < 8) {
            currString += this.getNYTBuffer(8 - currString.length());
            outputHuffmanCode(currString,fout);
        }

        //closes the file
        try {
            fileReader.close();
        } catch (IOException e) {
            System.out.println("Error closing file : exiting");
            System.exit(0);
        }

    }

    /**
     * Intiates a file reader with the file name given to the file and returns it.
     * @param filename The name of the file to be read.
     * @return A BufferedReader of the file to be read.
     * @throws FileNotFoundException If the given file name does not correspond to a file then
     * this exception is thrown.
     */
    private FileInputStream getFileReader(String filename) throws FileNotFoundException {

        //Encoding needed for an input stream reader - using system default as default.
        Charset encoding = Charset.defaultCharset();

        // /The file to read.
        File fileToReturn = new File(filename);

        //The InputStream from the file.
        FileInputStream fileStream = new FileInputStream(fileToReturn);

        return fileStream;
    }

    /**
     * Updates the encoder's tree with the value handed to the function and gets its Huffman encoding.
     * @param valueToBeAdded The value to be added to the tree.
     */
    private String getHuffmanCode(String valueToBeAdded) {

        String result;

        //boolean to determine if the character that was added is a new character or not
        boolean isNewValue = false;

        isNewValue = !this.tree.stringExists(valueToBeAdded);
        //tries to add the value to the tree or increment its frequency if it already exists.
        //if there is a problem in matching a parent to a child in one of the swaps, then the
        if(isNewValue) {
            result = this.tree.getHuffmanCode("") + valueToBeAdded;}
        else
            result = this.tree.getHuffmanCode(valueToBeAdded);

        //update tree
        this.tree.addCharToTree(valueToBeAdded);

        return result;
    }


    private FileOutputStream setupOutputFile(String absolouteFileName){

        String path = absolouteFileName.substring(0, absolouteFileName.lastIndexOf("/"));
        //retrieve the raw file name by removing everything before the absoloute filname
        String filename = absolouteFileName.substring(absolouteFileName.lastIndexOf("/") + 1);
        //The original file name split into the parts before and after the first "." so that the "compressed" file name part
        //can be inserted
        String[] compressedFileNameComps = filename.split("\\.", 2);

        //The compressed file name, made up of the file name components from the original file name and the compressed
        //file path keyword added
        String compressedFileName = path + "/" + compressedFileNameComps[0] + ".compressed" + this.numberOfBitsToUse +"." + compressedFileNameComps[1];

        //create new file
        File outputFile = new File(compressedFileName);

        //The output stream of the given file name
        FileOutputStream fout = null;

        //create new file from file name
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating new file " + compressedFileName + " : exiting");
            System.exit(0);
        }

        //create file output stream from the new file
        try {
            fout = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error creating new file output stream for " + filename + ": exiting");
            System.exit(0);
        }

        return fout;
    }

    /**
     * Manages the output of a given Huffman encoding.
     * @param code The code to output.
     */
    private void outputHuffmanCode(String code, FileOutputStream fout) {
        int toWrite = Integer.parseInt(code, 2) & 0xff;
        try {
            fout.write(toWrite);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Converts a character into an array of strings of the character's bit representation
     * Split at intervals determined by the class
     * @param charToChange The character to convert into the bit string array
     * @return An array of bit combinations depending on the character and the number of bits to use when encoding
     */
    private String[] getBits(int charToChange){
        //resulting array - number of characters added depends on how many bits the length of each string
        //will be.
        String[] result = new String[8 / this.numberOfBitsToUse];

        String bitString = String.format("%8s", Integer.toBinaryString(charToChange & 0xFF)).replace(' ', '0');
        //makes sure that bit string is 8 characters long, as the toBinaryString method
        //removes any preceding 0s from the string if there are any
        while(bitString.length() < 8){
            bitString = "0" + bitString;
        }

        //for every slot in the array
        for(int i = 0; i < result.length; i++){
            //beginning index of this bit string
            int beginIndex = i * this.numberOfBitsToUse;
            //end index of this bit string
            int endIndex = beginIndex + this.numberOfBitsToUse;
            result[i] = bitString.substring(beginIndex, endIndex);
        }

        return result;
    }

    /**
     * For the last byte in the file where it may not have 8 complete bits from reading the entire file,
     * add the route to the NYT node to the end of the byte so that the decoder will start an NYT cycle at the end
     * instead of trying to read in anything else, so that it all reads correctly.
     * @param size the size left at the end of the string needed
     * @return a buffer of the size given to add to the end of the last byte
     */
    public String getNYTBuffer(int size) {
        String result = this.getHuffmanCode("");

        if(size < result.length()) {
            result = result.substring(0, size);
        } else if (size > result.length()) {
            while(result.length() < size) {
                result += '0';
            }
        }

        return result;
    }

    public void printTree() {
        this.tp.printTree(this.tree.getRoot());
        System.out.println("\n\n\n\n\n");
    }

}