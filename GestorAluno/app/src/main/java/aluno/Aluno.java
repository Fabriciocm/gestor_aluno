package aluno;

public class Aluno {
    private String nome;
    private String sobrenome;
    private String sexo;
    private int idade;

    public String toString(){
        return "Nome: " + this.nome + " Sobrenome: " + this.sobrenome + "\nSexo: " + this.sexo + " Idade: " + this.idade + " anos";
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
