package med.api.models.paciente;

import jakarta.validation.constraints.NotNull;
import med.api.models.endereco.DadosEndereco;

public record DadosAtualizarPaciente(
        @NotNull
        Long id,
        String nome,
        String telefone,
        DadosEndereco endereco) {
}
