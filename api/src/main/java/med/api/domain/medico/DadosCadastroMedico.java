package med.api.domain.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.api.domain.endereco.DadosEndereco;

public record DadosCadastroMedico(
        @NotBlank // Exige que o nome não seja nulo e nem vazio
        String nome,
        @NotBlank // Exige que o telefone não seja nulo e nem vazio
        @Pattern(regexp = "\\d{11}") // Exige que o telefone tenha 11 números
        String telefone,
        @NotNull // Exige que o email não pode ser nulo
        @Email // Exige que o email tem que estar em formato de email "@xxxx.com"
        String email,
        @NotNull // Indica que o ‘id’ não seja nulo
        @Pattern(regexp = "\\d{4,6}") // Exige que o crm seja escrito com 6 números
        String crm,
        @NotNull // Exige que a especialidade não pode ser nulo
        Especialidade especialidade,
        @NotNull // Exige que o endereco não seja nulo
        @Valid // Faz uma validação a mais conforme as anotações da classe Endereco
        DadosEndereco endereco) {
}
