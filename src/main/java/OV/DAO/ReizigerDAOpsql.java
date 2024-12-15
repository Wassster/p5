package OV.DAO;

import OV.Domein.OVChipkaart;
import OV.Domein.Reiziger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReizigerDAOpsql implements ReizigerDAO {

    private Connection con;
    private OVChipkaartDAOpsql ovChipkaartDAO;

    public ReizigerDAOpsql(Connection con ) {
        this.con = con;

    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            String checkQuery = "SELECT COUNT(*) FROM reiziger WHERE reiziger_id = ?";
            PreparedStatement checkPs = con.prepareStatement(checkQuery);
            checkPs.setInt(1, reiziger.getId());
            ResultSet rs = checkPs.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                System.out.println("Reiziger met ID " + reiziger.getId() + " bestaat al.");
                return false;
            }

            String query = "INSERT INTO reiziger(reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ps.setString(2, reiziger.getVoorletters());
            ps.setString(3, reiziger.getTussenvoegsel());
            ps.setString(4, reiziger.getAchternaam());
            ps.setDate(5, reiziger.getGeboortedatum());
            ps.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Reiziger reiziger) {
        try {
            String query = "UPDATE Reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, reiziger.getVoorletters());
            ps.setString(2, reiziger.getTussenvoegsel());
            ps.setString(3, reiziger.getAchternaam());
            ps.setDate(4, reiziger.getGeboortedatum());
            ps.setInt(5, reiziger.getId());
            ps.executeUpdate();

            for (OVChipkaart ov : reiziger.getOvChipkaarts()) {
                ovChipkaartDAO.update(ov);
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            for (OVChipkaart ov : reiziger.getOvChipkaarts()) {
                ovChipkaartDAO.delete(ov);
            }

            String query = "DELETE FROM Reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, reiziger.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Reiziger findById(int id) {
        try {
            String query = "SELECT * FROM Reiziger WHERE reiziger_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                List<OVChipkaart> ovChipkaarts = ovChipkaartDAO.findByReiziger(reiziger);
                for (OVChipkaart ov : ovChipkaarts) {
                    reiziger.addOVChipkaart(ov);
                }
                return reiziger;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Reiziger> findAll() {
        List<Reiziger> reizigers = new ArrayList<>();
        try {
            String query = "SELECT * FROM Reiziger";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("reiziger_id");
                String voorletters = rs.getString("voorletters");
                String tussenvoegsel = rs.getString("tussenvoegsel");
                String achternaam = rs.getString("achternaam");
                Date geboortedatum = rs.getDate("geboortedatum");

                Reiziger reiziger = new Reiziger(id, voorletters, tussenvoegsel, achternaam, geboortedatum);
                List<OVChipkaart> ovChipkaarts = ovChipkaartDAO.findByReiziger(reiziger);
                for (OVChipkaart ov : ovChipkaarts) {
                    reiziger.addOVChipkaart(ov);
                }
                reizigers.add(reiziger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reizigers;
    }
}
