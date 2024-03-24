package med.api.controllers;

import jakarta.validation.Valid;
import med.api.models.entities.Medico;
import med.api.models.medico.DadosAtualizarMedico;
import med.api.models.medico.DadosCadastroMedico;
import med.api.models.medico.DadosDetalhamentoMedico;
import med.api.models.medico.DadosListagemMedico;
import med.api.models.repositories.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RequestMapping("/medicos")
@RestController
public class MedicoController {

    @Autowired
    private MedicoRepository medicoRepository;

    @PostMapping
    @Transactional
//  O Método save é chamado quando um cadastro é requisitado, salvando um novo médico no banco de dados;
    public ResponseEntity save(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados);
        medicoRepository.save(medico);
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

    @GetMapping
//  O Método list é chamado quando uma listagem de médicos é requisitada, listando 5 médicos;
    public ResponseEntity <Page<DadosListagemMedico>> list(@PageableDefault(size = 5, sort = {"crm"}) Pageable pagina){
        var page = medicoRepository.findAllByAtivoTrue(pagina).map(DadosListagemMedico::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
//  O Método update é chamado quando uma atualização é requisitada, atualizando algum dado do médico passado como parâmetro;
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id());
        medico.atualizarInformacoes(dados);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    @DeleteMapping("/{id}")
    @Transactional
//  O Método delete é chamado quando uma exclusão de médico é requisitada,o delete é lógico,
//  portanto não deleta o médico do banco de dados, apenas o desativa;
    public ResponseEntity delete(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);
        medico.inativar();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
//  O Método detail é chamado quando um detalhamento é requisitado, detalhando o médico passado como parâmetro;
    public ResponseEntity detail(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id);

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

}
