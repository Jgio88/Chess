package pieces;
import chess.BoardCells;

/**Bishop - holds all specific designs for the bishop piece.
 * 
 */
public class Bishop extends Piece {

	/**Constructor for Bishop
	 * 
	 * @param ID Either "wB" or "bB"
	 * @param team Either "White" or "Black"
	 */
	public Bishop(String ID, String team) {
		setPieceID(ID);
		setTeam(team);
	}
	
	/**Controls the movement for the Bishop
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the bishop wants to move to
	 * @param fromX Current column location of the bishop piece
	 * @param fromY Current row location of the bishop piece
	 * @param toX Column location that the bishop piece wants to move to
	 * @param toY Row location that the bishop piece wants to move to
	 * @return True only if the movement is valid
	 */
	@Override
	public boolean movement(BoardCells[][] chessBoard, Piece targetPiece, int fromX,int fromY,int toX,int toY) {
		//DEBUG--System.out.println(""+fromX+fromY+" "+toX+toY);
		
		//Can't move like ROOK so any like moves return FALSE
		if (fromX == toX || fromY == toY) {
			return false;
		}
		
		//Diagonal movements
		//Select ++ quadrant
		if(fromY < toY && fromX < toX){
		  int i = fromY + 1;
		  int j = fromX+1;
		  //check diag chessBoard pieces 
		  for(; i<=fromY+1;) {
		    for(; j<= toX;) {
		      //Hit piece and can't move to destination return FALSE
		      if(chessBoard[8-i][j].getPiece() != null && chessBoard[8-i][j].getPiece() != targetPiece) {
		          return false;
		      }
		      //hit destination return TRUE
		      if(j == toX && i == toY) {
		          return true;
		      }
		      j++;
		      i++;					
		      //DEBUG -- System.out.println(""+j+i+" "+toX+toY+"");
		    }
		  }
		}	

		//Select -+ Quadrant then mirror above
		if(fromY > toY && fromX < toX){
		  int i = fromY - 1;
		  int j = fromX +1;
		  for(; i >= fromY - 1;) {
		    for(; j<= toX;) {
		      if(chessBoard[8-i][j].getPiece() != null && chessBoard[8-i][j].getPiece() != targetPiece) {											
		        return false;				
		      }					
		      if(j == toX && i == toY) {
		        return true;			
		      }
		      i--;
		      j++;
		    }
		  }	
		}

		//Select +- Quadrant then mirror above
		if(fromY < toY && fromX > toX){
		  int i = fromY + 1;
		  int j = fromX-1;
		  for(; i <= fromY+1;) {
		    for(; j >= toX;) {
		      if(chessBoard[8-i][j].getPiece() != null  && chessBoard[8-i][j].getPiece() != targetPiece) {											
		        return false;				
		      }
		      if(j == toX && i == toY) {
		        return true;			
		      }
		      i++;
		      j--;
		    }
		  }	
		}
		    
		//Select -- Quadrant then mirror above
		if(fromY > toY && fromX > toX){
		  int i = fromY-1;
		  int j = fromX-1;
		  for(; i >= fromY-1;) {
		    for(; j >= toX;) {
		      if(chessBoard[8-i][j].getPiece() != null && chessBoard[8-i][j].getPiece() != targetPiece) {											
		        return false;				
		      }
		      if(j == toX && i == toY) {
		        return true;			
		      }
		      i--;
		      j--;
		    }
		  }	
		}
		return false;	
	}
}
