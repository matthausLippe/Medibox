package br.com.fiap.medibox.adapter;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.view.fragment.CadastroMedicamentosFragment;

public class MedicamentoItemAdapter extends RecyclerView.Adapter<MedicamentoItemAdapter.MedicamentoViewHolder> {
    private List<MedicamentoModel> mListaMedicamento;
    private Context context;
    private int selectedPos;




    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener, View.OnCreateContextMenuListener {
        public TextView mTextNome;
        public TextView mTextCaixa;
        public CardView adicionar;
        MyLongClickListener longClickListener;
        MyClickListener clickListener;

        public MedicamentoViewHolder(View itemView) {
            super(itemView);
            mTextNome =(TextView) itemView.findViewById(R.id.txt_nome);
            mTextCaixa =(TextView) itemView.findViewById(R.id.txt_caixa);
            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

            adicionar = (CardView) itemView.findViewById(R.id.itemAdd);
        }

        @Override
        public void onClick(View v) {
            this.clickListener.onClick((getLayoutPosition()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(0, 0, 0, "Editar");
            menu.add(0, 1, 0, "Deletar");
        }

        @Override
        public boolean onLongClick(View v) {
            this.longClickListener.onLongClick(getLayoutPosition());
            return false;
        }

        public void setClickListener(MyClickListener clickListener) {
            this.clickListener = clickListener;
        }

        public void setLongClickListener(MyLongClickListener longClickListener) {
            this.longClickListener = longClickListener;
        }
    }

    public MedicamentoItemAdapter(List<MedicamentoModel> medicamentoLista, Context context) {
        mListaMedicamento = medicamentoLista;
        this.context = context;

    }

    @Override
    public MedicamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if (viewType == R.layout.linha_medicamento) {
            v = LayoutInflater.from(context).inflate(R.layout.linha_medicamento, parent, false);
        } else {
            v = LayoutInflater.from(context).inflate(R.layout.linha_adicionar, parent, false);
        }

        MedicamentoViewHolder evh = new MedicamentoViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(MedicamentoViewHolder holder, int position) {
        if(position == mListaMedicamento.size()){
            holder.adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppCompatActivity activity = (AppCompatActivity) v.getContext();
                    Fragment fragment = new CadastroMedicamentosFragment();
                    activity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container,fragment).addToBackStack(null).commit();

                }
            });
        }else {
            MedicamentoModel currentItem = mListaMedicamento.get(position);
            holder.mTextNome.setText(currentItem.getNomeMedicamento());
            holder.mTextCaixa.setText(currentItem.getNomeMedicamento());
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
        return mListaMedicamento.size() + 1;
    }

    public void filterList(ArrayList<MedicamentoModel> filteredList) {
        mListaMedicamento = filteredList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int selectedPos) {
        return (selectedPos == mListaMedicamento.size()) ? R.layout.linha_adicionar : R.layout.linha_medicamento;
    }

}
