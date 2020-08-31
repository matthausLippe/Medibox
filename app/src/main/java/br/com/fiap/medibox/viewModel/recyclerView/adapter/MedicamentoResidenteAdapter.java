package br.com.fiap.medibox.viewModel.recyclerView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.Residente_Medicamento;
import br.com.fiap.medibox.viewModel.CadastroMedicamentoResidente;
import br.com.fiap.medibox.viewModel.recyclerView.viewHolder.MedicamentoResidenteViewHolder;
import br.com.fiap.medibox.viewModel.recyclerView.viewHolder.MyClickListener;
import br.com.fiap.medibox.viewModel.recyclerView.viewHolder.MyLongClickListener;


public class MedicamentoResidenteAdapter extends RecyclerView.Adapter<MedicamentoResidenteViewHolder> {
    private ArrayList<Residente_Medicamento> medicamentos;
    private Context context;
    private int selectedPos;

    public MedicamentoResidenteAdapter(ArrayList<Residente_Medicamento> medicamentos, Context context) {
        this.medicamentos = medicamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicamentoResidenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.linha_medicamento_residente) {
            view = LayoutInflater.from(context).inflate(R.layout.linha_medicamento_residente, parent, false);
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
                    //Toast.makeText(context,"Botão clicado", Toast.LENGTH_LONG).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new CadastroMedicamentoResidente();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment).addToBackStack(null).commit();
                }
            });
        } else {
            Residente_Medicamento residenteMedicamento = medicamentos.get(position);
            holder.medicamento.setText(residenteMedicamento.getMedicamento().getNomeMedicamento());
            holder.dosagem.setText(residenteMedicamento.getDosagem());
            holder.intervalo.setText(residenteMedicamento.getIntervalo());


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
        return (selectedPos == medicamentos.size()) ? R.layout.linha_adicionar : R.layout.linha_medicamento_residente;
    }

    public void deleteMedicamento() {
        Residente_Medicamento medicamento = medicamentos.get(selectedPos);
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

