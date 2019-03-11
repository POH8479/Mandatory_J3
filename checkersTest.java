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
	@Test (expected = IllegalArgumentException.class)
	public void testOutOfBounds() {
		
		//Initialize an in-bound position to use as reference object to call outOfBounds() for destination coordinates
		Position inBounds = new Position('a', 1);
		
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
	@Test (expected = IllegalArgumentException.class)
	public void testValidMoves() {
		
		//initialize a game for testing purposes
		BlackPlayer p1 = new BlackPlayer("Pieter");
		WhitePlayer p2 = new WhitePlayer("Jack");
		Game game = new Game(p1, p2);
		
		
		//initialize parameters for testing possible() for a White Player
		Position current = new Position('d', 6);
		Piece base = new Piece(p2, current); //Piece to be passed into possible()
		
		//Test destination square which is valid
		Position next1 = new Position('e', 5);
		Square dest1 = new Square(next1); //destination square to be passed into possible()
		boolean test1 = game.gameBoard.possible(dest1, base);
		assertTrue("Valid moves test failed", test1);
		
		
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
