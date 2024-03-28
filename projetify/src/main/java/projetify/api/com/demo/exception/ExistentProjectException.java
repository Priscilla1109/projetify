package projetify.api.com.demo.exception;

//Classe para representar uma exceção personalizada
public class ExistentProjectException extends RuntimeException{
    public ExistentProjectException(String mensagem){
        super(mensagem);
    }
}
