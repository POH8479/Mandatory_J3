import java.util.Scanner;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

class Game {

}

/**
 * The Board class represents an 8x8 checkers board. The board starts
 * with 24 Pieces, 12 black and 12 white.
 */
class Board {
	// INSTANCE VARIABLES
	Square[][] board;

	// CONSTRUCTOR 1
	public Board(WhitePlayer white, BlackPlayer black) {
		// create the matrix
		board = new Square[8][8];

		// construct an 8x8 matrix of Squares
		for(int r = 1; r <= 8 ; r++) {
			for(int c = 1 ; c <= 8 ; c++) {
				// The position of the current square
				Position pos = new Position(r,c);

				// create a new Square for each position and make the board
				board[r][c] = new Square(pos);

				// if the square has a piece starting on it then create one
				if(((r == 1 || r == 3) && (c == 1 || c == 3 || c == 5 || c == 7))
				|| (r == 2 && (c == 2 || c == 4 || c == 6 || c == 8))) {
					board[r][c].setPiece(new Piece(black, pos));
				} else if (((r == 7) && (c == 1 || c == 3 || c == 5 || c == 7))
				|| ((r == 6 || r == 8) && (c == 2 || c == 4 || c == 6 || c == 8))) {
					board[r][c].setPiece(new Piece(white, pos));
				}
			}
		}
	}

	/**
	 * This method returns the board in its current state to the user
	 * @return The current state of the board as a Square matrix
	 */
	public Square[][] currentState() {
		return this.board;
	}

	/**
	 * This method moves a Piece on the board. If any arguments are invalid
	 * an InvalidParameterException will be thrown.
	 * @param piecePos The position of the piece to move
	 * @param movePos The position to move it to
	 * @param player The player making the move
	 */
	public void movePiece(Position piecePos, Position movePos, Player player) throws InvalidParameterException {
		// store the piece that is to be moves in a variable
		Piece pieceToMove = this.board[piecePos.getX()][getPos.getY()].getPiece();

		// check that the player has a piece at the piecePos
		if(pieceToMove.getOwner().equals(player)) {
			// check that the move is in the possible moves set
			if(this.possible(movePos)) {
				// update the board
				this.board[piecePos.getX()][getPos.getY()].setPiece(null);
				this.board[movePos.getX()][movePos.getY()].setPiece(pieceToMove);
			} else { throw InvalidParameterException("The Position you you want to move to is invalid"); }
			}
		} else {
			throw InvalidParameterException("This Position does not contain your Piece");
		}
	}

	/**
	 * This method checks if a move is possible from the Position variables start to end
	 * @param start the position of the piece to check
	 * @param end the position the player wants to move thier piece
	 * @return true of false
	 */
	 private boolean possible(Position end, Position start) {
		 // TODO Finish this methods
	 }
}

/*
 * The Piece class represents a checker piece in the checkers game. A piece
 * has an owner and a position on the Game board or NULL if it is not in use.
 * @author Pieter O'Hearn
 */
class Piece {
	// INSTANCE VARIABLES
	Player owner;
	Position pos;

	// CONSTRUCTOR 1
	public Piece(Player pieceOwner, Position position) {
		// set the owner and pos variables
		this.owner = pieceOwner;
		this.pos = position;
	}

	// CONSTRUCTOR 2
	public Piece(Player pieceOwner) {
		// set owner to the given piece owner and set pos to NULL
		this.owner = pieceOwner;
		this.pos = null;
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
}

/*
 * The Position class represents position of a Piece on the Game Board.
 * The class stores the position as an x and y coordinate with int values between 0 and 8.
 * @author Pieter O'Hearn
 */
class Position {
	// INSTANCE VARIABLES
	int x;
	int y;

	// CONSTRUCTOR
	public Position(int x, char y) throws IllegalArgumentException {
		// store the y refrence as an int for easy lookup in the board matrix
		int intY = Character.getNumericValue(toUpperCase(y) - 64);

		// check x and y are not out of bound
		if(outOfBounds(x, intY)) {
			throw new IllegalArgumentException();
		} else {
			// set x and y
			this.x = x;
			this.y = intY;
		}
	}

	/**
	 * Checks if a Position is out of bounds
	 * @return true or false
	 */
	private boolean outOfBounds(int xPos, int yPos) {
		// check that the position is between 0 and 8
		if(xPos > 0 && xPos <= 8 && yPos > 0 && yPos <= 8) {
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
}

/**
 * The Player class represents a Player in the checkers gameBoard.
 * A Player has a score and a name.
 * @author Pieter O'Hearn
 */
abstract class Player {
	// INSTANCE VARIABLES
	String name;
	int score;

	// CONSTRUCTOR 1
	public Player(String playerName) {
		// set name and initalise score to 0
		this.name = playerName;
		score = 0;
	}

	// CONSTRUCTOR 2
	public Player() {
		// set name to NULL and initalise score to 0
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
	 public abstract boolean direction();
}

/**
 * The whitePlayer class extends the Player class. It represents a checkers
 * player whose in control of the white pieces.
 */
class WhitePlayer extends Player {
	public abstract boolean direction();

}

/**
 * The blackPlayer class extends the Player class. It represents a checkers
 * player whose in control of the black pieces.
 */
class BlackPlayer extends Player {
	public abstract boolean direction();
}

 // Jack Has implemented this class in a seperate Branch
class Square {

}

public class checkers {
	/**
	 * The main method of the program
	 */
	public static void main(String args[]) {

	}
}
