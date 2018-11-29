
public class PercolationUF implements IPercolate {
	private final int VTOP;
	private final int VBOTTOM;
	protected int myOpenCount;
	protected boolean[][] myGrid;
	IUnionFind myFinder;
	protected boolean inBounds(int row, int col) {
		if (row < 0 || row >= myGrid.length) return false;
		
		if (col < 0 || col >= myGrid[0].length) return false;
		return true;
	}
	
	public PercolationUF(int size, IUnionFind finder) {
		VTOP = size*size;
		VBOTTOM = size*size+1;
		finder.initialize(size*size+2);
		myFinder = finder;
		myGrid = new boolean[size][size];
	}
	
	@Override 
	public void open (int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		
		if (!isOpen(row,col)) {
			myGrid[row][col] = true;
			myOpenCount+=1;
		}
		
		
		if (isOpen(row,col)) {
			if (row == 0) {
				myFinder.union(row*myGrid.length+col, VTOP);
			}
			if (row == myGrid.length-1) {
				myFinder.union((row*myGrid.length+col), VBOTTOM);
			}
			if (inBounds(row-1,col) && isOpen(row-1,col)) {
				myFinder.union((row*myGrid.length+col),(row-1)*myGrid.length+col);
			}
			if (inBounds(row+1,col) && isOpen(row+1,col)) {
				myFinder.union(row*myGrid.length+col,(row+1)*myGrid.length+col);
			}
			if (inBounds(row,col-1) && isOpen(row,col-1)) {
				myFinder.union(row*myGrid.length+col, row*myGrid.length+(col-1));
			}
			if (inBounds(row,col+1) && isOpen(row,col+1)) {
				myFinder.union(row*myGrid.length+col, row*myGrid.length+(col+1));
			}
		}
	}
	
	@Override
	public boolean isOpen(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		
		return myGrid[row][col];
	}
	
	@Override
	public boolean isFull(int row, int col) {
		if (! inBounds(row,col)) {
			throw new IndexOutOfBoundsException(
					String.format("(%d,%d) not in bounds", row,col));
		}
		if (myFinder.connected((row*myGrid.length+col), VTOP)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean percolates() {
		if (myFinder.connected(VTOP,VBOTTOM)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public int numberOfOpenSites() {
		return myOpenCount;
	}
}
