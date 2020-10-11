package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	Scanner input = new Scanner(System.in);
	DatabaseAccessorObject db = new DatabaseAccessorObject();

	public static void main(String[] args) throws SQLException {
		FilmQueryApp app = new FilmQueryApp();
		
		app.launch();
	}



	private void launch() throws SQLException {
	boolean keepgoing = true;
		while(keepgoing) {
		System.out.println(" please choose one of the following options");

		System.out.println("1. to look up a film by its id");
		System.out.println("2. to look up a film by a keyword");
		System.out.println("3. to exit");

		
		
			int choice;
			 choice = input.nextInt();
	
		if(choice == 3) {
			keepgoing = false;
		}
		startUserInterface(choice);
	
	}
	}

	private void startUserInterface(int choice) throws SQLException {
		

		if (choice == 1) {
			System.out.println("please enter the id of the film you want to see");
			int id = input.nextInt();
			Film film = db.findFilmById(id);
			if(film == null) {
				System.out.println("invalid id");
			}
			System.out.println(film);
			
		}
		else if(choice == 2) { 
			System.out.println("please enter the keyword to use");
			String keyword = input.next();                   
			List <Film> films = db.findFilmByKeyword(keyword);
			System.out.println(films);
		}
		else if(choice == 3 ) {
			System.out.println("goodbye");
		}
		else {
			System.out.println("please enter a valid option");
		}
	}

}
