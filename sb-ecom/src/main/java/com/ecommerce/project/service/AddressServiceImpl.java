package com.ecommerce.project.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.repository.AddressRepository;
import com.ecommerce.project.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public AddressDTO createAddress(AddressDTO addressDTO, User user) {
		Address address = modelMapper.map(addressDTO, Address.class);
		
		List<Address> addressList = user.getAddresses();
		addressList.add(address);
		user.setAddresses(addressList);
		
		address.setUser(user);
		
		Address savedAddress = addressRepository.save(address);
		return modelMapper.map(savedAddress, AddressDTO.class); 
	}

	@Override
	public List<AddressDTO> getAddresses() {
		List<Address> addresses = addressRepository.findAll();
		return addresses.stream().map(address -> modelMapper.map(addresses, AddressDTO.class)).toList();
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		Address address = addressRepository.findById(addressId)
				.orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));
		return modelMapper.map(address, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getUserAddresses(User user) {
		List<Address> addresses = user.getAddresses();
		return addresses.stream().map(address -> modelMapper.map(addresses, AddressDTO.class)).toList();
	}

	@Override
	public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
		Address addressFromDb = addressRepository.findById(addressId)
				.orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));
		addressFromDb.setCity(addressDTO.getCity());
		addressFromDb.setPincode(addressDTO.getPincode());
		addressFromDb.setState(addressDTO.getState());
		addressFromDb.setCountry(addressDTO.getCountry());
		addressFromDb.setStreet(addressDTO.getStreet());
		addressFromDb.setBuildingName(addressDTO.getBuildingName());
		
		Address updatedAddress = addressRepository.save(addressFromDb);
		
		User user = addressFromDb.getUser();
		user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
		user.getAddresses().add(updatedAddress);
		userRepository.save(user);
		
		return modelMapper.map(updatedAddress, AddressDTO.class);
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDb = addressRepository.findById(addressId)
				.orElseThrow(()-> new ResourceNotFoundException("Address", "addressId", addressId));
		
		User user = addressFromDb.getUser();
		user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
		userRepository.save(user);
		
		addressRepository.delete(addressFromDb);
		
		return "Address deleted successfully with addressId: " + addressId;
	} 

}
