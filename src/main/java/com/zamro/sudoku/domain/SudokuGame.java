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
			setClues(blockNum, randomClues, numbers.get(blockNum), positions.get(blockNum));
		}
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

	private void setClues(int blockNum, List<Integer> totalClues, List<Integer> numbers, List<Integer> positions) {
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

	public static void main(String[] args) {
		SudokuGame game = new SudokuGame(MAX_CLUES);
		System.out.println(game.toString());
	}
}

class Pos {
	int x, y;
}