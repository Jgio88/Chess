package chess;
import pieces.*;

/**TurnKeeper -  This method holds information that keeps each turn in check. It keeps track of
 * 	whose turn it is, en passant rests, check and checkmates.
 * 	every piece of information that the cell might hold.
 */
public class TurnKeeper {
	/** Global value to keep track of whose turn it is.
	*/
	public static int turnNum;
	/**Uses modulo to change whose turn it is each time a turn is completed.
	 * 
	 * @return color - will be either "White" or "Black"
	 */
	public static String whosTurn() {
		String color;
		if (TurnKeeper.turnNum % 2 != 0) {
			color =  "White";
		} else {
			color = "Black";
		}	
		
		return color;
	}
	
	/**At the end of each turn this program searches for a check.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @return Either 0 or 1 - denoting whether or not there's a check
	 */
	public static int checkStarter(BoardCells[][] chessBoard) {
		//Variables to help locate correct king
		String color = whosTurn();
		BoardCells kingLocal = chessBoard[0][0];
		//Black king check
		if(color.equals("Black")) {
			for(int i = 0; i<9 ;i++) {
				for (int j = 0; j<9; j++){
					if(chessBoard[8-i][j].getPiece() != null) {
					if(chessBoard[8-i][j].getPiece().getPieceID().equals("wK") ||
						chessBoard[8-i][j].getPiece().getPieceID().equals("wKc")) {
						//Piece king = chessBoard[8-i][j].getPiece();					
						//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
						kingLocal = chessBoard[8-i][j];
					}
					}
				}
			}
		}
		//White king check
		if(color.equals("White")) {
			for(int i = 0; i<9 ;i++) {
				for (int j = 0; j<9; j++){
					if(chessBoard[8-i][j].getPiece() != null) {
					if(chessBoard[8-i][j].getPiece().getPieceID().equals("bK")||
							chessBoard[8-i][j].getPiece().getPieceID().equals("bKc")) {						
						kingLocal = chessBoard[8-i][j];
					}
					}
				}
			}
		}						
		//X/Y Coordinates of king.			
		int X = kingLocal.getX(); 			
		int Y = 8-kingLocal.getY();
		if(check(chessBoard,X,Y)) {
			//If in check, print check then check for check mate.
			System.out.println("Check");
			if(checkMate(chessBoard,X,Y)) {
				return 1;
			}
		}else {
			return 0;
		}
		return 0;
	}
	
	/**Checks all directions for enemies to report if the king is in check.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return true or false depending on if king is in check.
	 */
	public static boolean check(BoardCells[][] chessBoard,int X, int Y) {			
			//North
			if((Y-1 > 0)) {
				if(checkNorth(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the North.");
					return true;
				}
			}		
			//NorthEast
			if((Y-1 > 0) && (X+1)<8) {
				if(checkNorthEast(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the North East.");
					return true;
				}
			} 			
			//East
			if((X+1)<8) {
				if(checkEast(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the East");	
					return true;
				}				
			}	
			//SouthEast
			if((Y+1)<9 && (X+1)<8) {
				if(checkSouthEast(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the SouthEast.");
					return true;
				}				
			}			
			//South
			if((Y+1)<8) {		
				if(checkSouth(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the South.");
					return true;
				}
			} 						
			//SouthWest
			if((Y+1)<8 && (X-1)>0) {
				if(checkSouthWest(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the South West.");
					return true;
				}
			} 			
			//West
			if((X-1)>0) {
				if(checkWest(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the West.");
					return true;
				}
			}			
			//NorthWest
			if((Y-1)>0 && (X-1)>0) {
				if(checkNorthWest(chessBoard,X,Y)) {
					//DEBUG -- System.out.println("Enemy from the NorthWest.");
					return true;
				}
			}
			return false;			
	}
	
	/**Checks North for enemies that could attack from that way. Q/R/K
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return  Boolean that is true if king is in check.
	 */
	public static boolean checkNorth(BoardCells[][] chessBoard, int X, int Y) {		
		//Check if enemy King is here
		if(Y-1 > 0) {
			if(chessBoard[Y-1][X].getPiece() != null) {
				String icon = chessBoard[Y-1][X].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}		
		//Y-1 for Northbound
		for(int i = 1; i<8;i++) {
			if(Y-i>0) {
				//Only check spaces with enemy 
				if(chessBoard[Y-i][X].getPiece() != null) {
					Piece target = chessBoard[Y-i][X].getPiece();
					String ID = target.getPieceID();					
					if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
							ID.equals("wRc") || ID.equals("bRc"))){
						return false;							
					}			
					//This means team mate is blocking path.
					if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {								
					//DEBUG -- System.out.println("Path Blocked");
					return false;
					} else {			
						if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
								ID.equals("wRc") || ID.equals("bRc")){
									return true;
								}
					}
				}
			}	
		}	
		return false;
	}
	
	/**Checks south for enemies that can attack from south.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkSouth(BoardCells[][] chessBoard, int X, int Y) {		
		//Check if enemy King is here
		if(Y+1 < 8) {
			if(chessBoard[Y+1][X].getPiece() != null) {
				String icon = chessBoard[Y+1][X].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}		
		//Y+1 for Southbound
		for(int i = 1; i<8;i++) {
			if(Y+i<8) {
				if(chessBoard[Y+i][X].getPiece() != null) {
					Piece target = chessBoard[Y+i][X].getPiece();
					String ID = target.getPieceID();					
					if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
							ID.equals("wRc") || ID.equals("bRc"))){
						return false;							
					}					
					if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
						return false;
					} else {						
						if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
								ID.equals("wRc") || ID.equals("bRc")){
									return true;							
						}
					}
				}
			}			
		}		
		return false;
	}

	/**Checks east for enemies 
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkEast(BoardCells[][] chessBoard, int X, int Y) {
		//Check if enemy King is here
				if(X+1 < 8) {
					if(chessBoard[Y][X+1].getPiece() != null) {
						String icon = chessBoard[Y][X+1].getIcon();
						if(icon.equals("wK") || icon.equals("bK")) {
							return true;
						}					
					}
				}		
				//X+1 for Eastbound
				for(int i = 1; i<8;i++) {
					if(X+i<8) {
						if(chessBoard[Y][X+i].getPiece() != null) {
							Piece target = chessBoard[Y][X+i].getPiece();
							String ID = target.getPieceID();
							
							if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
									ID.equals("wRc") || ID.equals("bRc"))){
								return false;							
							}
							
							if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
								return false;
							} else {						
								if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
										ID.equals("wRc") || ID.equals("bRc")){
											return true;							
								}
							}	
						}
					}					
				}				
				return false;			
	}
	
	/**Checks west for enemies
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkWest(BoardCells[][] chessBoard, int X, int Y) {
		//Check if enemy King is here
		if(X-1 < 9) {
			if(chessBoard[Y][X-1].getPiece() != null) {
				String icon = chessBoard[Y][X-1].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}		
		//X-1 for Westbound
		for(int i = 1; i<9;i++) {
			if(X-i>0) {
				if(chessBoard[Y][X-i].getPiece() != null) {
					Piece target = chessBoard[Y][X-i].getPiece();
					String ID = target.getPieceID();
					
					if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
							ID.equals("wRc") || ID.equals("bRc"))){
						return false;							
					}
					
					if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
						return false;
					} else {						
						if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wR") || ID.equals("bR") || 
								ID.equals("wRc") || ID.equals("bRc")){
									return true;							
						}
					}	
				}
			}					
		}				
		return false;
	}
	
	/**Checks Northeast for enemies that can attack from this angle Q/B/K
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkNorthEast(BoardCells[][] chessBoard, int X, int Y) {
		//Y-1 && X+1 for NE
		
		//Check if enemy King is here
		if(X+1 < 8 && Y-1 > 0) {
			if(chessBoard[Y-1][X+1].getPiece() != null) {
				String icon = chessBoard[Y-1][X+1].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}
		
		
		//Pawn checks for white king vs black pawn NE
		if(TurnKeeper.whosTurn().equals("Black")) {
			if(X+1 <8 && Y-1 > 0) {
				if(chessBoard[Y-1][X+1].getPiece() != null) {
					String icon = chessBoard[Y-1][X+1].getIcon();
					if(icon.equals("bp")) {					
						return true;
					}
				}
			}
		}
		
		//Knight checks
		if(Y-2 >0 && X+1 < 8) {
			if(chessBoard[Y - 2][X+1].getPiece() != null) {
				Piece target = chessBoard[Y-2][X+1].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		//Knight checks
		if(Y-1 >0 && X+2 < 8) {
			if(chessBoard[Y-1][X+2].getPiece() != null) {
				Piece target = chessBoard[Y-1][X+2].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		
		
		//Queen and Bishop 
		//Y-1 X+1 for NEbound			
		for(int i = 1; i<8;i++) {		
			if(Y-i>0 && X+i<8 ) {
					if(chessBoard[Y-i][X+i].getPiece() != null) {
						Piece target = chessBoard[Y-i][X+i].getPiece();
						String ID = target.getPieceID();
						
						if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB"))){
							return false;							
						}
						
						
						if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
							return false;
						} else {						
												
							if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB")){
										return true;							
							}
						}	
					}
				}					
			}		
			
			
			
	
			
			return false;
	}

	/**Checks Southeast for enemies
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkSouthEast(BoardCells[][] chessBoard, int X, int Y) {
		//Y+1 && X+1 for SE
		
		//Check if enemy King is here
		if(X+1 < 8 && Y+1 < 8) {
			if(chessBoard[Y+1][X+1].getPiece() != null) {
				String icon = chessBoard[Y+1][X+1].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}
		
		//Pawn checks for black king vs white pawn SE
		if(TurnKeeper.whosTurn().equals("White")) {
			if(X+1 < 8 && Y+1 < 8 ) {
				if(chessBoard[Y+1][X+1].getPiece() != null) {
					String icon = chessBoard[Y+1][X+1].getIcon();
					if(icon.equals("wp")) {					
						return true;
					}
				}
			}
		}
		
		//Knight checks
		if(Y+2 < 8 && X+1 < 8) {
			if(chessBoard[Y+2][X+1].getPiece() != null) {
				Piece target = chessBoard[Y+2][X+1].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		//Knight checks
		if(Y+1 < 8 && X+2 < 8) {
			if(chessBoard[Y+1][X+2].getPiece() != null) {
				Piece target = chessBoard[Y+1][X+2].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		
		//Y+1 X+1 for SEbound			
		for(int i = 1; i<8;i++) {		
			if(Y+i<8 && X+i<8 ) {
					if(chessBoard[Y+i][X+i].getPiece() != null) {
						Piece target = chessBoard[Y+i][X+i].getPiece();
						String ID = target.getPieceID();
						
						if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB"))){
							return false;							
						}
						
						if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
							return false;
						} else {						
													
							if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB")) {
										return true;							
							}
						}	
					}
				}					
			}		
			return false;
	}
	
	/**Checks Southwest for enemies
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkSouthWest(BoardCells[][] chessBoard, int X, int Y) {
		//Y+1 && X-1 for SW		
		//Check if enemy King is here
		if(X-1 >0  && Y+1 < 8) {
			if(chessBoard[Y+1][X-1].getPiece() != null) {
				String icon = chessBoard[Y+1][X-1].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}
		
		//Pawn checks for black king vs white pawn SW
		if(TurnKeeper.whosTurn().equals("White")) {
			if(X-1 > 0 && Y+1 < 8 ) {
				if(chessBoard[Y+1][X-1].getPiece() != null) {
					String icon = chessBoard[Y+1][X-1].getIcon();
					if(icon.equals("wp")) {					
						return true;
					}
				}
			}
		}
		
		//Knight checks
		if(Y+2 < 8 && X-1 > 0) {
			if(chessBoard[Y+2][X-1].getPiece() != null) {
				Piece target = chessBoard[Y+2][X-1].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		//Knight checks
		if(Y+1 < 8 && X-2 > 0) {
			if(chessBoard[Y+1][X-2].getPiece() != null) {
				Piece target = chessBoard[Y+1][X-2].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		
		//Y+1 X-1 for SWbound			
		for(int i = 1; i<8;i++) {		
			if(Y+i<8 && X-i >0 ) {
					if(chessBoard[Y+i][X-i].getPiece() != null) {
						Piece target = chessBoard[Y+i][X-i].getPiece();
						String ID = target.getPieceID();
						if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB"))){
							return false;							
						}
						
						if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
							return false;
						} else {						
										
							if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB")){
										return true;							
							}
						}	
					}
				}					
			}		
			return false;
	}
	
	/**Checks Northwest for enemies
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean that is true if king is in check.
	 */
	public static boolean checkNorthWest(BoardCells[][] chessBoard, int X, int Y) {
		//Y-1 && X-1 for NW		
		//Check if enemy King is here
		if(X-1 >0  && Y-1 > 0) {
			if(chessBoard[Y-1][X-1].getPiece() != null) {
				String icon = chessBoard[Y-1][X-1].getIcon();
				if(icon.equals("wK") || icon.equals("bK")) {
					return true;
				}					
			}
		}
		
		//Pawn checks for white king vs black pawn NW
		if(TurnKeeper.whosTurn().equals("Black")) {
			if(X-1 > 0 && Y-1 > 0) {
				if(chessBoard[Y-1][X-1].getPiece() != null) {
					String icon = chessBoard[Y-1][X-1].getIcon();
					if(icon.equals("bp")) {					
						return true;
					}
				}
			}
		}
		
		//Knight checks
		if(Y-2 > 0 && X-1 > 0) {
			if(chessBoard[Y-2][X-1].getPiece() != null) {
				Piece target = chessBoard[Y-2][X-1].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		//Knight checks
		if(Y-1 > 0 && X-2 > 0) {
			if(chessBoard[Y-1][X-2].getPiece() != null) {
				Piece target = chessBoard[Y-1][X-2].getPiece();
				String ID = target.getPieceID();
				if((target.getTeam().equals(TurnKeeper.whosTurn()))) {
					if(ID.equals("wN") || ID.equals("bN")) {
						return true;
					}
				}
			}		
		}
		
		
		//Y-1 X-1 for NWbound			
		for(int i = 1; i<9;i++) {		
			if(Y-i >0 && X-i >0 ) {
					if(chessBoard[Y-i][X-i].getPiece() != null) {						
						Piece target = chessBoard[Y-i][X-i].getPiece();
						String ID = target.getPieceID();
						
						if(!(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB"))){
							return false;											
						}
						
						if(!(target.getTeam().equals(TurnKeeper.whosTurn()))) {													
							return false;
						} else {						
													
							if(ID.equals("wQ") || ID.equals("bQ") || ID.equals("wB") || ID.equals("bB")){
										return true;							
							}
						}	
					}
				}					
			}		
			return false;
	}
	
	/**If the king is in check, this program will check to see if he will continue to be in check for 
	 * 	each space that he can move into. It will end the program at checkmate.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param X The column for a particular location on the board
	 * @param Y The row for a particular location on the board
	 * @return Boolean value that will end game if true.
	 */
	public static boolean checkMate(BoardCells[][] chessBoard, int X, int Y) {
	
		//Variables to check for checkmate
		int checkMateSwitch = 0;	
		Piece king = chessBoard[Y][X].getPiece();
		Piece target = chessBoard[5][5].getPiece();
		String icon = chessBoard[Y][X].getIcon();
		chessBoard[Y][X] = new BoardCells(null,Y,X,"JG");		
		
		//North
		if(Y-1 > 0) {
			target = chessBoard[Y-1][X].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X, 8-Y+1)) {
				TurnKeeper.turnNum--;	
				if(check(chessBoard,X,Y-1)) {					
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//NE
		if(Y-1 > 0 && X+1 < 8) {
			target = chessBoard[Y-1][X+1].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X+1, 8-Y+1)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X+1,Y-1)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//East
		if(X+1 < 8) {
			target = chessBoard[Y][X+1].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X+1, 8-Y)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X+1,Y)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//SE
		if(Y+1 < 8 && X+1 < 8) {
			target = chessBoard[Y+1][X+1].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X+1, 8-Y-1)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X+1,Y+1)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//South
		if(Y+1 < 8) {
			target = chessBoard[Y+1][X].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X, 8-Y-1)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X,Y+1)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//SW
		if(Y+1 < 8 && X-1 > 0 ) {
			target = chessBoard[Y+1][X-1].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X-1, 8-Y-1)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X-1,Y+1)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//West
		if(X-1 > 0) {
			target = chessBoard[Y][X-1].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X-1, 8-Y)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X-1,Y)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		//NW
		if(Y-1 > 0 && X-1 > 0) {
			target = chessBoard[Y-1][X-1].getPiece();
			TurnKeeper.turnNum++;
			if(king.movement(chessBoard, target, X, 8-Y, X-1, 8-Y+1)) {
				TurnKeeper.turnNum--;
				if(check(chessBoard,X-1,Y-1)) {
					checkMateSwitch++;
				} else {
					chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
					return false;
				}
			} else {
				TurnKeeper.turnNum--;
				checkMateSwitch++;
			}
		} else {
			checkMateSwitch++;
		}
		
		if(checkMateSwitch == 8) {
			chessBoard[Y][X] = new BoardCells(king,Y,X,icon);
			System.out.println("Checkmate");
			return true;
		}	
		return false;		
	}
	
	/**This resets any enemy pawns that were previously marked for en passant.
	 * 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 */
	public static void enpassantReset(BoardCells[][] chessBoard) {
		String color = whosTurn();
		
		if(color.equals("Black")) {
			for(int i = 0; i<9 ;i++) {
				for (int j = 0; j<9; j++){
					if(chessBoard[8-i][j].getPiece() != null) {
					if(chessBoard[8-i][j].getPiece().getPieceID().equals("bpE")) {
						//Piece king = chessBoard[8-i][j].getPiece();					
						//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
						chessBoard[8-i][j].getPiece().setPieceID("bp");
					}
					}
				}
			}
		}

		if(color.equals("White")) {
			for(int i = 0; i<9 ;i++) {
				for (int j = 0; j<9; j++){
					if(chessBoard[8-i][j].getPiece() != null) {
					if(chessBoard[8-i][j].getPiece().getPieceID().equals("wpE")) {
						//Piece king = chessBoard[8-i][j].getPiece();					
						//DEBUG -- System.out.println(king.getPieceID()+color+" "+king );										
						chessBoard[8-i][j].getPiece().setPieceID("wp");
					}
					}
				}
			}
		}
	}	
}
