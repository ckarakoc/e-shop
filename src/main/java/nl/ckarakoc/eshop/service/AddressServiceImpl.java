package nl.ckarakoc.eshop.service;

import nl.ckarakoc.eshop.exception.ResourceNotFoundException;
import nl.ckarakoc.eshop.model.Address;
import nl.ckarakoc.eshop.model.User;
import nl.ckarakoc.eshop.payload.AddressDTO;
import nl.ckarakoc.eshop.repository.AddressRepository;
import nl.ckarakoc.eshop.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {
	private final ModelMapper mapper;
	private final AddressRepository addressRepository;
	private final UserRepository userRepository;

	public AddressServiceImpl(ModelMapper mapper, AddressRepository addressRepository, UserRepository userRepository) {
		this.mapper = mapper;
		this.addressRepository = addressRepository;
		this.userRepository = userRepository;
	}

	@Override
	public AddressDTO createAddress(AddressDTO addressDTO, User user) {
		Address address = mapper.map(addressDTO, Address.class);
		List<Address> addresses = user.getAddresses();
		addresses.add(address);
		user.setAddresses(addresses);

		address.setUser(user);
		Address saved = addressRepository.save(address);
		return mapper.map(saved, AddressDTO.class);
	}

	@Override
	public List<AddressDTO> getAddresses() {
		List<Address> addresses = addressRepository.findAll();
		return addresses.stream()
			.map(address -> mapper.map(address, AddressDTO.class))
			.toList();
	}

	@Override
	public AddressDTO getAddressById(Long addressId) {
		return mapper
			.map(
				addressRepository
					.findById(addressId)
					.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId)),
				AddressDTO.class
			);
	}

	@Override
	public List<AddressDTO> getUserAddresses(User user) {
		return user.getAddresses().stream().map((element) -> mapper.map(element, AddressDTO.class)).collect(Collectors.toList());
	}

	@Override
	public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
		Address addressFromDb = addressRepository.findById(addressId)
			.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		addressFromDb.updateFrom(addressDTO);
		Address saved = addressRepository.save(addressFromDb);

		User user = addressFromDb.getUser();
		user.getAddresses().removeIf(address -> address.getAddressId().equals(saved.getAddressId()));
		user.getAddresses().add(saved);
		userRepository.save(user);

		return mapper.map(saved, AddressDTO.class);
	}

	@Override
	public String deleteAddress(Long addressId) {
		Address addressFromDb = addressRepository.findById(addressId)
			.orElseThrow(() -> new ResourceNotFoundException("Address", "addressId", addressId));

		User user = addressFromDb.getUser();
		user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
		userRepository.save(user);

		addressRepository.delete(addressFromDb);
		return "Address deleted successfully";
	}
}
