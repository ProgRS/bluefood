package br.com.softblue.bluefood.infrastructure.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import br.com.softblue.bluefood.application.service.ImageService;
import br.com.softblue.bluefood.domain.pedido.Carrinho;
import br.com.softblue.bluefood.domain.pedido.RestauranteDiferenteException;
import br.com.softblue.bluefood.domain.restaurante.ItemCardapio;
import br.com.softblue.bluefood.domain.restaurante.ItemCardapioRepository;

@Controller
@RequestMapping("/cliente/carrinho")
@SessionAttributes("carrinho")
public class CarrinhoController {
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;
	
	@ModelAttribute("carrinho")
	public Carrinho carrinho() {
		return new Carrinho();
	}
	
	
	@GetMapping(path ="/adicionar")
	public String adicionarItem(
			@RequestParam("itemId") Integer itemId,
			@RequestParam("quantidade") Integer quantidade,
			@RequestParam("observacoes") String observacoes,
			@ModelAttribute("carrinho") Carrinho carrinho,
			 Model model) {
		
		ItemCardapio itemCardapio = itemCardapioRepository.findById(itemId).orElseThrow();
		try {
		carrinho.adicionarItem(itemCardapio, quantidade, observacoes);
		} catch(RestauranteDiferenteException e){
				model.addAttribute("msg" , "N?o ? possivel misturar comidas de restaurantes diferentes");	
		}
		return "cliente-carrinho";
	}
	
}
