package transform;

def closure = { List<io.vertx.groovy.core.Vertx> vertx_ ->
  vertx_[0].eventBus().send("the-address", true)
}

closure([vertx] as List)
