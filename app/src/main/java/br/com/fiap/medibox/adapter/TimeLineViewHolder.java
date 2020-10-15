package br.com.fiap.medibox.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.fiap.medibox.R;


public class TimeLineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView nomeResidente;
    public TextView nomeMedicamento;
    public TextView dosegem;
    public TextView horario;
    public TextView gaveta;
    public ImageView image;
    public TextView dataMedicacao;
    MyClickListener clickListener;

    public TimeLineViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeResidente = (TextView) itemView.findViewById(R.id.idNomeResidenteTimeLine);
        nomeMedicamento = (TextView) itemView.findViewById(R.id.idMedicamentoTimeLine);
        dosegem = (TextView) itemView.findViewById(R.id.idDoseTimeLine);
        horario = (TextView) itemView.findViewById(R.id.idHorarioTimeLine);
        gaveta = (TextView) itemView.findViewById(R.id.idGavetaTimeLine);
        image = (ImageView) itemView.findViewById(R.id.imageViewTimeLine);
        dataMedicacao = (TextView) itemView.findViewById(R.id.idDataTimeLineItem);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.clickListener.onClick(getLayoutPosition());
    }


    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }



}
