package AdaptiveHuffmanCoding;


public class AdaptiveHuffmanCoding {

    public static void main(String[] args){
        if(args[0] == "-e") {
            encode(args[1], args[2]);
        } else if (args[0] == "-d") {
            decode(args[1]);
        } else if (args[0] == "-p") {
            encodeAndDecode(args[1]);
        }
    }

    public static void encode(String readFilename, String encodingSize){
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(Integer.parseInt(encodingSize));
        System.out.println("Starting encoding");
        encoder.encode(readFilename);
        System.out.println("Finished encoding");
    }

    public static void decode(String fileName) {
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();

            decoder.decode(fileName);

    }

    public static void encodeAndDecode(String fileName) {
        AdaptiveHuffmanEncoder encoder = new AdaptiveHuffmanEncoder(8);
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        String[] compressedFileNameComps = fileName.split(".", 2);
        String compressedFileName = fileName.substring(0, fileName.lastIndexOf('/')) + compressedFileNameComps[0] + ".compressed." + compressedFileNameComps[1];

        encoder.encode(fileName);
        decoder.decode(compressedFileName);
    }
}