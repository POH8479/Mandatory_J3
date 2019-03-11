import static org.junit.Assert.*;
import org.junit.Test;

public class checkersTest {
	/**
	 * Testing that when a player has taken or jumped all of their opponents
	 * pieces then the game ends.
	 */
	/*@Test
	void testGameWon() {
		// TODO implement this method

		// create a board to play on and 2 players
		
		
		//Board gameBoard = new Board();
		//Player player1 = new Player("Alice", true);
		//Player player2 = new Player("Bob", false);

		fail("Not yet implemented");
	}*/

	/**
	 * Testing that a player cannot successfully move a piece out of bounds.
	 */
	@Test 
	public void testOutOfBounds() {
		
		//Initialize an in-bound position to use as reference object to call outOfBounds() for destination coordinates
		Position inBounds = new Position(1, 1);
		
		//Testing an in-bounds position -> outOfBounds should return False
		boolean test1 = inBounds.outOfBounds(3, 5);
		assertFalse("Out of bounds test failed", test1);
		
		//Testing an out of bounds position -> outOfBounds should return True
		boolean test2 = inBounds.outOfBounds(10, 2);
		assertTrue("Out of bounds test failed", test2);
		
		//Testing an out of bounds position -> outOfBounds should return True
		boolean test3 = inBounds.outOfBounds(15, 6);
		assertTrue("Out of bounds test failed", test3);
		
		//Testing an out of bounds position -> outOfBounds should return True
		boolean test4 = inBounds.outOfBounds(9, 9);
		assertTrue("Out of bounds test failed", test4);
		
		//Testing an in-bounds position -> outOfBounds should return False
		boolean test5 = inBounds.outOfBounds(1, 8);
		assertFalse("Out of bounds test failed", test5);
		
	}

	/**
	 * Testing that a player cannot successfully play an invalid move. e.g.
	 * placing a piece on another coloured square.
	 */
	@Test
	public void testValidMoves() {
		
		//initialize a game for testing purposes
		BlackPlayer p1 = new BlackPlayer("Pieter");
		WhitePlayer p2 = new WhitePlayer("Jack");
		Game game = new Game(p1, p2);
		
		//initialize parameters for testing possible() for a White Player
		Position current = new Position(4, 6);
		Piece base = new Piece(p2, current); //Piece to be passed into possible()
		
		//Test destination square which is valid
		Position next1 = new Position(5, 5);
		Square dest1 = new Square(next1); //destination square to be passed into possible()
		boolean test1 = game.gameBoard.possible(dest1, base);
		assertTrue("Valid moves test failed", test1);
		
		//Test destination square which is invalid (backward)
		Position next2 = new Position(5, 7);
		Square dest2 = new Square(next2); //destination square to be passed into possible()
		boolean test2 = game.gameBoard.possible(dest2, base);
		assertFalse("Valid moves test failed", test2);
		
		//Test destination square which is invalid (non-diagonal)
		Position next3 = new Position(4, 5);
		Square dest3 = new Square(next3); //destination square to be passed into possible()
		boolean test3 = game.gameBoard.possible(dest3, base);
		assertFalse("Valid moves test failed", test3);
		
		
		//initialize parameters for testing possible() for a Black Player
		Position currentBl = new Position(7, 3);
		Piece baseBl = new Piece(p1, currentBl); //Piece to be passed into possible()

		//Test destination square which is valid
		Position nextBl1 = new Position(6, 4);
		Square destBl1 = new Square(nextBl1); //destination square to be passed into possible()
		boolean testBl1 = game.gameBoard.possible(destBl1, baseBl);
		assertTrue("Valid moves test failed", testBl1);

		//Test destination square which is invalid (backward)
		Position nextBl2 = new Position(8, 2);
		Square destBl2 = new Square(nextBl2); //destination square to be passed into possible()
		boolean testBl2 = game.gameBoard.possible(destBl2, baseBl);
		assertFalse("Valid moves test failed", testBl2);

		//Test destination square which is invalid (non-diagonal)
		Position nextBl3 = new Position(7, 4);
		Square destBl3 = new Square(nextBl3); //destination square to be passed into possible()
		boolean testBl3 = game.gameBoard.possible(destBl3, baseBl);
		assertFalse("Valid moves test failed", testBl3);
		
	}
	
	/**
	 * Testing that getPosition returns the correct position from a square
	 */
	@Test
	public void testgetPosition() {
		//Initialize a position and square for testing
		Position pos = new Position(2,2);
		Square sq = new Square(pos);
		//Verify that getPosition returns the desired position
		assertSame("getPosition test failed", pos, sq.getPosition());
	}
	
	/**
	 * Testing the isEmpty method to make sure empty squares can be identified
	 */
	@Test
	public void testisEmpty() {
		//initialize a game for testing
		BlackPlayer p1 = new BlackPlayer("Pieter");
		WhitePlayer p2 = new WhitePlayer("Jack");
		Game game = new Game(p1, p2);
		
		//Test for a position that is occupied by a piece
		Position pos1 = new Position(2, 2);
		assertFalse("isEmpty test failed", game.gameBoard.getSquare(pos1).isEmpty());
		//Test for a position that is not occupied by a piece
		Position pos2 = new Position(4, 4);
		assertTrue("isEmpty test failed", game.gameBoard.getSquare(pos2).isEmpty());
		
	}

	/**
	 * Testing that a player can successfully jump their opponents piece and remove it from
	 * the game.
	 */
	/*@Test
	void testJump() {
		// TODO implement this method
		fail("Not yet implemented");
	}*/

	/**
	 * Testing that a player can successfully jump multiple of their opponents
	 * pieces and remove it from the game.
	 */
	/*@Test
	void testMultiJump() {
		// TODO implement this method
		fail("Not yet implemented");
	}*/
}
