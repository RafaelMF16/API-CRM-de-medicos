package med.api.controllers;

import jakarta.validation.Valid;
import med.api.domain.entities.Paciente;
import med.api.domain.paciente.DadosAtualizarPaciente;
import med.api.domain.paciente.DadosCadastroPaciente;
import med.api.domain.paciente.DadosDetalhamentoPaciente;
import med.api.domain.paciente.DadosListagemPaciente;
import med.api.domain.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@RequestMapping("/pacientes")
@RestController
public class PacienteController {
    @Autowired
    public PacienteRepository pacienteRepository; // Representa os pacientes no banco de dados

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados); // Cria um paciente com os dados passados no parâmetro
        pacienteRepository.save(paciente); // Salva o paciente criado no banco de dados
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente).toUri(); // Cria uma, uri para poder acessar o paciente no banco de dados

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente)); // Retorna o paciente, os seus dados e código 201 Created caso tenha sucesso
    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemPaciente>> list(@PageableDefault(size = 5, sort = {"nome"}) Pageable pagina){
        var page = pacienteRepository.findAllByAtivoTrue(pagina).map(DadosListagemPaciente::new); // Cria uma página apenas com os pacientes ativos
        return ResponseEntity.ok(page); // Retorna a página com 5 pacientes e código 200 Ok caso tenha sucesso
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarPaciente dados){
        var paciente = pacienteRepository.getReferenceById(dados.id()); // Guarda o paciente que terá os dados atualizados
        paciente.atualizarInformacoes(dados); // Atuazliza os dados do paciente

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente)); // Retorna os dados do paciente atualizados e o código 200 Ok caso tenha sucesso
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id); // Guarda o paciente que será inativado
        paciente.inativar(); // Inativa o paciente

        return ResponseEntity.noContent().build(); // Retorna o código 204 No Content caso tenha sucesso
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id); // Guarda o paciente que será detalhado

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente)); // Retorna os dados do paciente e o código 200 Ok caso tenha sucesso
    }
}
