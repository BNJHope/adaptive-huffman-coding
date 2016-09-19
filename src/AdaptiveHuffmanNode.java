/**
 * Structure for a node in a tree for adaptive Huffman coding.
 */
public abstract class AdaptiveHuffmanNode {

    /**
     * The number of appearances the character held by the node has made in the source.
     */
    private int freq;

    /**
     * Returns the number of appearances the character that the node represents has made in the source.
     * @return The number of appearances the character that the node represents has made in the source.
     */
    public int getFreq() {
        return freq;
    }

    /**
     * Sets the number of appearances the character that the node represents has made in the source.
     * @param freq The number of appearances the character that the node represents has made in the source.
     */
    public void setFreq(int freq) {
        this.freq = freq;
    }

    /**
     * Increments the frequency of this current node.
     */
    public void incrementFrequency() {
        this.freq++;
    }
}