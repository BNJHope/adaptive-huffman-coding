package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

/**
 * Structure for a node in a tree for adaptive Huffman coding.
 */
public class AdaptiveHuffmanNode {

    /**
     * The number of appearances the character held by the node has made in the source.
     */
    private int freq;

    /**
     * The ID number of the node.
     */
    private int nodeId;

    /**
     * The value which the Huffman node represents.
     */
    private String value;

    /**
     * The left child node of this Huffman Node.
     */
    private AdaptiveHuffmanNode leftChild;

    /**
     * The right child node of this Huffman Node.
     */
    private AdaptiveHuffmanNode rightChild;

    /**
     * The parent node of this node.
     */
    private AdaptiveHuffmanNode parentNode;

    /**
     * Constructor for non-leaf node
     * @param nodeId ID number of this node.
     * @param leftChild Left child node.
     * @param rightChild Right child node.
     */
    public AdaptiveHuffmanNode(int nodeId, AdaptiveHuffmanNode leftChild, AdaptiveHuffmanNode rightChild){
        this.nodeId = nodeId;

        //character set to -1 since the leaf node should have no character representation.
        this.value = null;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.freq = leftChild.getFreq() + rightChild.getFreq();
    }

    /**
     * Constructor for leaf node.
     * @param nodeId ID number of this node.
     * @param value The character that this node represents.
     */
    public AdaptiveHuffmanNode(int nodeId, String value){
        this.nodeId = nodeId;
        this.value = value;
        this.freq = 1;
        this.leftChild = null;
        this.rightChild = null;
    }
    /**
     * Returns the number of appearances the character that the node represents has made in the source.
     * @return The number of appearances the character that the node represents has made in the source.
     */
    public int getFreq() {
        return this.freq;
    }

    /**
     * Sets the number of appearances the character that the node represents has made in the source.
     * @param freq The number of appearances the character that the node represents has made in the source.
     */
    public void setFreq(int freq) {
        this.freq = freq;
    }

    /**
     * Gets the NodeID of this node.
     * @return The NodeID of this node.
     */
    public int getNodeId() {
        return nodeId;
    }

    /**
     * Sets the NodeID of this node.
     * @param nodeId The NodeID of this node.
     */
    public void setNodeId(int nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * Gets the character which the node represents.
     * @return The value which the node represents.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the character which the node represents.
     * @param character The value which the node represents.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the left child of this node.
     * @return The left child of this node.
     */
    public AdaptiveHuffmanNode getLeftChild() {
        return this.leftChild;
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
        return this.rightChild;
    }

    /**
     * Sets the right child of this node.
     * @return The right child of this node.
     */
    public void setRightChild(AdaptiveHuffmanNode rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Gets the parent node of this Huffman node.
     * @return The parent node of this node.
     */
    public AdaptiveHuffmanNode getParentNode() {
        return parentNode;
    }

    /**
     * Sets the parent node if this Huffman node.
     * @param parentNode The parent node of this node.
     */
    public void setParentNode(AdaptiveHuffmanNode parentNode) {
        this.parentNode = parentNode;
    }

    /**
     * Increments the frequency of this current node.
     */
    public void incrementFrequency() {
        this.freq++;
    }

}