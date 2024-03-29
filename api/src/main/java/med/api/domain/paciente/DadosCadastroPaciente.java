package med.api.domain.paciente;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.api.domain.endereco.DadosEndereco;

public record DadosCadastroPaciente(
        @NotBlank // Exige que o nome não seja nulo e nem vazio
        String nome,
        @NotBlank // Exige que o email não seja nulo e nem vazio
        @Email // Exige que o email tem que estar em formato de email "@xxxx.com"
        String email,
        @NotBlank // Exige que o telefone não seja nulo e nem vazio
        @Pattern(regexp = "\\d{11}") // Exige que o telefone tenha 11 números
        String telefone,
        @NotBlank // Exige que o cpf não seja nulo e nem vazio
        @Pattern(regexp = "\\d{3}\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}") // Exige que o cpf seja escrito em formato de cpf "000.000.000-00"
        String cpf,
        @NotNull // Exige que o endereco não seja nulo
        @Valid // Faz uma validação a mais conforme as anotações da classe Endereco
        DadosEndereco endereco
) {
}
