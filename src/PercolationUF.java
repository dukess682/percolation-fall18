
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
	//if cell is full, its placed in the same set as VTOP. make a union by connecting 4 and VTOP. 4 represents the cell 0,4. 
	//you know bc unionfind algorithm excepts a single integer for every cell. create getindex helper method to take row column and convert it into a number. 
	//Connect(4,VTOP)
	//1,4 is adjacent to open cells. CHECK ITS NEIGHBORS, and if the neighbor is also open,
	//you union them into the same groups. so this is before you do any filling
	//so do connect(9,4) and connect(9.14). not recursive
	//when open cell on last row, connect(23,VBOTTOM) and union it w the bottom. and bc its adjacent to an open cell, connect(23,14).
	//so when you open a cell, check its four neighbors, and union it with its neighbors if any neighbor is also open.
	//THEN basically if VTOP and VBOTTOM are connected, the system percolates!
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
				myFinder.union((row+1)*col+row, VTOP);
			}
			if (row == myGrid.length-1) {
				myFinder.union((row+1)*col+row, VBOTTOM);
			}
			if (inBounds(row-1,col) && isOpen(row-1,col)) {
				myFinder.union((row+1)*col+row,row*col+row-1);
			}
			if (inBounds(row+1,col) && isOpen(row+1,col)) {
				myFinder.union((row+1)*col+row,(row+2)*col+row+1);
			}
			if (inBounds(row,col-1) && isOpen(row,col-1)) {
				myFinder.union((row+1)*col+row, (row+1)*(col-1)+row+1);
			}
			if (inBounds(row,col+1) && isOpen(row,col+1)) {
				myFinder.union((row+1)*col+row, (row+1)*(col+1)+row+1);
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
		if (myFinder.connected((row+1)*col+row, VTOP)) {
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
