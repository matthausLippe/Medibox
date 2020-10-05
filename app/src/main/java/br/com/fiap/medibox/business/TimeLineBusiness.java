package br.com.fiap.medibox.business;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.fiap.medibox.dao.TimeLineDao;
import br.com.fiap.medibox.data.MyDataBase;
import br.com.fiap.medibox.model.TimeLineModel;



public class TimeLineBusiness {
    private SimpleDateFormat formataData = new SimpleDateFormat("yyyyMMdd");
    private TimeLineDao timeLineDao;
    private LiveData<List<TimeLineModel>> list;

    public TimeLineBusiness(Application application) {
        MyDataBase db = MyDataBase.getDatabase(application);
        timeLineDao = db.timeLineDao();
        list = timeLineDao.getAll();
    }

    public String converterData(Timestamp timestamp){
        String data;
        Date hoje = new Date();
        data = formataData.format(hoje);
        return data;
    }


    public List<TimeLineModel> getListHoje(){
        List<TimeLineModel> lista = new ArrayList<TimeLineModel>();



        return lista;
    }

    public void getDbInstance(Context context){
        TimeLineDao timeLineDao;
    }



}
