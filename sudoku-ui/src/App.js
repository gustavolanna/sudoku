import React, { Component } from 'react';
import Logo from './logo.png';
import SudokuImg from './Sudoku.png';
import './App.css';
import Sudoku from './components/Sudoku';
import api from './Api';

class App extends Component {

    render() {
        return (
            <div className="App">
                <header className="App-header">
                    <div><img src={Logo} className="App-logo" alt="logo" /></div>
                    <div><img src={SudokuImg} className="App-logo" alt="logo" /></div>
                </header>
                <Sudoku loadGame={api.loadGame} validateGame={api.validateGame} solveGame={api.solveGame}/>
            </div>
        );
    }
}

export default App;
