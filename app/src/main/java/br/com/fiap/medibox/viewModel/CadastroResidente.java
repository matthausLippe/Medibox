package br.com.fiap.medibox.viewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import br.com.fiap.medibox.R;

public class CadastroResidente extends Fragment {

    private EditText nome;
    private EditText dataNascimento;
    private EditText sexo;
    private EditText nomeResponsavel;
    private EditText telResponsavel;
    private EditText quarto;
    private EditText observacoes;
    private RecyclerView recycler;
    private Button salvar;
    private Button cancelar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_cadastro_residente, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        View view = getView();
        nome = view.findViewById(R.id.idNome);
        dataNascimento = view.findViewById(R.id.idNascimento);
        sexo = view.findViewById(R.id.idSexo);
        nomeResponsavel = view.findViewById(R.id.idResponsavel);
        telResponsavel = view.findViewById(R.id.idTelResponsavel);
        quarto = view.findViewById(R.id.idQuarto);
        observacoes = view.findViewById(R.id.idObs);
        recycler = view.findViewById(R.id.recyclerListaMedicamentosResidente);
        salvar = view.findViewById(R.id.idSalvar);
        cancelar = view.findViewById(R.id.idCancelar);
    }

    //Criar função carregar dados para editar

    //função botão salvar

    //função para o botão cancelar

    //Função para carregar informações dos medicamentos ligados ao residente

    //Função para vincular novo remedio ao residente
}