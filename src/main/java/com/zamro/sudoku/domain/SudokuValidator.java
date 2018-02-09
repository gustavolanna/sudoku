package com.zamro.sudoku.domain;

public class SudokuValidator {

	public SudokuValidator(Integer[][] board) {
		validateBoard(board);
	}

	public void validateBoard(Integer[][] board) {
		validateBoundaries(board);
		validateValues(board);

	}

	private void validateBoundaries(Integer[][] board) {
		if (board.length != SudokuGame.BOARD_SIZE) {
			throw new IllegalArgumentException("Invalid board size. It should be a 9 x 9 matrix");
		}
		for (int x = 0; x < board.length; x++) {
			if (board[x].length != SudokuGame.BOARD_SIZE) {
				throw new IllegalArgumentException("Invalid board size. It should be a 9 x 9 matrix");
			}
		}
	}

	private void validateValues(Integer[][] board) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				Integer value = board[x][y];
				if (value != null && (value < 0 || value > 9)) {
					throw new IllegalArgumentException("Invalid board value at [" + x + ", " + y + "]. Expected value 0-9, found " + value);
				}
			}
		}
	}

}
