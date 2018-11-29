package pl.javaSQL;

import java.util.List;

public class JavaSQL {

    public static void main(String[] args) {
        DataBase database = new DataBase();
        database.openConnection();

        database.createTables();
        database.insertCzytelnik("Karol", "Maciaszek", "92873847182");
        database.insertCzytelnik("Piotr", "Wojtecki", "89273849128");
        database.insertCzytelnik("Abdul", "Dabdul");

        database.insertKsiazka("Cień Wiatru", "Carlos Ruiz Zafon");
        database.insertKsiazka("W pustyni i w puszczy", "Henryk Sienkiewicz");
        database.insertKsiazka("Harry Potter", "Joanne Kathleen Rowling.");

        List<Czytelnik> czytelnicy = database.selectCzytelnicy();
        List<Ksiazka> ksiazki = database.selectKsiazki();

        System.out.println("Lista czytelników: ");
        for(Czytelnik c: czytelnicy)
            System.out.println(c);

        System.out.println("Lista książek:");
        for(Ksiazka k: ksiazki)
            System.out.println(k);

//            database.closeConnection();
    }

}
