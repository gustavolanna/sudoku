package com.zamro.sudoku.domain;

import java.util.HashSet;
import java.util.Set;

public class SudokuPositions {

	private static final String ROW = "R";

	private static final String COLUMN = "C";

	private static final String BLOCK = "B";

	private Set<String> positions;

	public SudokuPositions(Integer[][] board) {
		loadPositions(board);
	}

	private void loadPositions(Integer[][] board) {
		positions = new HashSet<String>();
		for (int x = 0; x < board.length; x++) {
			for (int y = 0; y < board[x].length; y ++) {
				register(x, y, board[x][y], null);
			}
		}
	}

	public void register(int x, int y, Integer value, Integer oldValue) {
		if (value != null) {
			positions.add(row(y, value));
			positions.add(col(x, value));
			positions.add(block(x, y, value));
		} else if (oldValue != null) {
			positions.remove(row(y, value));
			positions.remove(col(x, value));
			positions.remove(block(x, y, value));
		}
	}

	private String block(int x, int y, Integer value) {
		int blockX = (int) Math.floor(x / SudokuGame.BLOCK_SIZE);
		int blockY = (int) Math.floor(y / SudokuGame.BLOCK_SIZE);
		int blockNumber = 3 * blockX + blockY;
		return key(BLOCK + "-" + blockNumber, value);
	}

	private String col(int y, Integer value) {
		return key(COLUMN + "-" + y, value);
	}

	private String row(int x, Integer value) {
		return key(ROW + "-" + x, value);
	}

	private String key(String prefix, Integer value) {
		return prefix + "-" + value;
	}

	public boolean isAllowed(int x, int y, Integer value) {
		return value == null ||
				((value > 0 && value <= SudokuGame.BOUND_VALUE) &&
						!positions.contains(row(y, value)) &&
						!positions.contains(col(x, value)) &&
						!positions.contains(block(x, y, value)));
	}

}
