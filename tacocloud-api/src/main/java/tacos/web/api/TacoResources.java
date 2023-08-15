package tacos.web.api;

import java.util.List;

import org.springframework.hateoas.CollectionModel;

public class TacoResources extends CollectionModel<TacoResource> {
  public TacoResources(List<TacoResource> tacoResources) {
    super(tacoResources);
  }
}


//package tacos.web.api;
//
//import java.util.List;
//
//import org.springframework.hateoas.Resources;
//
//public class TacoResources extends Resources<TacoResource> {
//  public TacoResources(List<TacoResource> tacoResources) {
//    super(tacoResources);
//  }
//}