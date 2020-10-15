package br.com.fiap.medibox.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

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
import br.com.fiap.medibox.adapter.ResidenteAdapter;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.view.fragment.CadastroResidenteFragment;
import br.com.fiap.medibox.viewModel.ResidenteViewModel;

public class ListaResidenteActivity extends Fragment {

    private View view;
    private Context context;

    private RecyclerView recycler;
    private RecyclerView recyclerMedicamento;
    private ResidenteAdapter adapter;

    private ResidenteViewModel viewModel;
    private ResidenteModel residenteModel;
    private List<ResidenteModel> list;
    private Date dataNascimento;

    private DateFormat dataNascimentoFormat = new SimpleDateFormat("dd/MM/yyyy");

    private EditText nome;
    private EditText nascimento;
    private EditText sexo;
    private EditText nomeResponsavel;
    private EditText telResponsavel;
    private EditText quarto;
    private EditText observacoes;
    private Button salvar;
    private Button cancelar;
    private MedicamentoResidenteAdapter adapterMedicamento;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lista_residente, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        initialization();
        populate();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        residenteModel = list.get(adapter.getSelectedPos());
        if (item.getTitle() == "Editar") {
            editarResidente();
        } else if (item.getTitle() == "Deletar") {
            viewModel.delete(residenteModel.getIdResidente());
            adapter.deleteResidente();
        }
        return super.onContextItemSelected(item);
    }

    private void initialization(){
        view = getView();
        context = getContext();
        recycler = view.findViewById(R.id.idRecyclerResidente);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        viewModel = new ViewModelProvider(this).get(ResidenteViewModel.class);
    }

    private void populate() {
        viewModel.getListResidenteDb().observe(getViewLifecycleOwner(), new Observer<List<ResidenteModel>>() {
            @Override
            public void onChanged(List<ResidenteModel> residenteModels) {
                list = residenteModels;
                adapter = new ResidenteAdapter((ArrayList<ResidenteModel>) list, context);
                recycler.setAdapter(adapter);
                viewModel.saveList(residenteModels);
            }
        });

        viewModel.getListResidente().observe(getViewLifecycleOwner(), new Observer<List<ResidenteModel>>() {
            @Override
            public void onChanged(List<ResidenteModel> residenteModels) {
                list = residenteModels;
                viewModel.getCliente().observe(getViewLifecycleOwner(), new Observer<List<ClienteModel>>() {
                    @Override
                    public void onChanged(List<ClienteModel> clienteModels) {
                        for(int i = 0; i<clienteModels.size(); i++){
                            ClienteModel clienteModel = clienteModels.get(i);
                            for(int f = 0; f<list.size(); f++){
                                residenteModel = list.get(f);
                                if(clienteModel.getId() == residenteModel.getIdCliente()){
                                    list.get(f).setCliente(clienteModel);

                                }
                            }
                        }
                    }
                });
                viewModel.saveList(residenteModels);
            }
        });
    }



    private void editarResidente() {
        Fragment fragment = new CadastroResidenteFragment();
        Bundle args = new Bundle();
        args.putLong("idResidente", residenteModel.getIdResidente());
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment ).addToBackStack(null).commit();
    }

    private Date getdataNascimento(){
        try {
            dataNascimento = new Date(dataNascimentoFormat.parse(nascimento.getText().toString()).getTime());
            residenteModel.setDataNascimento(dataNascimento);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dataNascimento;
    }
}