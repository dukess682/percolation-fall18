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
				row = yo/myGrid.length;
				col = yo%myGrid.length;
				if (inBounds(row,col) && isFull(row,col)) {
					break;
				}
				if (inBounds(row,col) && isOpen(row,col)) {
					qp.add(row*myGrid.length+col);
					myGrid[row][col] = FULL;
				}
			}
		}
				
	}
}
