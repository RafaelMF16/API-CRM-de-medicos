package med.api.domain.medico;

import jakarta.validation.constraints.NotNull;
import med.api.domain.endereco.DadosEndereco;

public record DadosAtualizarMedico(
        @NotNull // Exige que o ‘id’ não seja nulo
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
