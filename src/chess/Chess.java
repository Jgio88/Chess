
package chess;
import pieces.Piece;

/**Chess mainframe. This initializes the board and runs the turnTracer. Hold the main method for the Java project.
 */
public class Chess extends GameManager {
	
	/**Constructor for use in BoardCells 
	 * 
	 * @param piece Game piece
	 * @param i Column for the game piece on the chess board
	 * @param j Row for the game piece on the chess board
	 * @param string What is printed onto the terminal chess board for the given piece
	 */
	public Chess(Piece piece, int i, int j, String string) {
		super(piece, i, j, string);
	}

	/**MAIN - initializes chessBoard for use in the GameManager, and all extended classes. Starts the Java project.
	 * @param args Command-line arguments
	 */
	public static void main(String args[]) {
		//Set turn to 1 and start.
		TurnKeeper.turnNum = 1;		
		BoardCells[][] chessBoard = BoardCells.initializer();
		GameManager.turnTracer(chessBoard);	
	}
}
