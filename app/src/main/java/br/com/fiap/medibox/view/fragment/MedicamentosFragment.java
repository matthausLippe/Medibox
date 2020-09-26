package br.com.fiap.medibox.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ItemRemedio;
import br.com.fiap.medibox.adapter.MedicamentoItemAdapter;

public class MedicamentosFragment extends Fragment {

    ArrayList<ItemRemedio> mListaExemplo;
    private MedicamentoItemAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lista_medicamento, container, false);
        RecyclerView mRecyclerView = view.findViewById(R.id.listaRemedio);
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());


        mListaExemplo = new ArrayList<>();
        mListaExemplo.add(new ItemRemedio("Paracetamol","A1"));
        mListaExemplo.add(new ItemRemedio("Amoxilina","B3"));
        mListaExemplo.add(new ItemRemedio("Codeína","B3"));
        mListaExemplo.add(new ItemRemedio("Diazepam","B3"));
        mListaExemplo.add(new ItemRemedio("Cetamina","B3"));

        mAdapter = new MedicamentoItemAdapter(mListaExemplo);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

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


        return view;
    }

    private void filter(String text) {
        ArrayList<ItemRemedio> filteredList = new ArrayList<>();
        for (ItemRemedio item : mListaExemplo) {
            if (item.getNomeRemedio().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mAdapter.filterList(filteredList);
    }
}
