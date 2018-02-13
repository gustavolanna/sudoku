import React, { Component } from 'react';
import SudokuCell from './SudokuCell';
import PropTypes from 'prop-types';

const DEFAULT_CLUES = 34;

class Sudoku extends Component {

    constructor(props) {
        super(props);
        this.state = {
            message: null,
            warning: false,
            invalidValues: [],
            fixedValues: [],
            board: [],
            original: []
        };
        this.updateState = this.updateState.bind(this);
        this.updateBoard = this.updateBoard.bind(this);
        this.validateGame = this.validateGame.bind(this);
        this.solveGame = this.solveGame.bind(this);
        this.handleErros = this.handleErros.bind(this);
    }

    componentWillMount() { 
        this.props.loadGame(DEFAULT_CLUES).then(this.updateState).catch(this.handleErros);
    }

    handleErros(response) {
        this.setState({
            ...this.state,
            message: response.message,
            warning: true
        })
    }

    updateState(response) {
        let fixedValues = this.getFixedValues(response);
        let original = this.getOriginalBoard(response);
        this.setState({
            ...this.state,
            fixedValues,
            original,
            message: response.data.message,
            warning: response.data.warning,
            invalidValues: response.data.invalidValues,
            board: response.data.game
        });
        this.clearMessage();
    }

    clearMessage() {
        setTimeout(() => this.setState({...this.state, message: ""}), 3000);
    }

    getFixedValues(response) {
        let fixedValues = this.state.fixedValues;
        if (fixedValues.length === 0) {
            const game = response.data.game;
            game.forEach((line, y) => line.forEach((value, x) => {
                if (value) {
                    fixedValues.push(x + (y * game.length))
                }
            }));
        }
        return fixedValues;        
    }

    getOriginalBoard(response) {
        let original = this.state.original;
        if (original.length === 0) {
            original = response.data.game.map(l => l.map(v => v));
        }
        return original;
    }

    updateBoard(value, x, y) {
        let board = [...this.state.board];
        board[y][x] = value > 0 && value <= 9 ? value : null;
        this.setState({ ...this.state, board});
    }
    
    validateGame() {
        if (this.gameWasChanged()) {
            this.props.validateGame(this.state.board).then(this.updateState).catch(this.handleErros);
        } else {
            this.setState({
                ...this.state,
                message: "The game wasn't changed, why don't you try some numbers first? :)",
                warning: true
            })
            this.clearMessage();
        }
    }

    gameWasChanged() {
        return !this.state.original.every((line, y) => line.every((value, x) => value === this.state.board[y][x]));
    }

    solveGame() {
        this.props.solveGame(this.state.original).then(this.updateState).catch(this.handleErros);
    }

    renderCell(value, x, y) {
        const key = x + (y * this.state.board.length);
        const invalid = this.state.invalidValues.find(value => value === key) >= 0;
        const fixed = this.state.fixedValues.find(value => value === key) >= 0;
        return <SudokuCell  key={key} 
                            value={value} 
                            x={x} y={y} 
                            updateBoard={this.updateBoard} 
                            invalid={invalid}
                            fixed={fixed}/>;
    }

    render() {
        if (this.state.board.length === 0 && this.state.warning) {
            return <p className={this.state.warning ? "warningMessage" : "message"}>{this.state.message}</p>
        } else if (this.state.board.length === 0) {
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