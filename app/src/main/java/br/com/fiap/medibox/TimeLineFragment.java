package br.com.fiap.medibox;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import br.com.fiap.medibox.model.ItemTimeline;

public class TimeLineFragment extends Fragment {

    TimeLineAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.timeline, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.lista_timeline);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<ItemTimeline> listItems = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ItemTimeline listItem = new ItemTimeline(
                    "Nome do Paciente: Paciente "+i,
                    "Comprimidos de 0,5,g",
                    "15:00",
                    "A1",
                    "Loren Ipsum Dolor"
            );
            listItems.add(listItem);
        }

        //previne o botão voltar de ir pra tela de login ou sair do app
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });


        adapter = new TimeLineAdapter(listItems, view.getContext());

        recyclerView.setAdapter(adapter);
        
        return view;
    }
}
