package OV.Domein;

import java.sql.Date;
import java.util.ArrayList;

public class Reiziger {
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private ArrayList<OVChipkaart> ovChipkaarts = new ArrayList<>();

    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public String getVoorletters() {
        return voorletters;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public int getId() {
        return id;
    }

    public ArrayList<OVChipkaart> getOvChipkaarts() {
        return ovChipkaarts;
    }

    public void addOVChipkaart(OVChipkaart ovChipkaart) {
        if (!ovChipkaarts.contains(ovChipkaart)) {
            ovChipkaarts.add(ovChipkaart);
            ovChipkaart.setReiziger(this);
        }
    }

    public void setOvChipkaarts(ArrayList<OVChipkaart> ovChipkaarts) {
        this.ovChipkaarts = ovChipkaarts;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public void setVoorletters(String voorletters) {
        this.voorletters = voorletters;
    }

    @Override
    public String toString() {
        return String.format("Reiziger {#%d %s%s %s, geb. %s}",
                id,
                voorletters,
                tussenvoegsel != null ? " " + tussenvoegsel : "",
                achternaam,
                geboortedatum);
    }
}