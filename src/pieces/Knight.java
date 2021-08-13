package pieces;
import chess.BoardCells;

/**Knight - holds all specific designs for the Knight piece.
 * 
 */
public class Knight extends Piece {

	/**Constructor for the knight
	 * 
	 * @param ID Either "wN" or "bN"
	 * @param team Either "White" or "Black"
	 */
	public Knight(String ID, String team) {
		setPieceID(ID);
		setTeam(team);	
	}
	
	/**Controls movement for the Knight
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that the knight wants to move to
	 * @param fromX Current column location of the knight piece
	 * @param fromY Current row location of the knight piece
	 * @param toX Column location that the knight piece wants to move to
	 * @param toY Row location that the knight piece wants to move to
	 * @return True only if the movement is valid
	 */
	@Override
	public boolean movement(BoardCells[][] chessBoard, Piece targetPiece, int fromX,int fromY,int toX,int toY) {
		//DEBUG -- System.out.println(""+fromX+fromY+" "+toX+toY);
		
	
		if(toY == fromY + 2 || toY == fromY - 2) {
			if( toX == fromX + 1  || toX == fromX - 1 ) {
				return true;
			}	
		}
		
		if(toX == fromX + 2 || toX == fromX - 2) {
			if( toY == fromY + 1  || toY == fromY - 1 ) {
				return true;
			}	
		}
		return false;
	}
		
}
