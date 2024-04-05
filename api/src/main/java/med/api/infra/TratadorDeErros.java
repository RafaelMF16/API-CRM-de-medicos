package med.api.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404(){
        return ResponseEntity.notFound().build(); //  caso seja disparada uma exception notFound a API retornará um erro 404;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarErro400(MethodArgumentNotValidException exception){
        var erros = exception.getFieldErrors(); // Guarda os erros que foram capturados

        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList()); // caso seja disparada uma exception badRequest retornará um erro 400 e a lista de erros
    }

    private record DadosErroValidacao(String campo, String mensagem) {
        public DadosErroValidacao (FieldError error){
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
