package br.com.fiap.medibox.view;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.CaixaModel;
import br.com.fiap.medibox.model.ClienteModel;
import br.com.fiap.medibox.model.EnderecoModel;
import br.com.fiap.medibox.model.GavetaModel;
import br.com.fiap.medibox.model.MedicamentoModel;
import br.com.fiap.medibox.model.ResidenteMedicamentoModel;
import br.com.fiap.medibox.model.ResidenteModel;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.view.activity.ListaResidenteActivity;
import br.com.fiap.medibox.view.activity.LoginActivity;
import br.com.fiap.medibox.view.fragment.ConfiguracoesFragment;
import br.com.fiap.medibox.view.fragment.MedicamentosFragment;
import br.com.fiap.medibox.view.fragment.TimeLineFragment;
import br.com.fiap.medibox.viewModel.MainViewModel;
import br.com.fiap.medibox.viewModel.TimeLineViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private Context context;
    private View view;

    public static String idResidenteMedicamento;

    private TimeLineViewModel timeLineViewModel;

    private MutableLiveData<List<TimeLineViewModel>> list;

    private List<ResidenteModel> listResidente;
    private List<ClienteModel> listCliente;
    private List<MedicamentoModel> listMedicamento;
    private List<ResidenteMedicamentoModel> listResidenteMedicamento;
    private List<TimeLineModel> listTimeLine;
    private List<CaixaModel> listCaixa;
    private List<GavetaModel> listGaveta;
    private List<EnderecoModel> listEndereco;

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        timeLineViewModel = new ViewModelProvider(this).get(TimeLineViewModel.class);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        ObterDados();
        //alarm
        createNotificationChannel();
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        criaAlarmes(alarmManager);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new TimeLineFragment()).commit();
            navigationView.setCheckedItem(R.id.timeline_item);
        }
    }

    private void ObterDados() {
        mainViewModel.getListClienteService().observe(this, new Observer<List<ClienteModel>>() {
            @Override
            public void onChanged(List<ClienteModel> clienteModels) {
                listCliente = clienteModels;
                mainViewModel.insertListCliente(listCliente);
            }
        });
        mainViewModel.getListEnderecoervice().observe(this, new Observer<List<EnderecoModel>>() {
            @Override
            public void onChanged(List<EnderecoModel> enderecoModels) {
                listEndereco = enderecoModels;
                mainViewModel.insertListEndereco(listEndereco);
            }
        });
        mainViewModel.getListCaixaService().observe(this, new Observer<List<CaixaModel>>() {
            @Override
            public void onChanged(List<CaixaModel> caixaModels) {
                listCaixa = caixaModels;
                mainViewModel.insertListCaixa(listCaixa);
            }
        });
        mainViewModel.getListGavetaService().observe(this, new Observer<List<GavetaModel>>() {
            @Override
            public void onChanged(List<GavetaModel> gavetaModels) {
                listGaveta = gavetaModels;
                mainViewModel.insertListGaveta(listGaveta);
            }
        });
        mainViewModel.getListMedicamentoService().observe(this, new Observer<List<MedicamentoModel>>() {
            @Override
            public void onChanged(List<MedicamentoModel> medicamentoModels) {
                listMedicamento = medicamentoModels;
                mainViewModel.insertListMedicamento(listMedicamento);
            }
        });
        mainViewModel.getListResidenteService().observe(this, new Observer<List<ResidenteModel>>() {
            @Override
            public void onChanged(List<ResidenteModel> residenteModels) {
                listResidente = residenteModels;
                mainViewModel.insertListResidente(listResidente);
            }
        });
        mainViewModel.getListResidenteMedicamentoService().observe(this, new Observer<List<ResidenteMedicamentoModel>>() {
            @Override
            public void onChanged(List<ResidenteMedicamentoModel> residenteMedicamentoModels) {
                listResidenteMedicamento =  residenteMedicamentoModels;
                mainViewModel.insertListResidenteMedicamento(listResidenteMedicamento);

            }
        });
        mainViewModel.getListTimeLineService().observe(this, new Observer<List<TimeLineModel>>() {
            @Override
            public void onChanged(List<TimeLineModel> timeLineModels) {
                listTimeLine = timeLineModels;
                mainViewModel.insertListTimeLine(listTimeLine);
            }
        });

    }

    private void criaAlarmes(AlarmManager alarmManager) {
        Intent notificationIntent = new Intent("action.DISPLAY_NOTIFICATION");

        //chama query que traz o medicamento/paciente
        Date data = new Date(new java.util.Date().getTime());
        List<TimeLineModel> lista;

        timeLineViewModel.getLitNotification(data).observe(this, new Observer<List<TimeLineModel>>() {
            @Override
            public void onChanged(List<TimeLineModel> timeLineModels) {
                TimeLineModel l = new TimeLineModel();
                Calendar date = Calendar.getInstance();
                Date d = new Date(date.getTimeInMillis() + 30000);
                l.setDataHoraMedicacao(d);
                l.setIdCliente(1);
                l.setIdTimeLine(1);
                l.setIdResidenteMedicamento(1);
                timeLineModels.add(l);
                for(int i = 0; i<timeLineModels.size(); i++){
                    PendingIntent broadcast = PendingIntent.getBroadcast(context, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, timeLineModels.get(i).getDataHoraMedicacao().getTime(), broadcast);
                }
            }
        });
        //List<TimeLineModel> lista =new ArrayList<TimeLineModel>();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.medicacao_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MedicamentosFragment()).commit();
                        setTitle("Medicações");
                        drawer.closeDrawers();
                break;

            case R.id.paciente_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListaResidenteActivity()).commit();
                        setTitle("Residentes");
                        drawer.closeDrawers();
                break;

            case R.id.timeline_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new TimeLineFragment()).commit();
                        setTitle("Timeline");
                        drawer.closeDrawers();
                break;

            case R.id.conf_item:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ConfiguracoesFragment()).commit();
                        setTitle("Configurações");
                        drawer.closeDrawers();
                break;

            case R.id.logout_item:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                Toast.makeText(this, "Logoff efetuado com sucesso", Toast.LENGTH_LONG).show();
                break;
        }

        return true;
    }

    private void createNotificationChannel() {
        CharSequence name = "channel1";
        String description = "Canal de notificação";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("channel1",name,importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


}