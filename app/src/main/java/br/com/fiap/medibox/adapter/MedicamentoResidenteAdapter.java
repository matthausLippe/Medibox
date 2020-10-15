package br.com.fiap.medibox.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.view.fragment.CadastroMedicamentoResidenteFragment;


public class MedicamentoResidenteAdapter extends RecyclerView.Adapter<MedicamentoResidenteViewHolder> {
    private List<ResidenteMedicamentoModel> medicamentos;
    private Context context;
    private int selectedPos;
    private Button cancelar;
    private SeekBar seekBar;
    private TextView textDosesSeekBar;
    private long idResidente;

    public MedicamentoResidenteAdapter(List<ResidenteMedicamentoModel> medicamentos, Context context, long idResidente) {
        this.medicamentos = medicamentos;
        this.context = context;
        this.idResidente = idResidente;
    }

    @NonNull
    @Override
    public MedicamentoResidenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.linha_residente_medicamento) {
            view = LayoutInflater.from(context).inflate(R.layout.linha_residente_medicamento, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.linha_adicionar, parent, false);
        }
        MedicamentoResidenteViewHolder medicamentoResidenteViewHolder = new MedicamentoResidenteViewHolder(view);
        return medicamentoResidenteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoResidenteViewHolder holder, int position) {
        if (position == medicamentos.size()) {
            holder.adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new CadastroMedicamentoResidenteFragment();
                    Bundle args = new Bundle();
                    args.putLong("idResidente", idResidente);
                    fragment.setArguments(args);
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment).addToBackStack(null).commit();
                }
            });
        } else {
            ResidenteMedicamentoModel residenteMedicamentoModel = medicamentos.get(position);
            holder.medicamento.setText(residenteMedicamentoModel.getMedicamento().getNomeMedicamento());
            holder.dosagem.setText(residenteMedicamentoModel.getDosagem());
            holder.intervalo.setText(Double.toString(residenteMedicamentoModel.getIntervalo()));
        }
        holder.setLongClickListener(new MyLongClickListener() {
            @Override
            public void onLongClick(int pos) {
                selectedPos = pos;
            }
        });
        holder.setClickListener(new MyClickListener() {
            @Override
            public void onClick(int pos) {
                selectedPos = pos;
            }
        });
    }

    @Override
    public int getItemCount() {
        return medicamentos.size() + 1;
    }

    @Override
    public int getItemViewType(int selectedPos) {
        return (selectedPos == medicamentos.size()) ? R.layout.linha_adicionar : R.layout.linha_residente_medicamento;
    }

    public void deleteMedicamento() {
        ResidenteMedicamentoModel medicamento = medicamentos.get(selectedPos);
        int id = selectedPos;
        if (medicamentos.remove(medicamento)) {
            Toast.makeText(context, "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
            this.notifyItemRemoved(selectedPos);
        } else {
            Toast.makeText(context, "Exclusão não permitida!", Toast.LENGTH_SHORT).show();
        }
    }

    public int getSelectedPos() {
        return selectedPos;
    }
}

