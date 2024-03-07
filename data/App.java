import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App {

    static String url = "jdbc:mysql://localhost:3306/nour";
    static String utilisateur = "root";
    static String motDePasse = "";

    public static void main(String[] args) {
        String tabname = "lolo";
        int id = 6;
        Connection connexion = connexion();
        getData(connexion, tabname);
        update(connexion, tabname);
        delete(connexion, tabname, id);

    }

    public static Connection connexion() {
        try {
            Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
            System.out.println("Connexion réussie !");
            return connexion;
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
            return null;
        }
    }

    public static void createTable(Connection connexion, String tabname) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS " + tabname + " (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(50)," +
                "age INT" +
                ");";

        try (Statement statement = connexion.createStatement()) {
            statement.executeUpdate(createTableSQL);
            System.out.println("Table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertData(Connection connexion, String tabname) {
        String insertSQL = "INSERT INTO " + tabname + " (name, age) VALUES (?, ?)";
        String name = "bobo";
        int age = 50;

        try (PreparedStatement preparedStatement = connexion.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) inserted successfully.");
        } catch (SQLException e) {
            System.err.println("Error inserting data: " + e.getMessage());
        }
    }

    static public void update(Connection connexion, String tabname) {
        String nameToUpdate = "jou";
        int newAge = 40;
        int idToUpdate = 10; // Assuming you want to update the record with id=1

        // SQL statement to update data
        String updateSQL = " UPDATE " + tabname + " SET name=?, age=? WHERE id=?";

        try (PreparedStatement preparedStatement = connexion.prepareStatement(updateSQL)) {
            // Set the new values for the prepared statement
            preparedStatement.setString(1, nameToUpdate);
            preparedStatement.setInt(2, newAge);
            preparedStatement.setInt(3, idToUpdate);

            // Execute the SQL statement to update the data
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
        }
    }

    static public void delete(Connection connexion, String tabname, int id) {

        int idToDelete = id; // Assuming you want to update the record with id=1

        // SQL statement to update data
        String deleteSQL = "DELETE FROM " + tabname + " WHERE id=?";

        try (PreparedStatement preparedStatement = connexion.prepareStatement(deleteSQL)) {
            // Set the new values for the prepared statement
            preparedStatement.setInt(1, idToDelete);

            // Execute the SQL statement to update the data
            int rowsAffected = preparedStatement.executeUpdate();
            System.out.println(rowsAffected + " row(s) updated successfully.");
        } catch (SQLException e) {
            System.err.println("Error updating data: " + e.getMessage());
        }
    }

    static public void getData(Connection connexion, String tabname) {
        String selectSQL = "SELECT * FROM " + tabname;

        try (Statement statement = connexion.createStatement();
                ResultSet resultSet = statement.executeQuery(selectSQL)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                System.out.println("ID: " + id + ", Name: " + name + ", Age: " + age);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving data: " + e.getMessage());
        }
    }
}
