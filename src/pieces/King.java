package pieces;
import chess.BoardCells;
import chess.TurnKeeper;

/**King - holds all specific designs for the king piece.
 * 
 */
public class King extends Piece {

	/**Constructor for King
	 * 
	 * @param ID Either "wK" or "bK", with a possible c on the end for denoting castling
	 * @param team Either "White" or "Black"
	 */
	public King(String ID, String team) {
		setPieceID(ID);
		setTeam(team);
	}
	
	/**Controls the movement for the King
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the king wants to move to
	 * @param fromX Current column location of the king piece
	 * @param fromY Current row location of the king piece
	 * @param toX Column location that the king piece wants to move to
	 * @param toY Row location that the king piece wants to move to
	 * @return True only if the movement is valid
	 */
	@Override
	public boolean movement(BoardCells[][] chessBoard, Piece targetPiece, int fromX,int fromY,int toX,int toY) {
		//DEBUG --System.out.println(""+fromX+fromY+" "+toX+toY);
		
		if(getPieceID().equals("wKc")) {
			if((toX == 6 && toY == 1) || (toX == 2 && toY == 1)){	
				return castling(chessBoard, targetPiece, fromX, fromY, toX, toY);			
			}				
		}
		
		if(getPieceID().equals("bKc")) {			
			if((toX == 2 && toY == 8) || (toX == 6 && toY == 8)){											
				return castling(chessBoard, targetPiece, fromX, fromY, toX, toY);			
			}				
		}
		
		
		if(targetPiece != null) {
							
		if(targetPiece.getTeam().equals(getTeam())) {
			return false;	
		}
		}
			
		if(fromY+1 == toY && fromX == toX) {
			return true;
		}
		
		if(fromY+1 == toY && fromX+1 == toX) {
			return true;
		}
		
		if(fromY == toY && fromX+1 == toX) {
			return true;
		}
		
		if(fromY-1 == toY && fromX == toX) {
			return true;
		}
		
		if(fromY-1 == toY && fromX-1 == toX) {
			return true;
		}
		
		if(fromY == toY && fromX-1 == toX) {
			return true;			
		}
		
		if(fromY+1 == toY && fromX-1 == toX) {
			return true;
		}
		
		if(fromY-1 == toY && fromX+1 == toX) {
			return true;
		}
		return false;
		
	}
		
	/**Controls castling. Castling is called if the player triggers a castle.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the king wants to move to
	 * @param fromX Current column location of the king piece
	 * @param fromY Current row location of the king piece
	 * @param toX Column location that the king piece wants to move to
	 * @param toY Row location that the king piece wants to move to
	 * @return True only if the movement is valid
	 */
	public boolean castling(BoardCells[][] chessBoard, Piece targetPiece, int fromX,int fromY,int toX,int toY) {
	
		if(fromY == toY){			
			if(fromX > toX) {
				if(chessBoard[8-fromY][toX-2].getPiece() !=null) {
					if(!(chessBoard[8-fromY][toX-2].getPiece().getTeam().equals(TurnKeeper.whosTurn()))){
						return false;
					}
				for(int i = fromX-1; i>toX; i--) {
					if(chessBoard[8-fromY][i].getPiece() != null) {											
						return false;				
					}					
				}
				}else{
					return false;
				}
			} else if (fromX < toX) {
				if(chessBoard[8-fromY][toX+1].getPiece() !=null) {
					if(!(chessBoard[8-fromY][toX+1].getPiece().getTeam().equals(TurnKeeper.whosTurn()))){
						return false;
					}
				for(int i = fromX+1; i<toX; i++) {
					if(chessBoard[8-fromY][i].getPiece() != null) {							
						return false;						
					}					
				}
				} else {
					return false;
				}
				
			}

			if(getPieceID().equals("bKc")) {
				setPieceID("bK");
			}
			if(getPieceID().equals("wKc")) {
				setPieceID("wK");
			}
			return true;
		}
		
		return false;		
	}
	
}
