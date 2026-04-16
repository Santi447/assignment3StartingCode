package implementations;

public class BSTreeNode<E>
{
	private E element;
	private BSTreeNode<E> left;
	private BSTreeNode<E> right;
	
	public BSTreeNode(E element) {
		
		this.element = element;
		this.left = null;
		this.right = null;
	}
	public BSTreeNode(E element, BSTreeNode<E>left,BSTreeNode<E>right) {
		
		this.element = element;
		this.left = left;
		this.right = right;
	}
	
	public E getElement() {
		return this.element;
	}
	public E setElement(E elementToSet) {
		return this.element = elementToSet;
	}
	public BSTreeNode<E> getLeft(){
		return this.left;
	}
	public BSTreeNode<E> getRight(){
		return this.right;
	}
	
	
}
