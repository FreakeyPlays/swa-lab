import React from "react";

import "./View.css";
import { styled } from '@mui/system';
import TabsListUnstyled from '@mui/base/TabsListUnstyled';
import TabPanelUnstyled from '@mui/base/TabPanelUnstyled';
import { Table, TableBody, TableCell, TableHead, TableRow } from "@mui/material";
import TabsUnstyled from '@mui/base/TabsUnstyled';
import { buttonUnstyledClasses } from '@mui/base/ButtonUnstyled';
import TabUnstyled, { tabUnstyledClasses } from '@mui/base/TabUnstyled';

const blue = {
    50: '#F0F7FF',
    100: '#C2E0FF',
    200: '#80BFFF',
    300: '#66B2FF',
    400: '#3399FF',
    500: '#007FFF',
    600: '#0072E5',
    700: '#0059B2',
    800: '#004C99',
    900: '#003A75',
  };
const grey = {
    50: '#f6f8fa',
    100: '#eaeef2',
    200: '#d0d7de',
    300: '#afb8c1',
    400: '#8c959f',
    500: '#6e7781',
    600: '#57606a',
    700: '#424a53',
    800: '#32383f',
    900: '#24292f',
  };
const TabsList = styled(TabsListUnstyled)(
    ({ theme }) => `
    min-width: 400px;
    background-color: ${blue[500]};
    border-radius: 12px;
    margin-bottom: 16px;
    display: flex;
    align-items: center;
    justify-content: center;
    align-content: space-between;
    box-shadow: 0px 4px 8px ${theme.palette.mode === 'dark' ? grey[900] : grey[200]};
    `,
  );
  const Tab = styled(TabUnstyled)`
  font-family: IBM Plex Sans, sans-serif;
  color: white;
  cursor: pointer;
  font-size: 0.875rem;
  font-weight: bold;
  background-color: transparent;
  width: 100%;
  padding: 12px;
  margin: 6px 6px;
  border: none;
  border-radius: 7px;
  display: flex;
  justify-content: center;

  &:hover {
    background-color: ${blue[400]};
  }

  &:focus {
    color: #fff;
    outline: 3px solid ${blue[200]};
  }
  
  &.${tabUnstyledClasses.selected} {
    background-color: #fff;
    color: ${blue[600]};
  }

  &.${buttonUnstyledClasses.disabled} {
    opacity: 0.5;
    cursor: not-allowed;
  }
`;

const TabPanel = styled(TabPanelUnstyled)`
  width: 100%;
  font-family: IBM Plex Sans, sans-serif;
  font-size: 0.875rem;
`;

class View extends React.Component{

    constructor(props) {
		super(props);
	    this.state = {	  
            list: [],
            isLoading: true
		};		
	}

    componentDidMount(){
        fetch("http://localhost:8080/company/all")
        .then( response => response.json())
        .then( list => {
          console.log(list)
          this.setState({list})
        })
          
        
    }

    render(){

        
        return(
           
           <div className="table">
            <header>
              <h1>Welcome back!</h1>
            </header>
            <TabsUnstyled defaultValue={0}>
                <TabsList>
                    <Tab>Customers</Tab>
                    <Tab>Contracts</Tab>
                    <Tab>User</Tab>
                </TabsList>
                    <TabPanel value={0}>
                        <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.list.map(company => ( 
                                <TableRow key={company.id}>
                                    <TableCell>{company.companyName}</TableCell>
                                    <TableCell>{company.id}</TableCell>
                                    <TableCell>{company.department}</TableCell>
                                    <TableCell><button onClick={this.handleEdit}>Edit</button></TableCell>
                                    <TableCell><button onClick={this.handleDelete}>Delete</button></TableCell>
                                    <TableCell><button onClick={this.handleShowContracts}>Contracts</button></TableCell>
                                    <TableCell><button onClick={this.handleShowUsers}>Users</button></TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                            </Table>
                    </TabPanel>
                    <TabPanel value={1}>
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.list.map(contracts => ( 
                                <TableRow key={contracts.id}>
                                    <TableCell>{contracts.companyName}</TableCell>
                                    <TableCell>{contracts.startDate}</TableCell>
                                    <TableCell>{contracts.endDate}</TableCell>
                                    <TableCell><button onClick={this.handleEdit}>Edit</button></TableCell>
                                    <TableCell><button onClick={this.handleDelete}>Delete</button></TableCell>
                                    <TableCell><button onClick={this.handleShowContracts}>Contracts</button></TableCell>
                                    <TableCell><button onClick={this.handleShowUsers}>Users</button></TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                            </Table>
                    </TabPanel>
                    <TabPanel value={2}>Page User</TabPanel>
            </TabsUnstyled>
            </div>
        
            
        );
        
    }
}

export default View;
