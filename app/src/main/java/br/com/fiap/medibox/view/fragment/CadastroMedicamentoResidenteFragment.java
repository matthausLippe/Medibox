package br.com.fiap.medibox.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;

public class CadastroMedicamentoResidenteFragment extends Fragment {

    private Button cancelar;
    private TextView textDosesSeekBar;
    private SeekBar seekBar;
    private View view;
    private Context context;
    private ResidenteMedicamentoModel residenteMedicamentoModel;
    private List<ResidenteMedicamentoModel> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente_medicamento, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        view = getView();
        context = getContext();
        textDosesSeekBar = view.findViewById(R.id.textDosesSeekBar);
        seekBar = view.findViewById(R.id.seekBarDoses);
        cancelar = view.findViewById(R.id.buttonCancelar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textDosesSeekBar.setText(Integer.toString(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment fragment = new CadastroMedicamentoResidenteFragment();
                activity.getSupportFragmentManager().popBackStack();
            }
        });
    }

    private void configureBusca(){
        EditText txtBusca = view.findViewById(R.id.idNomeResidenteTimeLine);
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

    private void filter(String text) {
        ArrayList<ResidenteMedicamentoModel> filteredList = new ArrayList<>();
        for (ResidenteMedicamentoModel item : list) {
            if (item.getMedicamento().getNomeMedicamento().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        //adapter.filterList(filteredList);
    }


}