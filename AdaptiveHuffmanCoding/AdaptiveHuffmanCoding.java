package AdaptiveHuffmanCoding;


public class AdaptiveHuffmanCoding {

    public static void main(String[] args){
        if(args[0].equals("-e")) {
            encode(args[1], args[2]);
        } else if (args[0].equals("-d")) {
            decode(args[1]);
        } else if (args[0].equals("-p")) {
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
        System.out.println("Starting decoding");
        decoder.decode(fileName);
        System.out.println("Finished decoding");
    }

    public static void encodeAndDecode(String fileName) {
        String[] compressedFileNameComps = fileName.split(".", 2);
        String compressedFileName = fileName.substring(0, fileName.lastIndexOf('/')) + compressedFileNameComps[0] + ".compressed." + compressedFileNameComps[1];

        encode(fileName, "8");
        decode(compressedFileName);
    }
}