package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
public DatabaseAccessorObject() {
	try {
		Class.forName("com.mysql.jdbc.Driver");
	} catch (ClassNotFoundException e) {

		e.printStackTrace();
	}
}
	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn;
		conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT * FROM film JOIN language on film.language_id = language.id WHERE film.id = ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int fId = rs.getInt(1);
			String title = rs.getString(2);
			String desc = rs.getString(3);
			short releaseYear = rs.getShort(4);
			int langId = rs.getInt(5);
			int rentDur = rs.getInt(6);
			double rate = rs.getDouble(7);
			int length = rs.getInt(8);
			double repCost = rs.getDouble(9);
			String rating = rs.getString(10);
			String features = rs.getString(11);
			String language = rs.getString(13);
			film = new Film(fId, title, desc, releaseYear, langId, language, rentDur, rate, length, repCost, rating, features,
					findActorsByFilmId(filmId));
		
		//film.setActors(findActorsByFilmId(filmId));
			//	List<Actor> actors = film.getActors();
		

		}
		stmt.close();
		conn.close();
		return film;
	}

	@Override
	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn;
		conn = DriverManager.getConnection(URL, user, pass);

		String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		if (actorResult.next()) {
			actor = new Actor();
			actor.setId(actorResult.getInt("id"));
			actor.setFirstName(actorResult.getString(2));
			actor.setLastName(actorResult.getString(3));

		}
		stmt.close();
		conn.close();
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<>();
		String user = "student";
		String pass = "student";
		Connection conn;
		conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id" + " WHERE film_id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Actor actor = new Actor();
			actor.setId(rs.getInt("id"));
			actor.setFirstName(rs.getString(2));
			actor.setLastName(rs.getString(3));
			actors.add(actor);

		}
		stmt.close();
		conn.close();

		return actors;
	}
      public List <Film> findFilmByKeyword(String keyword) throws SQLException {
    	  List<Film> films = new ArrayList<>();
    	  Film film = null;
  		String user = "student";
  		String pass = "student";
  		Connection conn;
  		conn = DriverManager.getConnection(URL, user, pass);
  		String sql = "SELECT * FROM film JOIN language on film.language_id = language.id WHERE description LIKE ? OR title LIKE ? ";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			int fId = rs.getInt("id");
			String title = rs.getString(2);
			String desc = rs.getString(3);
			short releaseYear = rs.getShort(4);
			int langId = rs.getInt(5);
			int rentDur = rs.getInt(6);
			double rate = rs.getDouble(7);
			int length = rs.getInt(8);
			double repCost = rs.getDouble(9);
			String rating = rs.getString(10);
			String features = rs.getString(11);
			String language = rs.getString(13);
			film = new Film(fId, title, desc, releaseYear, langId, language, rentDur, rate, length, repCost, rating, features, findActorsByFilmId(fId));
            films.add(film);
		}
		return films;
      }
}
