package kaknnea.java.redbox.service.impl;

import kaknnea.java.redbox.dto.RoleDtoRequest;
import kaknnea.java.redbox.dto.RoleDtoResponse;
import kaknnea.java.redbox.entity.Role;
import kaknnea.java.redbox.exception.APIException;
import kaknnea.java.redbox.repositoty.RoleRepository;
import kaknnea.java.redbox.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper){
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RoleDtoResponse addRole(RoleDtoRequest roleDtoRequest) {
        boolean existByName = roleRepository.existsByName(roleDtoRequest.getName());
        if (existByName) {
            throw new APIException(HttpStatus.CONFLICT, "Role already exists!");
        }
        Role role = new Role();
        role.setName(roleDtoRequest.getName());
        role.setActive(roleDtoRequest.isActive());

        Role savedRole = roleRepository.save(role);
        return modelMapper.map(savedRole, RoleDtoResponse.class);
    }

    @Override
    public RoleDtoResponse getRoleById(Long id){
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role with ID: "+id + " not found"));

        return modelMapper.map(role, RoleDtoResponse.class);
    }

    @Override
    public List<RoleDtoResponse> getAllRole(){
        List<Role> roles = roleRepository.findAll();
        return roles.stream()
                .map((role) -> modelMapper.map(role, RoleDtoResponse.class))
                .collect(Collectors.toList());

    }


    @Override
    public RoleDtoResponse updateRole(Long id, RoleDtoRequest request) {
       Role role = roleRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role with Id:"+id+" not found"));

       role.setName(request.getName());
       role.setActive(request.isActive());

       Role savedRole = roleRepository.save(role);

       return modelMapper.map(savedRole, RoleDtoResponse.class);
    }

    @Override
    public void deletedRole(Long id){
       Role roles = roleRepository.findById(id)
                .orElseThrow(() -> new APIException(HttpStatus.NOT_FOUND, "Role with Id:\"+id+\" not found"));

       roleRepository.deleteById(id);
    }
}
