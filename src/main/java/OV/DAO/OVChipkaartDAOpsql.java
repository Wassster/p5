package OV.DAO;

import OV.Domein.OVChipkaart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OVChipkaartDAOpsql implements OVChipkaartDAO {

    private Connection conn;

    public OVChipkaartDAOpsql(Connection conn) {
        this.conn = conn;
    }


    @Override
    public boolean save(OVChipkaart chipkaart) {
        try {
            String query = "INSERT INTO chipkaart(kaartnummer, geldige_tot, klasse, saldo, reiziger_id) VALUES (?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.setDate(2, chipkaart.getGeldige_tot());
            ps.setInt(3, chipkaart.getKlasse());
            ps.setDouble(4, chipkaart.getSaldo());
            ps.setInt(5,chipkaart.getReiziger().getId());
            ps.executeUpdate();
            return true;
        }catch (SQLException e) {
            return false;
        }

    }

    @Override
    public boolean update(OVChipkaart chipkaart) {
        try{
            String query = "UPDATE chipkaart SET geldige_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaartnummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setDate(1,chipkaart.getGeldige_tot());
            ps.setInt(2, chipkaart.getKlasse());
            ps.setDouble(3, chipkaart.getSaldo());
            ps.setInt(4, chipkaart.getReiziger().getId());
            ps.setInt(5, chipkaart.getKaartnummer());
            ps.executeUpdate();

            if (chipkaart.getReiziger() != null){
                ReizigerDAO.save(chipkaart.getReiziger());
                return true;
            }
        }catch (SQLException e) {
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart chipkaart) {
        try{
            String query = "DELETE FROM chipkaart WHERE kaartnummer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, chipkaart.getKaartnummer());
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            return false;
        }
    }

    @Override
    public OVChipkaart findById(int kaarnummer) {
        return null;
    }

    @Override
    public List<OVChipkaart> findAll() {
        return List.of();
    }
}
