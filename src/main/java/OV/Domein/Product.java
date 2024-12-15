package OV.Domein;

import java.util.ArrayList;

public class Product {

    private int product_nummer;
    private String naam;
    private double prijs;
    private String beschrijving;
    private ArrayList<OVChipkaart> ovchipkaarts;

    public Product(int product_nummer, String naam, double prijs, String beschrijving) {
        this.product_nummer = product_nummer;
        this.naam = naam;
        this.prijs = prijs;
        this.beschrijving = beschrijving;
        this.ovchipkaarts = new ArrayList<>();
    }

    public double getPrijs() {
        return prijs;
    }

    public int getProduct_nummer() {
        return product_nummer;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public String getNaam() {
        return naam;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void setProduct_nummer(int product_nummer) {
        this.product_nummer = product_nummer;
    }

    public ArrayList<OVChipkaart> getOvchipkaarts() {
        return ovchipkaarts;
    }

    public void setOvchipkaarts(ArrayList<OVChipkaart> ovchipkaarts) {
        this.ovchipkaarts = ovchipkaarts;
    }

    public void addovchipkaart(OVChipkaart ovchipkaart) {
        ovchipkaarts.add(ovchipkaart);
    }

    public void voegOVChipkaartToe(OVChipkaart ovChipkaart) {
        if (!ovchipkaarts.contains(ovChipkaart)) {
            ovchipkaarts.add(ovChipkaart);
        }
    }

    public void verwijderOVChipkaart(OVChipkaart ovChipkaart) {
        if (ovchipkaarts.contains(ovChipkaart)) {
            ovchipkaarts.remove(ovChipkaart);
        }
    }
    @Override
    public String toString() {
        return "Product {" +
                "product_nummer=" + product_nummer +
                ", naam='" + naam + '\'' +
                ", prijs=" + prijs +
                ", beschrijving='" + beschrijving + '\'' +
                ", gekoppelde OV-chipkaarten=" + ovchipkaarts +
                '}';
    }


}
