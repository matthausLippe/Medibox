package br.com.fiap.medibox;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.fiap.medibox.model.ItemTimeline;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.ViewHolder> {

    private List<ItemTimeline> listItems;
    private Context context;

    public TimeLineAdapter(List<ItemTimeline> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.linha_timeline, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemTimeline listItem = listItems.get(position);
        holder.nomeResidente.setText(listItem.getNome());
        holder.nomeRemedio.setText(listItem.getRemedio());
        holder.quantidadeRemedio.setText(listItem.getQuantidade());
        holder.horarioRemedio.setText(listItem.getHorario());
        holder.gavetaRemedio.setText(listItem.getGaveta());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView nomeResidente;
        public TextView nomeRemedio;
        public TextView quantidadeRemedio;
        public TextView horarioRemedio;
        public TextView gavetaRemedio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nomeResidente = (TextView) itemView.findViewById(R.id.nome);
            nomeRemedio = (TextView) itemView.findViewById(R.id.remedio);
            quantidadeRemedio = (TextView) itemView.findViewById(R.id.quantidade);
            horarioRemedio = (TextView) itemView.findViewById(R.id.horario);
            gavetaRemedio = (TextView) itemView.findViewById(R.id.gaveta);
        }
    }
}
