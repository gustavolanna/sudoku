package com.zamro.sudoku.controller;

import com.zamro.sudoku.domain.Sudoku;
import com.zamro.sudoku.dto.SudokuDTO;
import com.zamro.sudoku.service.SudokuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/board")
public class SudokuController {

	@Autowired
	private SudokuService service;

	@GetMapping
	public @ResponseBody SudokuDTO getBoard(@RequestParam("size") int size) {
		Sudoku sudoku = new Sudoku();
		sudoku.createBoard(size);
		service.save(sudoku);
		return new SudokuDTO(sudoku);
	}

}
