package com.zamro.sudoku.dto;

import com.zamro.sudoku.domain.SudokuGame;

import java.util.List;

public class SudokuGameDTO {

	private boolean warning;

	private String message;

	private List<Integer> invalidValues;

	private Integer[][] game;

	public SudokuGameDTO(Integer[][] game, String message) {
		this.message = message;
		this.game = game;
		this.warning = true;
	}

	public SudokuGameDTO(SudokuGame game) {
		this.invalidValues = game.getInvalidValues();
		this.game = game.getBoard();
		if (!invalidValues.isEmpty()) {
			this.message  = "This game has invalid values.";
			this.warning = true;
		} else if (game.isSolved()) {
			this.message = "Puzzle solved!";
		}
	}

	public SudokuGameDTO(SudokuGame game, boolean validate) {
		this(game);
		if (validate && !this.warning) {
			this.message = "This game still valid!";
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

	public boolean getWarning() {
		return warning;
	}
}
