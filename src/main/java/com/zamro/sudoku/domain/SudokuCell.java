package com.zamro.sudoku.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SudokuCell {

	@GeneratedValue
	@Id
	private Long id;

	private Integer x;

	private Integer y;

	private Integer value;

	@ManyToOne
	private Sudoku sudoku;

	public SudokuCell() {
	}

	public SudokuCell(int x, int y, int value) {
		setX(x);
		setY(y);
		setValue(value);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Sudoku getSudoku() {
		return sudoku;
	}

	public void setSudoku(Sudoku sudoku) {
		this.sudoku = sudoku;
	}

}
