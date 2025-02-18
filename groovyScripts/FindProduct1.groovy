import org.apache.ofbiz.base.util.UtilValidate;
import org.apache.ofbiz.entity.condition.EntityCondition;
import org.apache.ofbiz.entity.condition.EntityOperator;

logInfo("---------------FindProductService called-----------");

def findMethod() {
    def condList = []
    logInfo("---------------FindProductService called-----------");
    String productId = UtilValidate.isNotEmpty(parameters.get("productId")) ? parameters.get("productId") : null;
    String productName = UtilValidate.isNotEmpty(parameters.get("productName")) ? parameters.get("productName") : null;
    String price = UtilValidate.isNotEmpty(parameters.get("price")) ? parameters.get("price") : null;
    String productFeatureId = UtilValidate.isNotEmpty(parameters.get("productFeatureId")) ? parameters.get("productFeatureId") : null;



    if (productId) {
        condList.add(EntityCondition.makeCondition("productId", EntityOperator.EQUALS, productId))
    }

    if (productName) {
        condList.add(EntityCondition.makeCondition("productName", EntityOperator.EQUALS, productName))
    }

    if (price) {
        condList.add(EntityCondition.makeCondition("price", EntityOperator.EQUALS, price))
    }

    if (productFeatureId) {
        condList.add(EntityCondition.makeCondition("productFeatureId", EntityOperator.EQUALS, productFeatureId))
    }

    if (condList.isEmpty()) {
        logInfo("No search criteria provided. Returning empty product list.")
        parameters.put("productList", [])
        return parameters
    }

    def productList = select("productId", "productName", "price", "productFeatureId", "productCategoryId", "productTypeId")
            .from("FindProductView")
            .where(condList)
            .distinct()
            .cursorScrollInsensitive()
            .cache(true)
            .queryList()

    logInfo("Found products: ${productList.size()}")
    parameters.put("productList", productList)
    context.put("productList", productList)
    logInfo("XXXXXXXXXXXXXXXXXX Context XXXXXXXXXXXXX: ${context}")
    logInfo("xxxxxxxxxxxxxxx parameters XXXXXXXXXXXXXXX: ${parameters}")

}
