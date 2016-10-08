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
        System.out.println("Starting encoding " + readFilename + " with " + encodingSize + " bit tree node representation.");
        encoder.encode(readFilename);
        System.out.println("Finished encoding");
    }

    public static void decode(String fileName) {
        AdaptiveHuffmanDecoder decoder = new AdaptiveHuffmanDecoder();
        System.out.println("Starting decoding " + fileName);
        decoder.decode(fileName);
        System.out.println("Finished decoding");
    }

    public static void encodeAndDecode(String fileName) {
        String path = fileName.substring(0, fileName.lastIndexOf("/"));
        //retrieve the raw file name by removing everything before the absoloute filname
        String rawFileName = fileName.substring(fileName.lastIndexOf("/") + 1);
        //The original file name split into the parts before and after the first "." so that the "compressed" file name part
        //can be inserted
        String[] compressedFileNameComps = rawFileName.split("\\.", 2);

        //The compressed file name, made up of the file name components from the original file name and the compressed
        //file path keyword added
        String compressedFileName = path + "/" + compressedFileNameComps[0] + ".compressed8." + compressedFileNameComps[1];

        encode(fileName, "8");
        decode(compressedFileName);
    }
}