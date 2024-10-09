package OV.DAO;

import OV.Domein.OVChipkaart;
import OV.Domein.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOpsql implements ProductDAO {

    private Connection conn;


    @Override
    public boolean save(Product product) {
        try{
            String query = "INSERT into product(product_nummer, naam, prijs, beschrijving) values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,product.getProduct_nummer());
            ps.setString(2,product.getNaam());
            ps.setDouble(3,product.getPrijs());
            ps.setString(4,product.getBeschrijving());
            ps.executeUpdate();


            for(OVChipkaart ovChipkaart : product.getOvchipkaarts()){
                String query1 = "INSERT into ovchipkaart_product(kaartnummer, productnummer) values(?,?)";
                PreparedStatement ps1 = conn.prepareStatement(query1);
                ps1.setInt(1,ovChipkaart.getKaartnummer());
                ps1.setInt(2,product.getProduct_nummer());
                ps1.executeUpdate();

            }
            return true;
        } catch (SQLException e){
            e.printStackTrace();
            return false;}
    }

    @Override
    public boolean update(Product product) {
        try{
            String query = "UPDATE product SET naam = ?, prijs = ?, beschrijving = ? WHERE product_nummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,product.getNaam());
            ps.setDouble(2,product.getPrijs());
            ps.setString(3,product.getBeschrijving());
            ps.setInt(4,product.getProduct_nummer());
            ps.executeUpdate();

            String query1 = "DELETE FROM ovchipkaart_product WHERE product_nummer = ?";
            PreparedStatement ps1 = conn.prepareStatement(query1);
            ps1.setInt(1,product.getProduct_nummer());
            ps1.executeUpdate();

            for(OVChipkaart ovChipkaart : product.getOvchipkaarts()){
                String query2 = "INSERT into ovchipkaart_product(kaartnummer, productnummer) values(?,?)";
                PreparedStatement ps2 = conn.prepareStatement(query2);
                ps2.setInt(1,ovChipkaart.getKaartnummer());
                ps2.setInt(2,product.getProduct_nummer());
                ps2.executeUpdate();
            }
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        };
    }


    @Override
    public boolean delete(Product product) {
        try{
            String query = "DELETE FROM ovchipkaart_product WHERE product_nummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1,product.getProduct_nummer());
            ps.executeUpdate();
            return true;

        }catch (SQLException e){
            e.printStackTrace();
        };
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new ArrayList<>();

        try {
            String query = "SELECT * FROM product";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                double prijs = rs.getDouble("prijs");
                String beschrijving = rs.getString("beschrijving");
                Product product = new Product(product_nummer, naam, prijs, beschrijving);
                products.add(product);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ovChipkaart) {
        List<Product> products = new ArrayList<>();
        try{
            String qeury = "SELECT p.* FROM product p JOIN ovchipkaart_product ovp ON p.product_nummer = ovp.product_nummer WHERE ovchipkaart = ? ";
            PreparedStatement ps = conn.prepareStatement(qeury);
            ps.setInt(1,ovChipkaart.getKaartnummer());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int product_nummer = rs.getInt("product_nummer");
                String naam = rs.getString("naam");
                double prijs = rs.getDouble("prijs");
                String beschrijving = rs.getString("beschrijving");
                Product product  = new Product(product_nummer, naam, prijs, beschrijving);
                products.add(product);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return products;
    }
}
