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
     * The character which the Huffman node represents.
     */
    private int character;

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
        this.character = -1;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        this.freq = leftChild.getFreq() + rightChild.getFreq();
    }

    /**
     * Constructor for leaf node.
     * @param nodeId ID number of this node.
     * @param character The character that this node represents.
     */
    public AdaptiveHuffmanNode(int nodeId, int character){
        this.nodeId = nodeId;
        this.character = character;
        this.freq = 1;
        this.leftChild = null;
        this.rightChild = null;
    }
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
     * @return The character which the node represents.
     */
    public int getCharacter() {
        return character;
    }

    /**
     * Sets the character which the node represents.
     * @param character The character which the node represents.
     */
    public void setCharacter(int character) {
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

//    /**
//     * Updates the frequency. Returns true if it was able to, returns false if it was unable to as it had no child nodes.
//     */
//    public int updateFrequency(){
//        //the new frequency of this node.
//        int result;
//
//        //if this node has a left child and a right child then set its new frequency to to sum of its children
//        if(this.leftChild != null && this.rightChild != null) {
//            this.freq = this.leftChild.updateFrequency() + this.rightChild.updateFrequency();
//            result =  this.freq;
//        } else {
//            result = this.freq;
//        }
//
//        return result;
//    }

    /**
     * Checks this node and the child nodes of this node to see if it contains the character that is being searched for and if it does then returns the resulting node.
     * @param charToFind
     * @return Null if the character does not exist in the tree, otherwise returns the node that contains the character.
     */
    public AdaptiveHuffmanNode getRequiredNode(char charToFind) {

        //The result to be returned. Set to -1 by default.
        AdaptiveHuffmanNode result = null;

        //if the character in this nodeis the character that is being searched for then return this nodeId.
        if(this.character == charToFind)
            result = this;

        //if this node does not contain the character and it has a right child then search the right child.
        //if the child is on the right then it should have a higher frequency and therefore it holds a higher
        //probability that the character to be searched for is on a right child node
        if(result == null && this.rightChild != null)
            result = rightChild.getRequiredNode(charToFind);

        //if this node and its right child do not contain the character being searched for and this node has a left child
        //then search for the character in the left child.
        if(result == null && this.leftChild != null)
            result = this.leftChild.getRequiredNode(charToFind);

        return result;
    }
}