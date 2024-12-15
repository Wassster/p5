package OV.DAO;

import OV.Domein.OVChipkaart;
import OV.Domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OVChipkaartDAOpsql implements OVChipkaartDAO {

    private Connection conn;

    public OVChipkaartDAOpsql(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(OVChipkaart chipkaart) {
        try {
            String checkQuery = "SELECT COUNT(*) FROM ovchipkaart WHERE kaartnummer = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkQuery);
            checkPs.setInt(1, chipkaart.getKaartnummer());
            ResultSet rs = checkPs.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("OVChipkaart met kaartnummer " + chipkaart.getKaartnummer() + " bestaat al.");
                return false;
            }

            String query = "INSERT INTO ovchipkaart(kaartnummer, geldige_tot, klasse, saldo, reiziger_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.setDate(2, chipkaart.getGeldige_tot());
            ps.setInt(3, chipkaart.getKlasse());
            ps.setDouble(4, chipkaart.getSaldo());
            ps.setInt(5, chipkaart.getReiziger().getId());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(OVChipkaart chipkaart) {
        try {
            String query = "UPDATE OVChipkaart SET geldige_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaartnummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1, chipkaart.getGeldige_tot());
            ps.setInt(2, chipkaart.getKlasse());
            ps.setDouble(3, chipkaart.getSaldo());
            ps.setInt(4, chipkaart.getReiziger().getId());
            ps.setInt(5, chipkaart.getKaartnummer());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart chipkaart) {
        try {
            String query = "DELETE FROM OVChipkaart WHERE kaartnummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public OVChipkaart findById(int kaartnummer) {
        try {
            String query = "SELECT * FROM OVChipkaart WHERE kaartnummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, kaartnummer);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Date geldige_tot = rs.getDate("geldige_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reiziger_id = rs.getInt("reiziger_id");

                OVChipkaart chipkaart = new OVChipkaart(kaartnummer, geldige_tot, klasse, saldo);
                Reiziger reiziger = new ReizigerDAOpsql(conn).findById(reiziger_id);
                chipkaart.setReiziger(reiziger);
                return chipkaart;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> chipkaarten = new ArrayList<>();
        try {
            String query = "SELECT * FROM OVChipkaart";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int kaartnummer = rs.getInt("kaartnummer");
                Date geldige_tot = rs.getDate("geldige_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");
                int reiziger_id = rs.getInt("reiziger_id");

                OVChipkaart chipkaart = new OVChipkaart(kaartnummer, geldige_tot, klasse, saldo);
                Reiziger reiziger = new ReizigerDAOpsql(conn).findById(reiziger_id);
                chipkaart.setReiziger(reiziger);
                chipkaarten.add(chipkaart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chipkaarten;
    }

    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> chipkaarten = new ArrayList<>();
        try {
            String query = "SELECT * FROM OVChipkaart WHERE reiziger_id = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int kaartnummer = rs.getInt("kaartnummer");
                Date geldige_tot = rs.getDate("geldige_tot");
                int klasse = rs.getInt("klasse");
                double saldo = rs.getDouble("saldo");

                OVChipkaart chipkaart = new OVChipkaart(kaartnummer, geldige_tot, klasse, saldo);
                chipkaart.setReiziger(reiziger);
                chipkaarten.add(chipkaart);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chipkaarten;
    }
}
