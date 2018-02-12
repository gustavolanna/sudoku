import React, { Component } from 'react';

const DEFAULT_CLUES = 34;

class Sudoku extends Component {

    constructor(props) {
        super(props);
        this.state = {
            board: []
        };
    }

    componentWillMount() { 
        this.props.loadGame(DEFAULT_CLUES).then(result => {
            console.log(result.data);
            this.setState({
                board: result.data
            });
        });
    }

    render() {
        const divs = this.state.board.map(lines => lines.map(value => <div className="box">{value}</div>));
        return (<div className="wrapper">
            {divs}
        </div>);
    }

}

export default Sudoku;