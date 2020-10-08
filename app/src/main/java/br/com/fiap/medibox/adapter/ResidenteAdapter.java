package br.com.fiap.medibox.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.view.fragment.CadastroResidenteFragment;

public class ResidenteAdapter extends RecyclerView.Adapter<ResidenteViewHolder> {
    private ArrayList<ResidenteModel> residentes;
    private Context context;
    private int selectedPos;


    public ResidenteAdapter(ArrayList<ResidenteModel> residentes, Context context) {
        this.residentes = residentes;
        this.context = context;
    }

    @NonNull
    @Override
    public ResidenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.linha_residente) {
            view = LayoutInflater.from(context).inflate(R.layout.linha_residente, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.linha_adicionar, parent, false);
        }
        ResidenteViewHolder residenteViewHolder = new ResidenteViewHolder(view);
        return residenteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResidenteViewHolder holder, int position) {
        if (position == residentes.size()) {
            holder.adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Botão clicado", Toast.LENGTH_LONG).show();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();
                    Fragment fragment = new CadastroResidenteFragment();
                    activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            fragment).addToBackStack(null).commit();
                }
            });
        } else {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
            ResidenteModel residentePosicao = residentes.get(position);
            holder.nome.setText(residentePosicao.getNomeResidente());
            holder.quarto.setText(residentePosicao.getQuarto());
            if (residentePosicao.getDataNascimento() != null) {
                holder.nascimento.setText(format.format(residentePosicao.getDataNascimento()));
            }
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
        return residentes.size() + 1;
    }

    @Override
    public int getItemViewType(int selectedPos) {
        return (selectedPos == residentes.size()) ? R.layout.linha_adicionar : R.layout.linha_residente;
    }

    public void deleteResidente() {
        ResidenteModel residente = residentes.get(selectedPos);
        int id = selectedPos;

        if (residentes.remove(residente)) {
            Toast.makeText(context, "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Exclusão não permitida!", Toast.LENGTH_SHORT).show();
        }

        this.notifyItemRemoved(selectedPos);
    }

    public int getSelectedPos() {
        return selectedPos;
    }
}
