package aluno;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.pets.R;

import java.util.ArrayList;
import java.util.List;

import aluno.data.AlunoContract.AlunoEntry;
import aluno.data.AlunoDbHelper;

public class CatalogoActivity extends AppCompatActivity {

    private AlunoDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogo);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogoActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });
        mDbHelper = new AlunoDbHelper(this);

        popularLista();


    }

    public void popularLista(){
        final List<Aluno> alunos = listaAlunos();

        ListView listaDeAlunos = (ListView) findViewById(R.id.lista);

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,
                android.R.layout.simple_list_item_1, alunos);

        listaDeAlunos.setAdapter(adapter);


    }
    @Override
    public void onResume() {
        super.onResume();
        //System.out.println("aa");
        popularLista();
    }
    @Override
    protected void onStart() {
        super.onStart();
        //displayDatabaseInfo();
    }



    private List<Aluno> listaAlunos() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                AlunoEntry._ID,
                AlunoEntry.COLUMN_ALUNO_NOME,
                AlunoEntry.COLUMN_ALUNO_SOBRENOME,
                AlunoEntry.COLUMN_ALUNO_SEXO,
                AlunoEntry.COLUMN_ALUNO_IDADE };

        Cursor cursor = db.query(
                AlunoEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        //TextView displayView = (TextView) findViewById(R.id.text_view_pet);
        List<Aluno> lista = new ArrayList<>();
        try {
            int idColumnIndex = cursor.getColumnIndex(AlunoEntry._ID);
            int nomeColumnIndex = cursor.getColumnIndex(AlunoEntry.COLUMN_ALUNO_NOME);
            int sobrenomeColumnIndex = cursor.getColumnIndex(AlunoEntry.COLUMN_ALUNO_SOBRENOME);
            int sexoColumnIndex = cursor.getColumnIndex(AlunoEntry.COLUMN_ALUNO_SEXO);
            int idadeColumnIndex = cursor.getColumnIndex(AlunoEntry.COLUMN_ALUNO_IDADE);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentNome = cursor.getString(nomeColumnIndex);
                String currentSobrenome = cursor.getString(sobrenomeColumnIndex);
                int currentSexo = cursor.getInt(sexoColumnIndex);
                int currentPeso = cursor.getInt(idadeColumnIndex);
                Aluno aluno = new Aluno();
                aluno.setNome(currentNome);
                aluno.setSobrenome(currentSobrenome);
                String sexo;
                sexo = "";
                if(currentSexo == 0){
                    sexo = "Desconhecido";
                }else if(currentSexo == 1){
                    sexo = "Masculino";
                }else if(currentSexo == 2){
                    sexo = "Faminino";
                }
                aluno.setSexo(sexo);

                aluno.setIdade(idadeColumnIndex);

                lista.add(aluno);
            }
        } finally {
            cursor.close();
        }

        return lista;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_delete_all_entries:
                AlunoDbHelper mDbHelper = new AlunoDbHelper(this);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                long newRowId = db.delete(AlunoEntry.TABLE_NAME, null, null);

                if (newRowId == -1) {
                    Toast.makeText(this, "Erro ao apagar alunos", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Todos os alunos deletados ", Toast.LENGTH_SHORT).show();
                    popularLista();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
