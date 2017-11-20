package aluno;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.pets.R;

import aluno.data.AlunoContract.AlunoEntry;
import aluno.data.AlunoDbHelper;
public class EditorActivity extends AppCompatActivity {

    private EditText mNomeEditText;

    private EditText mSobrenomeEditText;

    private EditText mIdadeEditText;

    private Spinner mSexoSpinner;


    private int mSexo = AlunoEntry.SEXO_DESCONHECIDO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mNomeEditText = (EditText) findViewById(R.id.edit_aluno_nome);
        mSobrenomeEditText = (EditText) findViewById(R.id.edit_aluno_sobrenome);
        mIdadeEditText = (EditText) findViewById(R.id.edit_aluno_idade);
        mSexoSpinner = (Spinner) findViewById(R.id.spinner_sexo);

        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_sexo_options, android.R.layout.simple_spinner_item);

        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        mSexoSpinner.setAdapter(genderSpinnerAdapter);

        mSexoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Masculino")) {
                        mSexo = AlunoEntry.SEXO_MASCULINO;
                    } else if (selection.equals("Feminino")) {
                        mSexo = AlunoEntry.SEXO_FEMININO;
                    } else {
                        mSexo = AlunoEntry.SEXO_DESCONHECIDO;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mSexo = AlunoEntry.SEXO_DESCONHECIDO;
            }
        });
    }

    private void insertAluno() {
        String nomeString = mNomeEditText.getText().toString().trim();
        String sobrenomeString = mSobrenomeEditText.getText().toString().trim();
        String idadeString = mIdadeEditText.getText().toString().trim();
        int idade = Integer.parseInt(idadeString);

        AlunoDbHelper mDbHelper = new AlunoDbHelper(this);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(AlunoEntry.COLUMN_ALUNO_NOME, nomeString);
        values.put(AlunoEntry.COLUMN_ALUNO_SOBRENOME, sobrenomeString);
        values.put(AlunoEntry.COLUMN_ALUNO_SEXO, mSexo);
        values.put(AlunoEntry.COLUMN_ALUNO_IDADE, idade);

        long newRowId = db.insert(AlunoEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Erro salvando aluno", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Aluno inserido id: " + newRowId, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:

                insertAluno();

                finish();
                return true;

            case R.id.action_delete:
                /*
                AlunoDbHelper mDbHelper = new AlunoDbHelper(this);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                long newRowId = db.delete(AlunoEntry.TABLE_NAME, null, null);
                */
                return true;

            case android.R.id.home:

                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}