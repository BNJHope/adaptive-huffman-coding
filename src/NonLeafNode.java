/**
 * Represents a non leaf node in the Huffman tree.
 */
public class NonLeafNode extends AdaptiveHuffmanNode {

    /**
     * The left child node of this Huffman Node.
     */
    private AdaptiveHuffmanNode leftChild;

    /**
     * The right child node of this Huffman Node.
     */
    private AdaptiveHuffmanNode rightChild;

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
}