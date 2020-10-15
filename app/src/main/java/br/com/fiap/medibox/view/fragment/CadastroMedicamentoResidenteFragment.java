package br.com.fiap.medibox.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.viewModel.ResidenteMedicamentoViewModel;

public class CadastroMedicamentoResidenteFragment extends Fragment {

    private View view;
    private Context context;
    private Bundle args;

    private AutoCompleteTextView medicamentoTxt;
    private TextView dosagem;
    private TextView intervalo;
    private CalendarView calendarView;
    private TextView horaInicio;
    private SeekBar seekBar;
    private TextView textDosesSeekBar;
    private Button cancelar;
    private Button salvar;
    private GregorianCalendar calendar;

    private ResidenteMedicamentoViewModel viewModel;
    private ResidenteMedicamentoModel residenteMedicamentoModel;
    private List<String> listArray;
    private List<TimeLineModel> listaTimeLine;
    private List<MedicamentoModel> listaMedicamento;

    private long idResidenteMedicamento;
    private long idResidente;
    private long idMedicamento;
    private boolean editar = false;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente_medicamento, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        initialization();
        getArgs();
        verificarEditarOuNovo();
        buttonCancelar();
        buttonSalvar();
        autoComplete();
        configureSeekBar();
    }

    private void initialization(){
        view = getView();
        context = getContext();
        calendar = new GregorianCalendar();
        args = getArguments();
        listArray = new ArrayList<String>();
        listaTimeLine = new ArrayList<TimeLineModel>();
        viewModel = new ViewModelProvider(this).get(ResidenteMedicamentoViewModel.class);
        residenteMedicamentoModel = new ResidenteMedicamentoModel();
        medicamentoTxt = (AutoCompleteTextView) view.findViewById(R.id.idSelecioneMedicamento);
        dosagem = (TextView) view.findViewById(R.id.idDosagemRMedicamento);
        intervalo = (TextView) view.findViewById(R.id.idIntervaloRMedicamento);
        calendarView = (CalendarView) view.findViewById(R.id.calendarViewRMedicamento);
        horaInicio = (TextView) view.findViewById(R.id.idHoraInicioRMedicamento);
        textDosesSeekBar = view.findViewById(R.id.textDosesSeekBar);
        seekBar = view.findViewById(R.id.seekBarDoses);
        cancelar = view.findViewById(R.id.idCancelarRMedicamento);
        salvar = view.findViewById(R.id.idGravarRMedicamento);
    }

    private void getArgs(){
        idResidenteMedicamento = args.getLong("idResidenteMedicamento");
        idResidente = args.getLong("idResidente");

    }

    private void verificarEditarOuNovo(){
        if (idResidenteMedicamento != 0) {
            obterResidenteMedicamento(idResidenteMedicamento);
            editar = true;
        } else {
            obterResidente();
        }
    }

    private void configureSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textDosesSeekBar.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void buttonSalvar(){
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResidenteMedicamentoModel model = obterDadosTela();

                viewModel.save(model).observe(getViewLifecycleOwner(), new Observer<Long>() {
                    @Override
                    public void onChanged(Long longer) {
                        model.setIdResidenteMedicamento(longer);
                        idResidenteMedicamento = longer;
                        editar = true;
                        listaTimeLine = gerarTimeLine(model);
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        viewModel.insertListTimeLine(longer, listaTimeLine);
                        Toast.makeText(context,"ResidenteMedicamento Cadastrado com o ID: "+ longer,Toast.LENGTH_LONG).show();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                });
            }
        });
    }

    private void buttonCancelar(){
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void autoComplete(){
        viewModel.getListMedicamento().observe(getViewLifecycleOwner(), new Observer<List<MedicamentoModel>>() {
            @Override
            public void onChanged(List<MedicamentoModel> medicamentoModels) {
                listaMedicamento = medicamentoModels;
                for(int i = 0; i<medicamentoModels.size(); i++){
                    listArray.add(medicamentoModels.get(i).getNomeMedicamento());
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>
                        (context, android.R.layout.select_dialog_item, listArray);
                medicamentoTxt.setThreshold(1);
                medicamentoTxt.setAdapter(adapter);
            }
        });
    }

    private void obterResidenteMedicamento(long id) {
        viewModel.getById(id).observe(getViewLifecycleOwner(), new Observer<ResidenteMedicamentoModel>() {
            @Override
            public void onChanged(ResidenteMedicamentoModel model) {
                residenteMedicamentoModel = model;
                viewModel.getResidenteById(idResidente).observe(getViewLifecycleOwner(), new Observer<ResidenteModel>() {
                    @Override
                    public void onChanged(ResidenteModel residenteModel) {
                        residenteMedicamentoModel.setResidente(residenteModel);
                        viewModel.getMedicamentoById(residenteMedicamentoModel.getIdMedicamento()).observe(getViewLifecycleOwner(), new Observer<MedicamentoModel>() {
                            @Override
                            public void onChanged(MedicamentoModel medicamentoModel) {
                                residenteMedicamentoModel.setMedicamento(medicamentoModel);
                                populateEdit();
                            }
                        });
                    }
                });

            }
        });
    }

    private void obterResidente() {
        viewModel.getResidenteById(idResidente).observe(getViewLifecycleOwner(), new Observer<ResidenteModel>() {
            @Override
            public void onChanged(ResidenteModel residenteModel) {
                residenteMedicamentoModel.setResidente(residenteModel);
            }
        });
    }

    private void populateEdit(){
        if(residenteMedicamentoModel != null){
            for (int f = 0; f < listArray.size(); f++){
                if(residenteMedicamentoModel.getMedicamento().getNomeMedicamento().equals(listArray.get(f))) {
                    medicamentoTxt.setText(listArray.get(f));
                }
            }
        }
        dosagem.setText(residenteMedicamentoModel.getDosagem());
        intervalo.setText(Double.toString(residenteMedicamentoModel.getIntervalo()));
        calendarView.setDate(residenteMedicamentoModel.getDataHoraInicio().getTime());
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(residenteMedicamentoModel.getDataHoraInicio());
        String hora = Integer.toString(new Date(residenteMedicamentoModel.getDataHoraInicio().getTime()).getHours());
        String min = Integer.toString(new Date(residenteMedicamentoModel.getDataHoraInicio().getTime()).getMinutes());
        String horaMin = hora+":"+min;
        horaInicio.setText(horaMin);
        seekBar.setProgress(residenteMedicamentoModel.getDoses());
    }

    private ResidenteMedicamentoModel obterDadosTela() {
        ResidenteMedicamentoModel model = new ResidenteMedicamentoModel();
        if(editar) {
            model.setIdResidenteMedicamento(idResidenteMedicamento);
        }else {
            model.setIdResidenteMedicamento(0);
        }
        model.setIdCliente(1);
        model.setIdResidente(idResidente);
        model.setIdMedicamento(obterIdMedicamentoSelecionado());
        model.setDataHoraInicio(getDataHora());
        model.setDosagem(dosagem.getText().toString());
        model.setDoses(seekBar.getProgress());
        model.setIntervalo(Double.parseDouble(intervalo.getText().toString()));

        return model;
    }

    private long obterIdMedicamentoSelecionado(){
        for(MedicamentoModel medicamento : listaMedicamento){
            if(medicamento.getNomeMedicamento().equals(medicamentoTxt.getText().toString())){
                idMedicamento = medicamento.getIdMedicamento();
            }
        }
        return idMedicamento;
    }

    private java.sql.Date getDataHora(){
        java.sql.Date data = new java.sql.Date(new Date().getTime());
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd@HH:mm");
        String calendario = format2.format(calendarView.getDate()).toString();
        String horaMin = horaInicio.getText().toString();
        String dataFim = calendario+"@"+horaMin;
        Log.e("","DataHora String "+dataFim);
        try {
            data = new java.sql.Date(format.parse(dataFim).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    private List<TimeLineModel> gerarTimeLine(ResidenteMedicamentoModel model){
        calendar.setTime(model.getDataHoraInicio());
        List<TimeLineModel> lista = new ArrayList<TimeLineModel>();
        for(int i = 0; i<model.getDoses(); i++){
            TimeLineModel timeLine = new TimeLineModel();
            timeLine.setIdResidenteMedicamento(model.getIdResidenteMedicamento());
            timeLine.setIdCliente(model.getIdCliente());
            timeLine.setStatus(TimeLineModel.NAO_MEDICADO);
            calendar.add(Calendar.HOUR, (int)model.getIntervalo());
            timeLine.setDataHoraMedicacao(new java.sql.Date(calendar.getTimeInMillis()));
            lista.add(timeLine);
        }
        return lista;
    }
}