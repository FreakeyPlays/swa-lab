import React from "react";

import Login from './Login';
import View from "./View";



class App extends React.Component{

    constructor(props){
            super(props); 
            this.state = {
                loggedIn:false, 
            }; 
    }

    //isAdmin: true for AdminView
    authorized = () => {
            this.setState({loggedIn:true});
            console.log("authorized");
    }

    render(){
        if(this.state.loggedIn){
            return(
                <View></View>
            );
        } else {
        return(
        <Login authorized={this.authorized}></Login>
        );

        }      
    }
}

export default App; 