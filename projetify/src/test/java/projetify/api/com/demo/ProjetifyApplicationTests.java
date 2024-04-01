package projetify.api.com.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import projetify.api.com.demo.mapper.MapperProjeto;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.RepositoryProjeto;
import projetify.api.com.demo.service.ProjetoService;

import java.util.NoSuchElementException;
import java.util.Optional;

import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjetifyApplicationTests {
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
		verify(repositoryProjeto, times(2)).save(ArgumentMatchers.any(Projeto.class)); //foi usado o 2 pois o método é chamado duas vezes

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

	@Test
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

		//Comportamento esperado do mock
		when(repositoryProjeto.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		//Chama o método para atualizar o projeto
		Projeto projetoAtualizado = projetoService.atualizarProjeto(1L, projetoDomain);

		//Realiza a verificação por quantidade de interação
		verify(repositoryProjeto, times(1)).save(ArgumentMatchers.any(Projeto.class));
	}

	@Test
	public void testDeletarProjeto() {
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste2");
		projetoRequest.setDescricao("testando projeto2");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = MapperProjeto.toDomain(projetoRequest); //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?
		projetoDomain.setId(1L);

		doThrow(new NoSuchElementException(null)).when(repositoryProjeto).deleteById(1L);

		//Execução do método a ser testado
		Exception ex = assertThrows(NoSuchElementException.class, ()->{
			//Comportamento esperado do mock
			projetoService.deletarProjeto(projetoDomain.getId());
		});

		//Verificação da exceção lançada
		assertEquals(null, ex.getMessage());

		//Realiza a verificação por quantidade de interação
		verify(repositoryProjeto, times(2)).deleteById(projetoDomain.getId());
	}
}
