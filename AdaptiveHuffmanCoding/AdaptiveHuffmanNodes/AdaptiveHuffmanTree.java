package AdaptiveHuffmanCoding.AdaptiveHuffmanNodes;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by bh59 on 22/09/16.
 */
public class AdaptiveHuffmanTree {

    /**
     * The ID of the next node
     */
    private int nextNodeId;

    /**
     * The root node of the application.
     * The NYT node of the application
     */
    private AdaptiveHuffmanNode root, NYTNode;

    /**
     * A hasmap that holds a reference to each node in the tree to save time by searching the tree for a specific node.
     * Allows an O(1) check time for determining if a node is in the tree or not as it can check whether the hashmap is null at
     * the space where a value should be.
     * Also allows O(1) for searching for node as each node is indexed in the hashmap by the integer representation of its value.
     */
    private HashMap<String, AdaptiveHuffmanNode> nodeArray = new HashMap();

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
        AdaptiveHuffmanNode NYT = new AdaptiveHuffmanNode(127, "");

        //since the frequency is set to 1 by default, we need to set the NYT node frequency to 0.
        NYT.setFreq(0);

        //set the root node and the NYT node to 0.
        this.root = NYT;
        this.NYTNode = NYT;

        this.nextNodeId = 126;
    }

    /**
     * Gets the Huffman code of that given value.
     * @param valueToGet The value to retrieve from the tree.
     * @return The Huffman code to be returned.
     */
    public String getHuffmanCode(String valueToGet) {
        //the result string
        String result = "";

        //determines whether the root node has been found or not for the string
        boolean rootNodeFound = false;

        //the current node being looked at
        AdaptiveHuffmanNode currentNode, parentNode;

        //if the empty string is passed then get the Huffman Code for the NYT node
        //otherwise, get the Huffman code for the string passed to the function
        if(valueToGet == "") {
            currentNode = this.NYTNode;
            if(this.isRoot(this.NYTNode))
                return "0";
        } else {
            currentNode = this.nodeArray.get(valueToGet);
        }

        //assign the parent node variable initially as the parent node of the current node
        parentNode  = currentNode.getParentNode();

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
     * Edits the tree based on the given value. If the value already exists in the tree, then it increments its
     * frequency and checks that the tree is still in order. Otherwise, the NYT node is changed with a new parent node and the
     * new value node is added as its sibling.
     * @param valueToAdd The value to add to the tree.
     */
    public void addCharToTree(String valueToAdd) {

        //tries to find if the value is already in the tree by checking all of the child nodes.
        AdaptiveHuffmanNode nodeToEdit = this.getNodeFromTree(valueToAdd);

        //holds a reference to the node which is being checked when navigating up the tree
        AdaptiveHuffmanNode nodeHolder;

        //determines whether the root node has been found or not
        boolean rootNodeFound = false;

        //if it is a new value for the tree then add it to NYT.
        //otherwise, check to see if the node needs to be replaced anywhere before incrementing frequency.
        if(nodeToEdit == null) {
            nodeToEdit = this.createNewNode(valueToAdd);

            //add the new nodes to the weight lists, with parent node first
            //since it has a higher ID property
            if(!this.isRoot(nodeToEdit.getParentNode()))
                addNodeToList(nodeToEdit.getParentNode(), 1);
            addNodeToList(nodeToEdit, 1);
            nodeToEdit = nodeToEdit.getParentNode();
        } else {
            this.updateNode(nodeToEdit, nodeToEdit.getFreq() + 1);
            nodeToEdit.incrementFrequency();
        }

        //reference must start at the nodeToEdit segment when moving up the tree
        nodeHolder = nodeToEdit;

        if(this.isRoot(nodeToEdit)) {
            rootNodeFound = true;
        }

        //while there is a parent node
        while(!rootNodeFound) {
            //update the parent node and its position
            nodeHolder = nodeHolder.getParentNode();

            //root is not in any weight groups so all we need to do with the root node is update its frequency
            if(this.isRoot(nodeHolder))
                rootNodeFound = true;
            else
                this.updateNode(nodeHolder, nodeHolder.getFreq() + 1);

                //update the node's frequency in case the child nodes are updated
            nodeHolder.incrementFrequency();

            if(this.isRoot(nodeHolder))
                rootNodeFound = true;
        }

    }

    /**
     * Spawns a new node using the NYT node of the tree and gives back a reference to this new node.
     * @param valueToAdd The value to add to the tree.
     * @return A reference to the new node which was just added.
     */
    private AdaptiveHuffmanNode createNewNode(String valueToAdd) {

        //the new node to be added to the tree.
        AdaptiveHuffmanNode newNode = new AdaptiveHuffmanNode(this.nextNodeId, valueToAdd);

        //the new parent node which will replace the position of the NYT node in the tree.
        AdaptiveHuffmanNode newParentNode = new AdaptiveHuffmanNode(this.NYTNode.getNodeId(), this.NYTNode, newNode);

        //we have added 2 new nodes - therefore we need to change the next node ID to 2 lower
        //than it previously was
        this.nextNodeId -= 2;

        //the new Id for the NYT node
        this.NYTNode.setNodeId(newParentNode.getNodeId() - 2);

        //sets the new node's parent node as the new parent node.
        newNode.setParentNode(newParentNode);

        if(this.NYTNode.getParentNode() != null) {
            if(this.NYTNode.getParentNode().getLeftChild() == this.NYTNode) {
                //sets the old parent node's left child as the new parent node
                this.NYTNode.getParentNode().setLeftChild(newParentNode);
                newParentNode.setParentNode(this.NYTNode.getParentNode());
            } else if (this.NYTNode.getParentNode().getRightChild() == this.NYTNode) {
                newParentNode.setParentNode(this.NYTNode.getParentNode());
                this.NYTNode.getParentNode().setRightChild(newParentNode);
            }
        } else {
            this.root = newParentNode;
        }

        //sets the NYT's new parent as the new parent node.
        this.NYTNode.setParentNode(newParentNode);

        //add the new node to the node hashmap
        this.nodeArray.put(valueToAdd, newNode);

        //returns a reference to the new node which was created.
        return newNode;
    }

    /**
     * Updates the node and its position in the tree when another occurence of it happens. Returns true if a tree swap was made, in which case the
     * rest of the tree was already updated.
     * @param node
     */
    private void updateNode(AdaptiveHuffmanNode node, int newFreq) {

        LinkedList<AdaptiveHuffmanNode> listToEdit;

        //if the node is the highest in the group then
        //edit the lists position in the list group
        if(this.isRoot(node.getParentNode())){
            if(!this.isHighestInWeightGroup(node))
                this.editListPosition(node, 0, newFreq);
            else {
                this.nodeWeightGroups.get(node.getFreq()).removeLast();
                this.addNodeToList(node, node.getFreq() + 1);
            }
        } else if(!this.isHighestInWeightGroup(node) && !this.parentIsHighestWeightInGroup(node)) {
            this.editListPosition(node, 0, newFreq);
        } else if (this.parentIsHighestWeightInGroup(node) && !this.isSecondHighestInWeightGroup(node)) {
            this.editListPosition(node, 1, newFreq);
        } else if (this.parentIsHighestWeightInGroup(node) && this.isSecondHighestInWeightGroup(node)){
            listToEdit = this.nodeWeightGroups.get(node.getFreq());
            listToEdit.remove(listToEdit.size() - 2);
            this.addNodeToList(node, newFreq);
        } else {
            //removes the node from its previous list and adds it to its new list
            this.nodeWeightGroups.get(node.getFreq()).removeLast();
            this.addNodeToList(node, newFreq);
        }

    }

    /**
     * Changes position in the frequency group lists and if so then carry out
     * the changes.
     * @param node The node to change list position for.
     * @param indexFromEnd How far in from the end the node is that needs to be removed
     */
    public void editListPosition(AdaptiveHuffmanNode node, int indexFromEnd, int newFreq) {

        //get the linkedlist which holds the node passed to the function.
        LinkedList<AdaptiveHuffmanNode> firstList = this.nodeWeightGroups.get(node.getFreq());

        //the position of the node in the linkedlist.
        int position = firstList.indexOf(node);

        //the node at the end of the linked list, ie the one with the highest ID
        //now removed from the end of the list
        AdaptiveHuffmanNode highestNode = firstList.remove(firstList.size() - indexFromEnd - 1);

        //set the replace position to the highest node, which
        //replaces the node to be removed
        firstList.set(position, highestNode);

        //add the node to its new list
        this.addNodeToList(node, newFreq);

        //swap the two nodes in the tree position
        this.swapNodesInTree(node, highestNode);

    }

    /**
     * Adds the new node to the list of other nodes with the same new frequency as this node.
     * @param node The node to add to the new list.
     * @param newFrequency The new frequency of the node, and thus the key in the hashmap of the new list where the node should be added.
     */
    private void addNodeToList(AdaptiveHuffmanNode node, int newFrequency){

        //Gets the list to add the new node to by getting the new frequency list from the map.
        LinkedList<AdaptiveHuffmanNode> newList = this.nodeWeightGroups.get(newFrequency);

        boolean placeFound = false;
        int stepper = 0;
        AdaptiveHuffmanNode currentStepperNode;

        //if there is no list in the map when given the frequency as a key then make a new list and add it to the map
        //at that frequency.
        if(newList == null) {
            newList = new LinkedList<AdaptiveHuffmanNode>();
            newList.addFirst(node);
            this.nodeWeightGroups.put(newFrequency, newList);
        } else {
            //if the node is being added to the new list it will have the lowest ID since it was previously from a lower weight class
            //therefore it can be added to the start wihtout having to check if the IDs are in order.
            if(newList.size() == 0) {
                newList.add(node);
            } else {
                while(!placeFound) {
                    currentStepperNode = newList.get(stepper);
                    if (currentStepperNode.getNodeId() > node.getNodeId()){
                        newList.add(stepper, node);
                        placeFound = true;
                    }
                    stepper++;
                    if(newList.size() <= stepper) {
                        newList.addLast(node);
                        placeFound = true;
                    }

                }
            }

        }

    }

    /**
     * Checks if the node passed to the function has the highest ID number in its weight group or not and thus if it
     * needs to be changed.
     * @param nodeToCheck The node to check.
     * @return True if it has the highest ID in the weight group, false if not and thus needs to be swapped.
     */
    private boolean isHighestInWeightGroup(AdaptiveHuffmanNode nodeToCheck){
        AdaptiveHuffmanNode highestIdNodeInWeightGroup = this.nodeWeightGroups.get(nodeToCheck.getFreq()).getLast();

        //returns whether the reference to the highest weight in the group matches the reference to the node handed
        //to the function
        return (highestIdNodeInWeightGroup == nodeToCheck);
    }

    /**
     * Determines if the given node is the second highest in its weight group
     * @param nodeToCheck The node to examine
     * @return True if the given node is the highest in the weight group, false if not.
     */
    private boolean isSecondHighestInWeightGroup(AdaptiveHuffmanNode nodeToCheck){
        LinkedList<AdaptiveHuffmanNode> listToCheck = this.nodeWeightGroups.get(nodeToCheck.getFreq());

        AdaptiveHuffmanNode secondHighestNode = listToCheck.get(listToCheck.size() - 2);

        return(secondHighestNode == nodeToCheck);
    }

    /**
     *
     * @param nodeToCheck
     * @return
     */
    private boolean parentIsHighestWeightInGroup(AdaptiveHuffmanNode nodeToCheck) {
        AdaptiveHuffmanNode highestNode = this.nodeWeightGroups.get(nodeToCheck.getFreq()).getLast();

        return nodeToCheck.getParentNode() == highestNode;
    }

    /**
     * Swaps the two nodes passed to the function in the adaptive huffman tree.
     * @param firstNode The first node to be swapped.
     * @param secondNode The second node to be swapped.
     */
    private void swapNodesInTree(AdaptiveHuffmanNode firstNode, AdaptiveHuffmanNode secondNode) {

        //swap the ids of the two nodes first
        this.swapIds(firstNode, secondNode);

        //then swap the parents of the two nodes
        this.swapParents(firstNode, secondNode);
    }

    /**
     * Swaps the parents of the two nodes handed to the function.
     * @param firstNode The first node to be swapped.
     * @param secondNode The second node to be swapped.
     * then this exception is thrown.
     */
    private void swapParents(AdaptiveHuffmanNode firstNode, AdaptiveHuffmanNode secondNode) {
        //the parent node of the first node
        AdaptiveHuffmanNode firstParentNode = firstNode.getParentNode();

        //the parent node of the second node
        AdaptiveHuffmanNode secondParentNode = secondNode.getParentNode();

        firstNode.setParentNode(secondParentNode);
        secondNode.setParentNode(firstParentNode);
        //if the first node's parent node has the right child as the first node then make sure the new right child is
        //the second node
        if(firstParentNode.getRightChild() == firstNode)
            firstParentNode.setRightChild(secondNode);

            //if the first node's parent node has the left child as the first node then make sure the new left child is
            //the second node
        else if(firstParentNode.getLeftChild() == firstNode)
            firstParentNode.setLeftChild(secondNode);

        //if the second node's parent node has the right child as the second node then make sure the new right child is
        //the first node
        if(secondParentNode.getRightChild() == secondNode)
            secondParentNode.setRightChild(firstNode);

            //if the second node's parent node has the right child as the first node then make sure the new left child is
            //the first node
        else if(secondParentNode.getLeftChild() == secondNode)
            secondParentNode.setLeftChild(firstNode);


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
     * Checks whether the node given to the function is the root node of the tree or not.
     * @param node The node to check.
     * @return True if the node is the root node, false if it is not.
     */
    private boolean isRoot(AdaptiveHuffmanNode node) {
        return node == this.root;
    }

    /**
     * Returns the root of the tree
     * @return the root of the tree
     */
    public AdaptiveHuffmanNode getRoot(){
        return this.root;
    }

    /**
     * Gets the next node of the tree when given a node and the index of either 0 or 1 for its left or right child
     * @param prev The previous node
     * @param index Value of 0 or 1 to determine if the left or right child should be selected
     * @return The left child if inde = 0, right child if index = 1
     */
    public AdaptiveHuffmanNode getNextNode(AdaptiveHuffmanNode prev, int index) {
        AdaptiveHuffmanNode result;
        String bp = "";
        if(index == '0'){
            bp = "";
            result = prev.getLeftChild();}
        else{
            bp = "";
            result = prev.getRightChild();}
        bp = "";
        return result;
    }

    /**
     * Determines whether the given node is the NYT node of the tree
     * @param node The node to check if it is the NYT node or not
     * @return True if it is the NYT node, false if it is not.
     */
    public boolean isNYT(AdaptiveHuffmanNode node) {
        return node == this.NYTNode;
    }

    /**
     * Determines whether a node is a leaf node or not by checking to see if it has any children or not.
     * @param node The node to check.
     * @return True if it has no children and thus is a leaf node, false if it is not.
     */
    public boolean isLeaf(AdaptiveHuffmanNode node) {
        return node.getLeftChild() == null;
    }

    /**
     * Determines if the string exists in the hashmap as a key yet or not, which in turn determines
     * if the string has been seen in the tree or not yet
     * @param str The string to check
     * @return True if it exists and therefore has already been seen, false if not.
     */
    public boolean stringExists(String str) {
        return this.nodeArray.containsKey(str);
    }

    /**
     * Determines whether the root of the tree is also the NYT node.
     * @return True if the NYT node is the root, false if it is not
     */
    public boolean rootIsNYT() {
        return this.isRoot(this.NYTNode);
    }


    /**
     * Gets the node from the tree that is associated with the value passed to the function.
     * @param valueToFind The value to find in the tree.
     * @return If the value has already been seen, then this function returns the node that represents that value in the tree.
     * Otherwise, it returns null.
     */
    private AdaptiveHuffmanNode getNodeFromTree(String valueToFind){
        //returns the contents of the hashmap at the address.
        return this.nodeArray.get(valueToFind);
    }
}
