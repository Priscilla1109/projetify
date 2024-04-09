package projetify.api.com.demo.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import projetify.api.com.demo.exception.ExistentProjectException;
import projetify.api.com.demo.exception.InvalidDataException;
import projetify.api.com.demo.mapper.ProjetoMapper;
import projetify.api.com.demo.model.Projeto;
import projetify.api.com.demo.model.ProjetoRequest;
import projetify.api.com.demo.repository.ProjetoRepository;
import projetify.api.com.demo.service.ProjetoService;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class ProjetoServiceTests {
	@Mock
	private ProjetoRepository projetoRepository; //cria uma instancia simulada da classe

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
		Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest); //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?

		//Comportamento esperado do mock
		when(projetoRepository.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		Projeto projetoCriado = projetoService.criarProjeto(projetoRequest);

		//Realiza a verificação por quantidade de interação
		verify(projetoRepository, times(1)).save(ArgumentMatchers.any(Projeto.class)); //foi usado o 2 pois o método é chamado duas vezes

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals("Projeto Teste", projetoCriado.getNome());
	}

	@Test(expected = ExistentProjectException.class)
	public void testCriarProjetoExistente() {
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setId(1L);
		projetoRequest.setNome("Nome1");
		projetoRequest.setDescricao("Descrição1");
		projetoRequest.setDataInicio("2024-04-08");
		projetoRequest.setDataFim("2024-04-12");

		//Comportamento esperado do mock, para retornar um projeto existente
		when(projetoRepository.existsById(projetoRequest.getId())).thenReturn(true);

		projetoService.criarProjeto(projetoRequest);
	}

	@Test
	public void testListarProjetos(){
		ProjetoRequest projetoRequest1 = new ProjetoRequest();
		projetoRequest1.setId(1L);
		projetoRequest1.setNome("Nome1");
		projetoRequest1.setDescricao("Descrição1");
		projetoRequest1.setDataInicio("2024-04-08");
		projetoRequest1.setDataFim("2024-04-12");
		Projeto projeto1 = ProjetoMapper.toDomain(projetoRequest1);

		ProjetoRequest projetoRequest2 = new ProjetoRequest();
		projetoRequest2.setId(2L);
		projetoRequest2.setNome("Nome2");
		projetoRequest2.setDescricao("Descrição2");
		projetoRequest2.setDataInicio("2024-04-08");
		projetoRequest2.setDataFim("2024-04-12");
		Projeto projeto2 = ProjetoMapper.toDomain(projetoRequest2);

		//simulação da criação da lista de projetos paginada
		List<Projeto> projetos = Arrays.asList(projeto1, projeto2);
		Page<Projeto> projetoPage = new PageImpl<>(projetos);

		//Simula comportamento do método
		when(projetoService.listarProjetosPaginados(0, 10)).thenReturn(projetoPage);

		//Chama método de listagem
		Page<Projeto> resultadoLista = projetoService.listarProjetosPaginados(0,10);

		//Verifica se o restorno está de acordo com o esperado
		assertEquals(projetoPage, resultadoLista);
	}

	@Test
	public void testBuscaPorIdExistente(){
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste");
		projetoRequest.setDescricao("testando projeto1");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest);

		//Comportamento esperado do mock
		when(projetoRepository.findById(1L)).thenReturn(Optional.of(projetoDomain));

		//Chama o método de busca
		Optional<Projeto> resultadoBusca = Optional.ofNullable(projetoService.buscarProjetoId(1L));

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals(Optional.of(projetoDomain), resultadoBusca);
	}

	@Test(expected = NoSuchElementException.class)
	public void testBuscaPorIdInexistetente (){
		//Comportamento esperado do mock
		when(projetoRepository.findById(2L)).thenReturn(Optional.empty());

		//Chama o método de busca
		Optional<Projeto> resultadoBusca = Optional.ofNullable(projetoService.buscarProjetoId(2L));

		//Verifica se o método do repository foi chamado com o Id correto
		verify(projetoRepository).findById(2L);

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals(Optional.empty(), resultadoBusca);
	}

	@Test
	public void testAtualizarProjeto(){
		ProjetoRequest projetoExistente = new ProjetoRequest();
		projetoExistente.setId(1L);
		projetoExistente.setNome("Projeto Teste2");
		projetoExistente.setDescricao("testando projeto2");
		projetoExistente.setDataInicio("2024-03-31");
		projetoExistente.setDataFim("2024-04-04");
		Projeto projetoDomain = ProjetoMapper.toDomain(projetoExistente);  //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?

		//Simulação de atualização do projeto
		ProjetoRequest projetoAtualizado = new ProjetoRequest();
		projetoAtualizado.setNome("Projeto Atualizado2");
		projetoAtualizado.setDescricao("Descrição Atualizada");
		projetoAtualizado.setDataInicio("2024-04-01");
		projetoAtualizado.setDataFim("2024-04-10");
		Projeto projetoDomainAtualizado = ProjetoMapper.toDomain(projetoAtualizado);
		projetoDomainAtualizado.setId(1L);

		//Verificar se o projeto existe
		when(projetoRepository.existsById(projetoDomain.getId())).thenReturn(true);
		//when(projetoRepository.findById(projetoDomainAtualizado.getId())).thenReturn(Optional.of(projetoDomain));

		//Simular o salvamento do projetoAtualizado
		when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoDomainAtualizado);

		//Chamar o método
		Projeto projetoAtualizadoRetornado = projetoService.atualizarProjeto(projetoDomainAtualizado.getId(), projetoDomainAtualizado);

		//Verificar se o retorno está conforme o esperado
		assertEquals(projetoDomainAtualizado, projetoAtualizadoRetornado);
	}

	@Test(expected = NoSuchElementException.class)
	public void testAtualizarProjetoInexistente(){
		//Verificar se o projeto existe
		when(projetoRepository.existsById(1L)).thenReturn(false);
		//when(projetoRepository.findById(1L)).thenReturn(Optional.empty());

		//Chamar o método
		Projeto projetoInexistente = projetoService.atualizarProjeto(1L, any(Projeto.class));

		//Verificar se o retorno está conforme o esperado
		assertEquals(Optional.empty(), projetoInexistente);
	}

	@Test(expected = NoSuchElementException.class)
	public void testDeletarProjeto() {
		ProjetoRequest projetoRequest = new ProjetoRequest();
		projetoRequest.setNome("Projeto Teste");
		projetoRequest.setDescricao("testando projeto1");
		projetoRequest.setDataInicio("2024-03-31");
		projetoRequest.setDataFim("2024-04-04");
		Projeto projetoDomain = ProjetoMapper.toDomain(projetoRequest); //realiza a conervsão dos setters -- por que devo colocar a conversão aqui sendo que ja faz no metodo criarProjeto?
		projetoDomain.setId(1L);

		//Comportamento esperado do mock
		//when(projetoRepository.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		//Ação do método
		projetoService.deletarProjeto(1l);

		//Realiza a verificação por quantidade de interação
		verify(projetoRepository, times(1)).save(ArgumentMatchers.any(Projeto.class)); //foi usado o 2 pois o método é chamado duas vezes

		//Verifica se o resultado retornado é igual ao esperado
		assertEquals("O projeto com esse ID não existe!", projetoDomain.getId());
	}

	@Test(expected = InvalidDataException.class)
	public void testDataInicioIncorreta(){
		ProjetoRequest projeto = new ProjetoRequest();
		projeto.setDataInicio("2024-04-10");
		projeto.setDataFim("2024-04-01");
		Projeto projetoDomain = ProjetoMapper.toDomain(projeto);

		//when(projetoRepository.save(ArgumentMatchers.any(Projeto.class))).thenReturn(projetoDomain);

		//Ação do método
		projetoService.criarProjeto(projeto);

		//Realiza a verificação por quantidade de interação
		verify(projetoRepository, times(1)).save(ArgumentMatchers.any(Projeto.class));
	}
}
