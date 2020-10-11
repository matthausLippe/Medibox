package br.com.fiap.medibox.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.sql.Date;
import java.util.List;

import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.repository.TimeLineRepository;

public class TimeLineViewModel extends AndroidViewModel {

    private TimeLineRepository timeLineRepository;
    private LiveData<List<TimeLineModel>> list;
    private List<TimeLineModel> lista;

    public TimeLineViewModel(@NonNull Application application) {
        super(application);
        timeLineRepository = new TimeLineRepository(application);
    }

    public List<TimeLineModel> getLitNotification(Date date){
        lista = timeLineRepository.getListNotification(date);
        return lista;
    }


}
