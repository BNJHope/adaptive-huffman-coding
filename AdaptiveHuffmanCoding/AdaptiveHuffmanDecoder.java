package AdaptiveHuffmanCoding;

import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.AdaptiveHuffmanNode;
import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.AdaptiveHuffmanTree;
import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.ParentDoesNotMatchChildException;

import java.io.*;
import java.nio.charset.Charset;

/**
 * Created by bh59 on 03/10/16.
 */
public class AdaptiveHuffmanDecoder {

    public AdaptiveHuffmanTree tree;

    private BufferedReader inputStream;

    private FileOutputStream outputStream;

    public void decode(String filename) throws FileNotFoundException, ParentDoesNotMatchChildException {

        this.inputStream = this.setupInputFile(filename);

        this.outputStream = this.setupOutputFile(filename);

        AdaptiveHuffmanNode currNode = tree.getRoot();

        String currBits, NYTString = "";

        int currByte, currBit;

        final int EOFConst = -1;

        boolean loadingNYT = false;

        try {
            while((currByte = this.inputStream.read()) != EOFConst){
                currBits = this.toBits(currByte);

                while(currBits.length() != 0) {
                    currBit = currBits.charAt(0);

                    if(!loadingNYT) {
                        currNode = tree.getNextNode(currNode, currBit);

                        if( (currBit == 0 && this.tree.rootIsNYT()) || tree.isNYT(currNode)) {
                            loadingNYT = true;
                        } else if(tree.isLeaf(currNode)) {
                            this.outputChar(currNode.getValue());
                            this.tree.addCharToTree(currNode.getValue());
                            currNode = this.tree.getRoot();
                        }

                    } else {
                        NYTString += currBit;
                        if(NYTString.length() == 8) {
                            this.outputChar(NYTString);
                            this.tree.addCharToTree(NYTString);
                            NYTString = "";
                            currNode = this.tree.getRoot();
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
        byte charToWrite = (byte) Integer.parseInt(byteToConvert, 2);
        this.outputStream.write(charToWrite);
    }

    private BufferedReader setupInputFile(String inputFileName) throws FileNotFoundException {
        //Encoding needed for an input stream reader - using system default as default.
        Charset encoding = Charset.defaultCharset();

        // /The file to read.
        File fileToReturn = new File(inputFileName);

        //The InputStream from the file.
        InputStream fileStream = new FileInputStream(fileToReturn);

        //The buffered reader for the file.
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(fileStream, encoding));

        return fileReader;
    }

    private FileOutputStream setupOutputFile(String absolouteFileName){

        //name of new file replaces compressed with decompressed in title
        String decompressedFileName = absolouteFileName.replace(".compressed.", ".decompressed.");

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

}
