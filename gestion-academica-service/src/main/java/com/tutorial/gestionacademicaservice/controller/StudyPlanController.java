package com.tutorial.gestionacademicaservice.controller;

import com.tutorial.gestionacademicaservice.entity.StudyPlan;
import com.tutorial.gestionacademicaservice.service.StudyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("planes-de-estudio")
public class StudyPlanController {

    @Autowired
    private StudyPlanService studyPlanService;

    @GetMapping("/carrera/{id}")
    public List<StudyPlan> obtenerPlanesPorCarrera(@PathVariable int id){
        return studyPlanService.obtenerPlanesPorCarrera(id);
    }

    @GetMapping
    public List<StudyPlan> getAllStudyPlans() {
        return studyPlanService.getAllStudyPlans();
    }

    @GetMapping("/{id}")
    public StudyPlan getStudyPlanById(@PathVariable Integer id) {
        return studyPlanService.getStudyPlanById(id);
    }

    @PostMapping
    public StudyPlan createStudyPlan(@RequestBody StudyPlan studyPlan) {
        return studyPlanService.createStudyPlan(studyPlan);
    }

    @PutMapping("/{id}")
    public StudyPlan updateStudyPlan(@PathVariable Integer id, @RequestBody StudyPlan studyPlan) {
        return studyPlanService.updateStudyPlan(id, studyPlan);
    }

    @DeleteMapping("/{id}")
    public void deleteStudyPlan(@PathVariable Integer id) {
        studyPlanService.deleteStudyPlan(id);
    }
}
