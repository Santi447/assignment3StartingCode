/**
 * @author Group sonia: Santiago Pabon, Kaley Wood, Asad Arif, Dylan Dang
 * Southern Alberta Institute of Technology: CPRG 304
 * Assignment 3
 * Created: April 16 2026
 *
 * BSTreeNode -- stores one element in a binary search tree along with
 * references to its left and right child nodes.
 */
package implementations;

import java.io.Serializable;

public class BSTreeNode<E> implements Serializable
{
	private static final long serialVersionUID = 1L;
	private E element;
	private BSTreeNode<E> left;
	private BSTreeNode<E> right;
	
	/**
	 * Creates a node with the given element and no children.
	 *
	 * @param element the value to store in this node
	 */
	public BSTreeNode(E element) {
		
		this.element = element;
		this.left = null;
		this.right = null;
	}
	
	/**
	 * Creates a node with the given element and child references.
	 *
	 * @param element the value to store in this node
	 * @param left the node to link as the left child
	 * @param right the node to link as the right child
	 */
	public BSTreeNode(E element, BSTreeNode<E>left,BSTreeNode<E>right) {
		
		this.element = element;
		this.left = left;
		this.right = right;
	}
	
	/**
	 * Returns the element stored in this node.
	 *
	 * @return the current element
	 */	
	public E getElement() {
		return this.element;
	}
	
	/**
	 * Replaces the element stored in this node.
	 *
	 * @param elementToSet the new value to store
	 */
	public void setElement(E elementToSet) {
		this.element = elementToSet;
	}
	
	/**
	 * Returns the left child of this node.
	 *
	 * @return the left child, or null if none exists
	 */
	public BSTreeNode<E> getLeft(){
		return this.left;
	}
	
	/**
	 * Updates the left child reference of this node.
	 *
	 * @param leftToSet the node to set as the left child
	 */
	public void setLeft(BSTreeNode<E> leftToSet){
		this.left = leftToSet;
	}
	
	/**
	 * Returns the right child of this node.
	 *
	 * @return the right child, or null if none exists
	 */
	public BSTreeNode<E> getRight(){
		return this.right;
	}
	
	/**
	 * Updates the right child reference of this node.
	 *
	 * @param rightToSet the node to set as the right child
	 */
	public void setRight(BSTreeNode<E> rightToSet){
		this.right = rightToSet;
	}
	
	/**
	 * Returns true if this node has a left child.
	 *
	 * @return true when the left reference is not null
	 */
	public boolean hasLeftChild() {
		if(this.left == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if this node has a right child.
	 *
	 * @return true when the right reference is not null
	 */
	public boolean hasRightChild() {
		if(this.right == null){
			return false;
		}
		return true;
	}
	
	/**
	 * Returns true if this node has no children.
	 *
	 * @return true when both child references are null
	 */
	public boolean isLeaf() {
		if(this.left == null && this.right == null){
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the height of the subtree rooted at this node.
	 * Height is calculated as 1 plus the larger subtree height.
	 *
	 * @return the height of this node's subtree
	 */
	public int getHeight(){
		int leftHeight;
		int rightHeight;
		// if there is no left child, its subtree height is 0
		if(this.left == null) {
			leftHeight = 0;
		}
		else {
			leftHeight = this.left.getHeight();
		}
		// if there is no right child, its subtree height is 0
		if(this.right == null) {
			rightHeight = 0;
		}
		else {
			rightHeight = this.right.getHeight();
		}
		// the current node adds 1 to the taller side
		return 1 + Math.max(leftHeight, rightHeight);
	}
	
	/**
	 * Returns the total number of nodes in the subtree rooted at this node.
	 *
	 * @return the number of nodes in this subtree
	 */
	public int getNumberNodes() {
		int leftCount;
		int rightCount;
		
		// count nodes in the left subtree if it exists
		if(this.left == null) {
			leftCount = 0;
		}
		else {
			leftCount = this.left.getNumberNodes();
		}
		
		// count nodes in the right subtree if it exists
		if(this.right == null) {
			rightCount = 0;
		}
		else {
			rightCount = this.right.getNumberNodes();
		}
		// include this node in the total
		return 1 + (leftCount + rightCount);
	}
	
}
