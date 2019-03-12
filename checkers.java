import java.util.Scanner;
import java.security.InvalidParameterException;

/**
 * The Game class represents a game of checkers with two players on a 8x8 board.
 * @author Pieter O'Hearn
 */
class Game {
	// INSTANCE VARIABLES
	private BlackPlayer p1;
	private WhitePlayer p2;
	private Player turn;
	private Board gameBoard;

	// CONSTRUCTOR
	public Game(BlackPlayer player1, WhitePlayer player2) {
		// set the players and let p1 have the first turn
		this.p1 = player1;
		this.p2 = player2;
		this.turn = this.p2;

		// create a new gameBoard
		gameBoard = new Board(p1,p2);
	}

	/**
 	 * This method changes the Game variable turn and returns a string telling the user who's turn it is
 	 */
	public String nextTurn() {
		// swap the turn
		if(this.turn.equals(this.p1)) {
			// if p1 then change to p2
			this.turn = this.p2;
		} else { // otherwise change to p1
			this.turn = this.p1;
		}

		// make a string with the players name and the current board
		String msg = String.format("%s it's your turn\n\n%s\n", turn.getName(), gameBoard.toString());

		return msg;
	}

	/**
	 * This method checks that the player who's turn it is has a piece at
	 * the given coordinates. If so it moves the Piece on the game board,
	 * otherwise it throws an InvalidParameterException.
	 * @param from The Position to move from
	 * @param to The Position to move to
	 */
	public void addMove(Position from, Position to) throws InvalidParameterException {
		// store the piece that is to be moves in a variable
		Piece pieceToMove = this.gameBoard.getSquare(from).getPiece();
		// check that the player has a piece
		if(pieceToMove != null && pieceToMove.getOwner().equals(this.turn)) {
			// move Piece to position for the player who's turn it is
			gameBoard.movePiece(pieceToMove, to, this.turn);
		} else {
			throw new InvalidParameterException("This Position does not contain your Piece");
		}
	}
}

/**
 * The Board class represents an 8x8 checkers board. The board starts
 * with 24 Pieces, 12 black and 12 white.
 * @author Pieter O'Hearn
 */
class Board {
	// INSTANCE VARIABLES
	private Square[][] board;

	// CONSTRUCTOR
	public Board(BlackPlayer black, WhitePlayer white) {
		// create the matrix
		board = new Square[8][8];

		// construct an 8x8 matrix of Squares
		for(int r = 1; r <= 8 ; r++) {
			for(int c = 1 ; c <= 8 ; c++) {
				// The position of the current square
				Position pos = new Position(c,r);

				// create a new Square for each position and make the board
				board[r-1][c-1] = new Square(pos);

				// if the square has a piece starting on it then create one
				if(((r == 1 || r == 3) && (c == 1 || c == 3 || c == 5 || c == 7))
				|| (r == 2 && (c == 2 || c == 4 || c == 6 || c == 8))) {
					board[r-1][c-1].setPiece(new Piece(black, pos));
				} else if (((r == 7) && (c == 1 || c == 3 || c == 5 || c == 7))
				|| ((r == 6 || r == 8) && (c == 2 || c == 4 || c == 6 || c == 8))) {
					board[r-1][c-1].setPiece(new Piece(white, pos));
				}
			}
		}
	}

	/**
	 * This method returns the square at a given Position
	 * @param pos
	 * @return the Square at the given Position
	 */
	public Square getSquare(Position pos) {
		return this.board[pos.getY()][pos.getX()];
	}

	/**
	 * This method returns the board in its current state to the user
	 * @return The current state of the board as a Square matrix
	 */
	public Square[][] currentState() {
		return this.board;
	}

	/**
	 * This method moves a Piece on the board. If the given piece cannot
	 * be moved here an InvalidParameterException will be thrown.
	 * @param pieceToMove The piece to move
	 * @param moveTo The position to move it to
	 * @param player The player making the move
	 */
	public void movePiece(Piece pieceToMove, Position moveTo, Player player) throws InvalidParameterException {
		// check that the move is in the possible moves set
		if(possible(board[moveTo.getY()][moveTo.getX()], pieceToMove)) {
			// update the board
			this.board[pieceToMove.getPosition().getY()][pieceToMove.getPosition().getX()].setPiece(null);
			this.board[moveTo.getY()][moveTo.getX()].setPiece(pieceToMove);
		} else {
			throw new InvalidParameterException("The Position you you want to move to is invalid");
		}
	}

	/**
	 * This method produces a visual representation of the game board
	 * @return A String depicting the board
	 */
	public String toString() {
		// create a StringBuilder object and start with column numbers
		StringBuilder boardStr = new StringBuilder("    1 2 3 4 5 6 7 8   <- X axis\n  +-----------------+\n");

		// loop through the board
		for(int r = 0 ; r < 8 ; r++) {
			// append the edge of the board and row number
			boardStr.append(r + 1);
			boardStr.append(" |");
			// append each square
			for(int c = 0 ; c < 8 ; c++) {
				boardStr.append(" ");
				// check if piece is at position [r][c]
				if(this.board[r][c].isEmpty()) {
					boardStr.append(" ");
				} else if(this.board[r][c].getPiece().getOwner().getColour().equals("Black")) {
					boardStr.append("b");
				} else if(this.board[r][c].getPiece().getOwner().getColour().equals("White")) {
					boardStr.append("w");
				}
			}
			// append the edge of the board and row number
			boardStr.append(" | ");
			boardStr.append(r + 1);
			boardStr.append("\n");
		}
		boardStr.append("  +-----------------+\n    1 2 3 4 5 6 7 8   <- X axis\n");

		return boardStr.toString();
	}

	/**
	 * This method checks if a move is possible from the Position variables start to end
	 * @param start the position of the piece to check
	 * @param end the position the player wants to move their piece
	 * @return true of false
	 */
	public boolean possible(Square moveTo, Piece pieceToMove) {

		//check if desired square is empty
		if(moveTo.isEmpty()) {
			//initialize local variables for easier comparison
			Position newPos = moveTo.getPosition();
			Position old = pieceToMove.getPosition();

			//check if desired move is forward and diagonal -- assumes white pieces start on the bottom of board
			if(pieceToMove.getOwner().getColour().equals("White") && ((newPos.getX() == (old.getX() + 1)) || (newPos.getX() == (old.getX() - 1))) && (newPos.getY() == (old.getY() - 1))) {
				return true;
			} //check if desired move is forward and diagonal -- assumes black pieces start on the top of board
			else if(pieceToMove.getOwner().getColour().equals("Black") && ((newPos.getX() == (old.getX() + 1)) || (newPos.getX() == (old.getX() - 1))) && (newPos.getY() == (old.getY() + 1))) {
				return true;
			}
		}
		// else return false
		return false;
	}

	/**
	 * This is an overriding method that compares two Board objects.
	 * @param board The board to compare
	 * @return true is equal, false if not
	 */
	public boolean equals(Board board) {
		// use the toString method to compare the strings
		if(board.toString().equals(this.toString())) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * The Piece class represents a checker piece in the checkers game. A piece
 * has an owner and a position on the Game board or NULL if it is not in use.
 * @author Pieter O'Hearn
 */
class Piece {
	// INSTANCE VARIABLES
	private Player owner;
	private Position pos;

	// CONSTRUCTOR 1
	public Piece(Player pieceOwner, Position position) {
		// set the owner and position variables
		this.owner = pieceOwner;
		this.pos = position;
	}

	/**
	* This method shares the Owner of a piece and returns it to the user
	* @return the owner of the Piece
	*/
	public Player getOwner() {
		return this.owner;
	}

	/**
	* This method shares the Position of a piece and returns it to the user
	* @return the owner of the Piece
	*/
	public Position getPosition() {
		return this.pos;
	}

	/**
	 *
	 * @param pos The position to change to
	 */
	public void updatePosition(Position newPos) {
		// set the new position
		this.pos = newPos;
	}

	/**
	 * This is an overriding method that compares two Piece objects.
	 * @param piece The piece to compare
	 * @return true is equal, false if not
	 */
	public boolean equals(Piece piece) {
		//
		if(piece.getOwner().equals(this.owner) && piece.getPosition().equals(this.getPosition())) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * The Position class represents position of a Piece on the Game Board.
 * The class stores the position as an x and y coordinate with int values between 0 and 8.
 * @author Pieter O'Hearn
 */
class Position {
	// INSTANCE VARIABLES
	private int x;
	private int y;

	// CONSTRUCTOR
	public Position(int x, int y) throws IllegalArgumentException {
		// check x and y are not out of bound
		if(outOfBounds(x, y)) {
			throw new IllegalArgumentException();
		} else {
			// set x and y
			this.x = x-1;
			this.y = y-1;
		}
	}

	/**
	 * Checks if a Position is out of bounds
	 * @return true or false
	 */
	public boolean outOfBounds(int newX, int newY) {
		// check that the position is between 0 and 8
		if(newX > 0 && newX <= 8 && newY > 0 && newY <= 8) {
			return false;
		}
		// else return true
		return true;
	}

	/**
	 * Updates the x and y coordinates of the Position object
	 * @param x The x coordinate of the Position
	 * @param y The y coordinate of the Position
	 *
	 * @throws IllegalArgumentException
	 */
	public void update(int x, int y) throws IllegalArgumentException {
		// check x and y are not out of bound
		if(outOfBounds(x, y)) {
			throw new IllegalArgumentException("Position coordinates Out of Bounds");
		} else {
			// set x and y
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * This method returns the x coordinate of the Position
	 * @return x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * This method returns the y coordinate of the Position
	 * @return y
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * This is an overriding method that compares two Position objects.
	 * @param pos The Position to compare
	 * @return true is equal, false if not
	 */
	public boolean equals(Position pos) {
		//
		if(pos.getX() == this.x && pos.getY() == this.y) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * The Player class represents a Player in the checkers gameBoard.
 * A Player has a score and a name.
 * @author Pieter O'Hearn
 */
abstract class Player {
	// INSTANCE VARIABLES
	private  String name;
	private  int score;

	// CONSTRUCTOR 1
	public Player(String playerName) {
		// set name and initialise score to 0
		this.name = playerName;
		score = 0;
	}

	// CONSTRUCTOR 2
	public Player() {
		// set name to NULL and initialise score to 0
		this.name = null;
		score = 0;
	}

	/**
	 * This method gets the name of the player and returns it as a String
	 * @return The name of the player
	 */
	public String getName() {
		// return the name of the player
		return this.name;
	}

	/**
	 * This method gets the score of the Player object
	 * @return The score of the player
	 */
	public int getScore() {
		// return the score
		return this.score;
	}

	/**
	 * This is an abstract method to be implemented by its children classes,
	 * WhitePlayer and BlackPlayer.
	 */
	public abstract String getColour();

	/**
	 * This is an overriding method that compares two Player objects.
	 * @param p The player to compare
	 * @return true is equal, false if not
	 */
	public boolean equals(Player p) {
		// check the players have the same name and score
		if(p.getName().equals(this.name) && p.getScore() == this.score) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * The whitePlayer class extends the Player class. It represents a checkers
 * player whose in control of the white pieces.
 */
class WhitePlayer extends Player {

	// CONSTRUCTOR
	public WhitePlayer(String name) {
		super(name);
	}

	/**
	 * This method informs the user of the colour
	 * @return a string containing "White"
	 */
	public String getColour() {
		return "White";
	}

	/**
	 * This is an overriding method that compares two WhitePlayer objects.
	 * @param p The WhitePlayer to compare
	 * @return true is equal, false if not
	 */
	public boolean equals(WhitePlayer p) {
		// check the players have the same name and score and colour
		if(super.equals(p) && p.getColour().equals("White")) {
			return true;
		} else {
			return false;
		}
	}
}

/**
 * The blackPlayer class extends the Player class. It represents a checkers
 * player whose in control of the black pieces.
 */
class BlackPlayer extends Player {

	// CONSTRUCTOR
	public BlackPlayer(String name) {
		super(name);
	}

	/**
	 * This method informs the user of the colour
	 * @return a string containing "Black"
	 */
	public String getColour() {
		return "Black";
	}

	/**
	 * This is an overriding method that compares two BlackPlayer objects.
	 * @param p The BlackPlayer to compare
	 * @return true is equal, false if not
	 */
	public boolean equals(BlackPlayer p) {
		// check the players have the same name and score and colour
		if(super.equals(p) && p.getColour().equals("Black")) {
			return true;
		} else {
			return false;
		}
	}
}

/*
 * The Square class represents a spot on the game board.
 * It stores the position, colour, and piece.
 * @author Jack Rodman
 */
class Square {

	//INSTANCE VARIABLES
	private Position pos;
	private Piece piece;

	//CONSTRUCTOR
	public Square(Position p) {
		//set position and colour, initialize piece to null
		this.pos = p;
		this.piece = null;
		//this.colour = c;
	}

	/**
	 * get the position of the Square
	 * @return the position
	 */
	public Position getPosition() {
		return this.pos;
	}

	/*
	 * Checks if a given square is not occupied by a piece.
	 * @return true if empty or false if occupied
	 */
	public boolean isEmpty() {
		//check if piece for current position has been initialized
		if(this.piece == null) {
			return true;
		}
		return false;
	}

	/*
	 * Retrieves the piece occupying a square
	 * @return Piece at square (can be null if empty)
	 */
	public Piece getPiece() {
		//return piece at current square
		return this.piece;
	}

	/*
	 * Sets the Piece currently on the square
	 * @param newPiece The Piece to place on the square
	 */
	public void setPiece(Piece newPiece) {
		// update the piece
		this.piece = newPiece;
	}
}

public class checkers {
	/**
	 * The main method of the program
	 */
	public static void main(String args[]) {
		// initialise a Scanner
		Scanner scan = new Scanner(System.in);

		// Ask the user for the Players names
		System.out.println("What is the name of Player 1?");
		String name1 = scan.nextLine();
		BlackPlayer p1 = new BlackPlayer(name1);
		System.out.println(String.format("%s you are Black\n", p1.getName()));

		System.out.println("What is the name of Player 2?");
		String name2 = scan.nextLine();
		WhitePlayer p2 = new WhitePlayer(name2);
		System.out.println(String.format("%s you are White\n", p2.getName()));

		// create a new game
		Game checkers = new Game(p1,p2);

		// print a starting  message and create x and y variables
		System.out.println("Lets Start!! Enter the coordinates of the piece you wish to move and then the position you wish to move it to, when prompted.\n");
		int tempX = -1;
		int tempY = -1;
		Position from = null;
		Position to = null;

		// while there is no winner
		while(p1.getScore() != 12 || p2.getScore() != 12) {
			// print the board and player
			System.out.println(checkers.nextTurn());

			// ask what piece to move
			System.out.print("Move Piece at:\n    Enter x coordinate: ");
			tempX = scan.nextInt();
			System.out.print("\n    Enter y coordinate: ");
			tempY = scan.nextInt();
			from = new Position(tempX, tempY);

			// ask what position to move the piece
			System.out.print("Move Piece to Position:\n    Enter x coordinate: ");
			tempX = scan.nextInt();
			System.out.print("\n    Enter y coordinate: ");
			tempY = scan.nextInt();
			to = new Position(tempX, tempY);

			// perform the move
			checkers.addMove(from, to);
		}

		// close the scanner
		scan.close();
	}
}
