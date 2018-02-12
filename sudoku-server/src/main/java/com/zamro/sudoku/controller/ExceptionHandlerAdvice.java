package com.zamro.sudoku.controller;

import com.zamro.sudoku.domain.SudokuException;
import com.zamro.sudoku.dto.SudokuGameDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	@ExceptionHandler(SudokuException.class)
	public ResponseEntity<SudokuGameDTO> handleBadRequestException(SudokuException ex) {
		return new ResponseEntity(new SudokuGameDTO(ex.getGame(), ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<SudokuGameDTO> handleAnyOtherException(Exception ex) {
		return new ResponseEntity(new SudokuGameDTO(null, ex.getMessage()), HttpStatus.BAD_REQUEST);
	}

}