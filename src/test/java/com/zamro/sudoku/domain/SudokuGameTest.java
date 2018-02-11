package com.zamro.sudoku.domain;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SudokuGameTest {

	@Test
	public void shouldCreateValidBoardWithNoArgumentConstructor() {
		assertTrue(new SudokuGame().validate());
	}

	@Test(expected = SudokuException.class)
	public void shouldThrowSudokuExceptionForNullBoard() {
		new SudokuGame(null);
	}

	@Test(expected = SudokuException.class)
	public void shouldThrowSudokuExceptionForBoardsWithInvalidDimension() {
		Integer[][] board = {
				{2, 7, 1,  9, 5, 6,  3, 4, 4, 8},
				{9, 5, 6,  3, 4, 8,  2, 7, 1},
				{3, 4, 8,  2, 7, 1,  9, 5, 6},

				{7, 2, 9,  1, 6, 5,  4, 8, 3},
				{1, 6, 5,  4, 8, 3,  7, 2, 9},
				{4, 8, 3,  7, 2, 9,  1, 6, 5},

				{5, 1, 2,  6, 9, 7,  8, 3, 4},
				{6, 9, 7,  8, 3, 4,  5, 1, 2},
				{8, 3, 4,  5, 1, 2,  6, 9, 7}
		};
		new SudokuGame(board);
	}

	@Test(expected = SudokuException.class)
	public void shouldThrowSudokuExceptionForBoardsWithInvalidValues() {
		Integer[][] board = {
				{2, 7, 1,  9, 5, 6,  3, 4, 0},
				{9, 5, 6,  3, 4, 8,  2, 7, 1},
				{3, 4, 8,  2, 7, 1,  9, 5, 6},

				{7, 2, 9,  1, 6, 5,  4, 8, 3},
				{1, 6, 5,  4, 8, 10,  7, 2, 9},
				{4, 8, 3,  7, 2, 9,  1, 6, 5},

				{5, 1, 11,  6, 9, 7,  8, 3, 4},
				{6, 9, 7,  8, 3, 4,  5, 1, 2},
				{8, 3, 4,  5, 1, 2,  6, 9, 7}
		};
		new SudokuGame(board);
	}

	@Test
	public void shouldNotValidateBoardForDuplicatedNumbersInRow() {
		Integer[][] board = {
				{2, 2, 1,  9, 5, 6,  3, 4, 8},
				{9, 5, 6,  3, 4, 8,  2, 7, 1},
				{3, 4, 8,  2, 7, 1,  9, 5, 6},

				{7, 2, 9,  1, 6, 5,  4, 8, 3},
				{1, 6, 5,  4, 8, 3,  7, 2, 9},
				{4, 8, 3,  7, 2, 9,  1, 6, 5},

				{5, 1, 2,  6, 9, 7,  8, 3, 4},
				{6, 9, 7,  8, 3, 4,  5, 1, 2},
				{8, 3, 4,  5, 1, 2,  6, 9, 7}
		};
		SudokuGame game = new SudokuGame(board);
		assertFalse(game.validate());
	}

	@Test
	public void shouldNotValidateBoardForDuplicatedNumbersInCol() {
		Integer[][] board = {
				{2, 7, 1,  9, 5, 6,  3, 4, 8},
				{2, 5, 6,  3, 4, 8,  2, 7, 1},
				{3, 4, 8,  2, 7, 1,  9, 5, 6},

				{7, 2, 9,  1, 6, 5,  4, 8, 3},
				{1, 6, 5,  4, 8, 3,  7, 2, 9},
				{4, 8, 3,  7, 2, 9,  1, 6, 5},

				{5, 1, 2,  6, 9, 7,  8, 3, 4},
				{6, 9, 7,  8, 3, 4,  5, 1, 2},
				{8, 3, 4,  5, 1, 2,  6, 9, 7}
		};
		SudokuGame game = new SudokuGame(board);
		assertFalse(game.validate());
	}

	@Test
	public void shouldNotValidateBoardForDuplicatedNumbersInBlock() {
		Integer[][] board = {
				{2, 7, 1,  9, 5, 6,  3, 4, 8},
				{9, 5, 6,  3, 4, 8,  2, 7, 1},
				{3, 4, 2,  2, 7, 1,  9, 5, 6},

				{7, 2, 9,  1, 6, 5,  4, 8, 3},
				{1, 6, 5,  4, 8, 3,  7, 2, 9},
				{4, 8, 3,  7, 2, 9,  1, 6, 5},

				{5, 1, 2,  6, 9, 7,  8, 3, 4},
				{6, 9, 7,  8, 3, 4,  5, 1, 2},
				{8, 3, 4,  5, 1, 2,  6, 9, 7}
		};
		SudokuGame game = new SudokuGame(board);
		assertFalse(game.validate());
	}

	@Test
	public void shouldSolveBoardForIncompleteGame() {
		Integer n = null;
		Integer[][] board = {
				{2, n, 1,  n, n, 6,  n, n, n},
				{9, 5, n,  3, n, 8,  2, n, 1},
				{n, n, n,  2, n, n,  9, 5, n},

				{7, n, 9,  1, 6, n,  4, n, n},
				{1, n, n,  4, n, n,  n, n, 9},
				{4, n, n,  n, n, n,  n, n, 5},

				{5, n, n,  6, n, 7,  n, n, 4},
				{n, n, 7,  n, n, n,  n, n, n},
				{8, n, 4,  5, n, n,  6, n, 7}
		};
		SudokuGame game = new SudokuGame(board);
		game.solveBoard();
		assertTrue(game.validate());
	}

	@Test
	public void shouldReturnInvalidPositionsForInvalidGame() {
		Integer[][] board = {
				{2, 7, 1,  9, 5, 6,  3, 4, 8},
				{9, 5, 6,  3, 4, 8,  2, 7, 1},
				{3, 4, 2,  2, 7, 1,  9, 5, 6},

				{7, 2, 9,  1, 6, 5,  4, 8, 3},
				{1, 6, 5,  4, 8, 3,  7, 2, 9},
				{4, 8, 3,  7, 2, 9,  1, 6, 5},

				{5, 1, 2,  6, 9, 7,  8, 3, 4},
				{6, 9, 7,  8, 3, 4,  5, 1, 2},
				{8, 3, 4,  5, 1, 2,  6, 9, 7}
		};
		SudokuGame game = new SudokuGame(board);
		List<Integer> invalidPositions = game.getInvalidPositions();
		assertEquals(invalidPositions.size(), 4);
		assertTrue(invalidPositions.get(0) == 0);
		assertTrue(invalidPositions.get(1) == 20);
		assertTrue(invalidPositions.get(2) == 21);
		assertTrue(invalidPositions.get(3) == 56);
	}

}