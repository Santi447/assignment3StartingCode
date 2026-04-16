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
	
	public void setElement(E elementToSet) {
		this.element = elementToSet;
	}
	
	public BSTreeNode<E> getLeft(){
		return this.left;
	}
	
	public void setLeft(BSTreeNode<E> leftToSet){
		this.left = leftToSet;
	}
	
	public BSTreeNode<E> getRight(){
		return this.right;
	}
	
	public void setRight(BSTreeNode<E> rightToSet){
		this.right = rightToSet;
	}
	
	public boolean hasLeftChild() {
		if(this.left == null){
			return false;
		}
		return true;
	}
	
	public boolean hasRightChild() {
		if(this.right == null){
			return false;
		}
		return true;
	}
	
	public boolean isLeaf() {
		if(this.left == null && this.right == null){
			return true;
		}
		return false;
	}
	
	public int getHeight(){
		int leftHeight;
		int rightHeight;
		if(this.left == null) {
			leftHeight = 0;
		}
		else {
			leftHeight = left.getHeight();
		}
		if(right == null) {
			rightHeight = 0;
		}
		else {
			rightHeight = this.right.getHeight();
		}
		return 1 + Math.max(leftHeight, rightHeight);
	}
	
	public int getNumberNodes() {
		int leftCount;
		int rightCount;
		if(this.left == null) {
			leftCount = 0;
		}
		else {
			leftCount = this.left.getNumberNodes();
		}
		if(this.right == null) {
			rightCount = 0;
		}
		else {
			rightCount = this.right.getNumberNodes();
		}
		return 1 + (leftCount + rightCount);
	}
	
}
