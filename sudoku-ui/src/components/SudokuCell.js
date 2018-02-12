import React, { Component } from 'react';
import PropTypes from 'prop-types';

class SudokuCell extends Component {

    constructor(props) {
        super(props);
        this.onChange = this.onChange.bind(this);
    }

    onKeyPress(e) {
        const re = /[1-9]+/g;
        if (!re.test(e.key)) {
          e.preventDefault();
        }        
    }

    onChange(e) {
        this.props.updateBoard(parseInt(e.target.value), this.props.x, this.props.y);
    }

    render() {
        return (<div className="box"><input type="text" 
                                            value={this.props.value ? this.props.value : ""} 
                                            maxLength="1" 
                                            onChange={this.onChange}
                                            onKeyPress={this.onKeyPress}/></div>);
    }
}

SudokuCell.propTypes = {
    x: PropTypes.number.isRequired,
    y: PropTypes.number.isRequired,
    updateBoard: PropTypes.func.isRequired
}

export default SudokuCell;