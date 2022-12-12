package com.example.cs230;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates nodes and sets their properties for the trees that are needed
 * for the Breadth First Search that the smart thief executes.
 *
 * @param <T>
 * @author Vic
 */
public class Node<T> {
    private T data = null;
    private List<Node<T>> children = new ArrayList<>();
    private Node<T> parent = null;

    /**
     * create nodes for the tree.
     *
     * @param data data to go into tree node
     */
    public Node(T data) {
        this.data = data;
    }

    /**
     * Add a child node to a parent node.
     *
     * @param child the child node
     * @return the node as a child of the parent
     */
    public Node<T> addChild(Node<T> child) {
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    /**
     * get child node.
     *
     * @return child node
     */
    public List<Node<T>> getChildren() {
        return children;
    }

    /**
     * get data.
     *
     * @return data
     */
    public T getData() {
        return data;
    }

    /**
     * Sets the node as a parent node.
     *
     * @param parent the parent node
     */
    private void setParent(Node<T> parent) {
        this.parent = parent;
    }

    /**
     * gets the node as a parent node.
     *
     * @return parent
     */
    public Node<T> getParent() {
        return parent;
    }
}
