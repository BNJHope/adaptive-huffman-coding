package AdaptiveHuffmanCoding;

import AdaptiveHuffmanCoding.AdaptiveHuffmanNodes.AdaptiveHuffmanNode;

/**
 * Created by bnjhope on 03/10/16.
 */
public class TreePrinter {

    private AdaptiveHuffmanNode root;

    private int indent = 5;
    
    public void printTree(AdaptiveHuffmanNode root) {

        this.root = root;

        preorder(root, true, 0);
    }


    public void preorder(AdaptiveHuffmanNode currentNode, boolean lastChild, int previousIndentation) {

        if(currentNode != null) {

            if (currentNode == root) {
                System.out.println(String.format("%" + this.indent + "s", "") +  "└── " + printNode(currentNode));
            }
            else if (lastChild) {
                System.out.println(String.format("%" + this.indent + "s", "") +  "└── " + printNode(currentNode));

            }
            else {
                System.out.println(String.format("%" + this.indent + "s", "") +  "├── " + printNode(currentNode));
            }


            this.indent += 8;
            preorder(currentNode.getLeftChild(), false, this.indent - 8);
            preorder(currentNode.getRightChild(), true, this.indent - 8);
            this.indent -= 8;
        }
    }

    private String printNode(AdaptiveHuffmanNode node) {
        return node.getFreq() + " |" + node.getValue() + "| " + node.getNodeId();
    }
}
