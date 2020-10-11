package br.com.fiap.medibox.view.activity;

import android.app.Dialog;
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
import br.com.fiap.medibox.adapter.ResidenteAdapter;
import br.com.fiap.medibox.model.ResidenteModel;
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
        viewModel.getListResidente().observe(getViewLifecycleOwner(), new Observer<List<ResidenteModel>>() {
            @Override
            public void onChanged(List<ResidenteModel> residenteModels) {
                list = residenteModels;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ResidenteAdapter((ArrayList<ResidenteModel>) residenteModels, context);
                        recycler.setAdapter(adapter);
                    }
                });
                viewModel.saveList(residenteModels);
            }
        });
    }

    private void editarResidente() {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Cadastrar");
        dialog.setContentView(R.layout.fragment_cadastro_residente);
        dialog.setCancelable(true);
        nome = (EditText) dialog.findViewById(R.id.idNome);
        nascimento = (EditText) dialog.findViewById(R.id.idNascimento);
        sexo = (EditText) dialog.findViewById(R.id.idSexo);
        nomeResponsavel = (EditText) dialog.findViewById(R.id.idResponsavel);
        telResponsavel = (EditText) dialog.findViewById(R.id.idTelResponsavel);
        quarto = (EditText) dialog.findViewById(R.id.idQuarto);
        observacoes = (EditText) dialog.findViewById(R.id.idObs);
        salvar = (Button) dialog.findViewById(R.id.idSalvar);
        cancelar = (Button) dialog.findViewById(R.id.idCancelar);
        recyclerMedicamento = (RecyclerView) dialog.findViewById(R.id.recyclerListaMedicamentosResidente);

        nome.setText(residenteModel.getNomeResidente());
        nascimento.setText(dataNascimentoFormat.format(residenteModel.getDataNascimento()));
        sexo.setText(residenteModel.getSexo());
        nomeResponsavel.setText(residenteModel.getNomeResponsavel());
        telResponsavel.setText(residenteModel.getTelResponsavel());
        quarto.setText(residenteModel.getQuarto());
        observacoes.setText(residenteModel.getObservacoes());

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerMedicamento.setLayoutManager(layoutManager1);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

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
                    dialog.dismiss();
                }
            }
        });
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