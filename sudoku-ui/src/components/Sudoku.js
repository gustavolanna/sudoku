import React, { Component } from 'react';
import SudokuCell from './SudokuCell';
import PropTypes from 'prop-types';

const DEFAULT_CLUES = 34;

class Sudoku extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: null,
            warningMessage: false,
            invalidValues: [],
            fixedValues: [],
            board: [],
            original: []
        };
        this.updateState = this.updateState.bind(this);
        this.updateBoard = this.updateBoard.bind(this);
        this.validateGame = this.validateGame.bind(this);
        this.solveGame = this.solveGame.bind(this);
    }

    componentWillMount() { 
        this.props.loadGame(DEFAULT_CLUES).then(this.updateState);
    }

    updateState(response) {
        let fixedValues = this.state.fixedValues;
        if (fixedValues.length == 0) {
            const game = response.data.game;
            game.forEach((line, y) => line.forEach((value, x) => {
                if (value) {
                    fixedValues.push(x + (y * game.length))
                }
            }));
        }
        let original = this.state.original;
        if (original.length == 0) {
            original = response.data.game.map(l => l.map(v => v));
        }
        this.setState({
            ...this.state,
            fixedValues,
            original,
            message: response.data.message,
            warning: response.data.warning,
            invalidValues: response.data.invalidValues,
            board: response.data.game
        });
    }

    updateBoard(value, x, y) {
        let board = [...this.state.board];
        board[y][x] = value;
        this.setState({ ...this.state, board});
    }
    
    validateGame() {
        this.props.validateGame(this.state.board).then(this.updateState);
    }

    solveGame() {
        this.props.solveGame(this.state.original).then(this.updateState);
    }

    renderCell(value, x, y) {
        const key = x + (y * this.state.board.length);
        const invalid = this.state.invalidValues.find(value => value == key) >= 0;
        const fixed = this.state.fixedValues.find(value => value == key) >= 0;
        return <SudokuCell  key={key} 
                            value={value} 
                            x={x} y={y} 
                            updateBoard={this.updateBoard} 
                            invalid={invalid}
                            fixed={fixed}/>;
    }

    render() {
        if (this.state.board.length == 0) {
            return <div>Loading...</div>
        }
        return (
            <div>
                <p className="App-intro">Here is the puzzle. Good luck!</p>
                <div className="wrapper">
                    {this.state.board.map((line, y) => line.map((value, x) => this.renderCell(value, x, y)))}
                </div>
                <p className={this.state.warning ? "warningMessage" : "message"}>{this.state.message}</p>
                <button onClick={this.validateGame}>Validate</button>
                <button onClick={this.solveGame}>Solve</button>
            </div>
        )
    }

}

Sudoku.propTypes = {
    loadGame: PropTypes.func.isRequired,
    validateGame: PropTypes.func.isRequired,
    solveGame: PropTypes.func.isRequired
}

export default Sudoku;