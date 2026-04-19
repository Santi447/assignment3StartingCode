/**
 * @author Group sonia: Santiago Pabon, Kaley Wood, Asad Arif, Dylan Dang
 * Southern Alberta Institute of Technology: CPRG 304
 * Assignment 3
 * Created: April 16 2026
 *
 * BSTree -- implements a binary search tree that stores comparable elements
 * and provides access to tree-based operations like searching, adding,
 * removing, and tree traversals.
 */
package implementations;

import java.io.Serializable;
import java.util.NoSuchElementException;
import java.util.Stack;

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

	/**
	 * Returns an iterator that walks the tree in-order (left, node, right).
	 * This produces elements in ascending sorted order.
	 *
	 * @return an in-order iterator over the tree's elements
	 */	
	@Override
	public Iterator<E> inorderIterator() {
		return new InorderIterator();
	}

	/**
	 * Returns an iterator that walks the tree in pre-order (node, left, right).
	 * The root element comes first.
	 *
	 * @return a pre-order iterator over the tree's elements
	 */
	@Override
	public Iterator<E> preorderIterator() {
		return new PreorderIterator();
	}

	/**
	 * Returns an iterator that walks the tree in post-order (left, right, node).
	 * The root element comes last.
	 *
	 * @return a post-order iterator over the tree's elements
	 */
	@Override
	public Iterator<E> postorderIterator() {
		return new PostorderIterator();
	}

	// ======================== Iterator Inner Classes ========================

	/**
	 * In-order iterator -- traverses left subtree, visits node, then right subtree.
	 * Creates a safe snapshot of the tree into an array at construction time,
	 * so changes to the tree after creating the iterator won't affect it.
	 */
	private class InorderIterator implements Iterator<E> 
	{
		private E[] elements;
		private int position;

		/**
		 * Builds the snapshot array by doing an iterative in-order traversal
		 * using a stack. Left -> node -> right gives us sorted order.
		 */
		@SuppressWarnings("unchecked")
		public InorderIterator() {
			elements = (E[]) new Comparable[size()]; // create array to hold elements
			position = 0;

			if (root == null) {
				return; // empty tree, nothing to do
			}

			// Use a stack to do iterative in-order traversal
			Stack<BSTreeNode<E>> travStack = new Stack<>();
			BSTreeNode<E> current = root;
			int index = 0;

			while (current != null || !travStack.isEmpty()) {
				// push all left children onto the stack first
				while (current != null) {
					travStack.push(current);
					current = current.getLeft();
				}

				// pop the next node, grab its element, then go right
				current = travStack.pop();
				elements[index++] = current.getElement();
				current = current.getRight();
			}
		}

		@Override
		public boolean hasNext() {
			return position < elements.length;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			return elements[position++];
		}
	}

	/**
	 * Pre-order iterator -- visits the node first, then left subtree, then right.
	 * Uses a stack: push right child before left so that left gets processed first.
	 */
	private class PreorderIterator implements Iterator<E> {
		private E[] elements;
		private int position;

		/**
		 * Builds the snapshot array with an iterative pre-order traversal.
		 * Node -> left -> right.
		 */
		@SuppressWarnings("unchecked")
		public PreorderIterator() {
			elements = (E[]) new Comparable[size()];
			position = 0;

			if (root == null) {
				return;
			}

			Stack<BSTreeNode<E>> travStack = new Stack<>();
			travStack.push(root);
			int index = 0;

			while (!travStack.isEmpty()) {
				BSTreeNode<E> current = travStack.pop();

				// visit the node first
				elements[index++] = current.getElement();

				// push right before left so left is popped (processed) first
				if (current.hasRightChild()) {
					travStack.push(current.getRight());
				}
				if (current.hasLeftChild()) {
					travStack.push(current.getLeft());
				}
			}
		}

		@Override
		public boolean hasNext() {
			return position < elements.length;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			return elements[position++];
		}
	}

	/**
	 * Post-order iterator -- visits left subtree, then right subtree, then the
	 * node.
	 * Uses two stacks: one to process nodes, one to collect them in reverse
	 * post-order.
	 */
	private class PostorderIterator implements Iterator<E> {
		private E[] elements;
		private int position;

		/**
		 * Builds the snapshot array with an iterative post-order traversal.
		 * Left -> right -> node.
		 *
		 * The two-stack approach: push nodes onto stack1, pop them and push onto
		 * stack2 (pushing left then right children onto stack1). When stack1 is
		 * empty, stack2 holds everything in post-order top to bottom.
		 */
		@SuppressWarnings("unchecked")
		public PostorderIterator() {
			elements = (E[]) new Comparable[size()];
			position = 0;

			if (root == null) {
				return;
			}

			Stack<BSTreeNode<E>> stack1 = new Stack<>();
			Stack<BSTreeNode<E>> stack2 = new Stack<>();

			stack1.push(root);

			// build up stack2 in reverse post-order
			while (!stack1.isEmpty()) {
				BSTreeNode<E> current = stack1.pop();
				stack2.push(current);

				// push left first so right ends up on top of stack1
				if (current.hasLeftChild()) {
					stack1.push(current.getLeft());
				}
				if (current.hasRightChild()) {
					stack1.push(current.getRight());
				}
			}

			// pop stack2 to get elements in correct post-order
			int index = 0;
			while (!stack2.isEmpty()) {
				elements[index++] = stack2.pop().getElement();
			}
		}

		@Override
		public boolean hasNext() {
			return position < elements.length;
		}

		@Override
		public E next() throws NoSuchElementException {
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			return elements[position++];
		}
	}	
}
