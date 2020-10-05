package br.com.fiap.medibox.business;

import android.app.Application;

import br.com.fiap.medibox.dao.CaixaDao;
import br.com.fiap.medibox.data.MyDataBase;

public class CaixaBusiness {

    private CaixaDao caixaDao;
    CaixaBusiness (Application application){
        MyDataBase db = MyDataBase.getDatabase(application);
        caixaDao = db.caixaDao();
    }




}
