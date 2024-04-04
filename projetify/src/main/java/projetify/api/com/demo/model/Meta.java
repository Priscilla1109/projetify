package projetify.api.com.demo.model;

import lombok.Data;
//Essa classe irá representar os metadados da página
@Data
public class Meta {
    private int page;
    private int pageSize;
    private int totalPage;
    private long pageRecors;

    public Meta(int page, int pageSize, int totalPages, long totalElements) {
        this.page = page;
        this.pageSize = pageSize;
        this.totalPage = totalPages;
        this.pageRecors = totalElements;
    }
}
