import axios from 'axios';

export const loadGame = (clues) => {
    return axios.get(`/api/sudoku?clues=${clues}`);
}

export const validateGame = (board) => {
    return axios.post('/api/sudoku/validate', board);
}

export const solveGame = (board) => {
    return axios.put('/api/sudoku/solve', board);
}
