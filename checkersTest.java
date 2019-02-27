import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CheckerTest {
	/**
	 * Testing that when a player has taken or jumped all of their opponents
	 * pieces then the game ends.
	 */
	@Test
	void testGameWon() {
		// TODO implement this method

		// create a board to play on and 2 players
		Board gameBoard = new Board();
		Player player1 = new Player("Alice", true);
		Player player2 = new Player("Bob", false);

		fail("Not yet implemented");
	}

	/**
	 * Testing that a player cannot successfully move a piece out of bounds.
	 */
	@Test
	void testOutOfBounds() {
		// TODO implement this method

		// create a board to play on and 2 players
		Board gameBoard = new checkers.Board();
		Player player1 = new checkers.Player("Alice", true);
		Player player2 = new checkers.Player("Bob", false);

		fail("Not yet implemented");
	}

	/**
	 * Testing that a player cannot successfully play an invalid move. e.g.
	 * placing a piece on another coloured square.
	 */
	@Test
	void testValidMoves() {
		// TODO implement this method
		fail("Not yet implemented");
	}

	/**
	 * Testing that a player can successfully jump their opponents piece and remove it from
	 * the game.
	 */
	@Test
	void testJump() {
		// TODO implement this method
		fail("Not yet implemented");
	}

	/**
	 * Testing that a player can successfully jump multiple of their opponents
	 * pieces and remove it from the game.
	 */
	@Test
	void testMultiJump() {
		// TODO implement this method
		fail("Not yet implemented");
	}
}
