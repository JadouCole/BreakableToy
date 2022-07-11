import http from "../../frontend/src/http-common";
class TutorialDataService{
    getAll(){
        return http.get("/api/v1/valorant/champions")
    }
}
export default new TutorialDataService();