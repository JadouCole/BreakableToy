import React, {Component} from "react";
import {Button} from "reactstrap";
import axios from "axios";
const CHAMPION_API_BASE_URL = "http://localhost:9000/api/v1/tft/championlist";

class AllChampionList extends Component{
    constructor(props) {
        super(props);
        this.state = {
            champions:[],
        }
    }

    componentDidMount() {
        getChampions().then((res)=> {
            this.setState({champions:  res.data});
        });
    };

    handleClick= event => {
        event.preventDefault();
        this.props.history.push('/')
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
                            <th>Champion Name</th>
                            <th>First Trait</th>
                            <th>Last Trait</th>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            this.state.champions.map(
                                champion =>
                                    <tr key = {champion.id}>
                                        <td>{champion.champion}</td>
                                        <td>{champion.trait_0}</td>
                                        <td>{champion.trait_1}</td>
                                    </tr>
                            )

                        }
                        </tbody>
                    </table>
                </div>
                <Button onClick = {this.handleClick}>Reset</Button>
            </div>
        )
    }
}

function getChampions(){
    return  axios.get(CHAMPION_API_BASE_URL );
}

export default AllChampionList;
