package AdaptiveHuffmanCoding;

public class AdaptiveHuffmanCoding {

    public static void main(String[] args){
        String readFilename = args[0];
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder();
        encoder.encode(readFilename);
    }

}