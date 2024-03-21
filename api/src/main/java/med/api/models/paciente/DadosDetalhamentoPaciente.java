package med.api.models.paciente;

import med.api.models.endereco.Endereco;
import med.api.models.entities.Paciente;
import med.api.models.medico.DadosDetalhamentoMedico;

public record DadosDetalhamentoPaciente(Long id, String nome, String cpf, String telefone, Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEndereco());
    }
}
