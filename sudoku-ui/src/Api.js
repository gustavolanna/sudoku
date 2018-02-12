import axios from 'axios';

const api = {
    
    loadGame: (clues) => {
        return axios.get(`/api/sudoku?clues=${clues}`);
    },

    validateGame: (board) => {
        return axios.post('/api/sudoku/validate', board);
    },

    solveGame: (board) => {
        return axios.put('/api/sudoku/solve', board);
    }
}

export default api;