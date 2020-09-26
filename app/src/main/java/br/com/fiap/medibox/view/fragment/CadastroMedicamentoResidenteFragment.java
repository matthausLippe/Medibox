package br.com.fiap.medibox.view.fragment;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import br.com.fiap.medibox.R;

public class CadastroMedicamentoResidenteFragment extends Fragment {

    private Button cancelar;
    private TextView textDosesSeekBar;
    private SeekBar seekBar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cadastro_residente_medicamento, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
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




}