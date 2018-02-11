package com.zamro.sudoku.dto;

import com.zamro.sudoku.domain.SudokuGame;

import java.util.List;

public class SudokuGameDTO {

	private String message;

	private List<Integer> invalidPositions;

	private Integer[][] game;

	public SudokuGameDTO(Integer[][] game, String message) {
		this.message = message;
		this.game = game;
	}

	public SudokuGameDTO(SudokuGame game) {
		this.invalidPositions = game.getInvalidPositions();
		this.game = game.getBoard();
	}

	public List<Integer> getInvalidPositions() {
		return invalidPositions;
	}

	public Integer[][] getGame() {
		return game;
	}

	public String getMessage() {
		return message;
	}

}
