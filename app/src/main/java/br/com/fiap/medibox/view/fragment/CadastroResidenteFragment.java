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

    private EditText nome;
    private EditText dataNascimento;
    private EditText sexo;
    private EditText nomeResponsavel;
    private EditText telResponsavel;
    private EditText quarto;
    private EditText observacoes;

    private DateFormat dataNascimentoFormat = new SimpleDateFormat("dd/MM/yyyy");
    private Date nascimento;

    private RecyclerView recycler;
    private Button salvar;
    private Button cancelar;
    private List<ResidenteMedicamentoModel> list;
    private MedicamentoResidenteAdapter adapter;
    private View view;
    private Context context;
    private long idResidente;
    private ResidenteMedicamentoModel residenteMedicamentoModel;
    private ResidenteViewModel viewModel;
    private ResidenteModel residenteModel;
    private List<MedicamentoModel> listaMedicamentos;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        initialization();
    }

    private void initialization() {
        Bundle args = getArguments();
        view = getView();
        context = getContext();
        nome = view.findViewById(R.id.idNomeMedicamento);
        dataNascimento = view.findViewById(R.id.idNascimento);
        sexo = view.findViewById(R.id.idSexo);
        nomeResponsavel = view.findViewById(R.id.idResponsavel);
        telResponsavel = view.findViewById(R.id.idTelResponsavel);
        quarto = view.findViewById(R.id.idQuarto);
        observacoes = view.findViewById(R.id.idObs);
        recycler = view.findViewById(R.id.recyclerListaMedicamentosResidente);
        viewModel = new ViewModelProvider(this).get(ResidenteViewModel.class);
        salvar = view.findViewById(R.id.idSalvarMedicamento);
        cancelar = view.findViewById(R.id.idCancelarMedicamento);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().popBackStack();
            }
        });
        if (args != null) {
            idResidente = args.getLong("idResidente");
            obterDados(idResidente);
        } else {
            list = new ArrayList<ResidenteMedicamentoModel>();
            populate(list);
        }
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                residenteModel.setNomeResidente(nome.getText().toString());
                residenteModel.setSexo(sexo.getText().toString());
                residenteModel.setDataNascimento(getdataNascimento());
                residenteModel.setNomeResponsavel(nomeResponsavel.getText().toString());
                residenteModel.setTelResponsavel(telResponsavel.getText().toString());
                residenteModel.setQuarto(quarto.getText().toString());
                residenteModel.setObservacoes(observacoes.getText().toString());

                if(viewModel.update(residenteModel)){
                    Toast.makeText(context, "Residente Salvo com sucesso!",Toast.LENGTH_LONG).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    activity.getSupportFragmentManager().popBackStack();
                }
            }
        });
    }


    public void populate(final List<ResidenteMedicamentoModel> medicametos) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new MedicamentoResidenteAdapter((List<ResidenteMedicamentoModel>) medicametos, context);
                recycler.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        residenteMedicamentoModel = list.get(adapter.getSelectedPos());
        if (item.getTitle() == "Editar") {
            editarResidenteMedicamento();
        } else if (item.getTitle() == "Deletar") {
            adapter.deleteMedicamento();
        }
        return super.onContextItemSelected(item);
    }

    private void editarResidenteMedicamento() {
        Fragment fragment = new CadastroMedicamentoResidenteFragment();
        Bundle args = new Bundle();
        args.putLong("idResidenteMedicamento", residenteMedicamentoModel.getIdResidenteMedicamento());
        fragment.setArguments(args);
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
                        viewModel.getListResidenteMedicamento(residenteModel.getIdResidente()).observe(getViewLifecycleOwner(), new Observer<List<ResidenteMedicamentoModel>>() {
                            @Override
                            public void onChanged(List<ResidenteMedicamentoModel> residenteMedicamentoModels) {
                                list = residenteMedicamentoModels;
                                if (residenteMedicamentoModels != null) {
                                    viewModel.getListMedicamento().observe(getViewLifecycleOwner(), new Observer<List<MedicamentoModel>>() {
                                        @Override
                                        public void onChanged(List<MedicamentoModel> medicamentoModels) {
                                            listaMedicamentos = medicamentoModels;
                                            residenteModel.setCliente(clienteModel);
                                            associarMedicamentosResidenteMedicamento();
                                            residenteModel.setResidenteMedicamento(list);
                                            populateEditar();
                                        }
                                    });
                                }
                            }
                        });
                    }
                });
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
        }
    }

    private void populateEditar() {
        nome.setText(residenteModel.getNomeResidente());
        dataNascimento.setText(dataNascimentoFormat.format(residenteModel.getDataNascimento()));
        sexo.setText(residenteModel.getSexo());
        nomeResponsavel.setText(residenteModel.getNomeResponsavel());
        telResponsavel.setText(residenteModel.getTelResponsavel());
        quarto.setText(residenteModel.getQuarto());
        observacoes.setText(residenteModel.getObservacoes() + residenteModel.getCliente().getNomeCliente());
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new MedicamentoResidenteAdapter((List<ResidenteMedicamentoModel>) residenteModel.getResidenteMedicamento(), context);
                recycler.setAdapter(adapter);
            }
        });
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