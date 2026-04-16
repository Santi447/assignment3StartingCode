/**
 * @author Group sonia: Santiago Pabon, Kaley Wood, Asad Ashrif, Dylan Dang
 * Southern Alberta Institute of Technology: CPRG 304
 * Assignment 3
 * Created: April 16 2026
 *
 * BSTree -- implements a binary search tree that stores comparable elements
 * and provides access to tree-based operations like searching, adding,
 * removing, and tree traversals.
 */
package implementations;

import utilities.BSTreeADT;
import utilities.Iterator;

public class BSTree<E extends Comparable<? super E>> implements BSTreeADT<E>
{
	private BSTreeNode<E> root;
	
	/**
	 * Creates an empty binary search tree.
	 */
	public BSTree() {
		this.root = null;
	}
	
	/**
	 * Creates a binary search tree with one root node.
	 *
	 * @param element the value to store at the root
	 */
	public BSTree(E element) {
		this.root = new BSTreeNode<E>(element);
	}
	
	/**
	 * Returns the root node of the tree.
	 *
	 * @return the root node
	 * @throws NullPointerException if the tree is empty
	 */	
	@Override
	public BSTreeNode<E> getRoot() throws NullPointerException { 
		
		if (this.root == null) {
			throw new NullPointerException();
			
		}
		return this.root ;
	}
	
	/**
	 * Returns the height of the tree.
	 *
	 * @return 0 if the tree is empty, otherwise the height of the root subtree
	 */
	@Override
	public int getHeight() {
		return (this.root == null) ? 0 : this.root.getHeight();
	}
	
	/**
	 * Returns the total number of nodes in the tree.
	 *
	 * @return 0 if the tree is empty, otherwise the number of nodes in the root subtree
	 */
	@Override
	public int size() {
		return (this.root == null) ? 0 : this.root.getNumberNodes();
	}


	/**
	 * Returns true if the tree has no root node.
	 *
	 * @return true if the tree is empty
	 */
	@Override
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Removes all nodes from the tree.
	 */
	@Override
	public void clear() {
		this.root = null;
	}

	@Override
	public boolean contains(E entry) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BSTreeNode<E> search(E entry) throws NullPointerException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(E newEntry) throws NullPointerException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BSTreeNode<E> removeMin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BSTreeNode<E> removeMax() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> inorderIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> preorderIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<E> postorderIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
