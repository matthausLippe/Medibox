package br.com.fiap.medibox.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import br.com.fiap.medibox.R;

public class ConfiguracoesFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_configuracao, container, false);
        CardView a1 =  v.findViewById(R.id.a1);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                ConfiguracoesCaixaFragment a1Fragment = new ConfiguracoesCaixaFragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, a1Fragment).addToBackStack(null).commit();
            }
        });
        return v;


    }
}
