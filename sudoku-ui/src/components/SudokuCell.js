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
        this.props.updateBoard(parseInt(e.target.value, 10), this.props.x, this.props.y);
    }

    render() {
        let className;
        if (this.props.fixed) {
            className = " fixedValue";
        } else if (this.props.invalid) {
            className = " invalidValue";
        } else {
            className = " defaultValue";
        }
        return (<div className="box"><input type="text" 
                                            className={className}
                                            value={this.props.value ? this.props.value : ""} 
                                            maxLength="1" 
                                            readOnly={this.props.fixed}
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