package com.rchuchon.springboot.form.app.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.rchuchon.springboot.form.app.models.domain.Usuario;


@Controller
public class FormController {

	
	@GetMapping("/form")
	public String form(Model model) {
		/// solucion 1: creamos el objeto usuario en el form por defecto para que no genere la excepcion
		Usuario usuario = new Usuario();
		model.addAttribute("titulo", "Formulario usuarios");
		//model.addAttribute("user", usuario);
		model.addAttribute("usuario", usuario);
		return "form";
	}
	
	@PostMapping("/form-version-antigua")
	public String procesarAntigua(Model model, 
			@RequestParam(name="username") String username, ///es opcional (name="username")
			@RequestParam String password, 
			@RequestParam String email) {
		
		Usuario usuario = new Usuario(); /// esto es una clase pojo osea un clase entity ya que representa los datos de nuestra aplicacion que estan guardados en alguna parte. solamente persistenm se guardan. 
		usuario.setUsername(username);
		usuario.setPassword(password);
		usuario.setEmail(email);
		
		model.addAttribute("titulo", "Resultado del form");
		model.addAttribute("usuario", usuario);
		
		return "resultado";
	}
	
	///Para validar es muy importante agregar la siguiente dependencia en el pom.xml del proyecto Spring Boot
	///Muy importante, es una actualización desde la versión de Spring Boot 2.3.0 o superior, agregar esta dependencia para validar!
	///BindingResult es propio del spring y esta se inyecta cuando tiene la notacion @valid y tiene que pasarse como parametro justo despues de objeto a manipular
	///@ModelAttribute se puede cambiar el objeto de cara a la vista 
	@PostMapping("/form")
	//public String procesar(@Valid @ModelAttribute("user") Usuario usuario, BindingResult result, Model model) {
	public String procesar(@Valid Usuario usuario, BindingResult result, Model model) {

		model.addAttribute("titulo", "Resultado del form");
		
		if (result.hasErrors()) {
			
			Map<String, String> errores = new HashMap<String, String>();
			result.getFieldErrors().forEach(err -> {
					errores.put(err.getField(), 
					"El campo ".concat(err.getField()).concat(" ").concat(err.getDefaultMessage())); 
			});
			model.addAttribute("error", errores);
			return "form";
		}
		
		
		model.addAttribute("usuario", usuario);
		
		return "resultado";
	}
	
}
