package projetify.api.com.demo;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import projetify.api.com.demo.ProjetifyApplication;

@SpringBootTest(classes =ProjetifyApplication.class) //especifica a classe principal da aplicação
public class ProjetifyApplicationTest {
    @Test
    public void contextLoads(){
        //teste vazio apenas para garantir que o contexto da aplicação seja carregado corretamente
    }
}
