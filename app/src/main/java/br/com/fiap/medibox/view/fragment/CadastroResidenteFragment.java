package br.com.fiap.medibox.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.adapter.MedicamentoResidenteAdapter;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.viewModel.ResidenteViewModel;

public class CadastroResidenteFragment extends Fragment {

    private View view;
    private Context context;
    Bundle args;

    private EditText nome;
    private EditText dataNascimento;
    private EditText sexo;
    private EditText nomeResponsavel;
    private EditText telResponsavel;
    private EditText quarto;
    private EditText observacoes;
    private RecyclerView recycler;
    private Button salvar;
    private Button cancelar;
    private MedicamentoResidenteAdapter adapter;

    private DateFormat dataNascimentoFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date nascimento;

    private List<ResidenteMedicamentoModel> list;
    private List<MedicamentoModel> listaMedicamentos;

    private ResidenteMedicamentoModel residenteMedicamentoModel;
    private ResidenteViewModel viewModel;
    private ResidenteModel residenteModel;

    private long idResidente;

    private boolean editar = false;

    private boolean retorno = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        initialization();
        getArgs();
        verificaEditarOuNovo();
        botaoCancelar();
        botaoSalvar();
    }

    @Override
    public void onPause() {
        super.onPause();
        retorno = true;
        initialization();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        residenteMedicamentoModel = list.get(adapter.getSelectedPos());
        if (item.getTitle() == "Editar") {
            editarResidenteMedicamento();
        } else if (item.getTitle() == "Deletar") {
            viewModel.deleteResidenteMedicamento(residenteMedicamentoModel.getIdResidenteMedicamento());
            adapter.deleteMedicamento();
        }
        return super.onContextItemSelected(item);
    }

    private void initialization() {
        view = getView();
        args = getArguments();
        context = view.getContext();
        viewModel = new ViewModelProvider(this).get(ResidenteViewModel.class);
        nome = view.findViewById(R.id.idNomeMedicamento);
        dataNascimento = view.findViewById(R.id.idNascimento);
        sexo = view.findViewById(R.id.idSexo);
        nomeResponsavel = view.findViewById(R.id.idResponsavel);
        telResponsavel = view.findViewById(R.id.idTelResponsavel);
        quarto = view.findViewById(R.id.idQuarto);
        observacoes = view.findViewById(R.id.idObs);
        recycler = view.findViewById(R.id.recyclerListaMedicamentosResidente);
        salvar = view.findViewById(R.id.idSalvarMedicamento);
        cancelar = view.findViewById(R.id.idCancelarMedicamento);
        list = new ArrayList<ResidenteMedicamentoModel>();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        residenteModel = new ResidenteModel();
    }

    private void getArgs(){
        if(args != null) {
            idResidente = args.getLong("idResidente");
        }
    }

    private void verificaEditarOuNovo(){
        if(idResidente != 0 && !editar) {
            editar = true;
            if(!retorno){
                obterDados(idResidente);
            }else populateEditar();
        }else {
            obterResidenteMedicamento();
        }
    }

    public void botaoCancelar(){
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().popBackStack();
            }
        });
    }

    public void botaoSalvar(){
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResidenteModel model = obterDadosTela();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                viewModel.update(model).observe(getViewLifecycleOwner(), new Observer<Long>() {
                    @Override
                    public void onChanged(Long aLong) {
                        editar = true;
                        idResidente = aLong;
                        Toast.makeText(context, "Residente Salvo com sucesso!",Toast.LENGTH_LONG).show();
                        AppCompatActivity activity = (AppCompatActivity) view.getContext();
                        activity.getSupportFragmentManager().popBackStack();
                    }
                });

                }
        });
    }

    public ResidenteModel obterDadosTela(){
        ResidenteModel model = new ResidenteModel();
        model.setNomeResidente(nome.getText().toString());
        model.setSexo(sexo.getText().toString());
        model.setDataNascimento(getdataNascimento());
        model.setNomeResponsavel(nomeResponsavel.getText().toString());
        model.setTelResponsavel(telResponsavel.getText().toString());
        model.setQuarto(quarto.getText().toString());
        model.setObservacoes(observacoes.getText().toString());
        model.setIdCliente(1);
        model.setIdResidente(idResidente);
        return model;
    }

    public void populate(List<ResidenteMedicamentoModel> listaResidenteMedicamento) {
        adapter = new MedicamentoResidenteAdapter(listaResidenteMedicamento, context, idResidente);
        recycler.setAdapter(adapter);
    }

    private void editarResidenteMedicamento() {
        Fragment fragment = new CadastroMedicamentoResidenteFragment();
        Bundle argsMedicamento = new Bundle();
        argsMedicamento.putLong("idResidenteMedicamento", residenteMedicamentoModel.getIdResidenteMedicamento());
        argsMedicamento.putLong("idResidente", residenteMedicamentoModel.getIdResidente());
        fragment.setArguments(argsMedicamento);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void obterDados(long id) {
        viewModel.getResidenteById(id).observe(getViewLifecycleOwner(), new Observer<ResidenteModel>() {
            @Override
            public void onChanged(ResidenteModel model) {
                residenteModel = model;
                viewModel.getClienteById(residenteModel.getIdCliente()).observe(getViewLifecycleOwner(), new Observer<ClienteModel>() {
                    @Override
                    public void onChanged(ClienteModel clienteModel) {
                        residenteModel.setCliente(clienteModel);
                        obterResidenteMedicamento();
                    }
                });
            }
        });
    }

    private void obterResidenteMedicamento(){
        viewModel.getListResidenteMedicamento().observe(getViewLifecycleOwner(), new Observer<List<ResidenteMedicamentoModel>>() {
            @Override
            public void onChanged(List<ResidenteMedicamentoModel> residenteMedicamentoModels) {
                if (residenteMedicamentoModels != null) {
                    viewModel.getListMedicamento().observe(getViewLifecycleOwner(), new Observer<List<MedicamentoModel>>() {
                        @Override
                        public void onChanged(List<MedicamentoModel> medicamentoModels) {
                            for(ResidenteMedicamentoModel rmm : residenteMedicamentoModels){
                                if(rmm.getIdResidente() == idResidente){
                                    list.add(rmm);
                                }
                            }
                            listaMedicamentos = medicamentoModels;
                            associarMedicamentosResidenteMedicamento();
                            populateEditar();
                        }
                    });
                }
            }
        });
    }

    private void associarMedicamentosResidenteMedicamento() {
        if(list != null && listaMedicamentos != null) {
            for (ResidenteMedicamentoModel rm : list) {
                for (MedicamentoModel mm : listaMedicamentos) {
                    if(rm.getIdMedicamento() == mm.getIdMedicamento()) {
                        rm.setMedicamento(mm);
                    }
                }
            }
            residenteModel.setResidenteMedicamento(list);
        }
    }

    private void populateEditar() {
        if(editar && !retorno){
            nome.setText(residenteModel.getNomeResidente());
            dataNascimento.setText(dataNascimentoFormat.format(residenteModel.getDataNascimento()));
            sexo.setText(residenteModel.getSexo());
            nomeResponsavel.setText(residenteModel.getNomeResponsavel());
            telResponsavel.setText(residenteModel.getTelResponsavel());
            quarto.setText(residenteModel.getQuarto());
            observacoes.setText(residenteModel.getObservacoes());
            list =residenteModel.getResidenteMedicamento();
        }
        populate(residenteModel.getResidenteMedicamento());
    }

    private Date getdataNascimento(){
        try {
            nascimento = new Date(dataNascimentoFormat.parse(dataNascimento.getText().toString()).getTime());
            residenteModel.setDataNascimento(nascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return nascimento;
    }
}