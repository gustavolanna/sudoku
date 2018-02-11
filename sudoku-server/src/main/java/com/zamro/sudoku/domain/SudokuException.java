package com.zamro.sudoku.domain;

public class SudokuException extends RuntimeException {

	private Integer[][] game;

	public SudokuException(Integer[][] game, String message) {
		super(message);
		this.game = game;
	}

	public Integer[][] getGame() {
		return game;
	}
}
