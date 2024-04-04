package model;

import org.junit.Test;
import projetify.api.com.demo.model.Meta;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoPageResponse;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjetoPageResponseTest {
    @Test
    public void testGetters(){
        List<Projeto> projetos = new ArrayList<>();
        Meta meta = new Meta(1,10,3,30);
        ProjetoPageResponse response = new ProjetoPageResponse();

        response.setProjetos(projetos);
        response.setMeta(meta);

        assertEquals(projetos, response.getProjetos());
        assertEquals(meta, response.getMeta());
    }
}
