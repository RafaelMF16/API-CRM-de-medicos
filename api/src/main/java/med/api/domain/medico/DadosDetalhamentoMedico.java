package med.api.domain.medico;

import med.api.domain.endereco.Endereco;
import med.api.domain.entities.Medico;

public record DadosDetalhamentoMedico(
        Long id,
        String nome,
        String crm,
        String email,
        String telefone,
        Especialidade especialidade,
        Endereco endereco) {

    public DadosDetalhamentoMedico(Medico medico){
        this(medico.getId(),
                medico.getNome(),
                medico.getCrm(),
                medico.getEmail(),
                medico.getTelefone(),
                medico.getEspecialidade(),
                medico.getEndereco());
    }
}
