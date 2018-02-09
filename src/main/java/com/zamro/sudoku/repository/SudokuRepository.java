package com.zamro.sudoku.repository;

import com.zamro.sudoku.domain.Sudoku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SudokuRepository extends JpaRepository<Sudoku, Long> {

}
