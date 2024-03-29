package med.api.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.api.domain.medico.DadosAtualizarMedico;
import med.api.domain.medico.DadosCadastroMedico;
import med.api.domain.endereco.Endereco;
import med.api.domain.medico.Especialidade;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    // Atributos da classe Medico
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Estrátegia de geração de ‘id’
    private Long id;
    private String nome;
    private String telefone;
    private String email;
    private String crm;
    @Enumerated(EnumType.STRING) // Específica que o atributo especialidade é um ENUM e deve ser guardado no banco de dados como ‘String’
    private Especialidade especialidade;
    @Embedded // Indica que a entidade Endereco deve ser armazenada como parte da entidade Medico
    private Endereco endereco;
    private Boolean ativo;

    public Medico(DadosCadastroMedico dados) { // Constroi um objeto Medico com os dados passados
        this.ativo = true;
        this.nome = dados.nome();
        this.telefone = dados.telefone();
        this.email = dados.email();
        this.crm = dados.crm();
        this.especialidade = dados.especialidade();
        this.endereco = new Endereco(dados.endereco());
    }

    public void atualizarInformacoes(DadosAtualizarMedico dados) { // Atualiza os dados do médico caso os dados passados não sejam nulos
        if (dados.nome() != null) {
            this.nome = dados.nome();
        }
        if (dados.telefone() != null){
            this.telefone = dados.telefone();
        }
        if (dados.endereco() != null) {
            this.endereco.atualizarInformações(dados.endereco());
        }
    }

    public void inativar() {
        this.ativo = false; // Inativa o médico
    }
}
