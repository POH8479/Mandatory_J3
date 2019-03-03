import java.util.Scanner;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Queue;

class Game {

}

class Board {

}

class Piece {

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
	public Position(int x, int y) throws IllegalArgumentException {
		// check x and y are not out of bound
		if(outOfBounds(x, y)) {
			throw new IllegalArgumentException();
		} else {
			// set x and y
			this.x = x;
			this.y = y;
		}
	}

	/**
	 * Checks if a Position is out of bounds
	 * @return true or false
	 */
	private boolean outOfBounds(int xPos, int yPos) {
		// check that the position is between 0 and 8
		if(xPos >= 0 && xPos <= 8 && yPos >= 0 && yPos <= 8) {
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
			throw new IllegalArgumentException();
		} else {
			// set x and y
			this.x = x;
			this.y = y;
		}
	}
}

class Player {

}

class Square {

}

public class checkers {
	/**
	 * This method updates the board by moving the given piece and removing
	 * any opposition pieces in the case of a jump.
	 * @param current	- The current position the piece is at
	 * @param moveTo	- The position to move to
	 * @param player	- the player who's turn it is
	 * @param score		- the game score
	 * @param board		- the current state of the board
	 */
	private static void move(Point current, Point moveTo, int player, int[] score, char[][] board) {
		// check if the move jumps any piece's or not
		if(Math.abs(moveTo.y-current.y) == 1) {
			// if not, move the piece the one spot to the moveTo Point
			board[current.y+2][(current.x*2)+3] = ' ';
			board[moveTo.y+2][(moveTo.x*2)+3] = (char) (player + 48);
		} // if a jump has occurred
		else {
			// store the x and y direction in variables
			int yDirection = (player==1) ? 1 : -1;
			int xDirection = (moveTo.x-current.x > 0) ? 1 : -1;;

			// for every piece jumped, delete it from the board
			for(int i = 0 ; i <= Math.abs(moveTo.y-current.y) ; i++) {
				board[(current.y+(yDirection*i))+2][((current.x+(xDirection*i))*2)+3] = ' ';
				// deduct 1 from the score
				score[player-1]--;
			}
			//move the piece to the moveTo Point
			board[moveTo.y+2][(moveTo.x*2)+3] = (char) (player + 48);
		}
	}

	/**
	 * This method returns all possible moves for a given player in a given position.
	 * @param pos - the position of the piece to be moved
	 * @param player - the player who's piece it is
	 * @param board - the current state of the board
	 * @return A LinkedList of all possible coordinates the piece can move to
	 */
	private static Queue<Point> possibles(Point pos, int player, char[][] board) {
		// create variables for the opponent and direction
		int opponent = (player==1) ? 2 : 1;
		int direction = (player==1) ? 1 : -1;

		// create a Linked List for the coordinates of possible moves
		Queue<Point> possibles = new LinkedList<Point>();

		// create two points for the two diagonals
		Point left = new Point(pos.x+direction,pos.y+direction);
		Point right = new Point(pos.x-direction,pos.y+direction);

		// LEFT
		// if there is a left diagonal
		if(!outOfBounds(left)) {
			// if Empty
			if(board[left.y+2][(left.x*2)+3] == ' ') {
				// add the left diagonal to the list
				possibles.add(left);
			}
			// check if the left diagonal is the oppositions piece and that a left Jump is possible
			else if((int) board[left.y+2][(left.x*2)+3] - 48 == opponent && board[left.y+2+direction][(left.x*2)+3+direction] == ' ') {
				// add leftJump to possibles
				Point leftJump = new Point(left.x+direction, left.y+direction);
				possibles.add(leftJump);
			}
		}

		// RIGHT
		// if there is a right diagonal
		if(!outOfBounds(right)) {
			// if Empty
			if(board[right.y+2][(right.x*2)+3] == ' ') {
				// add the left diagonal to the list
				possibles.add(right);
			}
			// check if the right diagonal is the oppositions piece and that a right Jump is possible
			else if((int) board[right.y+2][(right.x*2)+3] - 48 == opponent && board[right.y+2+direction][(right.x*2)+3-direction] == ' ') {
				// add rightJump to possibles
				Point rightJump = new Point(right.x-direction, right.y+direction);
				possibles.add(rightJump);
			}
		}

		// return the list of possible moves
		return possibles;
	}

	/**
	 * This Method prints the board that is provided
	 * @param board - the current state of the board
	 */
	private static void printBoard(char[][] board) {
		// loop through the array and print each line
		for(char[] line : board) {
			System.out.println(String.valueOf(line));
		}
	}

	/**
	 * This method verifies that a player has a piece at the coordinates they have entered.
	 * @param pos - The coordinates to check
	 * @param player - The player to check
	 * @param board - the current state of the board
	 * @return True or False
	 */
	private static boolean verifyPiece(Point pos, int player, char[][] board) {
		// if the player has a piece at these coordinates, return true
		if((int) board[pos.y+2][(pos.x*2)+3] - 48 == player) {
			return true;
		}
		// otherwise return false
		return false;
	}

	/**
	 * This method checks if the coordinates given are in the bounds of the board.
	 * @param pos - the coordinates to check
	 * @return true or false
	 */
	private static boolean outOfBounds(Point pos) {
		// if out of bounds return true
		if(pos.x > 7 || pos.x < 0 ||  pos.y > 7 || pos.y < 0) {
			return true;
		}
		// otherwise return false
		return false;
	}

	/**
	 * This method checks that the entered move is valid for the player.
	 * @param currentPos - The coordinates of the current position
	 * @param newPos - The coordinates of the position to move to
	 * @param player - The player who's turn it is
	 * @param board - the current state of the board
	 * @return true or false
	 */
	private static boolean isValid(Point currentPos, Point newPos, int player, char[][] board) {
		// check if the player has a piece at these coordinates
		if(!verifyPiece(currentPos, player, board)) {
			return false;
		}
		// check the coordinates of their new position are in bounds
		if(outOfBounds(newPos)) {
			return false;
		}
		// check that the move is in the possible moves set
		for(Point move : possibles(currentPos, player, board)) {
			if(newPos.equals(move)) {
				return true;
			}
		}
		// otherwise return false
		return false;
	}

	/**
	 * This is the main method for the Checker board game.
	 * @param args - command line arguments
	 */
	public static void main(String[] args) {
		// Create a new board
		char[][] board = new char[][] {
				"   0 1 2 3 4 5 6 7   <- X axis".toCharArray(),
				"  +----------------+".toCharArray(),
				"0 |  1   1   1   1 |".toCharArray(),
				"1 |1   1   1   1   |".toCharArray(),
				"2 |  1   1   1   1 |".toCharArray(),
				"3 |                |".toCharArray(),
				"4 |                |".toCharArray(),
				"5 |2   2   2   2   |".toCharArray(),
				"6 |  2   2   2   2 |".toCharArray(),
				"7 |2   2   2   2   |".toCharArray(),
				"  +----------------+".toCharArray(),
				"   0 1 2 3 4 5 6 7  ".toCharArray()};

		// keep the score, turn and positions as variables
		int turn = 1;
		int [] score = {12,12};
		Point currentPos = new Point();
		Point newPos = new Point();

		// Open a Scanner
		Scanner s = new Scanner(System.in);

		// while there is no winner keep playing
		while(score[0] > 0 && score[1] > 0) {
			// print the board
			printBoard(board);

			// print message and receive coordinates
			System.out.format("\nTurn of player no. %d\nCoordinate of piece to move\n  Enter X:  ", turn);
			currentPos.x = s.nextInt();
			System.out.print("  Enter Y:  ");
			currentPos.y = s.nextInt();

			System.out.format("\nCoordinate of new position\n  Enter X:  ", turn);
			newPos.x = s.nextInt();
			System.out.print("  Enter Y:  ");
			newPos.y = s.nextInt();

			// check if move is Valid
			if(!isValid(currentPos, newPos, turn, board)) {
				System.out.print("Invalid Coordinates entered. Game Over!\n");
				break;
			}

			// move the piece
			move(currentPos, newPos, turn, score, board);
			System.out.println("Piece moved!\n");

			// change turns
			turn = (turn == 1) ? 2 : 1;
		}
		// Print a winning message
		if(score[0]==0) {
			System.out.println("Player 1 Wins!!!!");
		} else {
			System.out.println("Player 2 Wins!!!!");
		}
		// close the Scanner s
		s.close();
	}

}
