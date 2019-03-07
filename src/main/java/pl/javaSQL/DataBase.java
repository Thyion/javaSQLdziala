package pl.javaSQL;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class DataBase {

    private Connection conn;
    private Statement statement;

    public Connection openConnection() {

        if(conn == null){
            String url = "jdbc:mysql://localhost/";
            String dbName = "java";
            String driver = "com.mysql.jdbc.Driver";
            String userName = "root";
            String password = "1";

            try{
                Class.forName(driver);
                this.conn = (DriverManager.getConnection(url+dbName, userName, password));
                statement = conn.createStatement();
                System.out.println("OPEN CONNECTION SUCCESS");
            }
            catch (ClassNotFoundException | SQLException sql){
                System.out.println("FAILED");

            }
        }
        return conn;
    }

    public boolean createTables()  {
        String createCzytelnicy = "CREATE TABLE IF NOT EXISTS czytelnicy (id_czytelnika INTEGER PRIMARY KEY AUTO_INCREMENT, imie varchar(255), nazwisko varchar(255), pesel BIGINT(11))";
        String createKsiazki = "CREATE TABLE IF NOT EXISTS ksiazki (id_ksiazki INTEGER PRIMARY KEY AUTO_INCREMENT, tytul varchar(255), autor varchar(255))";
        String createWypozyczenia = "CREATE TABLE IF NOT EXISTS wypozyczenia (id_wypozycz INTEGER PRIMARY KEY AUTO_INCREMENT, id_czytelnika int, id_ksiazki int)";
        try {
            statement.execute(createCzytelnicy);
            statement.execute(createKsiazki);
            statement.execute(createWypozyczenia);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertCzytelnik(String imie, String nazwisko, String pesel) {
        try {

            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO czytelnicy (imie, nazwisko, pesel) VALUES ('"+imie+"','"+nazwisko+"','"+pesel+"')");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public boolean insertCzytelnik(String imie, String nazwisko) {
        try {

            PreparedStatement prepStmt = conn.prepareStatement("INSERT INTO czytelnicy (imie, nazwisko) VALUES ('"+imie+"','"+nazwisko+"')");
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertKsiazka(String tytul, String autor, int rok) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement("insert into ksiazki values (NULL, ?, ?, ?)");
            prepStmt.setString(1, tytul);
            prepStmt.setString(2, autor);
            prepStmt.setInt(3, rok);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu");
            return false;
        }
        return true;
    }

    public boolean insertWypozycz(int idCzytelnik, int idKsiazka) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into wypozyczenia values (NULL, ?, ?);");
            prepStmt.setInt(1, idCzytelnik);
            prepStmt.setInt(2, idKsiazka);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wypozyczaniu");
            return false;
        }
        return true;
    }

    public List<Czytelnik> selectCzytelnicy() {
        List<Czytelnik> czytelnicy = new LinkedList<Czytelnik>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM czytelnicy");
            int id;
            String imie, nazwisko, pesel;
            while(result.next()) {
                id = result.getInt("id_czytelnika");
                imie = result.getString("imie");
                nazwisko = result.getString("nazwisko");
                pesel = result.getString("pesel");
                czytelnicy.add(new Czytelnik(id, imie, nazwisko, pesel));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return czytelnicy;
    }

    public List<Ksiazka> selectKsiazki() {
        List<Ksiazka> ksiazki = new LinkedList<Ksiazka>();
        try {
            ResultSet result = statement.executeQuery("SELECT * FROM ksiazki");
            int id, rok;
            String tytul, autor;
            while(result.next()) {
                id = result.getInt("Id");
                tytul = result.getString("Tytuł");
                autor = result.getString("Autor");
                rok = result.getInt("Rok");
                ksiazki.add(new Ksiazka(id, tytul, autor, rok));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return ksiazki;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}
