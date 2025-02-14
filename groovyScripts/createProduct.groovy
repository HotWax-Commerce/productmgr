
def createProduct(){
  Map<String, Object> existingProduct = dispatcher.runSync("findProductService",null,context)

  if(existingProduct) return;





}