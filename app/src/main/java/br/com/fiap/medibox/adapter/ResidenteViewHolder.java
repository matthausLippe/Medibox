package br.com.fiap.medibox.adapter;

import android.view.ContextMenu;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.medibox.R;

public class ResidenteViewHolder  extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener, View.OnCreateContextMenuListener {

    TextView nome, nascimento, quarto;
    MyLongClickListener longClickListener;
    MyClickListener clickListener;


    public ResidenteViewHolder(@NonNull View itemView) {
        super(itemView);
        nome = (TextView) itemView.findViewById(R.id.idResidente);
        nascimento = (TextView) itemView.findViewById(R.id.idDataNascimento);
        quarto = (TextView) itemView.findViewById(R.id.idQuarto);
        itemView.setOnLongClickListener(this);
        itemView.setOnClickListener(this);
        itemView.setOnCreateContextMenuListener(this);
    }

    @Override
    public void onClick(View view) {
        this.clickListener.onClick(getLayoutPosition());

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
        menu.add(0,0,0,"Editar");
        menu.add(0,1,0,"Deletar");
    }

    @Override
    public boolean onLongClick(View view) {
        this.longClickListener.onLongClick(getLayoutPosition());
        return false;
    }

    public void setClickListener (MyClickListener clickListener){
        this.clickListener = clickListener;
    }

    public void setLongClickListener (MyLongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }
}
