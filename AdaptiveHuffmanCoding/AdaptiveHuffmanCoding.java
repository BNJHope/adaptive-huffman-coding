package AdaptiveHuffmanCoding;

import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.ParentDoesNotMatchChildException;

import java.io.FileNotFoundException;

public class AdaptiveHuffmanCoding {

    public static void main(String[] args){
        encode(args[0], args[1]);
        System.out.println("starting decoder");
        decode();
        System.out.println("Process complete");
    }

    public static void encode(String readFilename, String encodingSize){
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(Integer.parseInt(encodingSize));
        encoder.encode(readFilename);
    }

    public static void decode() {
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        try {
            decoder.decode("/cs/home/bh59/Documents/CS3000/DE/P1/test_items.compressed.test.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParentDoesNotMatchChildException e) {
            e.printStackTrace();
        }
    }
}