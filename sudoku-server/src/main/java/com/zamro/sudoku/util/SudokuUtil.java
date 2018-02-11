package com.zamro.sudoku.util;

import com.zamro.sudoku.domain.SudokuGame;

import java.util.*;

public final class SudokuUtil {

	public final static Integer[] POSITIONS = new Integer[SudokuGame.BOARD_SIZE * SudokuGame.BOARD_SIZE];

	private static final Integer[] NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9};

	static {
		for (int i = 0; i < POSITIONS.length; i++) {
			POSITIONS[i] = i;
		}
	}

	public static List<Integer> randomNumbers() {
		return randomNumbers(NUMBERS, NUMBERS.length);
	}

	public static List<Integer> randomPositions(int total) {
		return randomNumbers(POSITIONS, total);
	}

	public static List<Integer> randomNumbers(Integer[] numbers, int total) {
		Random random = new Random();
		List<Integer> orderedList = new LinkedList(Arrays.asList(numbers));
		List<Integer> randomList = new ArrayList<>();
		while (!orderedList.isEmpty() && total-- > 0) {
			Integer randomValue = orderedList.remove(random.nextInt(orderedList.size()));
			randomList.add(randomValue);
		}
		return randomList;
	}

}
