package br.com.fiap.medibox.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.com.fiap.medibox.MedicacoesFragment;
import br.com.fiap.medibox.R;
import br.com.fiap.medibox.adapter.ResidenteAdapter;
import br.com.fiap.medibox.model.Residente;

public class ListaResidenteActivity extends Fragment {


    private RecyclerView recycler;
    private List<Residente> residentes;
    ResidenteAdapter adapter;
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
    private RecyclerView recyclerMedicamento;
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
        dataNascimento = new Date();
        residente = new Residente(1, "Antonio Santos", dataNascimento, "Masculino", "Regina Santos", "11-99999-8888", "Com problemas cardiaco", "14");
        residentes.add(residente);
        populate(residentes);
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
            //getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
            //       new CadastroResidente()).commit();
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

        nome.setText(residente.getNomeResidente());
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Nascimento.setText(format.format(residente.getDataNascimento()));
        sexo.setText(residente.getSexo());
        nomeResponsavel.setText(residente.getNomeResponsavel());
        telResponsavel.setText(residente.getTelResponsavel());
        quarto.setText(residente.getQuarto());
        observacoes.setText(residente.getObservacoes());

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}