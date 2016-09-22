package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

/**
 * Structure for a node in a tree for adaptive Huffman coding.
 */
public abstract class AdaptiveHuffmanNode {

    /**
     * The number of appearances the character held by the node has made in the source.
     */
    private int freq;

    /**
     * The character which the Huffman node represents.
     */
    private byte character;

    /**
     * The left child node of this Huffman Node.
     */
    private AdaptiveHuffmanNode leftChild;

    /**
     * The right child node of this Huffman Node.
     */
    private AdaptiveHuffmanNode rightChild;

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
     * Gets the character which the node represents.
     * @return The character which the node represents.
     */
    public byte getCharacter() {
        return character;
    }

    /**
     * Sets the character which the node represents.
     * @param character The character which the node represents.
     */
    public void setCharacter(byte character) {
        this.character = character;
    }

    /**
     * Gets the left child of this node.
     * @return The left child of this node.
     */
    public AdaptiveHuffmanNode getLeftChild() {
        return leftChild;
    }

    /**
     * Sets the left child of this node.
     * @param leftChild The left child of this node.
     */
    public void setLeftChild(AdaptiveHuffmanNode leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Gets the right child of this node.
     * @return The right child of this node.
     */
    public AdaptiveHuffmanNode getRightChild() {
        return rightChild;
    }

    /**
     * Sets the right child of this node.
     * @return The right child of this node.
     */
    public void setRightChild(AdaptiveHuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Increments the frequency of this current node.
     */
    public void incrementFrequency() {
        this.freq++;
    }
}