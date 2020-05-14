public class Demo {

    void sort(Node node) {
        if (node != null) {
            sort(node.left);
            System.out.print(node.key + " ");
            sort(node.right);
        }
    }

    void print(Node node) {
        if (node != null) {
            System.out.print(node.key + " ");
            print(node.left);
            print(node.right);
        }
    }

    public static void main(String []args) {
        AvlTree tree = new AvlTree();
        Demo demo = new Demo();
        BTreePrinter printer = new BTreePrinter();
        int arr[] = {5,3,6,2,4,7,6,9,1,2,8,10,11,12,13,14,15};
        for(int i: arr)
            tree.root = tree.insertion(tree.root, i);
        System.out.println("###### Исходное дерево #####");
        BTreePrinter.printNode1(tree.root);
        tree.root = tree.deleteNod(tree.root,3);
        tree.root = tree.deleteNod(tree.root,9);
        tree.root = tree.deleteNod(tree.root,10);
        tree.root = tree.deleteNod(tree.root,15);
        System.out.println("Удалили 3,9,10,15");
        BTreePrinter.printNode1(tree.root);

        System.out.println("Sorting");
        demo.sort(tree.root);

    }
}
