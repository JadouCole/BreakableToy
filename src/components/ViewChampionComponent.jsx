import React, {Component} from "react";
import ChampionService from "../ChampionService";

class ViewChampionComponent extends Component{
    constructor(props) {
        super(props);
        this.state = {
            id: this.props.match.params.id,
            champion: {}
        }
    }
    componentDidMount() {
        ChampionService.getChampions().then(res => {
            this.setState({champion: res.data});
        })
    }render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Employee Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> Employee First Name: </label>
                            <div> { this.state.champion.champion }</div>
                        </div>
                        <div className = "row">
                            <label> Employee Last Name: </label>
                            <div> { this.state.champion.trait_0 }</div>
                        </div>
                        <div className = "row">
                            <label> Employee Email ID: </label>
                            <div> { this.state.champion.trait_1}</div>
                        </div>
                    </div>

                </div>
            </div>
        )
    }
}
export default ViewChampionComponent