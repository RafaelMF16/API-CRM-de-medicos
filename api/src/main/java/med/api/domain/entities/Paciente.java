package med.api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.api.domain.endereco.Endereco;
import med.api.domain.paciente.DadosAtualizarPaciente;
import med.api.domain.paciente.DadosCadastroPaciente;

@Table(name = "pacientes")
@Entity(name = "Paciente")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Paciente {

    // Atributos da classe Paciente
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrátegia de geração de ‘id’
    private Long id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    @Embedded // Indica que a entidade Endereço deve ser armazenada como parte da entidade Paciente
    private Endereco endereco;
    private Boolean ativo;

    public Paciente(DadosCadastroPaciente dados) { // Constroi um objeto Paciente com os dados passados
        this.ativo = true;
        this.nome = dados.nome();
        this.cpf = dados.cpf();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizarPaciente dados) { // Atualiza os dados do paciente caso os dados passados não sejam nulos
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null) {
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformações(dados.endereco());
        }
    }

    public void inativar() {
        this.ativo = false; // Inativa o paciente
    }
}
