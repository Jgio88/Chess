package chess;
import java.util.Scanner;
import java.util.StringTokenizer;
import pieces.*;

/**Game Manager. This accepts the inputs, asses its value, and then returns the correct output.
 */
public class GameManager extends BoardCells {

	/**Constructor for use in BoardCells
	 * @param piece Game piece
	 * @param i Column for the game piece on the chess board
	 * @param j Row for the game piece on the chess board
	 * @param string What is printed onto the terminal chess board for the given piece
	 */
	public GameManager(Piece piece, int i, int j, String string) {
		super(piece, i, j, string);
	}

	/**The turn tracer will accept the user input and assure that it is good enough to send to the "advance" method, 
	 *  or any other method, depending on the user input. Then it handles the return values of those particular methods.
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 */
	public static void turnTracer(BoardCells[][] chessBoard) {

		//Scanner and variables		
		Scanner input = new Scanner(System.in);			
		int endGame = 0;
		int draw = 0;
		
		
		//Loop to gather inputs from player. Ends in break or using int switch.		
		while(endGame == 0) {
				
				//Variables for getting next line.
				String color;				
				color = TurnKeeper.whosTurn();
				System.out.println("");
				System.out.print(color+"'s move: ");
				String moveCommand = input.nextLine();
				
				//If INPUT is Null
				if(moveCommand.length() == 0) {
					System.out.println("Illegal move, try again");
					continue;
				}
				
				//Resign Condition
				if(moveCommand.equals("resign")) {
					break;
				}
				
				
												
				//Check length of string, 5 for normal moves, 11 for draw, 7 for promotions
				if(moveCommand.length() != 5) {
					if(moveCommand.length() != 11 ) {
						if(moveCommand.length()!= 7) {
						System.out.println("Illegal move, try again");
						continue;
						}
					}
				}
				
				//Tokenize into moveFrom and moveTo
				StringTokenizer tokenizer = new StringTokenizer(moveCommand);
				
				//Assure only 2 tokens OR 3 for draw/promotions.
				if(tokenizer.countTokens() != 2) {
					if(tokenizer.countTokens() != 3) {				
						System.out.println("Illegal move, try again");
					continue;
					}
				}
				
				//Set tokens to strings.
				String moveFrom = tokenizer.nextToken();
				String moveTo = tokenizer.nextToken();
				
				//if token aren't two chars try again.
				if(moveFrom.length() != 2 || moveTo.length() != 2) {
					System.out.println("Illegal move, try again");
					continue;
				}
				
				//Seperate into 4 chars to get X/Y coordiante.
				char moveFromC = moveFrom.charAt(0);
				char moveFromI = moveFrom.charAt(1);
				char moveToC = moveTo.charAt(0);
				char moveToI = moveTo.charAt(1);
				
				//Make sure a-h
				if( !(moveFromC >= 'a' && moveFromC <='h') || !(moveToC >= 'a' && moveToC <='h') ) {
					System.out.println("Illegal move, try again");
					continue;
				}
				
				//Make sure 1-8
				if( !(moveFromI >= '1' && moveFromI <= '8') || !(moveToI >= '1' && moveToI <= '8') ) {
					System.out.println("Illegal move, try again");
					continue;
				}
											
				//Coordinate variables.
				int fromY = coordinator(moveFromC);
				int fromX = coordinator(moveFromI);
				int toY = coordinator(moveToC);		
				int toX = coordinator(moveToI);
				
				//DEBUG -- System.out.println(moveFromC+""+moveFromI+" "+moveToC+""+moveToI);
				//DEBUG -- System.out.println(fromY+""+fromX+" "+toY+""+toX);
				
				//Draw/Promotion condition with three tokens ( draw? / Q)
				if(tokenizer.countTokens() == 1 ) {
					String lastToken = tokenizer.nextToken();
					//Draw?
					if(lastToken.equals("draw?")){
						BoardCells.updateBoard(chessBoard);
						BoardCells.printBoard(chessBoard);				
						TurnKeeper.turnNum++;
						color = TurnKeeper.whosTurn();
						System.out.println("");						
						Scanner drawInput = new Scanner(System.in);						
						String drawA = drawInput.nextLine();
						if(drawA.equals("draw")) {
							draw = 1;
							drawInput.close();	
							break;
						}else {
							//Forced draw loop
							for(int end = 0; end<1;) {
								System.out.println("You have to type draw as per instructions.");
								System.out.println("");
								drawA = drawInput.nextLine();
								if(drawA.equals("draw")) {
									drawInput.close();
									draw = 1;
									endGame = 1;
									return;
								}								
							}							
							drawInput.close();
							}
						} else {
							//Check for promotion in this method.
							promotion(lastToken,chessBoard, fromX, fromY, toX, toY);
							continue;
						}
					} else {
						//All looks good FULL SEND! advance makes sure the move is valid.
						advance( chessBoard, fromX, fromY, toX, toY);
					}
				//
				TurnKeeper.enpassantReset(chessBoard);
				TurnKeeper.turnNum--;
				endGame = TurnKeeper.checkStarter(chessBoard);	
				TurnKeeper.turnNum++;
			}			
			input.close();	
			TurnKeeper.turnNum++;
			if(draw == 0) {
				System.out.println(TurnKeeper.whosTurn()+" wins.");					
			}
	}

	
	/** Translates instructions to X/Y Coordinate, using switch
	 * @param in The character value corresponding to either the row or column from the user-input
	 * @return The correct value of the X/Y coordinate into a-h/1-8
	 */
	public static int coordinator(char in) {
		switch(in) {
		case 'a':
		case '8':	
			return 0;
		case 'b':
		case '7':	
			return 1;
		case 'c':
		case '6':
			return 2;
		case 'd':
		case '5':	
			return 3;
		case 'e':
		case '4':	
			return 4;
		case 'f':
		case '3':	
			return 5;
		case 'g':
		case '2':	
			return 6;
		case 'h':
		case '1':
			return 7;	
			
		}	
		return 0;
	}
	
	/**Checks to see if the move the user wants to make is valid. Sorts through data to see what piece is to be moved, where it's to be moved to,
	 * then it does it if valid. This also handles castling and en passants. 
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param moveFromX Current column location of the piece the user wants to move
	 * @param moveFromY Current row location of the piece the user wants to move
	 * @param moveToX User-entered column location of the cell they want the piece to move to
	 * @param moveToY User-entered row location of the cell they want the piece to move to
	 */
	public static void advance(BoardCells[][] chessBoard, int moveFromX, int moveFromY, int moveToX, int moveToY) {		
		//DEBUG -- System.out.println("From: " + chessBoard[moveFromX][moveFromY].getX() + chessBoard[moveFromX][moveFromY].getY() + chessBoard[moveFromX][moveFromY].getIcon());
		//DEBUG -- System.out.println("To: " + chessBoard[moveToX][moveToY].getX() + chessBoard[moveToX][moveToY].getY() + chessBoard[moveToX][moveToY].getIcon());
		
		//Coordinate variables
		int toX = chessBoard[moveToX][moveToY].getX();
		int toY = chessBoard[moveToX][moveToY].getY();
		int fromX = chessBoard[moveFromX][moveFromY].getX();	
		int fromY = chessBoard[moveFromX][moveFromY].getY();
		//Piece values, and icon for target selection
		Piece pieceToMove = chessBoard[moveFromX][moveFromY].getPiece();
		String icon = chessBoard[moveFromX][moveFromY].getIcon();		
		Piece targetPiece = chessBoard[moveToX][moveToY].getPiece();
		String ticon = chessBoard[moveToX][moveToY].getIcon();
		
		//If a piece to move is not null, then run the command. If null "Illegal move" 
		if(pieceToMove != null) {				
			String teamColor = pieceToMove.getTeam();				
			String pID = pieceToMove.getPieceID();
		
		//Default pawn promo to queen.
		if(pieceToMove.getTeam().equals(TurnKeeper.whosTurn())) {
		if((icon.contentEquals("bp") && toY == 1) || (icon.contentEquals("wp") && toY == 8)) {	
			Boolean move = pieceToMove.movement(chessBoard, targetPiece, fromX,fromY, toX, toY);
			if(move) {
				chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");
				if(TurnKeeper.whosTurn().contentEquals("White")) {				
					chessBoard[8-toY][toX] = new BoardCells(new Queen("wQ","White"),8-toY,toX,"wQ");
					BoardCells.updateBoard(chessBoard);
					BoardCells.printBoard(chessBoard);
					TurnKeeper.turnNum++;
					return;
				} else {					
					chessBoard[8-toY][toX] = new BoardCells(new Queen("bQ","Black"),8-toY,toX,"bQ");
					BoardCells.updateBoard(chessBoard);
					BoardCells.printBoard(chessBoard);
					TurnKeeper.turnNum++;
					return;
				}				
			}else { 
				System.out.println("Illegal move, try again");
				return;
			}
		}
		} else {
			System.out.println("Illegal move, try again");
			return;
		}
	
		
		//EnPassant (Black Pawns)
		if(pID.equals("bp")){		
			String enPassIcon = chessBoard[8-toY-1][toX].getIcon();						
			if(enPassIcon.equals("wp")) {
				if(chessBoard[8-toY-1][toX].getPiece().getPieceID().equals("wpE")){
					enpassant(targetPiece, pieceToMove, chessBoard, fromX, fromY, toX, toY);
					BoardCells.updateBoard(chessBoard);
					BoardCells.printBoard(chessBoard);
					return;
				}
			}
		}
		
		//EnPassant (White Pawns)
		if(pID.equals("wp")){
			String enPassIcon = chessBoard[8-toY+1][toX].getIcon();	
			if(enPassIcon.equals("bp")) {
				if(chessBoard[8-toY+1][toX].getPiece().getPieceID().equals("bpE")){
					//DEBUG -- System.out.println("Enpassant!");
					enpassant(targetPiece, pieceToMove, chessBoard, fromX, fromY, toX, toY);
					BoardCells.updateBoard(chessBoard);
					BoardCells.printBoard(chessBoard);
					return;
				}
			}
		}
		
		//Castling if the Piece ID is King.
		if ( (pID.equals("bKc") && (((toX == 2 && toY == 8) || (toX == 6 && toY == 8))))
				|| (pID.equals("wKc") && (((toX == 6 && toY == 1) || (toX == 2 && toY == 1)))) ) {						
			//Runs a command to see if the king can move. The King Piece has code to check if this is a valid move.
			Boolean move = pieceToMove.movement(chessBoard, targetPiece, fromX,fromY, toX, toY);
			if(move) {								
				//Now that it is a valid move, the program runs it. Depending on where the king is, of course.
				TurnKeeper.turnNum++;						
				if(toX > fromX) {
					Piece rook = chessBoard[8-fromY][toX+1].getPiece();
					String rookIcon = chessBoard[8-fromY][toX+1].getIcon();
						//DEBUG-- System.out.println(""+fromX+fromY+" "+toX+toY);
						//DEBUG-- System.out.println(targetPiece.getPieceID());
						chessBoard[8-fromY][toX-1] = new BoardCells(rook,8-fromY,toX-1,rookIcon);			
						chessBoard[8-fromY][toX] = new BoardCells(pieceToMove,8-fromY,toX, icon);
						chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");
						chessBoard[8-fromY][toX+1] = new BoardCells(null,8-fromY,toX+1,"JG");
						BoardCells.updateBoard(chessBoard);
						BoardCells.printBoard(chessBoard);
						return;
					} else {
						Piece rook = chessBoard[8-fromY][toX-2].getPiece();
						String rookIcon = chessBoard[8-fromY][toX-2].getIcon();
						chessBoard[8-fromY][toX+1] = new BoardCells(rook,8-fromY,toX+1,rookIcon);			
						chessBoard[8-fromY][toX] = new BoardCells(pieceToMove,8-fromY,toX, icon);
						chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");
						chessBoard[8-toY][toX-2] = new BoardCells(null,8-toY,toX-2,"JG");
						BoardCells.updateBoard(chessBoard);
						BoardCells.printBoard(chessBoard);
						return;
					}  					
				} else {
				System.out.println("Illegal move, try again");	
				return;
				}
		}
				
		//Checks to see if target is own team and prevents move		
		if(teamColor.equals(TurnKeeper.whosTurn())) {			
			if(targetPiece !=null) {			
				String targetColor = targetPiece.getTeam();				
				if(teamColor.equals(targetColor)) {
					System.out.println("Illegal move, try again");
					return;
				}			
			}	
			
			//Can't move into a check
			chessBoard[8-fromY][fromX] = new BoardCells(targetPiece,8-fromY,fromX,ticon);
			chessBoard[8-toY][toX] = new BoardCells(pieceToMove,8-toY, toX,icon);
			TurnKeeper.turnNum++;
			if((icon.equals("bK") || icon.equals("wK")) && TurnKeeper.check(chessBoard,toX,8-toY)) {
				chessBoard[8-toY][toX] = new BoardCells(null,8-toY, toX,"JG");
				chessBoard[8-fromY][fromX] = new BoardCells(pieceToMove,8-fromY,fromX,icon);
				System.out.println("Illegal move, try again");
				TurnKeeper.turnNum--;
				return;
			}
			TurnKeeper.turnNum--;
			chessBoard[8-fromY][fromX] = new BoardCells(pieceToMove,8-fromY, fromX,icon);
			chessBoard[8-toY][toX] = new BoardCells(targetPiece,8-toY,toX,ticon);			
			
			
			//Normal move
			Boolean move = pieceToMove.movement(chessBoard, targetPiece, fromX,fromY, toX, toY);
			if(move) {
				//DEBUG -- String color = TurnKeeper.whosTurn();
				//DEBUG -- System.out.println(color+"'s move: "+fromX+fromY+" "+toX+toY );
				TurnKeeper.turnNum++;				
				chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");
				chessBoard[8-toY][toX] = new BoardCells(pieceToMove,8-toY,toX, icon);				
				BoardCells.updateBoard(chessBoard);
				BoardCells.printBoard(chessBoard);
			} else {
				System.out.println("Illegal move, try again");
			}			
		} else {
			System.out.println("Illegal move, try again");
		}
		}else {
			System.out.println("Illegal move, try again");
		}		
	} 
	
	/**En passant - if the conditions for an en passant are triggered, then this method will run to make the 
	 * necessary changes to the XY chessBoard to reflect the move.
	 * 
	 * @param targetPiece Piece that will be captured
	 * @param pieceToMove Piece the user wants to move
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param fromX Current column location of the piece the user wants to move
	 * @param fromY Current row location of the piece the user wants to move
	 * @param toX User-entered column location of the cell they want the piece to move to
	 * @param toY User-entered row location of the cell they want the piece to move to
	 */
	public static void enpassant( Piece targetPiece, Piece pieceToMove, BoardCells[][] chessBoard, 
			int fromX, int fromY, int toX, int toY) {	
		
		//If the white player makes the move.
		if(TurnKeeper.whosTurn().equals("White")) {			
			String icon = chessBoard[8-fromY][fromX].getIcon();			
			chessBoard[8-toY][toX] = new BoardCells(pieceToMove,8-toY,toX,icon);					
			chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");		
			chessBoard[8-toY+1][toX] = new BoardCells(null,8-toY+1,toX,"JG");
			TurnKeeper.turnNum++;
			return;
		}
		//If black player makes the move
		if(TurnKeeper.whosTurn().equals("Black")) {			
			String icon = chessBoard[8-fromY][fromX].getIcon();						
			chessBoard[8-toY][toX] = new BoardCells(pieceToMove,8-toY,toX,icon);					
			chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");		
			chessBoard[8-toY-1][toX] = new BoardCells(null,8-toY-1,toX,"JG");
			TurnKeeper.turnNum++;
			return;
		}	
	}
	
	/**Promotion - this method will run if a Promotion is triggered. It changes the pawn out for the new piece.
	 * 
	 * @param promo String corresponding to the rank of the new piece (either Q, N, R, or B)
	 * @param chessBoard The two dimensional array holding each of the spaces on the board
	 * @param moveFromX Current column location of the piece the user wants to move
	 * @param moveFromY Current row location of the piece the user wants to move
	 * @param moveToX User-entered column location of the cell they want the piece to move to
	 * @param moveToY User-entered row location of the cell they want the piece to move to
	 */
	public static void promotion(String promo, BoardCells[][] chessBoard, int moveFromX, int moveFromY, int moveToX, int moveToY) {
		//Variables for XY coordination
		int toX = chessBoard[moveToX][moveToY].getX();
		int toY = chessBoard[moveToX][moveToY].getY();
		int fromX = chessBoard[moveFromX][moveFromY].getX();	
		int fromY = chessBoard[moveFromX][moveFromY].getY();
		String icon = chessBoard[moveFromX][moveFromY].getIcon();		
		Piece pieceToMove = chessBoard[moveFromX][moveFromY].getPiece();
		Piece targetPiece = chessBoard[moveToX][moveToY].getPiece();

		
		
		//Fails if piece isn't pawn
		if(!(icon.equals("bp") || icon.equals("wp"))){
			System.out.println("Illegal move, try again.");
			return;
		}
		
		Boolean move = pieceToMove.movement(chessBoard, targetPiece, fromX,fromY, toX, toY);
		if(move) {
		
		//Checks to make sure the pawn is at the other end, and makes the appropriate switch.
		if((icon.contentEquals("bp") && toY == 1) || (icon.contentEquals("wp") && toY == 8)) {		
		if(promo.equals("Q") || promo.equals("N") ||promo.equals("R") ||promo.equals("B") ) {
	
			chessBoard[8-fromY][fromX] = new BoardCells(null,8-fromY,fromX,"JG");
			
			if(promo.equals("Q")) {
				if(TurnKeeper.whosTurn().contentEquals("White")) {
				chessBoard[8-toY][toX] = new BoardCells(new Queen("wQ","White"),8-toY,toX,"wQ");
				} else {
					chessBoard[8-toY][toX] = new BoardCells(new Queen("bQ","Black"),8-toY,toX,"bQ");
				}				
			}
			
			if(promo.equals("N")) {
				if(TurnKeeper.whosTurn().contentEquals("White")) {
				chessBoard[8-toY][toX] = new BoardCells(new Knight("wN","White"),8-toY,toX,"wN");
				} else {
					chessBoard[8-toY][toX] = new BoardCells(new Knight("bN","Black"),8-toY,toX,"bN");
				}				
			}
			
			if(promo.equals("R")) {
				if(TurnKeeper.whosTurn().contentEquals("White")) {
				chessBoard[8-toY][toX] = new BoardCells(new Rook("wR","White"),8-toY,toX,"wR");
				} else {
					chessBoard[8-toY][toX] = new BoardCells(new Rook("bR","Black"),8-toY,toX,"bR");
				}				
			}
			
			if(promo.equals("B")) {
				if(TurnKeeper.whosTurn().contentEquals("White")) {
				chessBoard[8-toY][toX] = new BoardCells(new Bishop("wB","White"),8-toY,toX,"wB");
				} else {
					chessBoard[8-toY][toX] = new BoardCells(new Bishop("bB","Black"),8-toY,toX,"bB");
				}				
			}						
			BoardCells.updateBoard(chessBoard);
			BoardCells.printBoard(chessBoard);
			TurnKeeper.turnNum++;
						
		} else {
			System.out.println("Illegal move, try again");
			return;
		}	
		}else {		
			System.out.println("Illegal move, try again");	
			return;	
		}
		}else {
			System.out.println("Illegal move, try again");
		}
		
	}
}
