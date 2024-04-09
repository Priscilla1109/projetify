package projetify.api.com.demo.util;

import org.apache.commons.lang3.RandomStringUtils;
import projetify.api.com.demo.model.ProjetoRequest;

import java.time.LocalDate;

import static java.util.Objects.isNull;

public class ProjetoRequestFixture {

    private ProjetoRequest projetoRequest;

    public static ProjetoRequestFixture get() {
        return new ProjetoRequestFixture();
    }

    public ProjetoRequestFixture withRandomData() {
        if (isNull(projetoRequest)) {
            projetoRequest = new ProjetoRequest();
        }

        projetoRequest.setId(Long.parseLong(RandomStringUtils.randomNumeric(2)));
        projetoRequest.setNome(RandomStringUtils.randomAlphabetic(10));
        projetoRequest.setDescricao(RandomStringUtils.randomAlphabetic(20));
        projetoRequest.setDataInicio(LocalDate.now());
        projetoRequest.setDataFim(LocalDate.now().plusDays(1));

        return this;
    }

    public ProjetoRequestFixture withRandomData_ErrorData() {
        if (isNull(projetoRequest)) {
            projetoRequest = new ProjetoRequest();
        }

        projetoRequest.setId(Long.parseLong(RandomStringUtils.randomNumeric(2)));
        projetoRequest.setNome(RandomStringUtils.randomAlphabetic(10));
        projetoRequest.setDescricao(RandomStringUtils.randomAlphabetic(20));
        projetoRequest.setDataInicio(LocalDate.now().plusDays(1));
        projetoRequest.setDataFim(LocalDate.now());

        return this;
    }

    public ProjetoRequest build() {
        return projetoRequest;
    }

    public ProjetoRequestFixture withId(long value) {
        if (isNull(projetoRequest)) {
            projetoRequest = new ProjetoRequest();
        }

        projetoRequest.setId(value);

        return this;
    }
}
