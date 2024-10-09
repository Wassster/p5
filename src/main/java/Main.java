import OV.DAO.OVChipkaartDAO;
import OV.DAO.OVChipkaartDAOpsql;
import OV.DAO.ProductDAO;
import OV.DAO.ProductDAOpsql;
import OV.Domein.OVChipkaart;
import OV.Domein.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {

            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ovchip", "postgres", "");


            ProductDAO productDAO = new ProductDAOpsql(conn);
            OVChipkaartDAO ovChipkaartDAO = new OVChipkaartDAOpsql(conn);


            Product product1 = new Product(1, "Dagkaart", "Onbeperkt reizen voor een dag", 50.0);
            productDAO.save(product1);
            System.out.println("Product opgeslagen: " + product1);


            OVChipkaart ovChipkaart1 = new OVChipkaart(12345, java.sql.Date.valueOf("2025-12-31"), 2, 25.0);
            product1.voegOVChipkaartToe(ovChipkaart1);
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
            System.out.println("Producten gekoppeld aan OVChipkaart " + ovChipkaart1.getKaartNummer() + ":");
            for (Product p : productenVanOVChipkaart) {
                System.out.println(p);
            }


            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

