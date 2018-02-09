package com.zamro.sudoku.controller;

import com.zamro.sudoku.domain.SudokuGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/board")
public class SudokuController {

	@GetMapping
	public @ResponseBody Integer[][] getBoard() {
		SudokuGame game = new SudokuGame();
		return new SudokuDTO(game);
	}

}
