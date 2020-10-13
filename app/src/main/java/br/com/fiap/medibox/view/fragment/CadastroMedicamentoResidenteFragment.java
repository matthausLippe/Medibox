package br.com.fiap.medibox.view.fragment;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;

public class CadastroMedicamentoResidenteFragment extends Fragment {

    private Button cancelar;
    private TextView textDosesSeekBar;
    private SeekBar seekBar;
    private View view;
    private Context context;
    private AutoCompleteTextView medicamentoTxt;
    private ResidenteMedicamentoModel residenteMedicamentoModel;
    private List<ResidenteMedicamentoModel> list;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente_medicamento, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Bundle args = getArguments();
        initialization();
        populate();
    }

    private void initialization(){
        view = getView();
        context = getContext();
        Bundle args = getArguments();
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

    private void populate(){
        String[] fruits = {"Apple", "Appy", "Banana", "Cherry", "Date", "Grape", "Kiwi", "Mango", "Pear"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (context, android.R.layout.select_dialog_item, fruits);
        medicamentoTxt = (AutoCompleteTextView) view.findViewById(R.id.idSelecioneMedicamento);
        medicamentoTxt.setThreshold(1);
        medicamentoTxt.setAdapter(adapter);
    }



}