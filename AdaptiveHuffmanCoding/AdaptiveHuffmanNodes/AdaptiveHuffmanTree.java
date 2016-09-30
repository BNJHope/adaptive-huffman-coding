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
     * Gets the Huffman code of that given character.
     * @param charToGet The character to retrieve from the tree.
     * @return The Huffman code to be returned.
     */
    public String getHuffmanCode(char charToGet) {
        //the result string
        String result = "";

        //determines whether the root node has been found or not for the string
        boolean rootNodeFound = false;

        //the current node being looked at
        AdaptiveHuffmanNode currentNode = this.nodeArray[(int) charToGet], parentNode = currentNode.getParentNode();

        //while the root node has not been found
        while(!rootNodeFound) {

            //if the child node is the parent's right child then add a 1 to the start of the string
            //otherwise, add a 0 to the begininng of the string
            result = parentNode.getRightChild() == currentNode ? "1" + result: "0" + result;

            //if the parent node is the root node of the tree then the tree does not need to be navigated
            //anymore and so the loop can be exited
            if(this.isRoot(parentNode)){
                rootNodeFound = true;
            } else {
                //otherwise, move the pointers up the tree
                currentNode = parentNode;
                parentNode = currentNode.getParentNode();
            }

        }

        //return the resulting string
        return result;
    }

    /**
     * Edits the tree based on the given character. If the character already exists in the tree, then it increments its
     * frequency and checks that the tree is still in order. Otherwise, the NYT node is changed with a new parent node and the
     * new character node is added as its sibling.
     * @param charToAdd The character to add to the tree.
     */
    public void addCharToTree(char charToAdd) throws ParentDoesNotMatchChildException {

        //tries to find if the character is already in the tree by checking all of the child nodes.
        AdaptiveHuffmanNode nodeToEdit = this.getNodeFromTree(charToAdd);

        //holds a reference to the node which is being checked when navigating up the tree
        AdaptiveHuffmanNode nodeHolder = nodeToEdit;

        //determines whether the root node has been found or not
        boolean rootNodeFound = false;

        //if it is a new character for the tree then add it to NYT.
        //otherwise, check to see if the node needs to be replaced anywhere before incrementing frequency.
        if(nodeToEdit == null) {
            nodeToEdit = this.createNewNode(charToAdd);
            addNodeToList(nodeToEdit, 1);
        } else {
            updateNode(nodeToEdit);
            nodeToEdit.incrementFrequency();
        }

        //while there is a parent node
        while(!rootNodeFound) {
            //update the parent node and its position
            nodeHolder = nodeHolder.getParentNode();
            updateNode(nodeHolder);
            nodeHolder.incrementFrequency();
            if(this.isRoot(nodeHolder))
                rootNodeFound = true;
        }


    }

    /**
     * Updates the node and its position in the tree when another occurence of it happens.
     * @param node
     */
    private void updateNode(AdaptiveHuffmanNode node) throws ParentDoesNotMatchChildException {

        //if the node is the highest in the group then
        //edit the lists position in the list group
        if(!this.isHighestInWeightGroup(node)){
            this.editListPosition(node);
        } else {
            //removes the node from its previous list and adds it to its new list
            this.nodeWeightGroups.get(node.getFreq()).remove(node);
            this.addNodeToList(node, node.getFreq() + 1);
        }
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
     * Changes position in the frequency group lists and if so then carry out
     * the changes.
     * @param node The node to change list position for.
     */
    public void editListPosition(AdaptiveHuffmanNode node) throws ParentDoesNotMatchChildException {

        //get the linkedlist which holds the node passed to the function.
        LinkedList<AdaptiveHuffmanNode> firstList = this.nodeWeightGroups.get(node.getFreq());

        //the position of the node in the linkedlist.
        int position = firstList.indexOf(node);

        //the node at the end of the linked list, ie the one with the highest ID
        //now removed from the end of the list
        AdaptiveHuffmanNode highestNode = firstList.removeLast();

        //swap the two nodes in the tree position
        this.swapNodesInTree(node, highestNode);

        //set the replace position to the highest node, which
        //replaces the node to be removed
        firstList.set(position, highestNode);

        //add the node to its new list
        this.addNodeToList(node, node.getFreq() + 1);
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

    /**
     * Checks if the node passed to the function has the highest ID number in its weight group or not and thus if it
     * needs to be changed.
     * @param nodeToCheck The node to check.
     * @return True if it has the highest ID in the weight group, false if not and thus needs to be swapped.
     */
    private boolean isHighestInWeightGroup(AdaptiveHuffmanNode nodeToCheck){
        //gets the last element of the weight group
        AdaptiveHuffmanNode highestIdNodeInWeightGroup = this.nodeWeightGroups.get(nodeToCheck.getFreq()).getLast();

        //returns whether the reference to the highest weight in the group matches the reference to the node handed
        //to the function
        return (highestIdNodeInWeightGroup == nodeToCheck);
    }

    /**
     * Swaps the two nodes passed to the function in the adaptive huffman tree.
     * @param firstNode The first node to be swapped.
     * @param secondNode The second node to be swapped.
     */
    private void swapNodesInTree(AdaptiveHuffmanNode firstNode, AdaptiveHuffmanNode secondNode) throws ParentDoesNotMatchChildException {

        //swap the ids of the two nodes first
        this.swapIds(firstNode, secondNode);

        //then swap the parents of the two nodes
        this.swapParents(firstNode, secondNode);
    }

    /**
     * Swaps the IDs of the two nodes given to the function.
     * @param firstNode The first node to be swapped.
     * @param secondNode The second node to be swapped.
     */
    private void swapIds(AdaptiveHuffmanNode firstNode, AdaptiveHuffmanNode secondNode){
        //swap space for the id numbers when they need to be swapped
        int idSwapSpace = firstNode.getNodeId();

        //set the first node's id to the second node.
        firstNode.setNodeId(secondNode.getNodeId());

        //set the second node's id to the first node's by using the id held in the swap space.
        secondNode.setNodeId(idSwapSpace);

    }

    /**
     * Swaps the parents of the two nodes handed to the function.
     * @param firstNode The first node to be swapped.
     * @param secondNode The second node to be swapped.
     * @throws ParentDoesNotMatchChildException If there is an error in figuring out if a node is a either the left or right child of a parent,
     * then this exception is thrown.
     */
    private void swapParents(AdaptiveHuffmanNode firstNode, AdaptiveHuffmanNode secondNode) throws ParentDoesNotMatchChildException {
        //the parent node of the first node
        AdaptiveHuffmanNode firstParentNode = firstNode.getParentNode();

        //the parent node of the second node
        AdaptiveHuffmanNode secondParentNode = secondNode.getParentNode();

        //if the first node's parent node has the right child as the first node then make sure the new right child is
        //the second node
        if(firstParentNode.getRightChild() == firstNode)
            firstParentNode.setRightChild(secondNode);

            //if the first node's parent node has the left child as the first node then make sure the new left child is
            //the second node
        else if(firstParentNode.getLeftChild() == firstNode)
            firstParentNode.setLeftChild(secondNode);

            //if neither of its children are equal to the first node then throw an exception since one of the children of
            //the parent node should have been the first child
        else
            throw new ParentDoesNotMatchChildException();

        //if the second node's parent node has the right child as the second node then make sure the new right child is
        //the first node
        if(secondParentNode.getRightChild() == secondNode)
            secondParentNode.setRightChild(firstNode);

            //if the second node's parent node has the right child as the first node then make sure the new left child is
            //the first node
        else if(secondParentNode.getLeftChild() == secondNode)
            secondParentNode.setLeftChild(firstNode);

            //if neither of its children are equal to the first node then throw an exception since one of the children of
            //the parent node should have been the first child
        else
            throw new ParentDoesNotMatchChildException();
    }
}
