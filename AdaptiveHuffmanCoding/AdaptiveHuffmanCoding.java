package AdaptiveHuffmanCoding;

import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.ParentDoesNotMatchChildException;

import java.io.FileNotFoundException;

public class AdaptiveHuffmanCoding {

    public static void main(String[] args){
        String readFilename = args[0];
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(Integer.parseInt(args[1]));
        encoder.encode(readFilename);
        System.out.println("Process complete");
    }

}