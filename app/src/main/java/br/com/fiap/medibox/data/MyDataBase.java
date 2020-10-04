package br.com.fiap.medibox.data;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import br.com.fiap.medibox.dao.CaixaDao;
import br.com.fiap.medibox.dao.ClienteDao;
import br.com.fiap.medibox.dao.EnderecoDao;
import br.com.fiap.medibox.dao.GavetaDao;
import br.com.fiap.medibox.dao.MedicamentoDao;
import br.com.fiap.medibox.dao.ResidenteDao;
import br.com.fiap.medibox.dao.ResidenteMedicamentoDao;
import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.model.CaixaModel;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.EnderecoModel;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;

@Database(entities = {CaixaModel.class, ClienteModel.class, EnderecoModel.class, GavetaModel.class
        , MedicamentoModel.class, ResidenteMedicamentoModel.class, ResidenteModel.class
        , TimeLineModel.class} ,version = 1)
public abstract class MyDataBase extends RoomDatabase {

    public abstract CaixaDao caixaDao();
    public abstract ClienteDao clienteDao();
    public abstract EnderecoDao enderecoDao();
    public abstract GavetaDao gavetaDao();
    public abstract MedicamentoDao medicamentoDao();
    public abstract ResidenteDao residenteDao();
    public abstract ResidenteMedicamentoDao residenteMedicamentoDao();
    public abstract TimeLineDao timeLineDao();




}
