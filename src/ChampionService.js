import axios from 'axios';
const CHAMPION_API_BASE_URL = "http://localhost:9000/api/v1/tft/compute/info";

class ChampionService{
    getChampions(playerName,playerTag,numberOfMatches){
        return  axios.get(CHAMPION_API_BASE_URL+ '/' + playerName +'/' + playerTag +'/'+numberOfMatches );
    }
}

export default new ChampionService();