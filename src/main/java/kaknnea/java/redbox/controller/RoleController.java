package kaknnea.java.redbox.controller;

import jakarta.validation.Valid;
import kaknnea.java.redbox.dto.RoleDtoRequest;
import kaknnea.java.redbox.dto.RoleDtoResponse;
import kaknnea.java.redbox.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping()
    public ResponseEntity<RoleDtoResponse> addRole(@Valid @RequestBody RoleDtoRequest roleDtoRequest){
        RoleDtoResponse response = roleService.addRole(roleDtoRequest);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<RoleDtoResponse> getRoleById(@PathVariable Long id){
        RoleDtoResponse response = roleService.getRoleById(id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleDtoResponse>> getAllRoles(){
        List<RoleDtoResponse> response = roleService.getAllRole();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleDtoResponse> updateRole(Long id, RoleDtoRequest request){
        RoleDtoResponse response = roleService.updateRole(id, request);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletedRoleById(@PathVariable Long id){
        roleService.deletedRole(id);

        return new  ResponseEntity<>("Deleted role successfully", HttpStatus.OK);
    }
}
