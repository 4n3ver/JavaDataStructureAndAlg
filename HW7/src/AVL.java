import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Creates the mythical AVL Tree
 *
 * @author Yoel Ivan (yivan3@gatech.edu)
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {

    // Do not make any new instance variables.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public AVL() {
        clear();
    }

    /**
     * Initializes the AVL with the data in the collection. The data should be
     * added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        this();
        validate(data);
        data.forEach(this::add);
    }


    @Override
    public void add(T data) {
        validate(data);
        root = add(root, data);
    }

    /**
     * Helper method to traverse the tree recursively and put the new data into
     * its rightful place.
     *
     * @param node subtree to be traversed
     * @param data data to be added to the tree
     * @return the argument pass as node if the tree is not modified, otherwise
     * return new node to be placed to the parent node
     */
    private AVLNode<T> add(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(node.getRight(), data));
        }
        updateNode(node);
        return checkBalanceFactor(node);
    }

    /**
     * Update Node's height and balance factor.
     *
     * @param node {@link AVLNode} which height and balance factor to be
     *             updated.
     */
    private void updateNode(AVLNode<T> node) {
        int leftHeight = -1;
        int rightHeight = -1;
        if (node.getLeft() != null) {
            leftHeight = node.getLeft().getHeight();
        }
        if (node.getRight() != null) {
            rightHeight = node.getRight().getHeight();
        }
        node.setHeight(1 + Math.max(leftHeight, rightHeight));
        node.setBalanceFactor(leftHeight - rightHeight);
    }

    /**
     * Check balance factor of a node and initiate the necessary rotation when
     * applicable.
     *
     * @param node {@link AVLNode} which balance factor to be checked
     * @return the root of the subtree of the checked node
     */
    private AVLNode<T> checkBalanceFactor(AVLNode<T> node) {
        if (node.getBalanceFactor() == -2) {
            if (node.getRight().getBalanceFactor() == 1) { // right-left
                node.setRight(rightRotate((node.getRight())));
                return leftRotate(node);
            } else {
                return leftRotate(node);
            }
        } else if (node.getBalanceFactor() == 2) {
            if (node.getLeft().getBalanceFactor() == -1) { // left-right
                node.setLeft(leftRotate(node.getLeft()));
                return rightRotate(node);
            } else {
                return rightRotate(node);
            }
        }
        return node;
    }

    /**
     * Perform left rotation on a subtree.
     *
     * @param node root of the subtree where the rotation will be performed
     * @return root of the subtree
     */
    private AVLNode<T> leftRotate(AVLNode<T> node) {
        AVLNode<T> subroot = node.getRight();
        node.setRight(subroot.getLeft());
        subroot.setLeft(node);
        updateNode(node);
        updateNode(subroot);
        return subroot;
    }

    /**
     * Perform right rotation on a subtree.
     *
     * @param node root of the subtree where the rotation will be performed
     * @return root of the subtree
     */
    private AVLNode<T> rightRotate(AVLNode<T> node) {
        AVLNode<T> subroot = node.getLeft();
        node.setLeft(subroot.getRight());
        subroot.setRight(node);
        updateNode(node);
        updateNode(subroot);
        return subroot;
    }

    @Override
    public T remove(T data) {
        validate(data);
        T ori = data;
        AVLNode<T> temp = new AVLNode<>(data);
        root = remove(root, temp);  // might throw NoSuchElementException
        size--;
        return temp.getData();
    }

    /**
     * Remove method helper that recursively traverse the tree.
     *
     * @param node subtree to be traversed
     * @param data dummy node contained data to be returned by the original
     *             remove method
     * @return null or its child node if the node is removed, or original node
     * passed as argument
     * @throws NoSuchElementException if the data to be removed is not on the
     *                                tree
     */
    private AVLNode<T> remove(AVLNode<T> node, AVLNode<T> data) {
        if (node == null) {
            throw new NoSuchElementException("data is not found");
        } else if (data.getData().compareTo(node.getData()) == 0) {
            data.setData(node.getData());   // put into dummy node
            if (node.getLeft() == null && node.getRight() == null) {
                return null;
            } else if (node.getLeft() != null && node.getRight() == null) {
                return node.getLeft();
            } else if (node.getLeft() == null && node.getRight() != null) {
                return node.getRight();
            } else {    // node has 2 child
                AVLNode<T> predecessorData = new AVLNode<>(null);
                node.setLeft(removePredecessor(node.getLeft(),
                        predecessorData));
                node.setData(predecessorData.getData());
            }
        } else if (data.getData().compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data));
        } else {
            node.setRight(remove(node.getRight(), data));
        }
        updateNode(node);
        return checkBalanceFactor(node);
    }

    /**
     * Handles the case where a node to be removed has 2 child.
     *
     * @param node            subtree to be traversed
     * @param predecessorData dummy node to place predecessor's data
     * @return the original node passed as argument or node's child if the node
     * is deleted
     */
    private AVLNode<T> removePredecessor(AVLNode<T> node,
                                         AVLNode<T> predecessorData) {
        if (node.getRight() != null) {
            node.setRight(removePredecessor(node.getRight(), predecessorData));
            updateNode(node);
            return checkBalanceFactor(node);
        } else {
            predecessorData.setData(node.getData());
            return remove(node, node);
        }
    }

    @Override
    public T get(T data) {
        validate(data);
        AVLNode<T> fetched = fetchNode(root, data);
        if (fetched == null || fetched.getData().compareTo(data) != 0) {
            throw new NoSuchElementException("not here!");
        } else {
            return fetched.getData();
        }
    }

    @Override
    public boolean contains(T data) {
        validate(data);
        AVLNode<T> fetched = fetchNode(root, data);
        return fetched != null && fetched.getData().compareTo(data) == 0;
    }

    /**
     * Search the tree for given data.
     *
     * @param node subtree root to be searched
     * @param data data to be be searched
     * @return node from the tree having <code>data</code> or last visited node
     * before the search terminated or <code>null</code> if tree is empty.
     */
    private AVLNode<T> fetchNode(AVLNode<T> node, T data) {
        if (node != null) {
            if (data.compareTo(node.getData()) < 0 && node.getLeft() != null) {
                return fetchNode(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0
                    && node.getRight() != null) {
                return fetchNode(node.getRight(), data);
            }
        }
        return node;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> data = new ArrayList<>(size);
        preorder(root, data);
        return data;
    }

    /**
     * Return a list contained data on the tree in pre-order fashion.
     *
     * @param node subtree to be travered
     * @param data list to be returned
     */
    private void preorder(AVLNode<T> node, List<T> data) {
        if (node != null) {
            data.add(node.getData());
            preorder(node.getLeft(), data);
            preorder(node.getRight(), data);
        }
    }

    @Override
    public List<T> postorder() {
        List<T> data = new ArrayList<>(size);
        postorder(root, data);
        return data;
    }

    /**
     * Return a list contained data on the tree in post-order fashion.
     *
     * @param node subtree to be travered
     * @param data list to be returned
     */
    private void postorder(AVLNode<T> node, List<T> data) {
        if (node != null) {
            postorder(node.getLeft(), data);
            postorder(node.getRight(), data);
            data.add(node.getData());
        }
    }

    @Override
    public List<T> inorder() {
        List<T> data = new ArrayList<>(size);
        inorder(root, data);
        return data;
    }

    /**
     * Return a list contained data on the tree in in-order fashion.
     *
     * @param node subtree to be travered
     * @param data list to be returned
     */
    private void inorder(AVLNode<T> node, List<T> data) {
        if (node != null) {
            inorder(node.getLeft(), data);
            data.add(node.getData());
            inorder(node.getRight(), data);
        }
    }

    @Override
    public List<T> levelorder() {
        List<T> data = new ArrayList<>(size);
        Queue<AVLNode<T>> aux = new LinkedList<>();
        aux.add(root);
        for (int i = 0; i < size; i++) {
            AVLNode<T> node = aux.remove();
            data.add(node.getData());
            if (node.getLeft() != null) {
                aux.add(node.getLeft());
            }
            if (node.getRight() != null) {
                aux.add(node.getRight());
            }
        }
        return data;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        return heightHelper(root);
    }

    /**
     * Calculate the height of the tree.
     *
     * @param node subtree to be traversed
     * @return height of the tree
     */
    private int heightHelper(AVLNode<T> node) {
        if (node != null) {
            return 1 + Math.max(heightHelper(node.getLeft()), heightHelper(
                    node.getRight()));
        } else {
            return -1;
        }
    }

    @Override
    public int depth(T data) {
        validate(data);
        return depthHelper(root, data);
    }

    /**
     * Calculate the depth of a node holding data passed as argument.
     *
     * @param node subtree to be traversed
     * @param data data to be found
     * @return the depth of the data in the tree
     * @throws NoSuchElementException if the data is not on the tree
     */
    private int depthHelper(AVLNode<T> node, T data) {
        if (node != null) {
            if (data.compareTo(node.getData()) == 0) {
                return 1;
            } else if (data.compareTo(node.getData()) < 0) {
                return 1 + depthHelper(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return 1 + depthHelper(node.getRight(), data);
            }
        }
        throw new NoSuchElementException("data is nowhere on the tree");
    }

    /**
     * Validate passed argument.
     *
     * @param data object to be validated
     * @throws IllegalArgumentException when the object is null (invalid)
     */
    private void validate(Object data) {
        if (data == null) {
            throw new IllegalArgumentException("data is null");
        }
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES. DO NOT USE IT IN YOUR CODE DO
     * NOT CHANGE THIS METHOD
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
        return root;
    }
}
