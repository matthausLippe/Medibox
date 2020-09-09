package br.com.fiap.medibox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText login_txt;
    EditText pass_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button login_bt = (Button) findViewById(R.id.bt_login);
        login_txt = findViewById(R.id.user_txt);
        pass_txt = findViewById(R.id.password_txt);


            login_bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    clickLogin();
                }
            });

    }

    protected void clickLogin(){
        if(login_txt.getText().toString().equals("admin") && pass_txt.getText().toString().equals("admin")) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }else{
        Toast.makeText(this, "Usuário ou Senha inválidos.", Toast.LENGTH_LONG).show();
    }
    }
}