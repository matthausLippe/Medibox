package br.com.fiap.medibox.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.fiap.medibox.R;

public class ConfiguracoesCaixaFragment extends Fragment {

    private SeekBar tempBar;
    private TextView txtTemp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuracao_caixa, container, false);
        tempBar = v.findViewById(R.id.seekBarTemp);
        txtTemp = v.findViewById(R.id.txt_temperatura);
        getActivity().setTitle("Configuração do Dispenser");


        tempBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                txtTemp.setText(i+"°");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return v;
    }

    
}
