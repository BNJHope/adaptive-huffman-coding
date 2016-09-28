package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by bh59 on 22/09/16.
 */
public class AdaptiveHuffmanTree {

    /**
     * The root node of the application.
     * The NYT node of the application
     */
    private AdaptiveHuffmanNode root, NYTNode;

    /**
     * An array that holds a reference to each node in the tree to save time by searching the tree for a specific node.
     * Allows an O(1) check time for determining if a node is in the tree or not as it can check whether the array is null at
     * the space where a character should be.
     * Also allows O(1) for searching for node as each node is indexed in the array by the integer representation of its character.
     */
    private AdaptiveHuffmanNode[] nodeArray = new AdaptiveHuffmanNode[256];

    /**
     * Maps frequency (weight) of a node to a list of the nodes which have this frequency.
     * Makes checking rest of weight group
     */
    private HashMap<Integer, LinkedList<AdaptiveHuffmanNode>> nodeWeightGroups = new HashMap();

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
        AdaptiveHuffmanNode nodeToEdit = this.getNodeFromTree(charToAdd);

        //the parent node that is currently being held.
        AdaptiveHuffmanNode parentNode;

        //if it is a new character for the tree then add it to NYT.
        //otherwise, check to see if the node needs to be replaced anywhere before incrementing frequency.
        if(nodeToEdit == null) {
            nodeToEdit = this.createNewNode(charToAdd);
        } else {
            nodeToEdit.incrementFrequency();
        }

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
    }

    /**
     * Gets the node from the tree that is associated with the character passed to the function.
     * @param charToFind The character to find in the tree.
     * @return If the character has already been seen, then this function returns the node that represents that character in the tree.
     * Otherwise, it returns null.
     */
    private AdaptiveHuffmanNode getNodeFromTree(char charToFind){
        //converts the character handed to the function into an integer address to be used with the array.
        int addressOfNode = (int) charToFind;

        //returns the contents of the array at the address.
        return this.nodeArray[addressOfNode];
    }

    /**
     * Checks whether the node given to the function is the root node of the tree or not.
     * @param node The node to check.
     * @return True if the node is the root node, false if it is not.
     */
    private boolean isRoot(AdaptiveHuffmanNode node) {

        //returns whether the reference of the node handed to the function is the same as the reference to the root node.
        return node == this.root;
    }

    /**
     * Spawns a new node using the NYT node of the tree and gives back a reference to this new node.
     * @param charToAdd The character to add to the tree.
     * @return A reference to the new node which was just added.
     */
    private AdaptiveHuffmanNode createNewNode(char charToAdd){

        //the new node to be added to the tree.
        AdaptiveHuffmanNode newNode = new AdaptiveHuffmanNode(1, charToAdd);

        //the new parent node which will replace the position of the NYT node in the tree.
        AdaptiveHuffmanNode newParentNode = new AdaptiveHuffmanNode(2, this.NYTNode, newNode);

        //sets the new node's parent node as the new parent node.
        newNode.setParentNode(newParentNode);

        //sets the old parent node's left child as the new parent node
        this.NYTNode.getParentNode().setLeftChild(newParentNode);

        //sets the NYT's new parent as the new parent node.
        this.NYTNode.setParentNode(newParentNode);

        //returns a reference to the new node which was created.
        return newNode;
    }

    /**
     * Adds the new node to the list of other nodes with the same new frequency as this node.
     * @param node The node to add to the new list.
     * @param newFrequency The new frequency of the node, and thus the key in the hashmap of the new list where the node should be added.
     */
    private void addNodeToList(AdaptiveHuffmanNode node, int newFrequency){

        //Gets the list to add the new node to by getting the new frequency list from the map.
        LinkedList<AdaptiveHuffmanNode> newList = this.nodeWeightGroups.get(newFrequency);

        //if there is no list in the map when given the frequency as a key then make a new list and add it to the map
        //at that frequency.
        if(newList == null) {
            newList = new LinkedList<AdaptiveHuffmanNode>();
            newList.addFirst(node);
            this.nodeWeightGroups.put(newFrequency, newList);
        } else {
            //if the node is being added to the new list it will have the lowest ID since it was previously from a lower weight class
            //therefore it can be added to the start wihtout having to check if the IDs are in order.
            newList.addFirst(node);
        }



    }
}
