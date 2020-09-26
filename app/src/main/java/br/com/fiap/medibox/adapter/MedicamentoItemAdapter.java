package br.com.fiap.medibox.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ItemRemedio;

public class MedicamentoItemAdapter extends RecyclerView.Adapter<MedicamentoItemAdapter.MedicamentoViewHolder> {
    private ArrayList<ItemRemedio> mListaMedicamento;
    private int selectedPos;


    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextNome;
        public TextView mTextCaixa;
        public MedicamentoViewHolder(View itemView) {
            super(itemView);
            mTextNome = itemView.findViewById(R.id.txt_nome);
            mTextCaixa = itemView.findViewById(R.id.txt_caixa);
        }
    }

    public MedicamentoItemAdapter(ArrayList<ItemRemedio> medicamentoLista) {
        mListaMedicamento = medicamentoLista;
    }

    @Override
    public MedicamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_medicamento,
                parent, false);
        MedicamentoViewHolder evh = new MedicamentoViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(MedicamentoViewHolder holder, int position) {
        ItemRemedio currentItem = mListaMedicamento.get(position);
        holder.mTextNome.setText(currentItem.getNomeRemedio());
        holder.mTextCaixa.setText(currentItem.getCaixaRemedio());
    }
    @Override
    public int getItemCount() {
        return mListaMedicamento.size();
    }
    public void filterList(ArrayList<ItemRemedio> filteredList) {
        mListaMedicamento = filteredList;
        notifyDataSetChanged();
    }

}
