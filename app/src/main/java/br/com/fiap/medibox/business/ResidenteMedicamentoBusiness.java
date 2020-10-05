package br.com.fiap.medibox.business;

import android.app.Application;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

import br.com.fiap.medibox.dao.ResidenteMedicamentoDao;
import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.TimeLineModel;

public class ResidenteMedicamentoBusiness {
    private ResidenteMedicamentoDao residenteMedicamentoDao;
    private TimeLineDao timeLineDao;

    ResidenteMedicamentoBusiness(Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        residenteMedicamentoDao = db.residenteMedicamentoDao();
        timeLineDao = db.timeLineDao();

    }


    private void gerarItensTimeLine(ResidenteMedicamentoModel residenteMedicamentoModel){
        Timestamp data = residenteMedicamentoModel.getDataHoraInicio();

        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(data);
        gc.add(Calendar.HOUR, (int) residenteMedicamentoModel.getIntervalo());
        residenteMedicamentoDao.insert(residenteMedicamentoModel);

        TimeLineModel timeLineModel = new TimeLineModel(residenteMedicamentoModel.getIdResidenteMedicamento(),residenteMedicamentoModel.getIdCliente(),data, TimeLineModel.NAO_MEDICADO);
        timeLineDao.insert(timeLineModel);

    }
}
