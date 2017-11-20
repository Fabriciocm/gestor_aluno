
package aluno.data;

import android.provider.BaseColumns;


public final class AlunoContract {


    private AlunoContract() {}

    public static final class AlunoEntry implements BaseColumns {

        public final static String TABLE_NAME = "aluno";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_ALUNO_NOME ="nome";


        public final static String COLUMN_ALUNO_SOBRENOME = "sobrenome";


        public final static String COLUMN_ALUNO_SEXO = "sexo";


        public final static String COLUMN_ALUNO_IDADE = "idade";

        public static final int SEXO_DESCONHECIDO = 0;
        public static final int SEXO_MASCULINO = 1;
        public static final int SEXO_FEMININO = 2;
    }

}

