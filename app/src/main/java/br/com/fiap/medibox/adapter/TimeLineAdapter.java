package br.com.fiap.medibox.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ItemTimeline;
import br.com.fiap.medibox.view.fragment.TimelineItemFragment;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<ItemTimeline> listItems;
    private Context context;
    private View view;
    private SimpleDateFormat horaFormat= new SimpleDateFormat("HH:mm");

    public TimeLineAdapter(List<ItemTimeline> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;

    }

    @Override
    public int getItemViewType(int selectedPos) {
        return listItems.size();
    }

    @NonNull
    @Override
    public TimeLineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_timeline, parent, false);
        TimeLineViewHolder timeLineViewHolder = new TimeLineViewHolder(view);
        return timeLineViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimeLineViewHolder holder, int position) {
        final ItemTimeline listItem = listItems.get(position);
        holder.nomeResidente.setText(listItem.getNome());
        holder.nomeMedicamento.setText(listItem.getMedicamento());
        holder.dosegem.setText(listItem.getDose());
        holder.horario.setText(horaFormat.format(listItem.getDataHora()));
        holder.gaveta.setText(listItem.getGaveta());
        if (listItem.getSituacao() == ItemTimeline.MEDICADO){
            holder.image.setImageResource(R.drawable.icon_check);
        } else {
            holder.image.setImageResource(R.drawable.icon_x);
        }

        holder.setClickListener(new MyClickListener() {
            @Override
            public void onClick(int pos) {
                Bundle args = new Bundle();
                args.putLong("idTimeLine",listItems.get(pos).getIdTimeline());
                args.putString("nomeResidente",listItems.get(pos).getNome());
                args.putString("medicamento",listItems.get(pos).getMedicamento());
                args.putString("dataHora",horaFormat.format(listItems.get(pos).getDataHora()));
                args.putString("dose",listItems.get(pos).getDose());
                args.putString("intervalo",listItems.get(pos).getIntervalo());
                args.putString("responsavel",listItems.get(pos).getTelResponsavel());
                args.putString("observacao",listItems.get(pos).getObservacao());
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                TimelineItemFragment detalheFragment = new TimelineItemFragment();
                detalheFragment.setArguments(args);
                FragmentTransaction transaction = activity.getSupportFragmentManager().
                        beginTransaction();
                transaction.replace(R.id.fragment_container, detalheFragment).addToBackStack(null).commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }


}
