package br.com.fiap.medibox.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.viewModel.TimeLineViewModel;

public class TimelineItemFragment extends Fragment {
    private TimeLineViewModel viewModel;
    private TextView idNome;
    private TextView idMedicamento;
    private TextView idHorario;
    private TextView idDose;
    private TextView idIntervalo;
    private TextView idResponsavel;
    private TextView idObservacao;
    private Button medicar;
    private View view;
    private Context context;
    private Bundle args;
    private long id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);
        return inflater.inflate(R.layout.fragment_detalhe_timeline, container, false);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        inicialization();
        populate();

    }

    private void inicialization(){
        view = getView();
        context = getContext();
        args = getArguments();
        idNome = view.findViewById(R.id.idNomeDetalhe);
        idMedicamento = view.findViewById(R.id.idMedicamentoDetalhe);
        idHorario = view.findViewById(R.id.idHorarioDetalhe);
        idDose = view.findViewById(R.id.idDoseDetalhe);
        idIntervalo = view.findViewById(R.id.idIntervaloDetalhe);
        idResponsavel = view.findViewById(R.id.idTelResponsavelDetalhe);
        idObservacao = view.findViewById(R.id.idObsDetalhe);
        medicar = view.findViewById(R.id.buttonMedicarDetalhe);
        medicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickedMedicar();
            }
        });
    }

    private void populate(){
        String nome = args.getString("nomeResidente");
        String medicamento = args.getString("medicamento");
        String horario = args.getString("dataHora");
        String dose = args.getString("dose");
        String intervalo = args.getString("intervalo");
        String responsavel = args.getString("responsavel");
        String observacao = args.getString("observacao");
        id = args.getLong("idTimeLine");

        idNome.setText(nome);
        idMedicamento.setText(medicamento);
        idHorario.setText(horario);
        idDose.setText(dose);
        idIntervalo.setText(intervalo);
        idResponsavel.setText(responsavel);
        idObservacao.setText(observacao);
    }

    private void clickedMedicar(){
        viewModel.medicar(id);
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
