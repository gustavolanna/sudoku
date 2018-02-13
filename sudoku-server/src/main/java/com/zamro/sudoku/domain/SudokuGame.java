package com.zamro.sudoku.domain;

import com.zamro.sudoku.util.SudokuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SudokuGame {

	public static final int BOARD_SIZE = 9;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final SudokuValidator validator = new SudokuValidator();

	private Integer[][] board;

	public SudokuGame(Integer[][] board) {
		validator.validate(board);
		this.board = board;
		log.info("creating new game with board " + board);
	}

	public SudokuGame(int clues) {
		log.info("creating new game with " + clues + " clues");
		board = new Integer[BOARD_SIZE][BOARD_SIZE];
		solveBoard(0, 0, SudokuUtil.randomNumbers());
		clear(SudokuUtil.randomPositions(SudokuUtil.POSITIONS.length - clues));
	}

	public SudokuGame() {
		this(BOARD_SIZE * BOARD_SIZE);
	}

	public Integer[][] getBoard() {
		return board;
	}

	public boolean validate() {
		return validator.validate(this);
	}

	public List<Integer> getInvalidValues() {
		return validator.validateGame(board);
	}

	public boolean solveBoard() {
		return solveBoard(0, 0, SudokuUtil.randomNumbers());
	}

	public int getTotalValues() {
		int total = 0;
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] != null) {
					total++;
				}
			}
		}
		return total;
	}

	public void clear(List<Integer> positions) {
		log.info("clearing positions");
		for (Integer position: positions) {
			int x = position % SudokuGame.BOARD_SIZE;
			int y = (int) Math.floor(position / SudokuGame.BOARD_SIZE);
			board[y][x] = null;
		}
	}

	private boolean solveBoard(int x, int y, List<Integer> randomNumbers) {
		if (x >= board[y].length) {
			x = 0;
			if (++y >= board.length) {
				log.info("Solution found");
				return true;
			}
		}
		if (board[y][x] != null) {
			return solveBoard(x + 1, y, randomNumbers);
		} else {
			for (Integer number : randomNumbers) {
				if (validator.isValidAssignment(board, x, y, number)) {
					board[y][x] = number;
					if (solveBoard(x + 1, y, randomNumbers)) {
						return true;
					}
					board[y][x] = null;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				Integer value = board[y][x];
				sb.append(value == null ? "_" : value);
				sb.append(" ");
				if ((x + 1) % 3 == 0) {
					sb.append(" ");
				}
			}
			if ((y + 1) % 3 == 0) {
				sb.append("\n");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	public boolean isSolved() {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == null) {
					return false;
				}
			}
		}
		return true;
	}
}