import React from 'react';
import ListChampionComponent from './ListChampionComponent';
import {useHistory} from "react-router-dom";
import {Button} from "reactstrap";


class UserInput extends React.Component {

    constructor(props) {
        super(props);
        this.state ={ playerName:'', playerTag: '',numberOfMatches:''};
    }

    handleChange = ({target})=> {
        this.setState({[target.name]: target.value});
    };

    handleSubmit= event => {
        event.preventDefault();
        this.props.history.push('/compute', this.state)
    };

    handleClick= event => {
        event.preventDefault();
        this.props.history.push('/championlist')
    };

    render(){
        return(
            <div>
                <form onSubmit={this.handleSubmit}>
                    <label htmlFor="playerName"></label>
                        Name:
                        <input type="text" name ="playerName" value = {this.state.playerName} onChange = {this.handleChange}/>
                         Player Tag:
                        <input type="text" name="playerTag" value ={this.state.playerTag} onChange = {this.handleChange}/>
                        Number of Matches:
                        <input type="text" name="numberOfMatches" value = {this.state.numberOfMatches} onChange={this.handleChange}/>
                        <input type="submit" value="Submit"/>
                </form>
                <Button onClick = {this.handleClick}>List Champions</Button>
            </div>
        );
    }
}
export default UserInput;