package sortEvolver;

public class TreeArithmatic {

	private ArithmaticNode root;
	
	@Override
	public TreeArithmatic clone() {
		// TODO
		
		return null;
	}

	public int solveFor(int firstAvail, int lastAvail, int pickedNum) {
		int nextPick;
		
		nextPick = root.evaluate(firstAvail, lastAvail, pickedNum);
		
		return Math.min(lastAvail, Math.max(firstAvail, nextPick));
	}
	
	public void swapWith(TreeArithmatic ta) {
		ArithmaticNode toGive = root.getNode(SortEvolver.rand.nextInt(root.getSize()));
		ArithmaticNode toTake = ta.root.getNode(SortEvolver.rand.nextInt(ta.root.getSize()));
		
		ArithmaticNode thisParent = toGive.getParent();
		ArithmaticNode otherParent = toTake.getParent();
		
		if (toGive == thisParent.getLeft()) {
			thisParent.setLeft(toTake);
		} else {
			thisParent.setLeft(toTake);
		}
		
		if (toTake == otherParent.getLeft()) {
			otherParent.setLeft(toTake);
		} else {
			otherParent.setLeft(toTake);
		}
		
		
	}
	
	public void mutate() {
		ArithmaticNode mutation = root.getNode(SortEvolver.rand.nextInt(root.getSize()));
		
		mutation.randomize();
	}
}
