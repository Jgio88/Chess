package chess;
import pieces.*;

/**BoardCells -  This keeps track of the 2D array chessBoard. It holds each cell on the board and
 * 	every piece of information that the cell might hold.
 */
public class BoardCells{
	//Variables for each cell
	/** String to be printed on the particular cell of the chess board
	*/
	public String icon;
	/** Piece on the particular cell of the chess board
	*/
	public Piece piece;
	/** Column location of the cell on the chess board
	*/
	public int x;
	/** Row location of the cell on the chess board
	*/
	public int y;
	
	/**Constructor for BoardCells
	 * 
	 * @param piece Possible game piece on the cell
	 * @param i The column for a particular location on the board
	 * @param j The row for a particular location on the board
	 * @param icon String to be printed onto the corresponding cell in the terminal
	 */
	public BoardCells (Piece piece, int i, int j, String icon) {
		this.setPiece(piece);
		this.setX(j);
		this.setY(8-i);
		this.setIcon(icon);
	}
	
	//Getters and Setters
	/**Getter for piece variable
	 * 
	 * @return piece - current game piece on the cell
	 */
    public Piece getPiece() 
    { 
        return this.piece; 
    } 
    /**Setter for piece variable
     * 
     * @param p Piece that is now on the cell
     */
    public void setPiece(Piece p) 
    { 
        this.piece = p; 
    } 
    /**Getter for x variable
     * 
     * @return x - column of the current cell
     */
    public int getX() 
    { 
        return this.x; 
    }  
    /**Setter for x variable
     * 
     * @param x Column of the current cell
     */
    public void setX(int x) 
    { 
        this.x = x; 
    } 
    /**Getter for y variable
     * 
     * @return y - row of the current cell
     */
    public int getY() 
    { 
        return this.y; 
    } 
    /**Setter for y variable
     * 
     * @param y Row of the current cell
     */
    public void setY(int y) 
    { 
        this.y = y; 
    } 
    /**Getter for icon variable
     * 
     * @return icon - String that is printed to the terminal for a particular cell
     */
    public String getIcon() 
    { 
        return this.icon; 
    } 
    /**Setter for icon variable
     * 
     * @param icon String that will be printed to the terminal for a particular cell
     */
    public void setIcon(String icon) 
    { 
        this.icon = icon; 
    } 
    
	
/**Initializes the entire chess board at start.
 * 
 * @return The initialized 2D array "chessBoard". This is used throughout the entire program.
 */
public static BoardCells[][] initializer(){
	//Using 2D array for the chess board 			
	BoardCells[][] chessBoard = new BoardCells[9][9];

		//All Piece's are initialized
		Piece bRook = new Rook("bR","Black");
		Piece wRook = new Rook("wR","White");
		Piece bKnight = new Knight("bN","Black");
		Piece wKnight = new Knight("wN","White");
		Piece bBishop = new Bishop("bB","Black");
		Piece wBishop = new Bishop("wB","White");
		Piece bQueen = new Queen("bQ","Black");
		Piece wQueen = new Queen("wQ","White");
		//King is initialized with c tag for castling
		Piece bKing = new King("bKc","Black");
		Piece wKing = new King("wKc","White");
		
		//Set up boarder
		for(int i = 0; i<8; i++) {
			char abc = 'a';						
			chessBoard[i][8] = new BoardCells(null,i,8,(String.valueOf(8-i)));
			chessBoard[8][i] = new BoardCells(null,8,i,(Character.toString(abc+i)+" "));
		}
		chessBoard[8][8] = new BoardCells(null,8,8,"");
				
		//Set up blank spaces
		//Could turn into a method to call after every play to reset all new empty spaces.
		for(int i = 0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(i%2 == 0) {
					if(j%2==0) {
						chessBoard[i][j] = new BoardCells(null,i,j,"  ");
					}else{
						chessBoard[i][j]= new BoardCells(null,i,j,"##");
					}
				} else{
					if(j%2==0) {
						chessBoard[i][j] = new BoardCells(null,i,j,"##");						
					}else {
						chessBoard[i][j] = new BoardCells(null,i,j,"  ");
					}
				}
			}			
		}

		//Uses for and switch to set up the initial starting places of all pieces.
		for(int i = 0; i<8; i++) {
			//Each Pawn needs to be given a S tag for starting move.
			String bpID = "bpS";
			String wpID = "wpS";
			Piece bPawn = new Pawn(bpID,"Black");
			Piece wPawn = new Pawn(wpID,"White");
			chessBoard[1][i] = new BoardCells(bPawn,1,i,"bp");
			chessBoard[6][i]= new BoardCells(wPawn,6,i,"wp");
			
			switch(i) {			
			case 0:
			case 7:			
				//Rooks
				chessBoard[0][i] = new BoardCells(bRook,0,i,"bR");
				chessBoard[7][i] = new BoardCells(wRook,7,i,"wR");
				break;
			case 1:				
			case 6:
				//kNights
				chessBoard[0][i] = new BoardCells(bKnight,0,i,"bN");				
				chessBoard[7][i] = new BoardCells(wKnight,7,i,"wN");
				break;
			case 2:
			case 5:
				//Bishops
				chessBoard[0][i] = new BoardCells(bBishop,0,i,"bB");
				chessBoard[7][i] = new BoardCells(wBishop,7,i,"wB");
				break;
			case 3:
				//Queens
				chessBoard[0][i] = new BoardCells(bQueen,0,i,"bQ");
				chessBoard[7][i] = new BoardCells(wQueen,7,i,"wQ");
				break;
			case 4:
				//Kings
				chessBoard[0][i] = new BoardCells(bKing,0,i,"bK");
				chessBoard[7][i] = new BoardCells(wKing,7,i,"wK");		
				break;
			}			
		}
		
		//does the initial print for all related "icons" in order.
		for(int i = 0; i<9; i++) {
			System.out.println();
			for(int j=0; j<9; j++) {			
			System.out.print(" "+chessBoard[i][j].getIcon()+" ");			
			}			
		}			
		System.out.println("");
		return chessBoard;
	}

	/**Updates the board after a move.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 */
	public static void updateBoard(BoardCells[][] chessBoard) {	
	//Set up blank spaces
	for(int i = 0; i<8; i++) {
		for(int j=0; j<8; j++) {		
			if(chessBoard[i][j].getPiece() == null) {
			if(i%2 == 0) {
				if(j%2==0) {
					chessBoard[i][j] = new BoardCells(null,i,j,"  ");
				}else{
					chessBoard[i][j]= new BoardCells(null,i,j,"##");
				}
			} else{
				if(j%2==0) {
					chessBoard[i][j] = new BoardCells(null,i,j,"##");						
				}else {
					chessBoard[i][j] = new BoardCells(null,i,j,"  ");
				}
			}
			}
		}			
	}
	}
	
	/**Prints board after each move.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 */
	public static void printBoard(BoardCells[][] chessBoard) {
		for(int i = 0; i<9; i++) {
			System.out.println();
			for(int j=0; j<9; j++) {
				//System.out.println();				
			System.out.print(" "+chessBoard[i][j].getIcon()+" ");			
			}			
		}			
		System.out.println("");
	}
}
