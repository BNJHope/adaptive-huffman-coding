package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by bh59 on 03/10/16.
 */
public class AdaptiveHuffmanDecoder {
    AdaptiveHuffmanTree tree;

    FileInputStream inputStream;

    public void decode(String filename) {

        AdaptiveHuffmanNode currNode = tree.getRoot();

        String currBits, NYTString;

        int currByte, currBit;

        final int EOFConst = -1;

        boolean loadingNYT = false;

        try {
            while((currByte = this.inputStream.read()) != EOFConst){
                currBits = this.toBits(currByte);

                while(currBits.length() != 0) {
                    currBit = currBits.charAt(0);

                    currNode = tree.getNextNode(currNode, currBit);

                    if(tree.isNYT(currNode)) {
                        loadingNYT = true;

                    }

                    if(currBits.length() > 1) {
                        currBits = currBits.substring(1);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToTree() {

    }

    public String toBits(int currByte) {
        String result = Integer.toBinaryString(currByte);

        while(result.length() < 8)
            result = '0' + result;

        return result;
    }

    public void outputChar()
}
