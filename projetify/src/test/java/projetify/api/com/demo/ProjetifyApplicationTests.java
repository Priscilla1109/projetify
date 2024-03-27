package projetify.api.com.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class ProjetifyApplicationTests {

	@Autowired
	private MockMvc mockMvc; //realiza solicitações http simuladas

	@Test
	public void testCriarProjeto() throws Exception{
		String requestBody = "{\"nome\": \"projeto1\", \"descricao\": \"criando funcionalidade xxxxx\", \"dataInicio\": \"2024-04-10\". \"dataFim\": \"2024-04-15\"}";
		mockMvc.perform(post("/APIs/projetify")
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isCreated());
	}

	@Test
	public void testAtualizarProjeto() throws Exception{
		String requestBody = "{\"nome\": \"NovoNome\", \"descricao\": \"NovoDescrição\", \"dataInicio\": \"2024-04-10\". \"dataFim\": \"2024-04-15\"}";
		mockMvc.perform(put("/APIs/projetify/1") //o 1 representa a realização da operação com um projeto que tenha aquele ID
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestBody))
				.andExpect(status().isOk());
	}

	@Test
	public void testDeletarProjeto() throws Exception{
		String requestBody = "{\"nome\": \"NovoNome\", \"descricao\": \"NovoDescrição\", \"dataInicio\": \"2024-04-10\". \"dataFim\": \"2024-04-15\"}";
		mockMvc.perform(delete("/APIs/projetify/1") //o 1 representa a realização da operação com um projeto que tenha aquele ID
						.contentType(MediaType.APPLICATION_JSON)
						.content(requestBody))
				.andExpect(status().isOk());
	}
}
