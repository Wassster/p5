package OV.Domein;

import java.sql.Date;
import java.util.ArrayList;

public class OVChipkaart {

    public int kaartnummer;
    public Date geldige_tot;
    public int klasse;
    public double saldo;
    private ArrayList<Product> products;

    public OVChipkaart(int kaartnummer, Date geldige_tot, int klasse, int saldo) {
        this.kaartnummer = kaartnummer;
        this.geldige_tot = geldige_tot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.products = new ArrayList<>();

    }


    public Date getGeldige_tot() {
        return geldige_tot;
    }

    public int getKaartnummer() {
        return kaartnummer;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setGeldige_tot(Date geldige_tot) {
        this.geldige_tot = geldige_tot;
    }

    public void setKaartnummer(int kaartnummer) {
        this.kaartnummer = kaartnummer;
    }
    public void setKlasse(int klasse) {
        this.klasse = klasse;
    }
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void voegProductToe(Product product) {
        if (!products.contains(product)) {
            products.add(product);
            product.voegOVChipkaartToe(this);
        }
    }

    public void verwijderProduct(Product product) {
        if (products.contains(product)) {
            products.remove(product);
            product.verwijderOVChipkaart(this);
        }
    }




}
