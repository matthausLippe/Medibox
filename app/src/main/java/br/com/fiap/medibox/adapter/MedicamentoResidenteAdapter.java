package br.com.fiap.medibox.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;


public class MedicamentoResidenteAdapter extends RecyclerView.Adapter<MedicamentoResidenteViewHolder> {
    private List<ResidenteMedicamentoModel> medicamentos;
    private Context context;
    private int selectedPos;
    private Button cancelar;
    private SeekBar seekBar;
    private TextView textDosesSeekBar;

    public MedicamentoResidenteAdapter(List<ResidenteMedicamentoModel> medicamentos, Context context) {
        this.medicamentos = medicamentos;
        this.context = context;
    }

    @NonNull
    @Override
    public MedicamentoResidenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == R.layout.linha_residente_medicamento) {
            view = LayoutInflater.from(context).inflate(R.layout.linha_residente_medicamento, parent, false);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.linha_adicionar, parent, false);
        }
        MedicamentoResidenteViewHolder medicamentoResidenteViewHolder = new MedicamentoResidenteViewHolder(view);
        return medicamentoResidenteViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoResidenteViewHolder holder, int position) {
        if (position == medicamentos.size()) {
            holder.adicionar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(context,"Botão clicado", Toast.LENGTH_LONG).show()
                    final Dialog dialog = new Dialog(context);
                    dialog.setTitle("Cadastrar");
                    dialog.setContentView(R.layout.fragment_cadastro_residente_medicamento);
                    dialog.setCancelable(true);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                    seekBar = (SeekBar) dialog.findViewById(R.id.seekBarDoses);
                    textDosesSeekBar = (TextView) dialog.findViewById(R.id.textDosesSeekBar);
                    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                            textDosesSeekBar.setText(Integer.toString(i));
                        }
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }
                    });

                    cancelar = (Button) dialog.findViewById(R.id.buttonCancelar);
                    cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    dialog.getWindow().setAttributes(lp);
                }
            });
        } else {
            ResidenteMedicamentoModel residenteMedicamentoModel = medicamentos.get(position);
            holder.medicamento.setText(Double.toString(residenteMedicamentoModel.getIdMedicamento()));
            holder.dosagem.setText(residenteMedicamentoModel.getDosagem());
            holder.intervalo.setText(Double.toString(residenteMedicamentoModel.getIntervalo()));
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
        return medicamentos.size() + 1;
    }

    @Override
    public int getItemViewType(int selectedPos) {
        return (selectedPos == medicamentos.size()) ? R.layout.linha_adicionar : R.layout.linha_residente_medicamento;
    }

    public void deleteMedicamento() {
        ResidenteMedicamentoModel medicamento = medicamentos.get(selectedPos);
        int id = selectedPos;
        if (medicamentos.remove(medicamento)) {
            Toast.makeText(context, "Exclusão realizada com sucesso!", Toast.LENGTH_SHORT).show();
            this.notifyItemRemoved(selectedPos);
        } else {
            Toast.makeText(context, "Exclusão não permitida!", Toast.LENGTH_SHORT).show();
        }
    }

    public int getSelectedPos() {
        return selectedPos;
    }
}

