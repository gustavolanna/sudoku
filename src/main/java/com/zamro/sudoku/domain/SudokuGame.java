package com.zamro.sudoku.domain;

public class SudokuGame {

	public static final int BOARD_SIZE = 9;

	private Integer[][] board;

	private SudokuPositions positions;

	private SudokuValidator validator;

	public SudokuGame(Integer[][] board) {
		validator = new SudokuValidator(board);
		positions = new SudokuPositions(board);
	}

	public SudokuGame() {
		board = new Integer[BOARD_SIZE][BOARD_SIZE];
	}

	public boolean setValue(int x, int y, Integer value) {
		if (positions.isAllowed(x, y, value)) {
			positions.register(x, y, value, board[x][y]);
			board[x][y] = value;
			return true;
		}
		return false;
	}


//	private int getBlockPosition(int blockNumber) {
//		return (BLOCK_SIZE * blockNumber) % BOARD_SIZE;
//	}

}