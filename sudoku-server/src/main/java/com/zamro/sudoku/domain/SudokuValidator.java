package com.zamro.sudoku.domain;

import java.util.ArrayList;
import java.util.List;

public class SudokuValidator {

	public static final int BLOCK_SIZE = 3;

	public boolean validate(SudokuGame game) {
		return validate(game.getBoard()) && validateGame(game.getBoard()).isEmpty();
	}

	public boolean validate(Integer[][] board) {
		if (board == null) {
			throw new SudokuException(board, "Board cannot be null. It should be a 9 x 9 matrix");
		}
		return validateBoundaries(board) && validateValues(board);
	}

	private boolean validateBoundaries(Integer[][] board) {
		if (board.length != SudokuGame.BOARD_SIZE) {
			throw new SudokuException(board, "Invalid board size. It should be a 9 x 9 matrix");
		}
		for (int x = 0; x < board.length; x++) {
			if (board[x].length != SudokuGame.BOARD_SIZE) {
				throw new SudokuException(board, "Invalid board size. It should be a 9 x 9 matrix");
			}
		}
		return true;
	}

	private boolean validateValues(Integer[][] board) {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				Integer value = board[y][x];
				if (value != null && (value <= 0 || value > 9)) {
					throw new SudokuException(board, "Invalid board value at [" + y + ", " + x + "]. Expected value 1-9 or null, found " + value);
				}
			}
		}
		return true;
	}

	public List<Integer> validateGame(Integer[][] board) {
		validate(board);
		List<Integer> invalidPositions = new ArrayList<>();
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				Integer currentValue = board[y][x];
				board[y][x] = null;
				if (!isValidAssignment(board, x, y, currentValue)) {
					invalidPositions.add(matrixToArray(x, y));
				}
				board[y][x] = currentValue;
			}
		}
		return invalidPositions;
	}

	private int matrixToArray(int x, int y) {
		return y * SudokuGame.BOARD_SIZE + x;
	}

	public boolean isValidAssignment(Integer[][] game, int x, int y, Integer number) {
		return number == null ||
				!hasValueInRow(game[y], number) &&
				!hasValueInCol(game, x, number) &&
				!hasValueInBlock(game, x, y, number);
	}

	private boolean hasValueInBlock(Integer[][] game, int x, int y, int value) {
		int offSetX = x - (x % BLOCK_SIZE) + BLOCK_SIZE;
		int offSetY = y - (y % BLOCK_SIZE) + BLOCK_SIZE;
		for (int startY = y - (y % BLOCK_SIZE); startY < offSetY; startY++) {
			for (int startX = x - (x % BLOCK_SIZE); startX < offSetX; startX++) {
				if (game[startY][startX] != null && game[startY][startX] == value) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasValueInCol(Integer[][] game, int x, int value) {
		for (int y = 0; y < game.length; y++) {
			if (game[y][x] != null && game[y][x] == value) {
				return true;
			}
		}
		return false;
	}

	private boolean hasValueInRow(Integer[] row, int value) {
		for (int x = 0; x < row.length; x++) {
			if (row[x] != null && row[x] == value) {
				return true;
			}
		}
		return false;
	}

}
