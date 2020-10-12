package br.com.fiap.medibox.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.adapter.MedicamentoResidenteAdapter;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;

public class CadastroResidenteFragment extends Fragment {

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
    private List<ResidenteMedicamentoModel> list;
    private MedicamentoResidenteAdapter adapter;
    private View view;
    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        initialization();

        list = new ArrayList<ResidenteMedicamentoModel>();
        populate(list);

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                activity.getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void initialization(){
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
        salvar = view.findViewById(R.id.idSalvarMedicamento);
        cancelar = view.findViewById(R.id.idCancelarMedicamento);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(layoutManager);
    }

    public void populate (final List<ResidenteMedicamentoModel> medicametos){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter = new MedicamentoResidenteAdapter((List<ResidenteMedicamentoModel>) medicametos, context);
                recycler.setAdapter(adapter);
            }
        });
    }


}