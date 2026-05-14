package kaknnea.java.redbox.service;

import kaknnea.java.redbox.dto.RoleDtoRequest;
import kaknnea.java.redbox.dto.RoleDtoResponse;

import java.util.List;

public interface RoleService {

    RoleDtoResponse addRole(RoleDtoRequest roleDtoRequest);

    RoleDtoResponse getRoleById(Long id);

    List<RoleDtoResponse> getAllRole();

    RoleDtoResponse updateRole(Long id, RoleDtoRequest request);

    void deletedRole(Long id);
}
