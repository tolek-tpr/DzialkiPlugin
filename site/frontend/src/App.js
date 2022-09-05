import React from 'react';
import { Switch, Route } from 'react-router'
import plots, {getPlots} from './pages/plots'

import './App.css';

const HomePage = () => (
  <p>TESTAAAAA</p>
)

class App extends React.Component {
  componentDidMount(plots) {
    console.log("Mounted")  
  }
    
  render() {
    return (
      <div>
        <Switch>
          
          <Route path='/world/:worldid' component={plots} />
          <Route exact path='/' component={HomePage} />
        </Switch>
      </div>
    );
  }
}

export default App;