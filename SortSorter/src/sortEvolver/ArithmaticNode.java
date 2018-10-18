package sortEvolver;

public class ArithmaticNode {
	public static enum Operators {ADDITION, SUBTRACTION, MULTIPLICATION, DIVISION}
	public static enum Variables {FIRSTAVAIL, LASTAVAIL, PICKEDNUM, TWO, ONE, ZERO}
	
	public static Operators[] OpList = Operators.values();
	public static Variables[] VarList = Variables.values();
	
	private ArithmaticNode left = null;
	private ArithmaticNode right = null;
	private ArithmaticNode parent = null;
	
	private boolean isOperator;
	private Operators operation;
	private Variables variable;
	
	private int size;
	
	public static ArithmaticNode makeRandomNode(int depth) {
		if (depth < 1) {
			return null;
		} else if (depth == 1) {
			return new ArithmaticNode(VarList[SortEvolver.rand.nextInt(VarList.length)]);
		} else {
			return new ArithmaticNode(OpList[SortEvolver.rand.nextInt(OpList.length)], makeRandomNode(depth-1), makeRandomNode(depth-1));
		}
	}
	
	public ArithmaticNode(Operators op, ArithmaticNode newLeft, ArithmaticNode newRight) {
		isOperator = true;
		operation = op;

		left = newLeft;
		right = newRight;
		
		left.parent = this;
		right.parent = this;
		
		size = left.size + 1 + right.size;
	}
	
	public ArithmaticNode(Variables var) {
		isOperator = false;
		variable = var;
		
		size = 1;
	}
	
	@Override
	public ArithmaticNode clone() {
		ArithmaticNode newNode;
		
		if (isOperator) {
			newNode = new ArithmaticNode(operation, left.clone(), right.clone());
			
		} else {
			newNode = new ArithmaticNode(variable);
		}
		
		return newNode;
	}
	
	public int evaluate(int firstAvail, int lastAvail, int pickedNum) {
		if (isOperator) {
			int l = left.evaluate(firstAvail, lastAvail, pickedNum);
			int r = right.evaluate(firstAvail, lastAvail, pickedNum);
			
			switch (operation) {
			case ADDITION:
				return l+r;
			case SUBTRACTION:
				return l-r;
			case MULTIPLICATION:
				return l*r;
			case DIVISION:
				return l/r; // Note: This is integer division, so will automatically concatenate to the whole number.
			}
		} else {
			switch (variable) {
			case FIRSTAVAIL:
				return firstAvail;
			case LASTAVAIL:
				return lastAvail;
			case PICKEDNUM:
				return pickedNum;
			case TWO:
				return 2;
			case ONE:
				return 1;
			case ZERO:
				return 0;
			}
		}
		
		return 0; // Defaults to 0 if a new enum is added but not implemented here.
	}
	
	public int getSize() {
		return size;
	}
	
	public ArithmaticNode getNode(int index) {
		if (index < 0 || index >= size) {
			return null;
		}
		
		if (!isOperator) {
			return this;
		}
		
		if (index == left.size) {
			return this;
		} else if (index < left.size) { 
			return left.getNode(index);
		} else {
			return right.getNode(index - left.size - 1);
		}
	}

	public ArithmaticNode getParent() {
		return parent;
	}
	public ArithmaticNode getLeft() {
		return left;
	}
	public ArithmaticNode getRight() {
		return right;
	}

	public void setParent(ArithmaticNode an) {
		parent = an;
	}
	public void setLeft(ArithmaticNode an) {
		left = an;
		left.parent = this;
	}
	public void setRight(ArithmaticNode an) {
		right = an;
		right.parent = this;
	}
	
	public void randomize() {
		if (isOperator) {
			operation = OpList[SortEvolver.rand.nextInt(OpList.length)];
		} else {
			variable = VarList[SortEvolver.rand.nextInt(VarList.length)];
		}
	}
	
	private void propogateSize() {
		if (isOperator) {
			size = left.size + 1 + right.size;
		} else {
			size = 1;
		}
		
		if (parent == null) {
			return;
		}
		
		parent.propogateSize();
	}
	
}
