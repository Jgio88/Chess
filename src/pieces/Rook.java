package pieces;
import chess.BoardCells;

/**Rook - holds all specific designs for the rook piece.
 * 
 */
public class Rook extends Piece {

	/**Constructor for Rook
	 * 
	 * @param ID Either "bR" or "wR"
	 * @param team Either "Black" or "White"
	 */
	public Rook(String ID, String team) {
		setPieceID(ID);
		setTeam(team);
	}
	
	/**Controls movement for the Rook
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the rook wants to move to
	 * @param fromX Current column location of the rook piece
	 * @param fromY Current row location of the rook piece
	 * @param toX Column location that the rook piece wants to move to
	 * @param toY Row location that the rook piece wants to move to
	 * @return True only if the movement is valid
	 */
	@Override
	public boolean movement(BoardCells[][] chessBoard, Piece targetPiece, int fromX,int fromY,int toX,int toY) {		
		//DEBUG -- System.out.println(""+fromX+fromY+" "+toX+toY);
		
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
			
			if(getPieceID().equals("bR")) {
				for(int i = 0; i<9 ;i++) {
					for (int j = 0; j<9; j++){
						if(chessBoard[8-i][j].getPiece() != null) {
						if(chessBoard[8-i][j].getPiece().getPieceID().equals("bKc")) {
							//Piece king = chessBoard[8-i][j].getPiece();					
							//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
							chessBoard[8-i][j].getPiece().setPieceID("bK");
						}
						}
					}
				}
			}

			if(getPieceID().equals("wR")) {
				for(int i = 0; i<9 ;i++) {
					for (int j = 0; j<9; j++){
						if(chessBoard[8-i][j].getPiece() != null) {
						if(chessBoard[8-i][j].getPiece().getPieceID().equals("wKc")) {
							//Piece king = chessBoard[8-i][j].getPiece();					
							//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
							chessBoard[8-i][j].getPiece().setPieceID("wK");
						}
						}
					}
				}
			}
			
			
			return true;
		}
			
							
		if(fromX == toX) {				
			if(fromY > toY) {	
				for(int i = fromY-1; i>toY; i--) {
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
				
				if(getPieceID().equals("bR")) {
					for(int i = 0; i<9 ;i++) {
						for (int j = 0; j<9; j++){
							if(chessBoard[8-i][j].getPiece() != null) {
							if(chessBoard[8-i][j].getPiece().getPieceID().equals("bKc")) {
								//Piece king = chessBoard[8-i][j].getPiece();					
								//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
								chessBoard[8-i][j].getPiece().setPieceID("bK");
							}
							}
						}
					}
				}

				if(getPieceID().equals("wR")) {
					for(int i = 0; i<9 ;i++) {
						for (int j = 0; j<9; j++){
							if(chessBoard[8-i][j].getPiece() != null) {
							if(chessBoard[8-i][j].getPiece().getPieceID().equals("wKc")) {
								//Piece king = chessBoard[8-i][j].getPiece();					
								//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
								chessBoard[8-i][j].getPiece().setPieceID("wK");
							}
							}
						}
					}
				}
				
		
			return true;	
		}
				
		return false;		
	}
	
	
	
}
