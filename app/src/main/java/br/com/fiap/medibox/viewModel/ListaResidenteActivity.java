package br.com.fiap.medibox.viewModel;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.Medicamento;
import br.com.fiap.medibox.model.Residente_Medicamento;
import br.com.fiap.medibox.viewModel.recyclerView.adapter.MedicamentoResidenteAdapter;
import br.com.fiap.medibox.viewModel.recyclerView.adapter.ResidenteAdapter;
import br.com.fiap.medibox.model.Residente;

public class ListaResidenteActivity extends Fragment {


    private RecyclerView recycler, recyclerMedicamento;
    private List<Residente> residentes;
    ResidenteAdapter adapter;
    MedicamentoResidenteAdapter adapterMedicamento;
    Residente residente;
    Date dataNascimento;
    View v;
    Context context;

    private EditText nome;
    private EditText Nascimento;
    private EditText sexo;
    private EditText nomeResponsavel;
    private EditText telResponsavel;
    private EditText quarto;
    private EditText observacoes;
    private Button salvar;
    private Button cancelar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_lista_residente, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        v = getView();
        context = getContext();
        super.onCreate(savedInstanceState);
        recycler = v.findViewById(R.id.idRecyclerResidente);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
        residentes = new ArrayList<Residente>();
        residente = criarResidente();
        residentes.add(residente);
        populate(residentes);
    }

    private Residente criarResidente(){
        Residente res;
        dataNascimento = new Date();
        Medicamento medicamento = new Medicamento(
                01,
                "Paracetamol",
                "Medley",
                "750mg",
                "Paracetamol é indicado para alívio temporário de dores associadas a gripes");
        Residente_Medicamento residenteMedicamento = new Residente_Medicamento(
                medicamento,
                "750mg",
                "8h",
                "1A");
        res = new Residente(
                1,
                "Antonio Santos",
                dataNascimento, "Masculino",
                "Regina Santos",
                "11-99999-8888",
                "Com problemas cardiaco",
                "14");
        res.addMedicamento(residenteMedicamento);

        return res;
    }

    private void populate(final List<Residente> residentes) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new ResidenteAdapter((ArrayList<Residente>) residentes, context);
                recycler.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        residente = residentes.get(adapter.getSelectedPos());
        if (item.getTitle() == "Editar") {
            editarResidente();
        } else if (item.getTitle() == "Deletar") {
            adapter.deleteResidente();

        }
        return super.onContextItemSelected(item);
    }

    private void editarResidente() {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Cadastrar");
        dialog.setContentView(R.layout.activity_cadastro_residente);
        dialog.setCancelable(true);
        nome = (EditText) dialog.findViewById(R.id.idNome);
        Nascimento = (EditText) dialog.findViewById(R.id.idNascimento);
        sexo = (EditText) dialog.findViewById(R.id.idSexo);
        nomeResponsavel = (EditText) dialog.findViewById(R.id.idResponsavel);
        telResponsavel = (EditText) dialog.findViewById(R.id.idTelResponsavel);
        quarto = (EditText) dialog.findViewById(R.id.idQuarto);
        observacoes = (EditText) dialog.findViewById(R.id.idObs);
        salvar = (Button) dialog.findViewById(R.id.idSalvar);
        cancelar = (Button) dialog.findViewById(R.id.idCancelar);
        recyclerMedicamento = (RecyclerView) dialog.findViewById(R.id.recyclerListaMedicamentosResidente);

        nome.setText(residente.getNomeResidente());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Nascimento.setText(format.format(residente.getDataNascimento()));
        sexo.setText(residente.getSexo());
        nomeResponsavel.setText(residente.getNomeResponsavel());
        telResponsavel.setText(residente.getTelResponsavel());
        quarto.setText(residente.getQuarto());
        observacoes.setText(residente.getObservacoes());

        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerMedicamento.setLayoutManager(layoutManager1);
        adapterMedicamento = new MedicamentoResidenteAdapter((ArrayList<Residente_Medicamento>) residente.getListaMedicamentos(), context);
        recyclerMedicamento.setAdapter(adapterMedicamento);



        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}