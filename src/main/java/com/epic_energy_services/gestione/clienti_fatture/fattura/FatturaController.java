package com.epic_energy_services.gestione.clienti_fatture.fattura;

import java.math.BigDecimal;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.epic_energy_services.gestione.clienti_fatture.cliente.Cliente;
import com.epic_energy_services.gestione.clienti_fatture.cliente.ClienteService;

@RestController
@RequestMapping("/fatture")
public class FatturaController {

	@Autowired FatturaService fatturaServ;
	@Autowired ClienteService clienteServ;
	
	
	@GetMapping
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<Fattura>> getAllFatture(@RequestParam(value = "pagina",  defaultValue = "0") int pagina, @RequestParam(value = "dim", defaultValue = "5") int dim){
		return ResponseEntity.ok(fatturaServ.findAll());
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Fattura> getFatturaById(@PathVariable Long id){
		return ResponseEntity.ok(fatturaServ.findById(id));
	}
	
	@PostMapping("/{idCliente}/aggiungi")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Fattura> addFattura(@RequestBody Fattura fattura,
			@PathVariable Long idCliente){
		Cliente c = clienteServ.findById(idCliente);
		fattura.setCliente(c);
		fatturaServ.createFattura(fattura);
		
		return ResponseEntity.ok(fattura);
	}
	
	@PutMapping("/modifica")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> modificaFattura(@RequestBody Fattura fattura){
		ResponseEntity.ok(fatturaServ.updateFattura(fattura));
		return ResponseEntity.ok("Fattura Aggiornata!");
	}
	
	@DeleteMapping()
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> eliminaFattura(@RequestBody Fattura fattura){
		fatturaServ.removeFattura(fattura);
		return ResponseEntity.ok("Fattura Cancellata!");
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> eliminaFatturaById(@PathVariable Long id){
		fatturaServ.removeFatturaById(id);
		return ResponseEntity.ok("Fattura Cancellata!");
	}
	
	@GetMapping("/filtra/cliente={id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<Fattura>> trovaFatturaByCliente(@PathVariable Long id, @RequestParam(value = "pagina",  defaultValue = "0") int pagina, @RequestParam(value = "dim", defaultValue = "5") int dim){
		return ResponseEntity.ok(fatturaServ.getFatturaByIdCliente(id,pagina, dim));
	}
	
	@GetMapping("/filtra/stato={stato}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<Fattura>> trovaFatturaByStato(@PathVariable StatoFattura stato,@RequestParam(value = "pagina",  defaultValue = "0") int pagina, @RequestParam(value = "dim", defaultValue = "5") int dim){
		return ResponseEntity.ok(fatturaServ.getFatturaByStatoFattura(stato,pagina, dim));
	}
	
	@GetMapping("/filtra/anno={anno}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<Fattura>> trovaFatturaByAnno(@PathVariable Integer anno, @RequestParam(value = "pagina",  defaultValue = "0") int pagina, @RequestParam(value = "dim", defaultValue = "5") int dim){
		return ResponseEntity.ok(fatturaServ.getFatturaByAnno(anno,pagina, dim));
	}
	
	@GetMapping("/filtra")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<Fattura>> trovaFatturaByImportoRange(
			@RequestParam String imp1,@RequestParam String imp2, @RequestParam(value = "pagina",  defaultValue = "0") int pagina, @RequestParam(value = "dim", defaultValue = "5") int dim){
		return ResponseEntity.ok(fatturaServ.getFatturaByRangeImport(new BigDecimal(imp1), new BigDecimal(imp2),pagina, dim));
		}

//	@GetMapping("/filtra/importo1?&")
//	@PreAuthorize("hasRole('USER')")
//	public ResponseEntity<String> trovaFatturaByImportoRange(
//			@RequestParam BigDecimal imp1,@RequestParam BigDecimal imp2){
//		return new ResponseEntity<String>("asd", HttpStatus.OK);}
	
	@GetMapping("/filtra/data={data}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<Page<Fattura>> trovaFatturaByData(@PathVariable Date data, @RequestParam(value = "pagina",  defaultValue = "0") int pagina, @RequestParam(value = "dim", defaultValue = "5") int dim){
		return ResponseEntity.ok(fatturaServ.getFatturaByData(data,pagina, dim));
	}
	
	
	@PostMapping("/cliente")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addCliente(@RequestBody Cliente cliente){
		ResponseEntity.ok(clienteServ.createCliente(cliente));
		return ResponseEntity.ok("Cliente Creata!");
	}
}