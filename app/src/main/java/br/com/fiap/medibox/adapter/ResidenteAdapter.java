package br.com.fiap.medibox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.Residente;

public class ResidenteAdapter extends RecyclerView.Adapter<ResidenteViewHolder> {
    private ArrayList<Residente> residentes;
    private Context context;
    private int selectedPos;

    public ResidenteAdapter(ArrayList<Residente> residentes, Context context) {
        this.residentes = residentes;
        this.context = context;
    }

    @NonNull
    @Override
    public ResidenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.linha_residente, parent, false);
        ResidenteViewHolder residenteViewHolder = new ResidenteViewHolder(view);
        return residenteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResidenteViewHolder holder, int position) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Residente residentePosicao = residentes.get(position);
        holder.nome.setText(residentePosicao.getNomeResidente());
        holder.quarto.setText(residentePosicao.getQuarto());
        if (residentePosicao.getDataNascimento() != null){
            holder.nascimento.setText(format.format(residentePosicao.getDataNascimento()));
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
        return residentes != null ? residentes.size() : 0;
    }

    public void deleteResidente(){
        Residente residente = residentes.get(selectedPos);
        int id = selectedPos;

        if(residentes.remove(residente))
        {
            Toast.makeText(context,"Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Exclusão não permitida!", Toast.LENGTH_SHORT).show();
        }

        this.notifyItemRemoved(selectedPos);
    }

    public int getSelectedPos(){
        return selectedPos;
    }
}
