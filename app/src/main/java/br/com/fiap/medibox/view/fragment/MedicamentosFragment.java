package br.com.fiap.medibox.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.adapter.MedicamentoItemAdapter;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.viewModel.MedicamentoViewModel;

public class MedicamentosFragment extends Fragment {

    private List<MedicamentoModel> list = new ArrayList<MedicamentoModel>();
    private MedicamentoItemAdapter mAdapter;
    private MedicamentoViewModel medicamentoViewModel;

    private View view;
    private RecyclerView recyclerView;
    private MedicamentoModel medicamentoModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_lista_medicamento, container, false);
        inicialization();
        configureBusca();
        obterDados();
        return view;
    }

    private void inicialization(){
        recyclerView = view.findViewById(R.id.listaRemedio);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        medicamentoViewModel = new ViewModelProvider(this).get(MedicamentoViewModel.class);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        medicamentoModel = list.get(mAdapter.getSelectedPos());
        if (item.getTitle() == "Editar") {
            editarMedicamento();
        } else if (item.getTitle() == "Deletar") {
            medicamentoViewModel.delete(medicamentoModel);
            mAdapter.deleteMedicamento();
        }
        return super.onContextItemSelected(item);
    }

    private void editarMedicamento() {
        Fragment fragment = new CadastroMedicamentosFragment();
        Bundle args = new Bundle();
        args.putLong("idMedicamento", medicamentoModel.getIdMedicamento());
        fragment.setArguments(args);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).addToBackStack(null).commit();
    }

    private void configureBusca(){
        EditText txtBusca = view.findViewById(R.id.searchRemedio);
        txtBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
    }

    private void obterDados(){
        medicamentoViewModel.getListMedicamento().observe(getViewLifecycleOwner(), new Observer<List<MedicamentoModel>>() {
            @Override
            public void onChanged(List<MedicamentoModel> medicamentoModels) {
                list = medicamentoModels;
                for(MedicamentoModel medicamento : list){
                    medicamentoViewModel.getGavetaById(medicamento.getIdGaveta()).observe(getViewLifecycleOwner(), new Observer<GavetaModel>() {
                        @Override
                        public void onChanged(GavetaModel gavetaModel) {
                            medicamento.setGaveta(gavetaModel);
                            populate();
                        }
                    });
                }
                medicamentoViewModel.saveList(list);
            }
        });
    }

    private void populate(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run(){
                mAdapter = new MedicamentoItemAdapter(list, getContext());
                recyclerView.setAdapter(mAdapter);
            }
        });
    }

    private void filter(String text) {
        ArrayList<MedicamentoModel> filteredList = new ArrayList<>();
        for (MedicamentoModel item : list) {
            if (item.getNomeMedicamento().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }
}
