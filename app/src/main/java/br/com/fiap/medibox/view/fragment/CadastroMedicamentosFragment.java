package br.com.fiap.medibox.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.viewModel.MedicamentoViewModel;

public class CadastroMedicamentosFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private TextView nome;
    private TextView laboratorio;
    private TextView dosagem;
    private TextView descricao;
    private Spinner dispenser;
    private Button salvar;
    private Button cancelar;
    private View view;
    private Context context;
    private MedicamentoModel medicamentoModel;
    private List<MedicamentoModel> list;
    private GavetaModel gavetaModel;
    private List<GavetaModel> listGaveta;
    private MedicamentoViewModel viewModel;
    private List<String> dispensers = new ArrayList<String>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_medicamentos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        view = getView();
        context = getContext();
        initialization();
    }


    private void initialization() {
        nome = (TextView) view.findViewById(R.id.idNomeMedicamento);
        laboratorio = (TextView) view.findViewById(R.id.idLaboratorioMedicamento);
        dosagem = (TextView) view.findViewById(R.id.idDosagemMedicamento);
        descricao = (TextView) view.findViewById(R.id.idDescricaoMedicamento);
        dispenser = (Spinner) view.findViewById(R.id.idDispenser);
        cancelar = (Button) view.findViewById(R.id.idCancelarMedicamento);
        salvar = (Button) view.findViewById(R.id.idSalvarMedicamento);
        viewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);
        viewModel.getListGaveta().observe(getViewLifecycleOwner(), new Observer<List<GavetaModel>>() {
            @Override
            public void onChanged(List<GavetaModel> gavetaModels) {
                for(int i = 0; i<gavetaModels.size(); i++){
                    dispensers.add(gavetaModels.get(i).getNomeGaveta());
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(view.getContext(),
                            android.R.layout.simple_spinner_dropdown_item, dispensers);
                    dispenser.setAdapter(adapter);
                    dispenser.setOnItemSelectedListener(CadastroMedicamentosFragment.this);
                }
            }
        });
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MedicamentoModel medicamentoModel = new MedicamentoModel();
                medicamentoModel.setNomeMedicamento(nome.getText().toString());
                medicamentoModel.setLaboratorio(laboratorio.getText().toString());
                medicamentoModel.setDosagem(dosagem.getText().toString());
                medicamentoModel.setDescricao(descricao.getText().toString());
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(context, "Dispenser Selecionado: "+dispensers.get(position) ,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
