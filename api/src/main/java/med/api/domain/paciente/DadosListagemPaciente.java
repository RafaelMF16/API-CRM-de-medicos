package med.api.domain.paciente;

import med.api.domain.entities.Paciente;

public record DadosListagemPaciente(Long id, String nome, String cpf, String email) {

    public DadosListagemPaciente(Paciente paciente){
        this(paciente.getId(), paciente.getNome(), paciente.getCpf(), paciente.getEmail());
    }
}
