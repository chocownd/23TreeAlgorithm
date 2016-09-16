package data_structure;

import sun.misc.*;

/**
 * Created by ChoHC on 2016. 9. 16..
 */
public class TwoThreeNode {
    private TwoThreeNode parentNode;
    private TwoThreeNode leftNode;
    private TwoThreeNode midNode;
    private TwoThreeNode rightNode;
    private TwoThreeNode hiddenNode;
    private Integer pack1;
    private Integer pack2;
    private Integer hiddenPack;
    private Integer type;

    public TwoThreeNode(TwoThreeNode parentNode, Integer pack1) {
        this.parentNode = parentNode;
        this.leftNode = null;
        this.midNode = null;
        this.rightNode = null;
        this.hiddenNode = null;
        this.pack1 = pack1;
        this.pack2 = null;
        this.hiddenPack = null;
        this.type = 2;
    }

    public TwoThreeNode getParentNode() {
        return parentNode;
    }

    public void setParentNode(TwoThreeNode parentNode) {
        this.parentNode = parentNode;
    }

    public TwoThreeNode getLeftNode() {
        return leftNode;
    }

    public void setLeftNode(TwoThreeNode leftNode) {
        this.leftNode = leftNode;
    }

    public TwoThreeNode getMidNode() {
        return midNode;
    }

    public void setMidNode(TwoThreeNode midNode) {
        this.midNode = midNode;
    }

    public TwoThreeNode getRightNode() {
        return rightNode;
    }

    public void setRightNode(TwoThreeNode rightNode) {
        this.rightNode = rightNode;
    }

    public TwoThreeNode getHiddenNode() {
        return hiddenNode;
    }

    public void setHiddenNode(TwoThreeNode hiddenNode) {
        this.hiddenNode = hiddenNode;
    }

    public Integer getPack1() {
        return pack1;
    }

    public void setPack1(Integer pack1) {
        this.pack1 = pack1;
    }

    public Integer getPack2() {
        return pack2;
    }

    public void setPack2(Integer pack2) {
        this.pack2 = pack2;
    }

    public Integer getHiddenPack() {
        return hiddenPack;
    }

    public void setHiddenPack(Integer hiddenPack) {
        this.hiddenPack = hiddenPack;
    }

    public Integer getType() {
        return type;
    }

    public boolean setType(Integer type) {
        if (type == 2 || type == 3 || type == 4) {
            this.type = type;

            return true;
        }
        else {
            return false;
        }
    }

    public boolean isOVFL() {
        return type > 3;
    }

    public boolean isLeaf() {
        if (type == 2)
            return getLeftNode() == null && getMidNode() == null;
        else if (type == 3)
            return getLeftNode() == null && getMidNode() == null &&
                getRightNode() == null;
        else
            return getLeftNode() == null && getMidNode() == null &&
                getRightNode() == null && getHiddenNode() == null;
    }

    public TwoThreeNode seperator() {
        if (!isOVFL())
            return null;

        TwoThreeNode newTop = new TwoThreeNode(parentNode, pack2);
        TwoThreeNode newLeft = new TwoThreeNode(newTop, pack1);
        TwoThreeNode newMid = new TwoThreeNode(newTop, hiddenPack);

        newTop.leftNode = newLeft;
        newTop.midNode = newMid;

        newLeft.leftNode = leftNode;
        newLeft.midNode = midNode;

        newMid.leftNode = rightNode;
        newMid.midNode = hiddenNode;

        return newTop;
    }

    public boolean addPack(Integer newPack) {
        if (isOVFL())
            return false;

        if (type == 2) {
            if (newPack < pack1) {
                pack2 = pack1;
                pack1 = newPack;
            }
            else {
                pack2 = newPack;
            }
        }
        else {
            if (newPack < pack1) {
                hiddenPack = pack2;
                pack2 = pack1;
                pack1 = newPack;
            }
            else if (newPack < pack2) {
                hiddenPack = pack2;
                pack2 = newPack;
            }
            else {
                hiddenPack = newPack;
            }
        }

        setType(type + 1);

        return true;
    }

    public void traverse() {
        Queue<TwoThreeNode> nodeQueue = new Queue<>();
        Queue<Integer> cntQueue = new Queue<>();
        TwoThreeNode currNode;
        int cnt;
        int currCnt;
        int level = 1;

        nodeQueue.enqueue(this);
        cntQueue.enqueue(1);

        try {
            while (!nodeQueue.isEmpty()) {
                currCnt = cntQueue.dequeue();

                cnt = 0;
                System.out.println("level " + level);
                for (int i = 0; i < currCnt; i++) {
                    currNode = nodeQueue.dequeue();

                    System.out.println("node : " + currNode);
                    System.out.println("pack1 : " + currNode.getPack1());
                    System.out.println("pack2 : " + currNode.getPack2());

                    if (currNode.getLeftNode() != null) {
                        cnt += 1;
                        nodeQueue.enqueue(currNode.getLeftNode());
                    }
                    if (currNode.getMidNode() != null) {
                        cnt += 1;
                        nodeQueue.enqueue(currNode.getMidNode());
                    }

                    if (currNode.getRightNode() != null) {
                        cnt += 1;
                        nodeQueue.enqueue(currNode.getRightNode());
                    }
                }

                cntQueue.enqueue(cnt);

                level += 1;
            }
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
    }
}
