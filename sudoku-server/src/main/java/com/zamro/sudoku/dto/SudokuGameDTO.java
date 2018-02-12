package com.zamro.sudoku.dto;

import com.zamro.sudoku.domain.SudokuGame;

import java.util.List;

public class SudokuGameDTO {

	private String message;

	private List<Integer> invalidValues;

	private Integer[][] game;

	public SudokuGameDTO(Integer[][] game, String message) {
		this.message = message;
		this.game = game;
	}

	public SudokuGameDTO(SudokuGame game) {
		this.invalidValues = game.getInvalidValues();
		this.game = game.getBoard();
		if (!invalidValues.isEmpty()) {
			this.message = "This game has invalid values.";
		}
	}

	public List<Integer> getInvalidValues() {
		return invalidValues;
	}

	public Integer[][] getGame() {
		return game;
	}

	public String getMessage() {
		return message;
	}

}
