package br.com.alura.loja.testes;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.alura.loja.dao.CategoriaDAO;
import br.com.alura.loja.dao.ClienteDAO;
import br.com.alura.loja.dao.PedidoDAO;
import br.com.alura.loja.dao.ProdutoDAO;
import br.com.alura.loja.modelo.Categoria;
import br.com.alura.loja.modelo.Cliente;
import br.com.alura.loja.modelo.Pedido;
import br.com.alura.loja.modelo.Produto;
import br.com.alura.loja.modelo.itemPedido;
import br.com.alura.loja.util.JPAUtil;
import br.com.alura.loja.vo.RelatorioDeVendaVo;

public class CadastroDePedido {

	public static void main(String[] args) {

		popularBancoDeDados();
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		
		Produto produto = new ProdutoDAO(em).buscarPorId(1l);
		Produto produto2 = new ProdutoDAO(em).buscarPorId(2l);
		Produto produto3 = new ProdutoDAO(em).buscarPorId(3l);
		
		Cliente cliente = new ClienteDAO(em).buscarPorId(1l);
		
		em.getTransaction().begin();
		
		Pedido pedido = new Pedido(cliente );
		pedido.adicionarItem(new itemPedido(10, pedido, produto));
		pedido.adicionarItem(new itemPedido(40, pedido, produto2));
		
		Pedido pedido2 = new Pedido(cliente );
		pedido.adicionarItem(new itemPedido(2, pedido, produto3));
		
		PedidoDAO pedidoDao = new PedidoDAO(em);
		pedidoDao.cadastrar(pedido);
		pedidoDao.cadastrar(pedido2);
		
		em.getTransaction().commit();
		
		BigDecimal totalVendido = pedidoDao.valorTotalVendido();
		System.out.println("Vendido: " + totalVendido);
		
		List<RelatorioDeVendaVo> relatorio = pedidoDao.relatorioDeVendas();
		relatorio.forEach(System.out::println);
	}
	
	private static void popularBancoDeDados() {
		Categoria celulares = new Categoria("CELULARES");
		Categoria videogames = new Categoria("VIDEOGAMES");
		Categoria informatica = new Categoria("INFORMATICA");
		
		Produto celular = new Produto("Xiaomi Redmi", "Muito legal", new BigDecimal("800.00"), celulares );
		Produto videogame = new Produto("PS5", "Playstation 5", new BigDecimal("2000.00"), videogames );
		Produto macbook = new Produto("Macbook", "Macbook Pro", new BigDecimal("28000.00"), informatica );
		
		
		Cliente cliente = new Cliente("Dara", "789456");
		
		EntityManager em = JPAUtil.getEntityManager();
		ProdutoDAO produtoDao = new ProdutoDAO(em);
		CategoriaDAO categoriaDao = new CategoriaDAO(em);
		ClienteDAO clienteDao = new ClienteDAO(em);
		
		em.getTransaction().begin();
		
		categoriaDao.cadastrar(celulares);
		categoriaDao.cadastrar(videogames);
		categoriaDao.cadastrar(informatica);
		
		produtoDao.cadastrar(celular);
		produtoDao.cadastrar(videogame);
		produtoDao.cadastrar(macbook);
		
		
		clienteDao.cadastrar(cliente);
		
		em.getTransaction().commit();
		em.close();
	}

}
