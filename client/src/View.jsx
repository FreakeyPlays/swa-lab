import React from "react";
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";

class View extends React.Component{

    constructor(props) {
		super(props);
	    this.state = {	  
            companies: [],
            isLoading: true
		};		
	}

    componentDidMount(){
            fetch("http:/localhost:8080/company/all")
            .then(function(response) { return response.json()})
            .then( companies => this.setState({ companies }));
            console.log("fetched data ")
    }

    render(){

        if (this.state.isLoading) {
            return <div>Loading...</div>;
        }
        return(
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell>Column 1</TableCell>
                        <TableCell>Column 2</TableCell>
                        <TableCell>Column 3</TableCell>
                        <TableCell>Actions</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {this.state.companies.map(comp => ( 
                        <TableRow key={comp.value}>
                            <TableCell>{comp.id}</TableCell>
                            <TableCell>{comp.companyName}</TableCell>
                            <TableCell>{comp.address}</TableCell>
                            <TableCell><button onClick={this.handleSave}>Save</button></TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        );
    }
}

export default View;
