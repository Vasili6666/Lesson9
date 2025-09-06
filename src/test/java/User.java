import java.util.List;

public class User {
    private int id;
    private String imie;
    private String nazwisko;
    private String email;
    private int wiek;
    private boolean aktywny;
    private List<String> zamowienia;

    // Пустой конструктор (обязателен для Jackson)
   public User() {
    }

    // Геттеры и сеттеры для всех полей
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getWiek() {
        return wiek;
    }

    public void setWiek(int wiek) {
        this.wiek = wiek;
    }

    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public List<String> getZamowienia() {
        return zamowienia;
    }

    public void setZamowienia(List<String> zamowienia) {
        this.zamowienia = zamowienia;
    }

    // Для красивого вывода
    @Override
    public String toString() {
        return imie + " " + nazwisko + " (wiek: " + wiek + ")";
    }
}
