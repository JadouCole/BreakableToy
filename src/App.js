import React from 'react';

import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListChampionComponent from './components/ListChampionComponent';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import UserInput from './components/UserInputComponent';
import AllChampionList from "./components/AllChampionList";

function App(){

  return(
      <div>
          <Router>
              <HeaderComponent/>
              <div className={"container"}>
                  <Switch>
                      <Route path = "/"  component = {UserInput} exact></Route>
                      <Route path = "/compute" component = {ListChampionComponent}></Route>
                      <Route path = "/championlist" component={AllChampionList}></Route>
                  </Switch>
              </div>
              <FooterComponent/>
          </Router>
      </div>
  );
};
export default App;

/*

 */