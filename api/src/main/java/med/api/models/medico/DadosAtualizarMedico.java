package med.api.models.medico;

import jakarta.validation.constraints.NotNull;
import med.api.models.endereco.DadosEndereco;

public record DadosAtualizarMedico(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
