package com.zamro.sudoku.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SudokuGame {

	public static final int BOARD_SIZE = 9;

	private static final Integer[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final SudokuValidator validator = new SudokuValidator();

	private Integer[][] board;

	public SudokuGame(Integer[][] board) {
		validator.validate(board);
		this.board = board;
	}

	public SudokuGame() {
		board = new Integer[BOARD_SIZE][BOARD_SIZE];
		fillBoard(board, 0, 0, randomNumbers());
	}

	public Integer[][] getBoard() {
		return board;
	}

	public boolean validate() {
		return validator.validate(this);
	}

	public List<Integer> getInvalidPositions() {
		return validator.validateGame(board);
	}

	private List<Integer> randomNumbers() {
		Random random = new Random();
		List<Integer> orderedList = new LinkedList(Arrays.asList(NUMBERS));
		List<Integer> randomList = new ArrayList<>();
		while (!orderedList.isEmpty()) {
			Integer randomValue = orderedList.remove(random.nextInt(orderedList.size()));
			randomList.add(randomValue);
		}
		return randomList;
	}

	public boolean fillBoard() {
		return fillBoard(board, 0, 0, randomNumbers());
	}

	private boolean fillBoard(Integer[][] game, int x, int y, List<Integer> numbers) {
		if (x >= game[y].length) {
			x = 0;
			if (++y >= game.length) {
				return true;
			}
		}
		if (game[y][x] != null) {
			return fillBoard(game, x + 1, y, numbers);
		} else {
			for (Integer number : numbers) {
				if (validator.isValidAssignment(game, x, y, number)) {
					game[y][x] = number;
					if (fillBoard(game, x + 1, y, numbers)) {
						return true;
					}
					game[y][x] = null;
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

}