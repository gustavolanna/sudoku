import React, { Component } from 'react';
import SudokuCell from './SudokuCell';

const DEFAULT_CLUES = 34;

class Sudoku extends Component {

    constructor(props) {
        super(props);
        this.state = {
            board: []
        };
        this.updateBoard = this.updateBoard.bind(this);
    }

    updateBoard(value, x, y) {
        let board = [...this.state.board];
        board[x][y] = value;
        this.setState({ ...this.state, board});
    }

    componentWillMount() { 
        this.props.loadGame(DEFAULT_CLUES).then(result => {
            this.setState({
                original: result.data,
                board: result.data
            });
        });
    }
    
    renderCell(value, x, y) {
        const key = x + (y * this.state.board.length);
        return <SudokuCell key={key} value={value} x={x} y={y} updateBoard={this.updateBoard} />;
    }

    render() {
        return (
            <div className="wrapper">
                {this.state.board.map((line, x) => line.map((value, y) => this.renderCell(value, x, y)))}
            </div>
        )
    }

}

export default Sudoku;