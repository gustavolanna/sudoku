package com.zamro.sudoku.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class SudokuGame {

	public static final int BOARD_SIZE = 9;

	public static final int BLOCK_SIZE = 3;

	public static final int BOUND_VALUE = 9;

	private static final int MAX_CLUES = 36;

	private static final int MAX_CLUES_PER_BLOCK = 4;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private Integer[][] board;

	private SudokuPositions positions;

	private SudokuValidator validator;

	private static final Integer[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

	public SudokuGame(Integer[][] board) {
		validator = new SudokuValidator(board);
		positions = new SudokuPositions(board);
	}

	public SudokuGame(int clues) {
		board = new Integer[BOARD_SIZE][BOARD_SIZE];
		positions = new SudokuPositions(board);
		Random random = new Random();
		List<List<Integer>> numbers = randomList(random);
		List<List<Integer>> positions = randomList(random);
		List<Integer> randomClues = randomClues(random, clues);
		for (int blockNum = 0; blockNum < BOARD_SIZE; blockNum++) {
			createBoard(blockNum, randomClues, numbers.get(blockNum), positions.get(blockNum));
		}
	}

	private boolean solveGame(int x, int y) {
		if (!gameSolved()) {
			for (int value = 1; value <= 9; value++) {
				if (setValue(x, y, value)) {
					return solveGame(x + 1, y + 1);
				}
			}
			return false;
		}
		return true;
	}

	private boolean gameSolved() {
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				if (board[y][x] == null) {
					return false;
				}
			}
		}
		return true;
	}

	private List<List<Integer>> randomList(Random random) {
		List<List<Integer>> list = new LinkedList();
		for (int i = 0; i < BOARD_SIZE; i++) {
			List<Integer> blockNumbers = new LinkedList<>();
			List<Integer> numbers = new LinkedList<>(Arrays.asList(NUMBERS));
			while (blockNumbers.size() < BOARD_SIZE) {
				int index = random.nextInt(numbers.size());
				Integer element = numbers.remove(index);
				blockNumbers.add(element);
			}
			list.add(blockNumbers);
		}
		return list;
	}

	private List<Integer> randomClues(Random random, int totalClues) {
		if (totalClues == 0) {
			totalClues = MAX_CLUES;
		}
		List<Integer> clues = new ArrayList<Integer>();
		for (int i = 0; i < BOARD_SIZE; i++) {
//			clues.add(random.nextInt(MAX_CLUES_PER_BLOCK) + 1);
			clues.add(9);
		}
		return clues;
	}

	private void createBoard(int blockNum, List<Integer> totalClues, List<Integer> numbers, List<Integer> positions) {
		Pos pos = getBlockPosition(blockNum);
		int clues = totalClues.get(blockNum);
		while (clues > 0 && !numbers.isEmpty()) {
			int value = numbers.remove(0);
			int position = positions.remove(0) - 1; // arrays starts at 0
			int cellX = position % BLOCK_SIZE;
			int cellY = (int) Math.floor(position / BLOCK_SIZE);
			if (setValue(pos.x + cellX, pos.y + cellY, value)) {
				clues--;
				log.info(toString());
			}
		}
	}

	public boolean setValue(int x, int y, Integer value) {
		if (positions.isAllowed(x, y, value)) {
			positions.register(x, y, value, board[y][x]);
			board[y][x] = value;
			return true;
		}
		return false;
	}

	private Pos getBlockPosition(int blockNumber) {
		Pos pos = new Pos();
		pos.x = (BLOCK_SIZE * blockNumber) % BOARD_SIZE;
		pos.y = (int) Math.floor(blockNumber / BLOCK_SIZE);
		return pos;
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


	public static boolean createBoard(int[][] game, int x, int y) {
		if (x >= game[y].length) {
			x = 0;
			if (++y >= game.length) {
				return true;
			}
		}
		for (int num = 1; num <= 9; num++) {
			if (validAssingment(game, x, y, num)) {
				game[y][x] = num;
				if (createBoard(game, x + 1, y)) {
					return true;
				}
				game[y][x] = 0;
			}
		}
		return false;
	}

	private static boolean validAssingment(int[][] game, int x, int y, int value) {
		return !hasValueInRow(game[y], value) &&
				!hasValueInCol(game, x, value) &&
				!hasValueInBlock(game, x, y, value);
	}

	private static boolean hasValueInBlock(int[][] game, int x, int y, int value) {
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

	private static boolean hasValueInCol(int[][] game, int x, int value) {
		for (int y = 0; y < game.length; y++) {
			if (game[y][x] == value) {
				return true;
			}
		}
		return false;
	}

	private static boolean hasValueInRow(int[] row, int value) {
		for (int x = 0; x < row.length; x++) {
			if (row[x] == value) {
				return true;
			}
		}
		return false;
	}

//	private static boolean hasFreePositions(int[][] game) {
//		for (int y = 0; y < game.length; y++) {
//			for (int x = 0; x < game[y].length; x++) {
//				if (game[y][x] == 0) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	public static String printGame(int[][] board) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		for (int y = 0; y < board.length; y++) {
			for (int x = 0; x < board[y].length; x++) {
				sb.append(board[y][x]);
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

	public static void main(String[] args) {
		int[][] game = new int[BOARD_SIZE][BOARD_SIZE];
		createBoard(game, 0, 0);
		System.out.println(printGame(game));
//		SudokuGame game = new SudokuGame(MAX_CLUES);
//		System.out.println(game.toString());
	}
}

class Pos {
	int x, y;
}