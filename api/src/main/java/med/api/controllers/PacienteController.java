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
    public PacienteRepository pacienteRepository;

    @PostMapping
    @Transactional
//  O Método save é chamado quando um cadastro é requisitado, salvando um novo paciente no banco de dados;
    public ResponseEntity save(@RequestBody @Valid DadosCadastroPaciente dados, UriComponentsBuilder uriBuilder){
        var paciente = new Paciente(dados);
        pacienteRepository.save(paciente);
        var uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoPaciente(paciente));
    }

    @GetMapping
//  O Método list é chamado quando uma listagem de pacientes é requisitada, listando 5 pacientes;
    public ResponseEntity<Page<DadosListagemPaciente>> list(@PageableDefault(size = 5, sort = {"nome"}) Pageable pagina){
        var page = pacienteRepository.findAllByAtivoTrue(pagina).map(DadosListagemPaciente::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
//  O Método update é chamado quando uma atualização é requisitada, atualizando algum dado do paciente passado como parâmetro;
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarPaciente dados){
        var paciente = pacienteRepository.getReferenceById(dados.id());
        paciente.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
//  O Método delete é chamado quando uma exclusão de paciente é requisitada,o delete é lógico,
//  portanto não deleta o paciente do banco de dados, apenas o desativa;
    public ResponseEntity delete(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);
        paciente.inativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
//  O Método detail é chamado quando um detalhamento é requisitado, detalhando o paciente passado como parâmetro;
    public ResponseEntity detail(@PathVariable Long id){
        var paciente = pacienteRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoPaciente(paciente));
    }
}
