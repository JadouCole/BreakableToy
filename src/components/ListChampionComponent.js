import React, {Component} from "react"
import axios from "axios";
import {Button} from "reactstrap";
const CHAMPION_API_BASE_URL = "http://localhost:9000/api/v1/tft/compute/info";

class ListChampionComponent extends Component{

    constructor(props) {
        super(props);

        this.state = {
            champions:[],
        }
    };

    componentDidMount() {
        getChampions(this.props.location.state.playerName,this.props.location.state.playerTag,this.props.location.state.numberOfMatches).then((res)=> {
           this.setState({champions:  res.data});
        });
    };

    handleClick= event => {
        event.preventDefault();
        this.props.history.push('/');
    };

    render(){
        return (
            <div className="Compute">
                <h2 className={"text-center"}> Champion List</h2>
                <div className={"row"}>
                    <button className={"btn btn-primary"}> Do Nothing</button>
                </div>
                <br></br>
                <div className ={"row"}>
                    <table className={"table table-striped table-bordered"}>
                    <thead>
                        <tr>
                            <th>Player Name</th>
                            <th>Average Placement</th>
                            <th>Most Common Trait</th>
                        </tr>
                    </thead>
                    <tbody>
                        {
                            <tr>
                                <th>{this.state.champions.playerName}</th>
                                <th>{this.state.champions.averagePlacement}</th>
                                <th>{this.state.champions.mostCommonTrait}</th>
                            </tr>
                        }
                    </tbody>
                    </table>

                </div>
                <Button onClick = {this.handleClick}>Reset</Button>
            </div>
        )
    }
}

function getChampions(playerName,playerTag,numberOfMatches){
    return  axios.get(CHAMPION_API_BASE_URL + '/' + playerName + '/' + playerTag + '/' + numberOfMatches );
}

export default ListChampionComponent;
