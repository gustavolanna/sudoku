package com.zamro.sudoku.controller;

import com.zamro.sudoku.domain.SudokuGame;
import com.zamro.sudoku.dto.SudokuGameDTO;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/sudoku")
public class SudokuController {

	@GetMapping
	public @ResponseBody SudokuGameDTO getBoard(@RequestParam("clues") int totalClues) {
		return new SudokuGameDTO(new SudokuGame(totalClues));
	}

	@PostMapping("/validate")
	public @ResponseBody SudokuGameDTO validateBoard(@RequestBody Integer[][] board) {
		return new SudokuGameDTO(new SudokuGame(board), true);
	}

	@PutMapping("/solve")
	public @ResponseBody SudokuGameDTO solveGame(@RequestBody Integer[][] board) {
		SudokuGame game = new SudokuGame(board);
		game.solveBoard();
		return new SudokuGameDTO(game);
	}

}
