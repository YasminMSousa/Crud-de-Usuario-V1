package com.senai.api.services;

import com.senai.api.dtos.UsuarioDto;
import com.senai.api.entities.UsuarioEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsuarioService {

    private List<UsuarioEntity> listaUsuario = new ArrayList<>();

    public boolean cadastrar(UsuarioDto dto) {
        for (UsuarioEntity u : listaUsuario) {
            if (u.getLogin().equals(dto.getLogin())) return false;
        }
        UsuarioEntity novo = new UsuarioEntity(dto.getCpf(), dto.getNome(), dto.getLogin(), dto.getSenha());
        listaUsuario.add(novo);
        return true;
    }

    public List<UsuarioEntity> listar() {
        return listaUsuario;
    }

    public UsuarioEntity buscarPorCpf(String cpf) {
        return listaUsuario.stream().filter(u -> u.getCpf().equals(cpf)).findFirst().orElse(null);
    }

    public boolean atualizar(String cpf, UsuarioDto dto) {
        UsuarioEntity usuario = buscarPorCpf(cpf);
        if (usuario == null) return false;

        for (UsuarioEntity u : listaUsuario) {
            if (u.getLogin().equals(dto.getLogin()) && !u.getCpf().equals(cpf)) return false;
        }

        usuario.setNome(dto.getNome());
        usuario.setLogin(dto.getLogin());
        usuario.setSenha(dto.getSenha());
        return true;
    }

    public boolean remover(String cpf) {
        return listaUsuario.removeIf(u -> u.getCpf().equals(cpf));
    }
}
