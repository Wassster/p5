package OV.DAO;

import OV.Domein.OVChipkaart;

import java.util.List;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart chipkaart);
    boolean update(OVChipkaart chipkaart);
    boolean delete(OVChipkaart chipkaart);

    OVChipkaart findById(int kaarnummer);

    List<OVChipkaart> findAll();
}
