package pieces;
import chess.BoardCells;

/**Pawn - holds all specific designs for the pawn piece.
 * 
 */
public class Pawn extends Piece {

	/**Pawn constructor
	 * 
	 * @param ID Either "bp" or "wp", with a possible E on the end
	 * @param team Either "Black" or "White"
	 */
	public Pawn(String ID, String team) {
		setPieceID(ID);
		setTeam(team);
		}
	
	/**Controls movement for the pawn
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the pawn wants to move to
	 * @param fromX Current column location of the pawn piece
	 * @param fromY Current row location of the pawn piece
	 * @param toX Column location that the pawn piece wants to move to
	 * @param toY Row location that the pawn piece wants to move to
	 * @return True only if the movement is valid
	 */
	@Override
	public boolean movement(BoardCells[][] chessBoard,Piece targetPiece, int fromX,int fromY,int toX,int toY) {			
		//Match team color of piece for proper grid movement.
		//If piece is black
		if(getTeam().equals("Black")) {
			//Since Pawns can move 2 space at start,
			//Use this ID to check if pawn can move 2 spaces.
			if(getPieceID().equals("bpS")) {
				if(targetPiece == null && toX == fromX && (toY == fromY-1)){
					setPieceID("bp");
					return true;
				}else if(targetPiece == null && toX == fromX &&(toY == fromY-2)) {
					setPieceID("bpE");
					return true;				
				}else {
					if(targetPiece != null && toY == fromY-1 && (toX == fromX-1 || toX == fromX+1 ) ) {
						setPieceID("bp");
						return true;
					} else {
					return false;
					}
				}
			}
			
			//Normal movements after first.
			if(targetPiece == null && (toY == fromY-1 && toX == fromX)) {
				setPieceID("bp");
				return true;				
			} else {
				if(targetPiece != null && toY == fromY-1 && (toX == fromX-1 || toX == fromX+1 ) ) {
					setPieceID("bp");
					return true;
				} else {
					return false;
				}
			}
		}
				
		//If piece is white.
		if(getTeam().equals("White")) {
			//Since Pawns can move 2 space at start,
			//Use this ID to check if pawn can move 2 spaces.
			if(getPieceID().equals("wpS")) {
				if(targetPiece == null && toX == fromX && (toY == fromY+1)) {
					setPieceID("wp");
					return true;
				}else if(targetPiece == null && toX == fromX && (toY == fromY+2)) {
					setPieceID("wpE");
					return true;	
				}else {
					if(targetPiece != null && toY == fromY+1 && (toX == fromX+1 || toX == fromX-1 ) ) {
						setPieceID("wp");
						return true;
					} else {
						return false;
					}
				}
			}
			
			//Normal movements after first.
			if(targetPiece == null && (toY == fromY+1 && toX == fromX)) {
				setPieceID("wp");
				return true;				
			} else {
				if(targetPiece != null && toY == fromY+1 && (toX == fromX+1 || toX == fromX-1 ) ) {
					setPieceID("wp");
					return true;
				} else {
					return false;
				}
			}
		
		}
		return false;
	}
}

