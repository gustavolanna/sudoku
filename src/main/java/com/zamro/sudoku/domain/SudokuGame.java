package com.zamro.sudoku.domain;

import java.util.HashSet;
import java.util.Set;

public class SudokuGame {

	private static int BOARD_SIZE = 9;

	private static int BOUND_VALUE = 10;

	private final String ROW = "r";

	private final String COLUMN = "c";

	private final String BLOCK = "b";

	private Integer [][] board;

	private Set<String> positions;

	public SudokuGame(Integer[][] board) {
		checkBoard(board);
		loadPositions(board);
	}

	public SudokuGame() {
	}

	private void checkBoard(Integer[][] board) {
		checkBoundaries(board);
		checkValues(board);
	}

	private void checkBoundaries(Integer[][] board) {
		if (board.length != BOARD_SIZE) {
			throw new IllegalArgumentException("Invalid board size. It should be a 9 x 9 matrix");
		}
		for (int x = 0; x < board.length; x++) {
			if (board[x].length != BOARD_SIZE) {
				throw new IllegalArgumentException("Invalid board size. It should be a 9 x 9 matrix");
			}
		}
	}

	private void checkValues(Integer[][] board) {
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y++) {
				Integer value = board[x][y];
				if (value != null && (value < 0 || value > 9)) {
					throw new IllegalArgumentException("Invalid board value at [" + x + ", " + y + "]. Expected value 0-9, found " + value);
				}
			}
		}
	}

	public boolean setValue(int x, int y, Integer value) {
		if (isAllowed(x, y, value)) {
			register(x, y, value);
			board[x][y] = value;
			return true;
		}
		return false;
	}

	public boolean isAllowed(int x, int y, Integer value) {
		return value == null ||
				(!positions.contains(row(value)) &&
				!positions.contains(col(value)) &&
				!positions.contains(block(x, y, value)));
	}

	private void loadPositions(Integer[][] board) {
		this.board = board;
		positions = new HashSet<String>();
		for (int x = 0; x < this.board.length; x++) {
			for (int y = 0; y < this.board[x].length; y ++) {
				register(x, y, this.board[x][y]);
			}
		}
	}

	private void register(int x, int y, Integer value) {
		if (value != null) {
			positions.add(row(value));
			positions.add(col(value));
			positions.add(block(x, y, value));
		} else if (board[x][y] != null) {
			positions.remove(row(value));
			positions.remove(col(value));
			positions.remove(block(x, y, value));
		}
	}

	private String block(int x, int y, Integer value) {
		int blockX = (int) Math.floor(x/3);
		int blockY = (int) Math.floor(y/3);
		int blockNumber = 3 * blockX + blockY;
		return key(BLOCK + "-" + blockNumber, value);
	}

	private String col(Integer value) {
		return key(COLUMN, value);
	}

	private String row(Integer value) {
		return key(ROW, value);
	}

	private String key(String prefix, Integer value) {
		return prefix + "-" + value;
	}

}
