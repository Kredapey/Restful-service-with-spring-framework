package aston.bootcamp.controllers;

import aston.bootcamp.dto.TypeIncomingDto;
import aston.bootcamp.dto.TypeOutgoingDto;
import aston.bootcamp.dto.TypeUpdateDto;
import aston.bootcamp.exceptions.NotFoundException;
import aston.bootcamp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {
    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<TypeOutgoingDto>> findAllTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getAllTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TypeOutgoingDto> findTypeById(@PathVariable(name = "id") Long id) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(typeService.getByTypeId(id));
    }

    @PostMapping
    public ResponseEntity<TypeOutgoingDto> createType(@RequestBody TypeIncomingDto type) {
        TypeOutgoingDto created = typeService.createType(type);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TypeOutgoingDto> updateType(@PathVariable(name = "id") Long id,
                                                      @RequestBody TypeUpdateDto updatedType)
            throws NotFoundException {
        TypeOutgoingDto updated = typeService.updateType(id, updatedType);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTypeById(@PathVariable(name = "id") Long id) throws NotFoundException {
        typeService.deleteByTypeId(id);
        return ResponseEntity.noContent().build();
    }
}
