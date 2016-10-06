package AdaptiveHuffmanCoding;

public class AdaptiveHuffmanCoding {

    public static void main(String[] args){
        String readFilename = args[0];
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(Integer.parseInt(args[1]));
        encoder.encode(readFilename);
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        System.out.println("Process complete");
    }

}