package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

/**
 * Created by bh59 on 22/09/16.
 */
public class AdaptiveHuffmanTree {

    private AdaptiveHuffmanNode head;

    private AdaptiveHuffmanNode NYTNode = null;
    /**
     * Constructs tree by adding NYT node to it.
     */
    public AdaptiveHuffmanTree(){
        AdaptiveHuffmanNode NYT = new AdaptiveHuffmanNode(0, 0);
        this.head = NYT;
        this.NYTNode = NYT;
    }

    public void addCharToTree(char charToAdd){


    }

}
