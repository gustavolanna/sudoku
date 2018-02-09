package com.zamro.sudoku.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Entity
public class Sudoku {

	@GeneratedValue
	@Id
	private Long id;

	private Integer size;

	@OneToMany(cascade = CascadeType.ALL)
	private List<SudokuCell> board;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public List<SudokuCell> getBoard() {
		return board;
	}

	public void setBoard(List<SudokuCell> board) {
		this.board = board;
	}

	public void createBoard(int size) {
		createCells(size);
	}

	private void createCells(int size) {
		setBoard(new ArrayList<SudokuCell>());
		setSize(size);
		Random random = new Random();
		for (int x = 0; x < size; x++) {
			for (int y = 0; y < size; y++) {
				addNumber(x, y, random.nextInt(size));
			}
		}
	}

	private void addNumber(int x, int y, int value) {
		SudokuCell cell = new SudokuCell(x, y, value);
		cell.setSudoku(this);
		getBoard().add(cell);
	}

	public int[][] getMatrix() {
		int[][] matrix = new int[getSize()][getSize()];
		for (SudokuCell cell : getBoard()) {
			matrix[cell.getX()][cell.getY()] = cell.getValue();
		}
		return matrix;
	}

}
