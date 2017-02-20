import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * BSTInterface implementation.
 *
 * @author Yoel Ivan (yivan3@gatech.edu)
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Initializes the BST with the data in the collection. The data in the BST
     * should be added in the same order it is in the collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> data) {
        this();
        validateData(data);
        data.forEach(this::add);
    }

    @Override
    public void add(T data) {
        validateData(data);
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
    private BSTNode<T> add(BSTNode<T> node, T data) {
        if (node == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(add(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(add(node.getRight(), data));
        }
        return node;
    }

    @Override
    public T remove(T data) {
        T ori = data;
        validateData(data);
        BSTNode<T> temp = new BSTNode<>(data);
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
    private BSTNode<T> remove(BSTNode<T> node, BSTNode<T> data) {
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
                BSTNode<T> successorData = new BSTNode<>(null);
                node.setRight(removeSuccessor(node.getRight(), successorData));
                node.setData(successorData.getData());
                return node;
            }
        } else if (data.getData().compareTo(node.getData()) < 0) {
            node.setLeft(remove(node.getLeft(), data));
            return node;
        } else {
            node.setRight(remove(node.getRight(), data));
            return node;
        }
    }

    /**
     * Handles the case where a node to be removed has 2 child.
     *
     * @param node          subtree to be traversed
     * @param successorData dummy node to place successor data
     * @return the original node passed as argument or node's child if the node
     * is deleted
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> node,
                                       BSTNode<T> successorData) {
        if (node.getLeft() != null) {
            node.setLeft(removeSuccessor(node.getLeft(), successorData));
            return node;
        } else {
            successorData.setData(node.getData());
            return remove(node, node);
        }
    }

    @Override
    public T get(T data) {
        validateData(data);
        BSTNode<T> node = fetchNode(root, data);
        if (node == null) {
            throw new NoSuchElementException("data is not found");
        }
        return node.getData();
    }

    @Override
    public boolean contains(T data) {
        validateData(data);
        return fetchNode(root, data) != null;
    }

    /**
     * Search the tree for given data.
     *
     * @param node subtree root to be searched
     * @param data data to be be searched
     * @return node from the tree having <code>data</code> or <code>null</code>
     * if not found
     */
    private BSTNode<T> fetchNode(BSTNode<T> node, T data) {
        if (node == null) {
            return null;
        } else if (data.compareTo(node.getData()) < 0) {
            return fetchNode(node.getLeft(), data);
        } else if (data.compareTo(node.getData()) > 0) {
            return fetchNode(node.getRight(), data);
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
    private void preorder(BSTNode<T> node, List<T> data) {
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
    private void postorder(BSTNode<T> node, List<T> data) {
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
    private void inorder(BSTNode<T> node, List<T> data) {
        if (node != null) {
            inorder(node.getLeft(), data);
            data.add(node.getData());
            inorder(node.getRight(), data);
        }
    }

    @Override
    public List<T> levelorder() {
        List<T> data = new ArrayList<>(size);
        Queue<BSTNode<T>> aux = new LinkedList<>();
        aux.add(root);
        for (int i = 0; i < size; i++) {
            BSTNode<T> node = aux.remove();
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
        return height(root);
    }

    /**
     * Calculate the height of the tree.
     *
     * @param node subtree to be traversed
     * @return height of the tree
     */
    private int height(BSTNode<T> node) {
        if (node != null) {
            return 1 + Math.max(height(node.getLeft()), height(
                    node.getRight()));
        } else {
            return -1;
        }
    }

    @Override
    public int depth(T data) {
        validateData(data);
        try {
            return depth(root, data);
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * Calculate the depth of a node holding data passed as argument.
     *
     * @param node subtree to be traversed
     * @param data data to be found
     * @return the depth of the data in the tree
     * @throws NoSuchElementException if the data is not on the tree
     */
    private int depth(BSTNode<T> node, T data) {
        if (node != null) {
            if (data.compareTo(node.getData()) == 0) {
                return 1;
            } else if (data.compareTo(node.getData()) < 0) {
                return 1 + depth(node.getLeft(), data);
            } else if (data.compareTo(node.getData()) > 0) {
                return 1 + depth(node.getRight(), data);
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
    private void validateData(Object data) {
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
    public BSTNode<T> getRoot() {
        return root;
    }
}