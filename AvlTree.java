import java.util.concurrent.TransferQueue;

public class AvlTree {
    Node root;

    int max(int a, int b){return (a>b)?a:b;}

    int height(Node node){
        if(node == null)
            return 0;
        return node.height;
    }

    int getBalance(Node node){
        if(node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    Node rightRotation(Node z){
      /* z                                      y
        / \                                   /   \
       y   T4      Right Rotate (z)          x      z
      / \          - - - - - - - - ->      /  \    /  \
     x   T3                               T1  T2  T3  T4
    / \
  T1   T2
  */
        Node y = z.left;
        Node T3 = y.right;
        //Правое вращение
        y.right = z;
        z.left = T3;

        //Обновление высоты, +1 т.к счёт высоты идёт: 0 - null, 1 - для одного узла, и т.д
        z.height = max(height(z.left), height(z.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;
        return y;
    }

    Node leftRotation(Node z){
        /*z                                y
         /  \                            /   \
        T1   y     Left Rotate(z)       z      x
            /  \   - - - - - - - ->    / \    / \
           T2   x                     T1  T2 T3  T4
               / \
             T3  T4*/

        Node y = z.right;
        Node T2 = y.left;
        //Вращение
        y.left = z;
        z.right = T2;
        //Обновление высоты, +1 т.к счёт высоты идёт: 0 - null, 1 - для одного узла, и т.д
        z.height = max(height(z.left),height(z.right)) + 1;
        y.height = max(height(y.left),height(y.right)) + 1;
        return y;
    }

    Node insertion(Node root, int key){
        if(root == null)
            return (new Node(key));
        if(key < root.key)
            root.left = insertion(root.left,key);
        else if(key > root.key)
            root.right = insertion(root.right,key);

        else
            return root;
        //Обновление высоты
        root.height = 1 + max(height(root.left),height(root.right));

        // Вычисление баланса
        int balance = getBalance(root);
        //a) Left Left Case -> Right Rotate(root)
        if(balance > 1 && key < root.left.key)
            return rightRotation(root);
            // Left Right Case -> Left Rotate(root.left) -> Right Rotate(root)
        else if(balance > 1 && key > root.left.key){
            /*   z                               z                           x
                / \                            /   \                        /  \
               y   T4  Left Rotate (y)        x    T4  Right Rotate(z)    y      z
              / \      - - - - - - - - ->    /  \      - - - - - - - ->  / \    / \
            T1   x                          y    T3                    T1  T2 T3  T4
                / \                        / \
              T2   T3                    T1   T2*/
            root.left = leftRotation(root.left);
            return rightRotation(root);
        }
        //c) Right Right Case -> Left Rotate(root)
        else if(balance < -1 && key > root.right.key)
            return leftRotation(root);
            //d) Right Left Case -> Right Rotate (root.right) -> Left Rotate(root)
        else if(balance < -1 && key < root.right.key){
            /* z                            z                            x
              / \                          / \                          /  \
            T1   y   Right Rotate (y)    T1   x      Left Rotate(z)   z      y
                / \  - - - - - - - - ->     /  \   - - - - - - - ->  / \    / \
               x   T4                      T2   y                  T1  T2  T3  T4
              / \                              /  \
            T2   T3                           T3   T4*/
            root.right = rightRotation(root.right);
            return leftRotation(root);
        }
        return root;
    }

    Node getMinKey(Node node){
        Node current = node.right;
        //Минимум правого потомка корневого узла
        while(current != null){
            if(current.left == null)
                break;
            current = current.left;
        }
        return current;
    }

    Node deleteNod(Node root, int key){
        //Searching
        if(root == null){
            System.out.println("Узла " + key+  " нет!!!");
            return root;
        }
        if(key > root.key)
            root.right = deleteNod(root.right,key);
        else if(key < root.key)
            root.left = deleteNod(root.left,key);
        if(root.key == key){
            if(root.left == null || root.right == null){
                Node temp = root.right;
                if(temp == null)
                    temp = root.left;
                //No child case
                if(temp == null){
                    return root = null;
                }
                //One child case
                else if(temp != null){
                    root = temp;
                    temp = null;
                }
            }
            else {//Two childes case
                Node temp = getMinKey(root);
                root.key = temp.key;
                root.right = deleteNod(root.right,temp.key);
            }
        }

        //Обновление высоты
        if(root == null)
            return root;
        root.height = max(height(root.left),height(root.right)) + 1;

        //Проверка баланса
        int balance = getBalance(root);
        if(balance > 1){//Left Case
            if( getBalance(root.left) >= 0 ) //LL
                return rightRotation(root);
            else if( getBalance(root.left) < 0 ){//LR
                root.left = leftRotation(root.left);
                return rightRotation(root);
            }
        }
        else if(balance < -1){//Right Case
            if( getBalance(root.right) <= 0 ) //RR
                return leftRotation(root);
            else if( getBalance(root.right) > 0 ){ //RL
                root.right = rightRotation(root.right);
                return leftRotation(root);
            }
        }
        return root;
    }

}
