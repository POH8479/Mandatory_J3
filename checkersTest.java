package Exercise3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.jupiter.api.Test;

class CheckerTest {
	
	@Test
	void testMove() {
		// create a board to play on and 2 players
		BlackPlayer p1 = new BlackPlayer("Bob");
		WhitePlayer p2 = new WhitePlayer("Alice");
		
		// create a new game
		Board gameBoard = new Board(p1,p2);
		
		String board = "    1 2 3 4 5 6 7 8   <- X axis\n" + 
				"  +-----------------+\n" + 
				"1 | b   b   b   b   | 1\n" + 
				"2 |   b   b   b   b | 2\n" + 
				"3 | b   b   b   b   | 3\n" + 
				"4 |                 | 4\n" + 
				"5 |                 | 5\n" + 
				"6 |   w   w   w   w | 6\n" + 
				"7 | w   w   w   w   | 7\n" + 
				"8 |   w   w   w   w | 8\n" + 
				"  +-----------------+\n" + 
				"    1 2 3 4 5 6 7 8   <- X axis\n";
		
		assertEquals(board, gameBoard.toString());
		
		// move
		Position from = new Position(1,3);
		Position to = new Position(2,4);
		gameBoard.movePiece(gameBoard.getSquare(from).getPiece(), to, p1);
		
		board = "    1 2 3 4 5 6 7 8   <- X axis\n" + 
				"  +-----------------+\n" + 
				"1 | b   b   b   b   | 1\n" + 
				"2 |   b   b   b   b | 2\n" + 
				"3 |     b   b   b   | 3\n" + 
				"4 |   b             | 4\n" + 
				"5 |                 | 5\n" + 
				"6 |   w   w   w   w | 6\n" + 
				"7 | w   w   w   w   | 7\n" + 
				"8 |   w   w   w   w | 8\n" + 
				"  +-----------------+\n" + 
				"    1 2 3 4 5 6 7 8   <- X axis\n";
		
		assertEquals(board, gameBoard.toString());

	}

	@Test
	void testPossible() {
		// create a board to play on and 2 players
		BlackPlayer p1 = new BlackPlayer("Bob");
		WhitePlayer p2 = new WhitePlayer("Alice");
		Board gameBoard = new Board(p1, p2);
		
		Position from = new Position(1,3);
		Position to = new Position(2,4);
		
		
		assertTrue(gameBoard.possible(gameBoard.getSquare(to),gameBoard.getSquare(from).getPiece()));

		Square square = new Square(to);
		assertTrue(square.isEmpty());
		
		Piece piece = new Piece(p1, from);
		
		assertTrue(square.getPosition().getY() == piece.getPosition().getY() + 1);
		assertTrue(piece.getOwner().getColour().equals("Black"));
		assertTrue(square.getPosition().getX() == piece.getPosition().getX() + 1 || square.getPosition().getX() == piece.getPosition().getX() - 1 );
		
		assertTrue(gameBoard.possible(square,piece));
		
	}
		
	// ** The getSquare test is not working, not sure why so I decided to move on and not spend more time on it **
	
	/**
	 * Testing the getSquare method to determine if the method can successfully return a square from a given position
	 */
	@Test
	public void testgetSquare() { 
		//initialize a game for testing
		BlackPlayer p1 = new BlackPlayer("Pieter");
		WhitePlayer p2 = new WhitePlayer("Jack");
		
		//Initialize position and square 
		Position pos1 = new Position(2,2);
		Board board = new Board(p1,p2);
		
		//fails if both objects are not the same
		assertTrue("getSquare test failed", board.getSquare(pos1).getPosition().equals(pos1));
		
	}
	
	/**
	 * Testing getX and getY to make sure they return coordinates correctly
	 */
	@Test
	public void testgetCoord() {
		//Initialize a position
		Position pos = new Position(3, 5);
		//Test that getX works correctly
		assertSame("getX test failed", 2, pos.getX());
		//Test that getY works correctly
		assertSame("getY test failed", 4, pos.getY());
	}

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
		Board gameBoard = new Board(p1, p2);
		
		//initialize parameters for testing possible() for a White Player
		Position current = new Position(4, 6);
		Piece base = new Piece(p2, current); //Piece to be passed into possible()
		
		//Test destination square which is valid
		Position next1 = new Position(5, 5);
		Square dest1 = new Square(next1); //destination square to be passed into possible()
		boolean test1 = gameBoard.possible(dest1, base);
		assertTrue("Valid moves test failed", test1);
		
		//Test destination square which is invalid (backward)
		Position next2 = new Position(5, 7);
		Square dest2 = new Square(next2); //destination square to be passed into possible()
		boolean test2 = gameBoard.possible(dest2, base);
		assertFalse("Valid moves test failed", test2);
		
		//Test destination square which is invalid (non-diagonal)
		Position next3 = new Position(4, 5);
		Square dest3 = new Square(next3); //destination square to be passed into possible()
		boolean test3 = gameBoard.possible(dest3, base);
		assertFalse("Valid moves test failed", test3);
		
		
		//initialize parameters for testing possible() for a Black Player
		Position currentBl = new Position(7, 3);
		Piece baseBl = new Piece(p1, currentBl); //Piece to be passed into possible()

		//Test destination square which is valid
		Position nextBl1 = new Position(6, 4);
		Square destBl1 = new Square(nextBl1); //destination square to be passed into possible()
		boolean testBl1 = gameBoard.possible(destBl1, baseBl);
		assertTrue("Valid moves test failed", testBl1);

		//Test destination square which is invalid (backward)
		Position nextBl2 = new Position(8, 2);
		Square destBl2 = new Square(nextBl2); //destination square to be passed into possible()
		boolean testBl2 = gameBoard.possible(destBl2, baseBl);
		assertFalse("Valid moves test failed", testBl2);

		//Test destination square which is invalid (non-diagonal)
		Position nextBl3 = new Position(7, 4);
		Square destBl3 = new Square(nextBl3); //destination square to be passed into possible()
		boolean testBl3 = gameBoard.possible(destBl3, baseBl);
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
		Board gameBoard = new Board(p1, p2);
		
		//Test for a position that is occupied by a piece
		Position pos1 = new Position(2, 2);
		assertFalse("isEmpty test failed", gameBoard.getSquare(pos1).isEmpty());
		//Test for a position that is not occupied by a piece
		Position pos2 = new Position(4, 4);
		assertTrue("isEmpty test failed", gameBoard.getSquare(pos2).isEmpty());
		
	}
	


}
