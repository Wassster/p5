package OV;

import OV.DAO.*;
import OV.Domein.OVChipkaart;
import OV.Domein.Product;
import OV.Domein.Reiziger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "");


            ProductDAOpsql productDAO = new ProductDAOpsql(conn);
            OVChipkaartDAOpsql ovChipkaartDAO = new OVChipkaartDAOpsql(conn);
            ReizigerDAOpsql reizigerDAO = new ReizigerDAOpsql(conn);


            Reiziger reiziger1 = new Reiziger(1, "J", "van", "Doe", java.sql.Date.valueOf("1990-01-01"));
            reizigerDAO.save(reiziger1);
            System.out.println("Reiziger opgeslagen: " + reiziger1);


            Product product1 = new Product(2, "Dagkaart", 50.0, "Onbeperkt reizen voor een dag");
            productDAO.save(product1);
            System.out.println("Product opgeslagen: " + product1);


            OVChipkaart ovChipkaart1 = new OVChipkaart(12345, java.sql.Date.valueOf("2025-12-31"), 2, 25.0);
            ovChipkaart1.setReiziger(reiziger1);
            ovChipkaart1.voegProductToe(product1);


            ovChipkaartDAO.save(ovChipkaart1);
            productDAO.update(product1);
            System.out.println("OVChipkaart gekoppeld aan product: " + ovChipkaart1);


            product1.setPrijs(55.0);
            productDAO.update(product1);
            System.out.println("Product bijgewerkt: " + product1);


            product1.verwijderOVChipkaart(ovChipkaart1);
            productDAO.update(product1);
            System.out.println("Relatie tussen product en OV-chipkaart verwijderd");


            List<Product> alleProducten = productDAO.findAll();
            System.out.println("Alle producten:");
            for (Product p : alleProducten) {
                System.out.println(p);
            }


            List<Product> productenVanOVChipkaart = productDAO.findByOVChipkaart(ovChipkaart1);
            System.out.println("Producten gekoppeld aan OVChipkaart " + ovChipkaart1.getKaartnummer() + ":");
            for (Product p : productenVanOVChipkaart) {
                System.out.println(p);
            }


            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
