package model;

import org.junit.Test;
import projetify.api.com.demo.model.Meta;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MetaTest {
    @Test
    public void testConstrutorMeta(){
        int page = 1;
        int pageSize = 10;
        int totalPages = 3;
        long totalRecors = 30;
        Meta meta = new Meta(page, pageSize, totalPages, totalRecors);

        assertEquals(page, meta.getPage());
        assertEquals(pageSize, meta.getPageSize());
        assertEquals(totalPages, meta.getTotalPage());
        assertEquals(totalRecors, meta.getPageRecors());
    }
}
