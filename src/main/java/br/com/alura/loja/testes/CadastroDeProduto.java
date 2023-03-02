package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.CategoriaId;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.util.JPAUtil;

public class CadastroDeProduto {

	public static void main(String[] args) {
		cadastrarProduto();
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		
		Produto p = produtoDao.buscarPorId(1l);
		System.out.println(p.getPreco());
		
		List<Produto> todos = produtoDao.buscarTodos();
		todos.forEach(p2 -> System.out.println(p2.getNome()));
		
		List<Produto> porNome = produtoDao.buscarPorNomeDoProduto("Xiaomi Redmi");
		porNome.forEach(p2 -> System.out.println(p2.getNome()));
		
		List<Produto> porNomeCategoria = produtoDao.buscarPorNomeDaCategoria("CELULARES");
		porNomeCategoria.forEach(c -> System.out.println(c.getNome()));
		
		BigDecimal precoProduto = produtoDao.buscarPrecoPorNomeDoProduto("Xiaomi Redmi");
		System.out.println(precoProduto);
		
//		Teste Metodo de busca com Criteria API
		List<Produto> buscaDinamica = produtoDao.buscaPorParametrosComCriteria(null, new BigDecimal("800"), null);
		System.out.println(buscaDinamica);
		
	}

	private static void cadastrarProduto() {
		Categoria celulares = new Categoria("CELULARES");
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800"), celulares );
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		produtoDao.cadastrar(celular);
		
		em.getTransaction().commit();
		
		em.find(Categoria.class, new CategoriaId("CELULARES", "xpto"));
		
		em.close();
	}
}
