package projetify.api.com.demo.exception;

//Classe para representar uma exceção personalizada
public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String mensagem){
        super(mensagem);
    }
}
