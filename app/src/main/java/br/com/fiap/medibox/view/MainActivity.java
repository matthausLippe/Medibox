package br.com.fiap.medibox.view;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.navigation.NavigationView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.fiap.medibox.AlarmReceiver;
import br.com.fiap.medibox.R;
import br.com.fiap.medibox.model.TimeLineModel;
import br.com.fiap.medibox.view.activity.ListaResidenteActivity;
import br.com.fiap.medibox.view.activity.LoginActivity;
import br.com.fiap.medibox.view.fragment.ConfiguracoesFragment;
import br.com.fiap.medibox.view.fragment.MedicamentosFragment;
import br.com.fiap.medibox.view.fragment.TimeLineFragment;
import br.com.fiap.medibox.viewModel.TimeLineViewModel;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private TimeLineViewModel timeLineViewModel;

    private MutableLiveData<List<TimeLineViewModel>> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void criaAlarmes(AlarmManager alarmManager) {
        Intent notificationIntent = new Intent("action.DISPLAY_NOTIFICATION");


        //chama query que traz o medicamento/paciente
        //List<TimeLineModel> lista = timeLineViewModel.getLitNotification(new Date(new java.util.Date().getTime()));
        List<TimeLineModel> lista =new ArrayList<TimeLineModel>();
        TimeLineModel l = new TimeLineModel();

        Calendar date = Calendar.getInstance();
        Date d = new Date(date.getTimeInMillis() + 30000);
        l.setDataHoraMedicacao(d);
        l.setIdCliente(1);
        l.setIdTimeLine(1);
        l.setIdResidenteMedicamento(1);
        lista.add(l);

        for(int i = 0; i<lista.size(); i++){
            PendingIntent broadcast = PendingIntent.getBroadcast(this, i, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, lista.get(i).getDataHoraMedicacao().getTime(), broadcast);

        }

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