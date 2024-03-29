package med.api.domain.paciente;

import med.api.domain.endereco.Endereco;
import med.api.domain.entities.Paciente;

public record DadosDetalhamentoPaciente(
        Long id,
        String nome,
        String cpf,
        String telefone,
        Endereco endereco) {

    public DadosDetalhamentoPaciente(Paciente paciente){
        this(paciente.getId(),
                paciente.getNome(),
                paciente.getCpf(),
                paciente.getTelefone(),
                paciente.getEndereco());
    }
}
