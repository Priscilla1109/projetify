package service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import projetify.api.com.demo.exception.InvalidDataException;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;
import projetify.api.com.demo.service.ProjetoService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjetoServiceTests {
	@Mock
	private RepositoryProjeto repositoryProjeto; //cria uma instancia simulada da classe

	@InjectMocks
	private ProjetoService projetoService; //injeta as dependências da classe durante a execução do teste

	@Before
	public void setUp(){
		//pode ser usado para definir os atributos antes da execução de qualquer teste
	}

	@Test
	public void testCriarProjeto() {
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste");
		projetoRequest.setDescricao("testando projeto1");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest); //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?

		//Comportamento esperado do mock
		when(repositoryProjeto.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		Projeto projetoCriado = projetoService.criarProjeto(projetoRequest);

		//Realiza a verificação por quantidade de interação
		verify(repositoryProjeto, times(1)).save(ArgumentMatchers.any(Projeto.class)); //foi usado o 2 pois o método é chamado duas vezes

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals("Projeto Teste", projetoCriado.getNome());
	}

	@Test
	public void testBuscaPorIdExistente(){
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste");
		projetoRequest.setDescricao("testando projeto1");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest);

		//Comportamento esperado do mock
		when(repositoryProjeto.findById(1L)).thenReturn(Optional.of(projetoDomain));

		//Chama o método de busca
		Optional<Projeto> resultadoBusca = Optional.ofNullable(projetoService.buscarProjetoId(1L));

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals(Optional.of(projetoDomain), resultadoBusca);
	}

	@Test(expected = NoSuchElementException.class)
	public void testBuscaPorIdInexistetente (){
		//Comportamento esperado do mock
		when(repositoryProjeto.findById(2L)).thenReturn(Optional.empty());

		//Chama o método de busca
		Optional<Projeto> resultadoBusca = Optional.ofNullable(projetoService.buscarProjetoId(2L));

		//Verifica se o método do repository foi chamado com o Id correto
		verify(repositoryProjeto).findById(2L);

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals(Optional.empty(), resultadoBusca);
	}

	@Test
	public void testAtualizarProjeto(){
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste2");
		projetoRequest.setDescricao("testando projeto2");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest); //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?
		projetoDomain.setId(1L);

		//Simulação de atualização do projeto
		ProjetoRequest projetoAtualizado = new ProjetoRequest();
		projetoAtualizado.setNome("Projeto Atualizado2");
		projetoAtualizado.setDescricao("Descrição Atualizada");
		projetoAtualizado.setDataInicio("2024-04-01");
		projetoAtualizado.setDataFim("2024-04-10");
		Projeto projetoDomainAtualizado = MapperProjeto.toDomain(projetoAtualizado);
		projetoDomainAtualizado.setId(1L);

		//Chama o método para atualizar o projeto da Controller
		projetoService.atualizarProjeto(1L, projetoDomainAtualizado);

		//Realiza a verificação por quantidade de interação
		assertEquals(projetoDomain.getId(), projetoDomainAtualizado.getId());
	}

	@Test (expected = NoSuchElementException.class)
	public void testDeletarProjeto() {
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste");
		projetoRequest.setDescricao("testando projeto1");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest); //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?
		projetoDomain.setId(1L);

		//Comportamento esperado do mock
		when(repositoryProjeto.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		//Ação do método
		projetoService.deletarProjeto(1l);

		//Realiza a verificação por quantidade de interação
		verify(repositoryProjeto, times(1)).save(ArgumentMatchers.any(Projeto.class)); //foi usado o 2 pois o método é chamado duas vezes

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals("O projeto com esse ID não existe!", projetoDomain.getId());
	}

	@Test(expected = InvalidDataException.class)
	public void testDataInicioIncorreta(){
		ProjetoRequest projeto = new ProjetoRequest();
		projeto.setDataInicio("2024-04-10");
		projeto.setDataFim("2024-04-01");
		Projeto projetoDomain = MapperProjeto.toDomain(projeto);

		when(repositoryProjeto.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		//Ação do método
		projetoService.criarProjeto(projeto);

		//Realiza a verificação por quantidade de interação
		verify(repositoryProjeto, times(1)).save(ArgumentMatchers.any(Projeto.class));
	}
}
