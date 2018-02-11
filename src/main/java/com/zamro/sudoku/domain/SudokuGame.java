package com.zamro.sudoku.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SudokuGame {

	public static final int BOARD_SIZE = 9;

	public static final int BLOCK_SIZE = 3;

	public static final int BOUND_VALUE = 9;

	private static final int MAX_CLUES = 36;

	private static final int MAX_CLUES_PER_BLOCK = 4;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private int[][] board;

	private SudokuValidator validator;

	private static final Integer[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

	public SudokuGame(Integer[][] board) {
		validator = new SudokuValidator(board);
	}

	public SudokuGame() {
		board = new int[BOARD_SIZE][BOARD_SIZE];
		createBoard(board, 0, 0);
//		Random random = new Random();
	}

//	private List<List<Integer>> randomList(Random random) {
//		List<List<Integer>> list = new LinkedList();
//		for (int i = 0; i < BOARD_SIZE; i++) {
//			List<Integer> blockNumbers = new LinkedList<>();
//			List<Integer> numbers = new LinkedList<>(Arrays.asList(NUMBERS));
//			while (blockNumbers.size() < BOARD_SIZE) {
//				int index = random.nextInt(numbers.size());
//				Integer element = numbers.remove(index);
//				blockNumbers.add(element);
//			}
//			list.add(blockNumbers);
//		}
//		return list;
//	}


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


	public boolean createBoard(int[][] game, int x, int y) {
		if (x >= game[y].length) {
			x = 0;
			if (++y >= game.length) {
				return true;
			}
		}
		for (int num = 1; num <= 9; num++) {
			if (isValidAssignment(game, x, y, num)) {
				game[y][x] = num;
				if (createBoard(game, x + 1, y)) {
					return true;
				}
				game[y][x] = 0;
			}
		}
		return false;
	}

	private boolean isValidAssignment(int[][] game, int x, int y, int value) {
		return !hasValueInRow(game[y], value) &&
				!hasValueInCol(game, x, value) &&
				!hasValueInBlock(game, x, y, value);
	}

	private boolean hasValueInBlock(int[][] game, int x, int y, int value) {
		int offSetX = x - (x % BLOCK_SIZE) + BLOCK_SIZE;
		int offSetY = y - (y % BLOCK_SIZE) + BLOCK_SIZE;
		for (int startY = y - (y % BLOCK_SIZE); startY < offSetY; startY++) {
			for (int startX = x - (x % BLOCK_SIZE); startX < offSetX; startX++) {
				if (game[startY][startX] == value) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean hasValueInCol(int[][] game, int x, int value) {
		for (int y = 0; y < game.length; y++) {
			if (game[y][x] == value) {
				return true;
			}
		}
		return false;
	}

	private boolean hasValueInRow(int[] row, int value) {
		for (int x = 0; x < row.length; x++) {
			if (row[x] == value) {
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		SudokuGame sudokuGame = new SudokuGame();
		System.out.println(sudokuGame.toString());
	}
}