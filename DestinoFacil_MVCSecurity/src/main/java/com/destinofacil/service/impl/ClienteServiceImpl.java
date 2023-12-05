package com.destinofacil.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.destinofacil.dto.ClienteDto;
import com.destinofacil.entity.Cliente;
import com.destinofacil.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void saveCliente(ClienteDto clienteDto) {
		Cliente cliente = new Cliente();

		cliente.setNome(clienteDto.getNome());
		cliente.setEmail(clienteDto.getEmail());
		cliente.setSenha(passwordEncoder.encode(clienteDto.getSenha()));

		clienteRepository.save(cliente);
	}

	@Override
	public Cliente findClienteByEmail(String email) {
		return clienteRepository.findByEmail(email);
	}

	@Override
	public List<ClienteDto> findAllClientes() {
		List<Cliente> clientes = clienteRepository.findAll();
		return clientes.stream()
				.map((cliente) -> mapToClienteDto(cliente))
				.collect(Collectors.toList());
	}

	private ClienteDto mapToClienteDto(Cliente cliente) {
		ClienteDto clienteDto = new ClienteDto();
		clienteDto.setId(cliente.getId());
	    clienteDto.setNome(cliente.getNome());
	    clienteDto.setSenha(cliente.getSenha());
		clienteDto.setEmail(cliente.getEmail());
		return clienteDto;
	}
}