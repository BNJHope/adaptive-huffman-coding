package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

/**
 * Created by bh59 on 22/09/16.
 */
public class AdaptiveHuffmanTree {

    private AdaptiveHuffmanNode root;

    private AdaptiveHuffmanNode NYTNode;

    /**
     * Constructs tree by adding NYT node to it.
     */
    public AdaptiveHuffmanTree(){
        //makes the initial NYT node for the tree.
        AdaptiveHuffmanNode NYT = new AdaptiveHuffmanNode(0, 0);

        //since the frequency is set to 1 by default, we need to set the NYT node frequency to 0.
        NYT.setFreq(0);

        //set the root node and the NYT node to 0.
        this.root = NYT;
        this.NYTNode = NYT;
    }

    /**
     * Edits the tree based on the given character. If the character already exists in the tree, then it increments its
     * frequency and checks that the tree is still in order. Otherwise, the NYT node is changed with a new parent node and the
     * new character node is added as its sibling.
     * @param charToAdd The character to add to the tree.
     */
    public void addCharToTree(char charToAdd){
        //tries to find if the character is already in the tree by checking all of the child nodes.
        AdaptiveHuffmanNode nodeToEdit = this.root.getRequiredNode(charToAdd);

        //the parent node that is currently being held.
        AdaptiveHuffmanNode parentNode;

        //if the node does not already exist in the tree then create a new node using the character handed to it
        if(nodeToEdit == null) {
            nodeToEdit = constructNewNode(charToAdd);
        } else {
            nodeToEdit.incrementFrequency();
        }

        //get the parent node of the node which is being changed.
        parentNode = nodeToEdit.getParentNode();

        //while there is a parent node
        while(parentNode != null){
            parentNode = nodeToEdit.getParentNode();

        }
    }

    /**
     * Constructs a new branch from the new character
     * @param charToAdd The character to be added to the branch.
     * @return A reference to the new node to be added.
     */
    public AdaptiveHuffmanNode constructNewNode(char charToAdd){

        //gets the current parent node for the NYT node so its child nodes can be changed afterwards.
        AdaptiveHuffmanNode oldParent = this.NYTNode.getParentNode();

        //the new Huffman node to be added to the tree.
        AdaptiveHuffmanNode newCharNode = new AdaptiveHuffmanNode(1, (int) charToAdd);

        //the new parent node to be added to the tree.
        AdaptiveHuffmanNode newParentNode = new AdaptiveHuffmanNode(2, this.NYTNode, newCharNode);

        //sets the left child of the old parent node to the new parent node.
        oldParent.setLeftChild(newParentNode);

        return newCharNode;
    }.
}
