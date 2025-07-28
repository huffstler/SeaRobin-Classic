package org.searobin.server.fish;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/fish")
public class FishController {

  private final FishRepository fishRepository;
  private final FishModelAssembler assembler;

  public FishController(@Autowired FishRepository fishRepository, @Autowired FishModelAssembler assembler) {
    this.fishRepository = fishRepository;
    this.assembler = assembler;
  }

  @GetMapping
  public CollectionModel<EntityModel<Fish>> getAllFish() {

    List<Fish> allFish = fishRepository.findAll();

    List<EntityModel<Fish>> fishList = allFish.stream().map(assembler::toModel).toList();

    return CollectionModel.of(fishList, linkTo(methodOn(FishController.class).getAllFish()).withSelfRel());
  }

  @GetMapping("/{id}")
  public EntityModel<Fish> getFishById(@PathVariable Long id) {
    Fish fish = new Fish();
    try {
      fish = fishRepository.findById(id).orElseThrow(ClassNotFoundException::new);
    } catch (ClassNotFoundException ex) {
      System.out.println("Error here: " + ex.getException());
    }
    return assembler.toModel(fish);
  }

}
