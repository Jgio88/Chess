package pieces;
import chess.BoardCells;

/**Queen - holds all specific designs for the Queen piece.
 * 
 */
public class Queen extends Piece {

	/**Constructor for Queen
	 * 
	 * @param ID Either "wQ" or "bQ"
	 * @param team Either "White" or "Black"
	 */
	public Queen(String ID, String team) {
		setPieceID(ID);
		setTeam(team);		
	}
	
	/**Controls movement for the Queen
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the queen wants to move to
	 * @param fromX Current column location of the queen piece
	 * @param fromY Current row location of the queen piece
	 * @param toX Column location that the queen piece wants to move to
	 * @param toY Row location that the queen piece wants to move to
	 * @return True only if the movement is valid
	 */
	@Override
	public boolean movement(BoardCells[][] chessBoard, Piece targetPiece, int fromX,int fromY,int toX,int toY) {
		//DEBUG -- System.out.println(""+fromX+fromY+" "+toX+toY);
		//Rook movement for queen
		if(fromY == toY){			
			if(fromX > toX) {
				for(int i = fromX-1; i>toX; i--) {
					if(chessBoard[8-fromY][i].getPiece() != null) {											
						return false;				
					}					
				}				
			} else if (fromX < toX) {
				for(int i = fromX+1; i<toX; i++) {
					if(chessBoard[8-fromY][i].getPiece() != null) {							
						return false;						
					}					
				}						
				
			}
			return true;
		}										
		if(fromX == toX) {				
			if(fromY > toY) {	
				for(int i = fromY-1; i>=toY; i--) {
					if(chessBoard[8-i][toX].getPiece() != null) {							
						return false;						
					}					
				}						
				
			} else if (fromY < toY) {				
				for(int i = fromY+1; i<toY; i++) {					
					if(chessBoard[8-i][toX].getPiece() != null) {							
						return false;						
					}					
				}	
			}			
			return true;	
		}
		
		//Bishop movement for Queen
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
