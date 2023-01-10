import React from "react";
import Login from "./Login";
import UserView from "./UserView"; 


const theUrl = "localhost:8080/user"; 

class App extends React.Component{

    constructor(props){
            super(props); 
            this.state = {
                loggedIn:false, 
            }; 
    }

    authorized = () => {
            this.setState({loggedIn:true});
            console.log("authorized");
    }

    render(){
        /*if(this.state.loggedIn){
            return(
                <View url={theUrl}></View>
            );
        } else {*/
        return(
        <Login url={theUrl} authorized={this.authorized}></Login>
        );
            
        
    }
}

export default App; 