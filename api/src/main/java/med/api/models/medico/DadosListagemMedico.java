package med.api.models.medico;

import med.api.models.entities.Medico;

public record DadosListagemMedico(Long id, String nome, String crm, String email, Especialidade especialidade, String telefone) {

    public DadosListagemMedico(Medico medico) {
        this(medico.getId(), medico.getNome(), medico.getCrm(), medico.getEmail(), medico.getEspecialidade(), medico.getTelefone());
    }
}
