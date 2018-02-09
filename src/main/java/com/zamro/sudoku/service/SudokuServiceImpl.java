package com.zamro.sudoku.service;

import com.zamro.sudoku.domain.Sudoku;

import com.zamro.sudoku.repository.SudokuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SudokuServiceImpl implements SudokuService {

	@Autowired
	private SudokuRepository repository;

	@Override
	public void save(Sudoku sudoku) {
		repository.save(sudoku);
	}
}
