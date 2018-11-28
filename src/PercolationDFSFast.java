
public class PercolationDFSFast extends PercolationDFS {
	public PercolationDFSFast (int n) {
		super(n);
	}
	
	@Override
	protected void updateOnOpen (int row, int col){
		if (row == 0 || inBounds(row-1,col) && isFull(row-1,col) || inBounds(row+1,col) && isFull(row+1,col) || 
				inBounds(row,col-1)&& isFull(row,col-1) || inBounds(row,col+1)&&isFull(row,col+1)) {
			dfs(row,col);
		}
		return;
	}
}

//for UI.
//arrays.fill that takes an array and value
//so do a nested for loop for each row and fill each to become full

