package data_structure;

/**
 * Created by ChoHC on 2016. 9. 16..
 */
public class TwoThreeTree {
    private TwoThreeNode root;

    public TwoThreeTree(Integer initData) {
        root = new TwoThreeNode(null, initData);
    }

    public TwoThreeNode findTarNode(Integer item) {
        TwoThreeNode node = root;

        while (!node.isLeaf()) {
            if (node.getType() == 2) {
                if (item < node.getPack1())
                    node = node.getLeftNode();
                else
                    node = node.getMidNode();
            }
            else {
                if (item < node.getPack1())
                    node = node.getLeftNode();
                else if (item < node.getPack2())
                    node = node.getMidNode();
                else
                    node = node.getRightNode();
            }
        }

        return node;
    }

    public void traverse() {
        root.traverse();
    }

    public boolean add(Integer item) {
        TwoThreeNode node = findTarNode(item);
        TwoThreeNode parent = node.getParentNode();
        TwoThreeNode newGenNode;

        node.addPack(item);

        while (node.isOVFL()) {
            if (parent == null) {
                if (node.seperator() != null)
                    root = node.seperator();

                return true;
            }

            newGenNode = node.seperator();

            if (parent.getLeftNode() == node) {
                parent.setHiddenNode(parent.getRightNode());
                parent.setHiddenPack(parent.getPack2());
                parent.setRightNode(parent.getMidNode());
                parent.setPack2(parent.getPack1());
                parent.addPack(newGenNode.getPack1());
                parent.setMidNode(newGenNode.getMidNode());
                parent.setLeftNode(newGenNode.getLeftNode());
            }
            else if (parent.getMidNode() == node) {
                parent.setHiddenNode(parent.getRightNode());
                parent.setHiddenPack(parent.getPack2());
                parent.addPack(newGenNode.getPack1());
                parent.setRightNode(newGenNode.getMidNode());
                parent.setMidNode(newGenNode.getLeftNode());
            }
            else {
                parent.addPack(newGenNode.getPack1());
                parent.setHiddenNode(newGenNode.getMidNode());
                parent.setRightNode(newGenNode.getLeftNode());
            }

            newGenNode.getMidNode().setParentNode(parent);
            newGenNode.getLeftNode().setParentNode(parent);

            node = parent;
            parent = node.getParentNode();
        }

        return true;
    }

    public static void main(String[] args) {
        TwoThreeTree tree = new TwoThreeTree(1);
        tree.add(2);
        tree.add(3);
        tree.add(4);
        tree.add(5);
        tree.add(6);

        tree.traverse();
    }
}
