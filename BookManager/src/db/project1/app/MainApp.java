/* 
	Program Overview:
		This program accepts user commands from console. Once program starts, user is prompted
		to select the desired function by entering the corresponding number. Based on the user's
		initial selection, a series of prompts will guide the user until his or her desired function
		is accomplished. Program will run until user terminates by selecting the 'Exit' function.
		Program options available to user include: 1. Add publisher 2. Add book 3. Edit book
		4. Delete book 5. Search book 6. Exit. The MySQL database 'bookmanager' will be updated
		according to user input. Program checks for integrity constraint violations and prompts
		user to change his or her input accordingly to ensure database integrity.

	Input:
		Once program starts, user enters commands at-will. Program will run until user selects
		'Exit' function. All user input is from console.

	Output:
		Prints a confirmation message for Add, Edit and Delete functions; prints an error message if exception
		occurs. Prompts user to revise input to avoid integrity constraint violations if necessary.
		Search Book options print desired output based on user's request. All output is to console*/

package db.project1.app;

import db.project1.view.*;;

/**
 * Application starts here. A MainView instance is created.
 */
public class MainApp {
	public static void main(String[] args) {
		new MainView().run();
	}
}