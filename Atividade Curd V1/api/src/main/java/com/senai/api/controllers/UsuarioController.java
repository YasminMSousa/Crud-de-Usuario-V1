package com.senai.api.controllers;

import com.senai.api.dtos.RespostaUsuarioDto;
import com.senai.api.dtos.UsuarioDto;
import com.senai.api.entities.UsuarioEntity;
import com.senai.api.services.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UsuarioController {

    private final UsuarioService service;

    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @PostMapping("/usuario")
    public ResponseEntity<String> cadastrar(@RequestBody UsuarioDto dto) {
        return service.cadastrar(dto) ? ResponseEntity.ok("Cadastrado com sucesso") : ResponseEntity.badRequest().body("Login já existe");
    }

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioEntity>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/usuario/{cpf}")
    public ResponseEntity<Object> buscar(@PathVariable String cpf) {
        UsuarioEntity u = service.buscarPorCpf(cpf);
        if (u == null) return ResponseEntity.status(404).body("Usuário não encontrado");

        RespostaUsuarioDto res = new RespostaUsuarioDto();
        res.setCpf(u.getCpf());
        res.setNome(u.getNome());
        res.setLogin(u.getLogin());
        return ResponseEntity.ok(res);
    }

    @PutMapping("/usuario/{cpf}")
    public ResponseEntity<String> atualizar(@PathVariable String cpf, @RequestBody UsuarioDto dto) {
        return service.atualizar(cpf, dto) ? ResponseEntity.ok("Atualizado com sucesso") : ResponseEntity.badRequest().body("Erro ao atualizar (Login duplicado ou CPF não existe)");
    }

    @DeleteMapping("/usuario/{cpf}")
    public ResponseEntity<String> remover(@PathVariable String cpf) {
        return service.remover(cpf) ? ResponseEntity.ok("Removido com sucesso") : ResponseEntity.status(404).body("Usuário não encontrado");
    }
}