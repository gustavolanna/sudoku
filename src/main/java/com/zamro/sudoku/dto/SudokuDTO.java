package com.zamro.sudoku.dto;

import com.zamro.sudoku.domain.Sudoku;

public class SudokuDTO {

	private Long id;

	private int[][] board;

	public SudokuDTO() {
	}

	public SudokuDTO(Sudoku sudoku) {
		this.id = sudoku.getId();
		this.board = sudoku.getMatrix();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}
}
