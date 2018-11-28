import java.util.LinkedList;
import java.util.Queue;

public class PercolationBFS extends PercolationDFSFast {
	public PercolationBFS(int n) {
		super(n);
	}
	@Override 
	protected void dfs (int row, int col) {
		Queue<Integer> qp = new LinkedList<>();
		int [] rowDelta = {-1,1,0,0};
		int [] colDelta = {0,0,-1,1};
		myGrid[row][col] = FULL;
		qp.add(row*myGrid.length+col);
		while (qp.size()!=0) {
			Integer yo = qp.remove();
			for (int k =0; k<rowDelta.length;k++) {
			//	row = row + rowDelta[k];
			//	col = col + colDelta[k];
				yo = (row + rowDelta[k])*myGrid.length + (col+colDelta[k]);
			//	int newrow  = yo/myGrid.length;
			//	int newcol = yo%myGrid.length;
				int newrow = row+rowDelta[k];
				int newcol = col+colDelta[k];
				if (inBounds(newrow,newcol) && isFull(newrow,newcol)) {
					break;
				}
				if (inBounds(newrow,newcol) && isOpen(newrow,newcol)) {
					qp.add(newrow*myGrid.length+newcol);
					myGrid[newrow][newcol] = FULL;
				}
			}
		}
				
	}
}
