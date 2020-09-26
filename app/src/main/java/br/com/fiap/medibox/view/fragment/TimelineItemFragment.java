package br.com.fiap.medibox.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.fiap.medibox.R;

public class TimelineItemFragment extends Fragment {
    private TextView idNome;
    private TextView idMedicamento;
    private TextView idHorario;
    private TextView idDose;
    private TextView idIntervalo;
    private TextView idResponsavel;
    private TextView idObservacao;
    private View view;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       view = inflater.inflate(R.layout.fragment_detalhe_timeline, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        super.onCreate(savedInstanceState);
        view = getView();
        context = getContext();
        Activity activity = getActivity();
        Bundle args = getArguments();

        idNome = view.findViewById(R.id.idNomeDetalhe);
        idMedicamento = view.findViewById(R.id.idMedicamentoDetalhe);
        idHorario = view.findViewById(R.id.idHorarioDetalhe);
        idDose = view.findViewById(R.id.idDoseDetalhe);
        idIntervalo = view.findViewById(R.id.idIntervaloDetalhe);
        idResponsavel = view.findViewById(R.id.idTelResponsavelDetalhe);
        idObservacao = view.findViewById(R.id.idObsDetalhe);

        String nome = args.getString("nomeResidente");
        String medicamento = args.getString("medicamento");
        String horario = args.getString("horario");
        String dose = args.getString("dose");
        String intervalo = args.getString("intervalo");
        String responsavel = args.getString("responsavel");
        String observacao = args.getString("observacao");

        idNome.setText(nome);
        idMedicamento.setText(medicamento);
        idHorario.setText(horario);
        idDose.setText(dose);
        idIntervalo.setText(intervalo);
        idResponsavel.setText(responsavel);
        idObservacao.setText(observacao);
    }
}
