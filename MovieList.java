package lektionmedjdbc;
import java.sql.*;
import java.util.*;
public class MovieList {
	
	final static String PATH ="jdbc:mysql://localhost/movie_list";
	final static String USER = "root";
	final static String PASSWORD = "";
	private static Scanner myScanner = new Scanner (System.in);
	public static void main(String[] args) {
		
		Connection cnt = null;
		PreparedStatement stmt = null;
		while(true){
			
			try{
				
				cnt = DriverManager.getConnection(PATH, USER, PASSWORD);
			System.out.println("Do you want to?\nadd a movie\nadd a lead actor\nremove a movie\nremove a lead actor\nchange a movie\nchange a lead actor\nsee the movies table\n"
					+ "see the lead actors table\nsee the genre table\nselect a genre from the movie list\nwrite a genre from the movie list\nselect a lead actor from a movie\n"
					+ "see the lead actor from a movie\nselect a letter of a name\nEXIT or show the database?");
			String ask = myScanner.nextLine();
			switch(ask){
			case "add a movie":
				stmt = cnt.prepareStatement("INSERT INTO movies (title, premiere, id_genre) VALUES (?, ?, ?)");
				
				System.out.println("Give me a movie title");
				stmt.setString(1, myScanner.nextLine());
				
				System.out.println("Premiers date (YYYY-MM-DD)");
				stmt.setString(2, myScanner.nextLine());
				
				System.out.println("And Genre id");
				stmt.setInt(3, Integer.parseInt(myScanner.nextLine()));
				
				stmt.executeUpdate();
				break;
				
			case "add a lead actor":
				stmt = cnt.prepareStatement("INSERT INTO lead_actors (fname, lname, birthdate, id_movie) VALUES (?, ?, ?, ?)");
				
				System.out.println("Give me the first name");
				stmt.setString(1, myScanner.nextLine());
				
				System.out.println("Give me the last name"); 
				stmt.setString(2, myScanner.nextLine());
				
				System.out.println("Birthdate (YYYY-MM-DD)");
				stmt.setString(3, myScanner.nextLine());
				
				System.out.println("And the movie id");
				stmt.setInt(4, Integer.parseInt(myScanner.nextLine()));
				
				stmt.executeUpdate();
				break;
			
			case "remove a movie":
				stmt = cnt.prepareStatement("DELETE FROM movies WHERE title=?");
				
				System.out.println("Write the name of the movie");
				stmt.setString(1, myScanner.nextLine());
				
				stmt.executeUpdate();
				break;
				
			case "remove a lead actor":
				stmt = cnt.prepareStatement("DELETE FROM lead_actors WHERE id=?");
				
				System.out.println("Select an ID from the lead actors table");
				stmt.setInt(1, Integer.parseInt(myScanner.nextLine()));
				
				stmt.executeUpdate();
				break;
			
			case "change a movie":
				stmt = cnt.prepareStatement("UPDATE movies SET title=?, premiere=?, id_genre=? WHERE id=?");
				
				System.out.println("Enter the ID of the movie");
				stmt.setInt(4, Integer.parseInt(myScanner.nextLine()));
				
				System.out.println("change the name of the movie");
				stmt.setString(1, myScanner.nextLine());
				
				System.out.println("change the date of the premiere (YYYY-MM-DD)");
				stmt.setString(2, myScanner.nextLine());
				
				System.out.println("change the ID genre");
				stmt.setInt(3, Integer.parseInt(myScanner.nextLine()));
				
				
				stmt.executeUpdate();
				
				break;
				
			case "change a lead actor":
				stmt = cnt.prepareStatement("UPDATE lead_actors SET fname=?, lname=?, birthdate=? WHERE id=?");
				
				System.out.println("Enter the ID of the actor");
				stmt.setInt(4, Integer.parseInt(myScanner.nextLine()));
				
				System.out.println("Change the first name");
				stmt.setString(1, myScanner.nextLine());
				
				System.out.println("Change the last name");
				stmt.setString(2, myScanner.nextLine());
				
				System.out.println("Change the birthdate (YYYY-MM-DD)");
				stmt.setString(3, myScanner.nextLine());
				
				
				stmt.executeUpdate();
				break;
				
				
			case "select a genre from the movie list":
				stmt = cnt.prepareStatement("SELECT * FROM movies WHERE id_genre=?");
				
				
				System.out.println("Select a number between 1 to 7 genre from the list\n1. Action\n2. Comedy\n3. Drama\n4. Thriller\n5. Romance\n6. Mystery\n7. Crime");
				stmt.setString(1, myScanner.nextLine());
				ResultSet rs0 = stmt.executeQuery();
				
				while(rs0.next()){
				String title = rs0.getString("title");
				System.out.println("The movie is: " + title);
				}
				
				break;
				
			case "write a genre from the movie list":
				stmt = cnt.prepareStatement("SELECT * FROM movies INNER JOIN genre ON movies.id_genre=genre.id WHERE genre=?");
				
				
				System.out.println("write between those genre from the list\nAction\nComedy\nDrama\nThriller\nRomance\nMystery\nCrime");
				stmt.setString(1, myScanner.nextLine());
				ResultSet rs01 = stmt.executeQuery();
				
				while(rs01.next()){
				String title = rs01.getString("title");
				System.out.println("the movies is: " + title);
				}
				
				break;
				
			case"select a lead actor from a movie":
				stmt = cnt.prepareStatement("SELECT * FROM movies INNER JOIN lead_actors ON lead_actors.id_movie=movies.id WHERE lead_actors.id=?");
				
				System.out.println("Select a number between 1 to 10 actors from the list\n1. Leonardo Dicaprio\n2. Tom Hanks\n3. Jason Biggs\n4. Christian Bale\n5. Al Pacino"
						+ "\n6. Damian Chapa\n7. Anna Faris\n8. Silvestre Stalone \n9. Johnny Depp \n10. Noami Watts");
				stmt.setString(1, myScanner.nextLine());
				ResultSet rs02 = stmt.executeQuery();
				
				while(rs02.next()){
					String title = rs02.getString("title");
					System.out.println(title);
					
				}
				
				break;
				
			case"select a letter of a name":

				System.out.println("Write the title of the lead actors");
				String searchString;
				searchString = myScanner.nextLine();
				
				stmt = cnt.prepareStatement("SELECT * FROM lead_actors WHERE fname LIKE ?");
				stmt.setString(1, searchString + "%");
				ResultSet rs04 = stmt.executeQuery();
				
				while(rs04.next()){
					String fname = rs04.getString("fname");
					String lname = rs04.getString("lname");
					System.out.println("The lead actor is: " + fname + " " +  lname);
					
				}
				
				break;
				
			case "see the lead actor from a movie":
				stmt = cnt.prepareStatement("SELECT * FROM lead_actors INNER JOIN movies ON lead_actors.id_movie=movies.id WHERE movies.title=?");
				
				System.out.println("Write the title of the movie");
				stmt.setString(1, myScanner.nextLine());
				ResultSet rs03 = stmt.executeQuery();
				
				while(rs03.next()){
					String fname = rs03.getString("fname");
					String lname = rs03.getString("lname");
					System.out.println("The lead actor is :" + fname + " " + lname);
					
				}
				
				break;
				
			case "see the movies table":
				stmt = cnt.prepareStatement("SELECT * FROM movies");
				
				ResultSet rs1 = stmt.executeQuery();
				while(rs1.next()){
				System.out.println("ID: " + Integer.parseInt(rs1.getString("id")) + "\nTitle: " + rs1.getString("title") + "\nPremiere: " + rs1.getString("premiere")
				+ "\nID genre: " + Integer.parseInt(rs1.getString("id_genre")) + "\n");
				}
				break;
				
			case "see the lead actors table":
				stmt = cnt.prepareStatement("SELECT * FROM lead_actors");
				
				ResultSet rs2 = stmt.executeQuery();
				
				while(rs2.next()){
				System.out.println("ID: " + Integer.parseInt(rs2.getString("id")) + "\nFirst name: " + rs2.getString("fname") + "\nLast name: " + rs2.getString("lname")
				+ "\nBirthdate: " + rs2.getString("birthdate") + "\nThe ID movie: " + Integer.parseInt(rs2.getString("id_movie")) + "\n");
				
				}
				break;
				
			case "see the genre table":
				stmt = cnt.prepareStatement("SELECT * FROM genre");
				
				ResultSet rs3 = stmt.executeQuery();
				while(rs3.next()){
				System.out.println("ID: " + Integer.parseInt(rs3.getString("id")) + "\t|\tGenre: " + rs3.getString("genre"));
				}
				break;
			
			case "show the database":
				//TO-DO
				stmt = cnt.prepareStatement("SELECT * FROM movies INNER JOIN genre ON movies.id_genre=genre.id JOIN lead_actors ON movies.id=lead_actors.id_movie");
				
				ResultSet rs = stmt.executeQuery();
				
				
				while(rs.next()){
					System.out.println("Name of the movie: " + rs.getString("title") + "\nPremiere: " + rs.getString("premiere") + "\nGenre: " + 
				rs.getString("genre") + "\nLead actor: " + rs.getString("fname") + " " + rs.getString("lname")
				+ "\nThe lead actor's birthdate: " + rs.getString("birthdate") +"\n");
					
				}
				
				break;
			case "exit" :
				System.out.println("Goodbye!");
				System.exit(0);
				
				
			default: System.out.println("Incorrect input");
			
			
			
			}
		
		}
		
		catch (SQLException | NumberFormatException e)
		{
			System.out.println(e.getMessage());
		}
		
		}
		
	}

}
