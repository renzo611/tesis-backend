package com.tutorial.aulaservice.controller;

import com.tutorial.aulaservice.DTO.ClassroomDTO;
import com.tutorial.aulaservice.entity.Classroom;
import com.tutorial.aulaservice.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/aula")
public class ClassroomController {

    @Autowired
    ClassRoomService classRoomService;

    @GetMapping("/lista")
    public ResponseEntity<List<Classroom>> getAll() {
        List<Classroom> classrooms = classRoomService.findAll();
        if(classrooms.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        Optional<Classroom> classroom = classRoomService.findById(id);
        if(classroom.isEmpty())
            return ResponseEntity.badRequest().body("El aula solicitada no existe");
        return ResponseEntity.ok(classroom.get());
    }

    @PostMapping("/nuevo")
    public ResponseEntity<?> save(@RequestBody ClassroomDTO classroomDTO){
        try {
            return ResponseEntity.ok(classRoomService.save(classroomDTO));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable int id,@RequestBody ClassroomDTO classroomDTO){
        try {
            return ResponseEntity.ok(classRoomService.update(id,classroomDTO));
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> save(@PathVariable int id){
        try {
            classRoomService.delete(id);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}