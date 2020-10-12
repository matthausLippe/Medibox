package br.com.fiap.medibox.adapter;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.medibox.R;


public class MedicamentoResidenteViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener, View.OnCreateContextMenuListener {

    public TextView medicamento;
    public TextView dosagem;
    public TextView intervalo;
    public CardView adicionar;
    MyLongClickListener longClickListener;
    MyClickListener clickListener;

    public MedicamentoResidenteViewHolder(@NonNull View itemView) {
        super(itemView);
        medicamento = (TextView) itemView.findViewById(R.id.idMedicamentoLinha);
        dosagem = (TextView) itemView.findViewById(R.id.idDosagemLinha);
        intervalo = (TextView) itemView.findViewById(R.id.idIntervaloLinha);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);

        adicionar = (CardView) itemView.findViewById(R.id.itemAdd);
    }

    @Override
    public void onClick(View view) {
        this.clickListener.onClick((getLayoutPosition()));
    }

    @Override
    public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        contextMenu.add(0, 0, 0, "Editar");
        contextMenu.add(0, 1, 0, "Deletar");
    }

    @Override
    public boolean onLongClick(View view) {
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
