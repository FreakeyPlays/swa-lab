import React from "react";

import Login from './Login';
import View from "./View";

const theUrl = "localhost:8080/user"; 

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
        <Login url={theUrl} authorized={this.authorized}></Login>
        );

        }    
        
    }
    /*render(){
        
        return(
        <View></View>
        );
        
      
        
    }*/
}

export default App; 