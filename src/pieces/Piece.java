package pieces;
import chess.BoardCells;

/**Piece - is an abstract class that holds the blueprint for each chess piece.
 * 
 */
public abstract class Piece {
	//Variables for each Piece -- since they're private they don't go in the Javadoc
	/** String that denotes what team, either "White" or "Black", that a given piece is on
	*/
	public String teamColor;
	/** String that acts as an identification in certain methods, with the format of first character of the piece's team color 
	 * followed by a character denoting the piece's rank. There can also be character(s) after this, depending on the needs of
	 * the particular piece
	*/
	public String pieceID;	
	/**Will control movement for a particular piece once instantiated
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param targetPiece Possible piece that is currently occupying the cell that a piece wants to move to
	 * @param fromX Current column location of a piece
	 * @param fromY Current row location of a piece
	 * @param toX Column location that a piece wants to move to
	 * @param toY Row location that a piece wants to move to
	 * @return True only if the movement is valid
	 */
	public abstract boolean  movement(BoardCells[][] chessBoard, Piece targetPiece, int fromX, int fromY, int toX, int toY); 	
	
	/**Setter for teamColor variable
	 * 
	 * @param team Color that the piece belongs to
	 */
	public void setTeam (String team) {
		this.teamColor = team;
	}
	
	/**Getter for teamColor variable
	 * 
	 * @return teamColor - color that the piece belongs to
	 */
	public String getTeam() {
		return this.teamColor;
	}
	
	/**Setter for pieceID
	 * 
	 * @param ID Identification String for the piece
	 */
	public void setPieceID(String ID) {
		this.pieceID = ID;
	}
	
	/**Getter for pieceID variable
	 * 
	 * @return pieceID - identification String for the piece
	 */
	public String getPieceID() {
		return this.pieceID;
	}
}



