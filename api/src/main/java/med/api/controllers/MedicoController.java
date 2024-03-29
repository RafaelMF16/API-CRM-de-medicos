package med.api.controllers;

import jakarta.validation.Valid;
import med.api.domain.entities.Medico;
import med.api.domain.medico.DadosAtualizarMedico;
import med.api.domain.medico.DadosCadastroMedico;
import med.api.domain.medico.DadosDetalhamentoMedico;
import med.api.domain.medico.DadosListagemMedico;
import med.api.domain.repositories.MedicoRepository;
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
    private MedicoRepository medicoRepository; // Representa os médicos no banco de dados

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody @Valid DadosCadastroMedico dados, UriComponentsBuilder uriBuilder){
        var medico = new Medico(dados); // Cria um médico com os dados passados no parâmetro
        medicoRepository.save(medico); // Salva o médico criado no banco de dados
        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); // Cria uma, uri para poder acessar o médico no banco de dados

        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico)); // Retorna o médico, seus dados e o código 201 Created caso tenha sucesso no cadastro
    }

    @GetMapping
    public ResponseEntity <Page<DadosListagemMedico>> list(@PageableDefault(size = 5, sort = {"crm"}) Pageable pagina){
        var page = medicoRepository.findAllByAtivoTrue(pagina).map(DadosListagemMedico::new); // Cria uma página apenas com os médicos ativos

        return ResponseEntity.ok(page); // Retorna uma página com 5 médicos e código 200 Ok caso tenha sucesso
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DadosAtualizarMedico dados){
        var medico = medicoRepository.getReferenceById(dados.id()); // Guarda o médico que terá os dados atualizados
        medico.atualizarInformacoes(dados); // Atualiza os dados do médico

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); // Retorna os dados do médico atualizados e o código 200 Ok caso tenha sucesso
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id); // Guarda o médico que será inativado
        medico.inativar(); // Inativa o médico

        return ResponseEntity.noContent().build(); // Retorna o código 204 No Content caso tenha sucesso
    }

    @GetMapping("/{id}")
    public ResponseEntity detail(@PathVariable Long id){
        var medico = medicoRepository.getReferenceById(id); // Guarda o médico que será detalhado

        return ResponseEntity.ok(new DadosDetalhamentoMedico(medico)); // Retorna os dados do médico e código 200 Ok caso tenha sucesso
    }

}
