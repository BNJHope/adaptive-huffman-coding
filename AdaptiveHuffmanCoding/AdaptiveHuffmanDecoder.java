package AdaptiveHuffmanCoding;

import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.AdaptiveHuffmanNode;
import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.AdaptiveHuffmanTree;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by bh59 on 03/10/16.
 */
public class AdaptiveHuffmanDecoder {

    /**
     * An object for printing the tree.
     */
    TreePrinter tp = new TreePrinter();

    /**
     * The tree which is being manipulated by the decoder.
     */
    public AdaptiveHuffmanTree tree;

    /**
     * The input stream to read from the compressed file.
     */
    private FileInputStream inputStream;

    /**
     * The output stream to write to the decompressed file
     */
    private FileOutputStream outputStream;

    /**
     * Constructor method.
     */
    public AdaptiveHuffmanDecoder(){
        tree = new AdaptiveHuffmanTree();
    }

    /**
     * Decodes the given compressed file.
     * @param filename
     */
    public void decode(String filename) {

        try {
            this.inputStream = this.setupInputFile(filename);
        } catch (FileNotFoundException e) {
            System.err.println("unable to find file : " + filename);
            System.err.println("exiting");
            System.exit(0);
        }

        this.outputStream = this.setupOutputFile(filename);

        AdaptiveHuffmanNode currNode = null;

        String currBits, NYTString = "";

        int currByte;

        char currBit;

        final int EOFConst = -1;

        boolean loadingNYT = false;

        try {
            while((currByte = this.inputStream.read()) != EOFConst){
                currBits = String.format("%8s", Integer.toBinaryString(currByte & 0xff)).replace(' ', '0');

                while(currBits.length() != 0) {
                    currBit = currBits.charAt(0);

                    if(!loadingNYT) {
                        if (currNode == null) {
                            loadingNYT = true;
                        } else {
                            currNode = tree.getNextNode(currNode, currBit);
                            if ((currBit == 0 && this.tree.rootIsNYT()) || tree.isNYT(currNode)) {
                                loadingNYT = true;
                            } else if (tree.isLeaf(currNode)) {
                                this.outputChar(currNode.getValue());
                                this.tree.addCharToTree(currNode.getValue());
                                currNode = this.tree.getRoot();
                            }
                        }
                    } else {
                        NYTString += currBit;
                        if(NYTString.length() == 8) {
                            this.outputChar(NYTString);
                            this.tree.addCharToTree(NYTString);
                            NYTString = "";
                            currNode = this.tree.getRoot();
                            loadingNYT = false;
                        }

                    }

                    if(currBits.length() > 1) {
                        currBits = currBits.substring(1);
                    } else if (currBits.length() == 1) {
                        currBits = "";
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            this.cleanUp();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void cleanUp() throws IOException {
        this.outputStream.close();
        this.inputStream.close();
    }

    public String toBits(int currByte) {
        String result = Integer.toBinaryString(currByte);

        while(result.length() < 8)
            result = '0' + result;

        return result;
    }

    public void outputChar(String byteToConvert) throws IOException {
        int charToWrite = Integer.parseInt(byteToConvert, 2);
        this.outputStream.write(charToWrite);
    }

    private FileInputStream setupInputFile(String inputFileName) throws FileNotFoundException {
        //Encoding needed for an input stream reader - using system default as default.
        Charset encoding = Charset.defaultCharset();

        // /The file to read.
        File fileToReturn = new File(inputFileName);

        //The InputStream from the file.
        FileInputStream fileStream = new FileInputStream(fileToReturn);

        return fileStream;
    }

    private FileOutputStream setupOutputFile(String absolouteFileName){

        //name of new file replaces compressed with decompressed in title
        String decompressedFileName = absolouteFileName.replace(".compressed8.", ".decompressed.");

        //create new file
        File outputFile = new File(decompressedFileName);

        //The output stream of the given file name
        FileOutputStream fout = null;

        //create new file from file name
        try {
            outputFile.createNewFile();
        } catch (IOException e) {
            System.err.println("Error creating new file " + decompressedFileName + " : exiting");
            System.exit(0);
        }

        //create file output stream from the new file
        try {
            fout = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            System.err.println("Error creating new file output stream for " + absolouteFileName + ": exiting");
            System.exit(0);
        }

        return fout;
    }

    public void printTree() {
        this.tp.printTree(this.tree.getRoot());
        System.out.println("\n\n\n\n\n");
    }

}
