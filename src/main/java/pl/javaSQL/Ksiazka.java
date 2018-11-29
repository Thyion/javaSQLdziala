package pl.javaSQL;

public class Ksiazka {
    private int id;
    private String tytul;
    private String autor;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTytul() {
        return tytul;
    }
    public void setTytul(String tytul) {
        this.tytul = tytul;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Ksiazka() {}
    public Ksiazka(int id, String tytul, String autor) {
        this.id = id;
        this.tytul = tytul;
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "["+id+"] - "+tytul+" - "+autor;
    }
}