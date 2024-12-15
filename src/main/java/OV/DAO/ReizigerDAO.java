package OV.DAO;

import OV.Domein.Reiziger;

import java.util.List;

public interface ReizigerDAO {
    boolean save(Reiziger reiziger);
    boolean update(Reiziger reiziger);
    boolean delete(Reiziger reiziger);

    Reiziger findById(int id);

    List<Reiziger> findAll();
}

