package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by bh59 on 03/10/16.
 */
public class AdaptiveHuffmanDecoder {
    AdaptiveHuffmanTree tree;

    FileInputStream inputStream;

    public String getNextBitsFromFile() throws IOException {
        return Integer.toBinaryString(this.inputStream.read());
    }
}
