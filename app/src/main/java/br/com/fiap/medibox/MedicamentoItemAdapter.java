package br.com.fiap.medibox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.fiap.medibox.model.ItemRemedio;

public class MedicamentoItemAdapter extends RecyclerView.Adapter<MedicamentoItemAdapter.MedicamentoViewHolder> {
    private ArrayList<ItemRemedio> mListaRemedio;

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {

        public TextView mTextNome;
        public TextView mTextCaixa;
        public MedicamentoViewHolder(View itemView) {
            super(itemView);
            mTextNome = itemView.findViewById(R.id.txt_nome);
            mTextCaixa = itemView.findViewById(R.id.txt_caixa);
        }
    }

    public MedicamentoItemAdapter(ArrayList<ItemRemedio> remedioList) {
        mListaRemedio = remedioList;
    }

    @Override
    public MedicamentoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_remedio,
                parent, false);
        MedicamentoViewHolder evh = new MedicamentoViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(MedicamentoViewHolder holder, int position) {
        ItemRemedio currentItem = mListaRemedio.get(position);
        holder.mTextNome.setText(currentItem.getNomeRemedio());
        holder.mTextCaixa.setText(currentItem.getCaixaRemedio());
    }
    @Override
    public int getItemCount() {
        return mListaRemedio.size();
    }
    public void filterList(ArrayList<ItemRemedio> filteredList) {
        mListaRemedio = filteredList;
        notifyDataSetChanged();
    }

}
