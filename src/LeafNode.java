/**
 * Represents a leaf node in the Huffman tree.
 */
public class LeafNode extends AdaptiveHuffmanNode {

    /**
     * The character which the Huffman node represents.
     */
    private byte character;

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
}